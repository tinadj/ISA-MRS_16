package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.comparator.RentACarCity;
import org.tim16.booker.comparator.RentACarName;
import org.tim16.booker.comparator.RentACarState;
import org.tim16.booker.comparator.VehiclePrice;
import org.tim16.booker.dto.RACSearchParamsDTO;
import org.tim16.booker.dto.RentACarDTO;
import org.tim16.booker.model.rent_a_car.BranchOffice;
import org.tim16.booker.model.rent_a_car.RentACar;
import org.tim16.booker.model.rent_a_car.RentACarReservation;
import org.tim16.booker.model.rent_a_car.Vehicle;
import org.tim16.booker.model.utility.Destination;
import org.tim16.booker.service.DestinationService;
import org.tim16.booker.service.RacReservationService;
import org.tim16.booker.service.RentACarService;
import org.tim16.booker.service.VehicleService;
import sun.util.resources.CalendarData;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@RestController
@RequestMapping(value = "/api/rent-a-cars")
public class RentACarController {

    @Autowired
    private RentACarService rentACarService;
    @Autowired
    private DestinationService destinationService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private RacReservationService reservationService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('SYS_ADMIN') or hasAuthority('USER')")
    public ResponseEntity<List<RentACar>> getAll() {
        List<RentACar> rentACars = rentACarService.findAll();
        Collections.sort(rentACars, new RentACarName());
        return new ResponseEntity<>(rentACars, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes="application/json")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<RentACar> add(@RequestBody RentACarDTO dto) {
        RentACar rentACar = new RentACar();
        rentACar.setName(dto.getName());
        rentACar.setDescription(dto.getDescription());
        rentACar.setLatitude(dto.getLatitude());
        rentACar.setLongitude(dto.getLongitude());

        Destination destination = destinationService.findByCityAndState(dto.getAddress().getCity(), dto.getAddress().getCity());

        if (destination == null) {
            destination = new Destination();
            destination.setCity(dto.getAddress().getCity());
            destination.setState(dto.getAddress().getState());
            destinationService.create(destination);
        }
        rentACar.setAddress(destination);

        try {
            rentACar = rentACarService.create(rentACar);
            return new ResponseEntity<>(rentACar, HttpStatus.CREATED);
        } catch(Exception e)
        {   // catches duplicate name
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<RentACar> getRentACar(@PathVariable Integer id) {
        RentACar rentACar = rentACarService.findOne(id);

        if (rentACar == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(rentACar, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<RentACar> update(@RequestBody RentACarDTO dto) {

        try {
            RentACar rentACar = rentACarService.findOne(dto.getId());

            rentACar.setName(dto.getName());
            rentACar.setDescription(dto.getDescription());
            rentACar.setLatitude(dto.getLatitude());
            rentACar.setLongitude(dto.getLongitude());

            Destination destination = destinationService.findByCityAndState(dto.getAddress().getCity(), dto.getAddress().getCity());

            if (destination == null) {
                destination = new Destination();
                destination.setCity(dto.getAddress().getCity());
                destination.setState(dto.getAddress().getState());
                destinationService.create(destination);
            }
            rentACar.setAddress(destination);

            return new ResponseEntity<>(rentACarService.update(rentACar), HttpStatus.OK);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}/vehicles", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('RAC_ADMIN') or hasAuthority('USER')")
    public ResponseEntity<List<Vehicle>> getVehicles(@PathVariable Integer id) {
        RentACar rentACar = rentACarService.findOne(id);

        if (rentACar == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Vehicle> vehicles = new ArrayList<>();
        for (Vehicle v: rentACar.getVehicles()) {
            vehicles.add(v);
        }

        Collections.sort(vehicles, new VehiclePrice());
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/branch-offices", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('RAC_ADMIN') or hasAuthority('USER')")
    public ResponseEntity<List<BranchOffice>> getBranchOffices(@PathVariable Integer id) {
        RentACar rentACar = rentACarService.findOne(id);

        if (rentACar == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<BranchOffice> branchOffices = new ArrayList<>();
        for (BranchOffice bo: rentACar.getBranchOffices()) {
            branchOffices.add(bo);
        }

        return new ResponseEntity<>(branchOffices, HttpStatus.OK);

    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<RentACar>> searchRegisteredUser(@RequestBody RACSearchParamsDTO dto) {
        List<RentACar> rentACars = rentACarService.findAll();
        List<RentACar> result = rentACarService.findAll();

        if (!dto.getName().isEmpty()) {
            for (RentACar rentACar: rentACars) {
                if (!rentACar.getName().toLowerCase().contains(dto.getName().toLowerCase())) {
                    result.remove(rentACar);
                }
            }
        }

        if (!dto.getCity().isEmpty()) {
            for (RentACar rentACar: rentACars) {
                if (!rentACar.getAddress().getCity().toLowerCase().contains(dto.getCity().toLowerCase())) {
                    result.remove(rentACar);
                }
            }
        }

        if (!dto.getState().isEmpty()) {
            for (RentACar rentACar: rentACars) {
                if (!rentACar.getAddress().getState().toLowerCase().contains(dto.getState().toLowerCase())) {
                    result.remove(rentACar);
                }
            }
        }

        if (dto.getPickUpDate() != null && dto.getReturnDate() != null) {
            for (RentACarReservation reservation: reservationService.findAll()) {
                Date returnDate = calculateReturnDate(reservation);

                if (reservation.getPickUpDate().before(dto.getReturnDate()) && returnDate.after(dto.getPickUpDate())) {
                    result.remove(reservation.getVehicle().getRentACar());
                }
            }
        }

        if (dto.getCriteria() == 0) {
            Collections.sort(result, new RentACarName());
        }
        else if (dto.getCriteria() == 1) {
            Collections.sort(result, new RentACarName());
            Collections.reverse(result);
        }
        else if (dto.getCriteria() == 2) {
            Collections.sort(result, new RentACarCity());
        }
        else if (dto.getCriteria() == 3) {
            Collections.sort(result, new RentACarCity());
            Collections.reverse(result);
        }
        else if (dto.getCriteria() == 4) {
            Collections.sort(result, new RentACarState());
        }
        else if (dto.getCriteria() == 5) {
            Collections.sort(result, new RentACarState());
            Collections.reverse(result);
        }
        else {
            Collections.sort(result, new RentACarName());
        }


        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private Date calculateReturnDate(RentACarReservation reservation) {
        Calendar c = Calendar.getInstance();
        c.setTime(reservation.getPickUpDate());
        c.add(Calendar.DATE, reservation.getDays());
        return c.getTime();
    }


}
