package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.tim16.booker.dto.BranchOfficeDTO;
import org.tim16.booker.model.admins.RentACarAdmin;
import org.tim16.booker.model.rent_a_car.BranchOffice;
import org.tim16.booker.model.rent_a_car.RentACar;
import org.tim16.booker.model.rent_a_car.Vehicle;
import org.tim16.booker.model.users.RegisteredUser;
import org.tim16.booker.model.utility.Destination;
import org.tim16.booker.repository.BranchOfficeRepository;
import org.tim16.booker.repository.DestinationRepository;
import org.tim16.booker.repository.RentACarRepository;
import org.tim16.booker.repository.VehicleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BranchOfficeService {

    @Autowired
    private BranchOfficeRepository branchOfficeRepository;
    @Autowired
    private RentACarRepository rentACarRepository;
    @Autowired
    private DestinationRepository destinationRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    public BranchOffice findOne(Integer id) {
        return branchOfficeRepository.getOne(id);
    }

    public List<BranchOffice> findAll() {
        return branchOfficeRepository.findAll();
    }

    @Transactional(readOnly = false)
    public BranchOffice create(BranchOffice branchOffice) {
        return branchOfficeRepository.save(branchOffice);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public BranchOffice update(BranchOffice branchOffice) {
        return branchOfficeRepository.save(branchOffice);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public HttpStatus remove(Integer id) {
        RentACarAdmin user = (RentACarAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getUsername().equals("rac2")) {
            try {
                 Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        try {
            BranchOffice vehicle = branchOfficeRepository.getOne(id);

            if (vehicle != null) {
                RentACar rentACar = rentACarRepository.getOne(vehicle.getRentACar().getId());

                if (rentACar != null) {

                    for (Vehicle v : vehicleRepository.findAll()) {
                        if (v.getCurrentlyIn().getId().equals(id))
                            return HttpStatus.FORBIDDEN;
                    }

                    rentACar.removeBranchOffice(vehicle.getId());

                    rentACarRepository.save(rentACar);
                    branchOfficeRepository.deleteById(id);

                    return HttpStatus.OK;
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return HttpStatus.NOT_FOUND;
                }
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return HttpStatus.NOT_FOUND;
            }
        } catch (EntityNotFoundException ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return HttpStatus.NOT_FOUND;
        }

    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public ResponseEntity<BranchOffice> editInfo(BranchOfficeDTO dto) {
        try
        {
            BranchOffice branchOffice = branchOfficeRepository.getOne(dto.getId());

            branchOffice.setName(dto.getName());
            branchOffice.setLatitude(dto.getLatitude());
            branchOffice.setLongitude(dto.getLongitude());

            RentACar rentACar = rentACarRepository.getOne(dto.getRentACar().getId());
            branchOffice.setRentACar(rentACar);

            Destination destination = destinationRepository.findByCityAndState(dto.getAddress().getCity(), dto.getAddress().getCity());

            if (destination == null) {
                destination = new Destination();
                destination.setCity(dto.getAddress().getCity());
                destination.setState(dto.getAddress().getState());
                destinationRepository.save(destination);
            }
            branchOffice.setAddress(destination);

            rentACar.removeBranchOffice(dto.getId());
            rentACar.addBranchOffice(branchOffice);
            rentACarRepository.save(rentACar);

            return new ResponseEntity<>(branchOfficeRepository.save(branchOffice), HttpStatus.OK);
        }
        catch (EntityNotFoundException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
