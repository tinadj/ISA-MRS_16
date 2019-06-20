package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.dto.BranchOfficeDTO;
import org.tim16.booker.model.rent_a_car.BranchOffice;
import org.tim16.booker.model.rent_a_car.RentACar;
import org.tim16.booker.model.utility.Destination;
import org.tim16.booker.service.BranchOfficeService;
import org.tim16.booker.service.DestinationService;
import org.tim16.booker.service.RentACarService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "api/branch-offices")
public class BranchOfficeController {

    @Autowired
    private BranchOfficeService branchOfficeService;

    @Autowired
    private RentACarService rentACarService;

    @Autowired
    private DestinationService destinationService;

    @GetMapping(path = "/all")
    public ResponseEntity<List<BranchOffice>> getAll() {
        return new ResponseEntity<>(branchOfficeService.findAll(), HttpStatus.OK);
    }

    @PostMapping(path = "/add", consumes="application/json")
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<BranchOffice> add(@RequestBody BranchOfficeDTO dto) {
        // a new branch office must have rent a car defined
        if (dto.getRentACar() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RentACar rentACar = rentACarService.findOne(dto.getRentACar().getId());

        if (rentACar.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BranchOffice branchOffice = new BranchOffice();
        branchOffice.setName(dto.getName());
        branchOffice.setLatitude(dto.getLatitude());
        branchOffice.setLongitude(dto.getLongitude());

        Destination destination = destinationService.findByCityAndState(dto.getAddress().getCity(), dto.getAddress().getCity());

        if (destination == null) {
            destination = new Destination();
            destination.setCity(dto.getAddress().getCity());
            destination.setState(dto.getAddress().getState());
            destinationService.create(destination);
        }
        branchOffice.setAddress(destination);
        rentACar.addBranchOffice(branchOffice);
        branchOffice.setRentACar(rentACar);

        rentACarService.update(rentACar);
        return new ResponseEntity<>(branchOffice, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<BranchOffice> getBranchOffice(@PathVariable Integer id)
    {
        BranchOffice branchOffice = branchOfficeService.findOne(id);

        if (branchOffice == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(branchOffice, HttpStatus.OK);
    }

    @DeleteMapping(path = "/remove/{id}")
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<List<BranchOffice>> removeBranchOffice(@PathVariable Integer id)
    {
        HttpStatus status = branchOfficeService.remove(id);
        return new ResponseEntity<>(branchOfficeService.findAll(), status);
    }

    @PutMapping(path = "/update", consumes = "application/json")
    @PreAuthorize("hasAuthority('RAC_ADMIN')")
    public ResponseEntity<BranchOffice> updateBranchOffice(@RequestBody BranchOfficeDTO dto)
    {
        return branchOfficeService.editInfo(dto);
    }
}
