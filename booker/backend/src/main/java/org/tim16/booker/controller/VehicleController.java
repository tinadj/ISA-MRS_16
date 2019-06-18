package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.comparator.*;
import org.tim16.booker.dto.VehicleDTO;
import org.tim16.booker.dto.VehicleSearchParamsDTO;
import org.tim16.booker.model.rent_a_car.*;
import org.tim16.booker.model.users.RegisteredUser;
import org.tim16.booker.model.utility.Rate;
import org.tim16.booker.service.*;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@RestController
@RequestMapping(value = "/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private RentACarService rentACarService;

    @Autowired
    private RacReservationService reservationService;

    @Autowired
    private BranchOfficeService branchOfficeService;

    @Autowired
    private RateService rateService;

    @GetMapping(path = "/all")
    public ResponseEntity<List<Vehicle>> getAll() {
        return new ResponseEntity<>(vehicleService.findAll(), HttpStatus.OK);
    }

    @PostMapping(path = "/add", consumes="application/json")
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<Vehicle> add(@RequestBody VehicleDTO dto) {
        // a new vehicle must have rent a car defined
        if (dto.getRentACar() == null || dto.getCurrentlyIn() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RentACar rentACar = rentACarService.findOne(dto.getRentACar().getId());
        BranchOffice branchOffice = branchOfficeService.findOne(dto.getCurrentlyIn());
        if (rentACar.getId() == null || branchOffice.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setVersion(0l);
        vehicle.setName(dto.getName());
        vehicle.setBrand(dto.getBrand());
        vehicle.setDescription(dto.getDescription());
        vehicle.setModel(dto.getModel());
        vehicle.setProductionYear(dto.getProductionYear());
        vehicle.setSeatsNum(dto.getSeatsNum());
        vehicle.setPrice(dto.getPrice());
        vehicle.setDiscount(0);
        vehicle.setType(intToVehicleType(dto.getType()));
        rentACar.addVehicle(vehicle);
        vehicle.setRentACar(rentACar);
        vehicle.setCurrentlyIn(branchOffice);

        vehicle = vehicleService.create(vehicle);
        rentACarService.update(rentACar);
        return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable Integer id)
    {
        Vehicle vehicle = vehicleService.findOne(id);

        if (vehicle == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }

    @DeleteMapping(path = "/remove/{id}")
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<List<Vehicle>> removeVehicle(@PathVariable Integer id)
    {
        Vehicle vehicle = vehicleService.findOne(id);

        if (vehicle == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RentACar rentACar = rentACarService.findOne(vehicle.getRentACar().getId());
        rentACar.removeVehicle(vehicle.getId());

        rentACarService.update(rentACar);
        vehicleService.remove(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/update", consumes = "application/json")
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody VehicleDTO dto)
    {
        try
        {
            Vehicle vehicle = vehicleService.findOne(dto.getId());

            vehicle.setName(dto.getName());
            vehicle.setBrand(dto.getBrand());
            vehicle.setModel(dto.getModel());
            vehicle.setSeatsNum(dto.getSeatsNum());
            vehicle.setProductionYear(dto.getProductionYear());
            vehicle.setDescription(dto.getDescription());
            vehicle.setType(intToVehicleType(dto.getType()));
            RentACar rentACar = rentACarService.findOne(dto.getRentACar().getId());
            vehicle.setRentACar(rentACar);

            rentACar.removeVehicle(dto.getId());
            rentACar.addVehicle(vehicle);
            rentACarService.update(rentACar);

            return new ResponseEntity<>(vehicleService.update(vehicle), HttpStatus.OK);

        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/is-reserved/{id}")
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public HttpStatus isReserved(@PathVariable Integer id) {
        try
        {
            for (RentACarReservation reservation: reservationService.findAll()) {
                if (reservation.getVehicle().getId().equals(id))
                    return HttpStatus.FORBIDDEN;
            }
            return HttpStatus.OK;
        }
        catch (EntityNotFoundException e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PutMapping(path = "/discount/{id}/{discount}")
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<HttpStatus> setDiscount(@PathVariable Integer id, @PathVariable Integer discount) {
        Vehicle vehicle = vehicleService.findOne(id);

        if (vehicle == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        vehicle.setDiscount(discount);
        vehicleService.update(vehicle);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/update-vehicle-location/{id}/{officeID}")
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public HttpStatus updateVehicleLocation(@PathVariable Integer id, @PathVariable Integer officeID) {
        Vehicle vehicle = vehicleService.findOne(id);
        BranchOffice branchOffice = branchOfficeService.findOne(officeID);
        if (vehicle == null || branchOffice == null)
            return HttpStatus.NOT_FOUND;

        vehicle.setCurrentlyIn(branchOffice);
        vehicleService.update(vehicle);
        return HttpStatus.OK;
    }

    @PostMapping(path = "/search")
    public ResponseEntity<List<Vehicle>> search(@RequestBody VehicleSearchParamsDTO dto) {
        RentACar rentACar = rentACarService.findOne(dto.getRacID());

        if (rentACar == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Vehicle> vehicles = getVehicles(dto.getRacID());
        List<Vehicle> result = getVehicles(dto.getRacID());

        if (dto.getPickUpLocation() != null) {
            result = searchByPickUpLocation(vehicles, result, dto.getPickUpLocation());
        }

        if (dto.getPassangerNum() != null) {
            result = searchByPassangerNum(vehicles, result, dto.getPassangerNum());
        }

        if (dto.getVehicleType() != null) {
            result = searchByVehicleType(vehicles, result, dto.getVehicleType());
        }

        if (dto.getMinPrice() != 0 && dto.getMaxPrice()!= 0) {
            result = searchByPrice(vehicles, result, (float)dto.getMinPrice(), (float)dto.getMinPrice());
        }

        if (dto.getPickUpDate() != null && dto.getDropOffDate() != null) {
            result = searchByDates(vehicles, result, dto.getPickUpDate(), dto.getDropOffDate());
        }

        result = sort(result, dto.getCriteria());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "rating/{id}")
    public ResponseEntity<Float> getRating(@PathVariable Integer id) {
        Vehicle vehicle = vehicleService.findOne(id);

        if (vehicle == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(getAverageRating(vehicle.getRating()), HttpStatus.OK);
    }

    @PostMapping(path = "rate/{id}/{rateValue}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Float> rateVehicle(@PathVariable Integer id, @PathVariable Integer rateValue) {
        Vehicle vehicle = vehicleService.findOne(id);
        RegisteredUser user = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (vehicle == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // Ako je korisnik vec ocenio, promeni vrednost te ocene
        boolean rated = false;
        for (Rate rate : vehicle.getRating()) {
            if (rate.getUser().getId().equals(user.getId())) {
                rate.setRateValue(rateValue);
                rateService.update(rate);
                rated = true;
                break;
            }
        }

        // Ako nije ocenio, dodaj novu ocenu
        if (!rated) {
            Rate rate = new Rate(user, rateValue);
            vehicle.getRating().add(rate);
            rateService.create(rate);
            vehicleService.update(vehicle);
        }

        return new ResponseEntity<>(getAverageRating(vehicle.getRating()), HttpStatus.OK);
    }

    /*
    Racuna prosecnu ocenu
     */
    private Float getAverageRating(Set<Rate> rates) {
        Float sum = 0f;
        for (Rate rate : rates) {
            sum += rate.getRateValue();
        }

        if (rates.size() != 0)
            sum = sum / rates.size();

        return sum;
    }

    /*
    Konvertovanje int vrednosti u tip vozila
     */
    private VehicleType intToVehicleType(Integer i)
    {
        switch (i)
        {
            case 0 : return VehicleType.ECONOMY;
            case 1 : return VehicleType.COMPACT;
            case 2 : return VehicleType.MID_SIZE;
            case 3 : return VehicleType.FULL_SIZE;
            case 4 : return VehicleType.LUXURY;
            case 5 : return VehicleType.MINIVAN;
            case 6 : return VehicleType.SUV;
            default: return VehicleType.UNDEFINED;
        }
    }

    /*
    Lista vozila u odredjenom rent a car servisu
     */
    private List<Vehicle> getVehicles(Integer racId) {
        RentACar rentACar = rentACarService.findOne(racId);
        List<Vehicle> vehicles = new ArrayList<>();

        if (rentACar != null) {
            for (Vehicle v : rentACar.getVehicles()) {
                vehicles.add(v);
            }
        }
        return vehicles;
    }

    /*
    Provera da li je vozilo rezervisano u odrednjenom periodu
     */
    private boolean isReserved(Vehicle vehicle, Date pickUp, Date dropOff) {
        for (RentACarReservation reservation: reservationService.findAll()) {
            if (reservation.getVehicle().getId().equals(vehicle.getId())) {
                Date returnDate = calculateReturnDate(reservation);

                if (reservation.getPickUpDate().before(dropOff) && returnDate.after(pickUp)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
    Izracunava datum povratka vozila na osnovu datuma preuzimanja i broja dana
     */
    private Date calculateReturnDate(RentACarReservation reservation) {
        Calendar c = Calendar.getInstance();
        c.setTime(reservation.getPickUpDate());
        c.add(Calendar.DATE, reservation.getDays());
        return c.getTime();
    }

    /*
    Pretraga po lokaciji preuzimanja vozila
     */
    private List<Vehicle> searchByPickUpLocation(List<Vehicle> vehicles, List<Vehicle> result, Integer pickUpLocation) {
        for (Vehicle vehicle: vehicles) {
            if (!vehicle.getCurrentlyIn().getId().equals(pickUpLocation))
                result.remove(vehicle);
        }
        return result;
    }

    /*
    Pretraga po broju putnika
     */
    private List<Vehicle> searchByPassangerNum(List<Vehicle> vehicles, List<Vehicle> result, Integer passangerNum) {
        for (Vehicle vehicle: vehicles) {
            if (vehicle.getSeatsNum() < passangerNum) {
                result.remove(vehicle);
            }
        }
        return result;
    }

    /*
    Pretraga po tipu vozila
     */
    private List<Vehicle>searchByVehicleType(List<Vehicle> vehicles, List<Vehicle> result, Integer vehicleType) {
        VehicleType type = intToVehicleType(vehicleType);

        for (org.tim16.booker.model.rent_a_car.Vehicle vehicle: vehicles) {
            if (vehicle.getType() != type) {
                result.remove(vehicle);
            }
        }
        return result;
    }

    /*
    Pretraga po ceni
     */
    private List<Vehicle> searchByPrice(List<Vehicle> vehicles, List<Vehicle> result, Float minPrice, Float maxPrice) {
        for (Vehicle vehicle: vehicles) {
            if (vehicle.getPrice() < minPrice || vehicle.getPrice() >= maxPrice) {
                result.remove(vehicle);
            }
        }
        return result;
    }

    /*
    Pretraga po datumu preuzimanja i vracanja vozila
     */
    private List<Vehicle> searchByDates(List<Vehicle> vehicles, List<Vehicle> result, Date pickUpDate, Date dropOffDate) {
        for (Vehicle vehicle: vehicles) {
            if (isReserved(vehicle, pickUpDate, dropOffDate)) {
                result.remove(vehicle);
            }
        }
        return result;
    }

    /*
    Soritranje liste na osnovu kriterijuma
     */
    private List<Vehicle> sort(List<Vehicle> result, Integer criteria) {
        if (criteria == 0) {
            Collections.sort(result, new VehiclePrice());
        }
        else if (criteria == 1) {
            Collections.sort(result, new VehiclePrice());
            Collections.reverse(result);
        }
        else if (criteria == 2) {
            Collections.sort(result, new VehicleYear());
        }
        else if (criteria == 3) {
            Collections.sort(result, new VehicleYear());
            Collections.reverse(result);
        }
        else if (criteria == 4) {
            Collections.sort(result, new VehicleNumOfPassangers());
        }
        else if (criteria == 5) {
            Collections.sort(result, new VehicleNumOfPassangers());
            Collections.reverse(result);
        }
        else {
            Collections.sort(result, new VehicleYear());
        }
        return  result;
    }
}
