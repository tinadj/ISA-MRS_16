package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.tim16.booker.controller.VehicleController;
import org.tim16.booker.dto.VehicleDTO;
import org.tim16.booker.model.admins.RentACarAdmin;
import org.tim16.booker.model.rent_a_car.QuickRACReservation;
import org.tim16.booker.model.rent_a_car.RentACar;
import org.tim16.booker.model.rent_a_car.Vehicle;
import org.tim16.booker.repository.RentACarRepository;
import org.tim16.booker.repository.VehicleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class VehicleService  {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private RentACarRepository rentACarRepository;
    @Autowired
    private QuickRACReservationService quickRACReservationService;


    public Vehicle findOne(Integer id) {
        return vehicleRepository.getOne(id);
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Transactional(readOnly = false)
    public Vehicle create(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public Vehicle update(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean remove(Integer id) {
        try {
            Vehicle vehicle = vehicleRepository.getOne(id);
            if (vehicle != null) {
                RentACar rentACar = rentACarRepository.getOne(vehicle.getRentACar().getId());

                if (rentACar != null) {
                    rentACar.removeVehicle(vehicle.getId());

                    List<QuickRACReservation> quickRACReservations = quickRACReservationService.findAll();
                    for (QuickRACReservation quickReservation : quickRACReservations) {
                        if (quickReservation.getVehicle().getId().equals(vehicle.getId())) {
                            quickRACReservationService.remove(quickReservation.getId());
                        }
                    }

                    rentACarRepository.save(rentACar);
                    vehicleRepository.deleteById(id);
                    return true;
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return false;
                }
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        } catch (EntityNotFoundException ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public ResponseEntity<Vehicle> editInfo(VehicleDTO dto) {
        try
        {
            Vehicle vehicle = vehicleRepository.getOne(dto.getId());

            vehicle.setName(dto.getName());
            vehicle.setBrand(dto.getBrand());
            vehicle.setModel(dto.getModel());
            vehicle.setSeatsNum(dto.getSeatsNum());
            vehicle.setProductionYear(dto.getProductionYear());
            vehicle.setDescription(dto.getDescription());
            vehicle.setType(VehicleController.intToVehicleType(dto.getType()));
            RentACar rentACar = rentACarRepository.getOne(dto.getRentACar().getId());
            vehicle.setRentACar(rentACar);

            rentACar.removeVehicle(dto.getId());
            rentACar.addVehicle(vehicle);
            rentACarRepository.save(rentACar);

            return new ResponseEntity<>(vehicleRepository.save(vehicle), HttpStatus.OK);
        }
        catch (EntityNotFoundException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
