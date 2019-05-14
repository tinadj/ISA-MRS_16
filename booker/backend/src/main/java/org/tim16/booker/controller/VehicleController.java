package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.comparator.*;
import org.tim16.booker.dto.VehicleDTO;
import org.tim16.booker.dto.VehicleSearchParamsDTO;
import org.tim16.booker.model.rent_a_car.*;
import org.tim16.booker.service.RacReservationService;
import org.tim16.booker.service.RentACarService;
import org.tim16.booker.service.VehicleService;

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

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> getAll() {
        return new ResponseEntity<>(vehicleService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes="application/json")
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<Vehicle> add(@RequestBody VehicleDTO dto) {
        // a new vehicle must have rent a car defined
        if (dto.getRentACar() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RentACar rentACar = rentACarService.findOne(dto.getRentACar().getId());

        if (rentACar.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setName(dto.getName());
        vehicle.setBrand(dto.getBrand());
        vehicle.setDescription(dto.getDescription());
        vehicle.setModel(dto.getModel());
        vehicle.setProductionYear(dto.getProductionYear());
        vehicle.setSeatsNum(dto.getSeatsNum());
        vehicle.setPrice(dto.getPrice());
        vehicle.setType(intToVehicleType(dto.getType()));
        rentACar.addVehicle(vehicle);
        vehicle.setRentACar(rentACar);

        vehicle = vehicleService.create(vehicle);
        rentACarService.update(rentACar);
        return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable Integer id)
    {
        Vehicle vehicle = vehicleService.findOne(id);

        if (vehicle == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
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

    @RequestMapping(value = "/update", method =  RequestMethod.PUT, consumes = "application/json")
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody VehicleDTO dto)
    {
        try
        {
            Vehicle vehicle = vehicleService.findOne(dto.getId());

            vehicle.setName(dto.getName());
            vehicle.setBrand(dto.getBrand());
            vehicle.setModel(dto.getModel());
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

    @RequestMapping(value = "/is-reserved/{id}", method =  RequestMethod.GET)
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public HttpStatus isReserved(@PathVariable Integer id) {
        try
        {
            Vehicle vehicle = vehicleService.findOne(id);

            for (RentACarReservation reservation: reservationService.findAll()) {
                if (reservation.getVehicle().getId() == id)
                    return HttpStatus.FORBIDDEN;
            }
            return HttpStatus.OK;
        }
        catch (EntityNotFoundException e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<Vehicle>> searchRegisteredUser(@RequestBody VehicleSearchParamsDTO dto) {
        RentACar rentACar = rentACarService.findOne(dto.getRacID());

        if (rentACar == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Vehicle> vehicles = getVehicles(dto.getRacID());
        List<Vehicle> result = getVehicles(dto.getRacID());

        if (dto.getVehicleType() != null) {
            for (Vehicle vehicle: vehicles) {
                VehicleType type = intToVehicleType(dto.getVehicleType());
                if (vehicle.getType() != type) {
                    result.remove(vehicle);
                }
            }
        }

        if (dto.getMinPrice() != 0 && dto.getMaxPrice()!= 0) {
            for (Vehicle vehicle: vehicles) {
                if (vehicle.getPrice() < dto.getMinPrice() || vehicle.getPrice() >= dto.getMaxPrice()) {
                    result.remove(vehicle);
                }
            }
        }

        if (dto.getPickUpDate() != null && dto.getDropOffDate() != null) {
            for (Vehicle vehicle: vehicles) {
                if (isReserved(vehicle, dto.getPickUpDate(), dto.getDropOffDate())) {
                    result.remove(vehicle);
                }
            }
        }

        if (dto.getCriteria() == 0) {
            Collections.sort(result, new VehiclePrice());
        }
        else if (dto.getCriteria() == 1) {
            Collections.sort(result, new VehiclePrice());
            Collections.reverse(result);
        }
        else if (dto.getCriteria() == 2) {
            Collections.sort(result, new VehicleYear());
        }
        else if (dto.getCriteria() == 3) {
            Collections.sort(result, new VehicleYear());
            Collections.reverse(result);
        }
        else if (dto.getCriteria() == 4) {
            Collections.sort(result, new VehicleNumOfPassangers());
        }
        else if (dto.getCriteria() == 5) {
            Collections.sort(result, new VehicleNumOfPassangers());
            Collections.reverse(result);
        }
        else {
            Collections.sort(result, new VehicleYear());
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

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

    private boolean isReserved(Vehicle vehicle, Date pickUp, Date dropOff) {
        for (RentACarReservation reservation: reservationService.findAll()) {
            if (reservation.getVehicle().getId() == vehicle.getId()) {
                Date returnDate = calculateReturnDate(reservation);

                if (reservation.getPickUpDate().before(dropOff) && returnDate.after(pickUp)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Date calculateReturnDate(RentACarReservation reservation) {
        Calendar c = Calendar.getInstance();
        c.setTime(reservation.getPickUpDate());
        c.add(Calendar.DATE, reservation.getDays());
        return c.getTime();
    }
}
