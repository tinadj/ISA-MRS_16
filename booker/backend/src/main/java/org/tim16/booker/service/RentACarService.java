package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tim16.booker.dto.DestinationDTO;
import org.tim16.booker.dto.RentACarDTO;
import org.tim16.booker.model.rent_a_car.RentACar;
import org.tim16.booker.model.utility.Destination;
import org.tim16.booker.repository.DestinationRepository;
import org.tim16.booker.repository.RentACarRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class RentACarService {

    @Autowired
    private RentACarRepository rentACarRepository;
    @Autowired
    private DestinationRepository destinationRepository;

    public RentACar findOne(Integer id) { return rentACarRepository.getOne(id); }

    public RentACar findByName(String name) { return rentACarRepository.findByName(name); }

    public List<RentACar> findAll() { return rentACarRepository.findAll(); }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public RentACar create(RentACar rentACar) {
        return rentACarRepository.save(rentACar);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void remove(Integer id) {
        rentACarRepository.deleteById(id);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void update(RentACar rentACar) { rentACarRepository.save(rentACar);}

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public ResponseEntity<RentACar> editInfo(RentACarDTO dto) {
        try {
            RentACar rentACar = rentACarRepository.getOne(dto.getId());

            rentACar.setName(dto.getName());
            rentACar.setDescription(dto.getDescription());
            rentACar.setLatitude(dto.getLatitude());
            rentACar.setLongitude(dto.getLongitude());
            rentACar.setAddress(generateDestination(dto.getAddress()));

            return new ResponseEntity<>(rentACarRepository.save(rentACar), HttpStatus.OK);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*
    Vraca novu ili vec postojecu destinaciju na kojoj se nalazi rent a ca r servis
     */
    private Destination generateDestination(DestinationDTO dto) {
        Destination destination = destinationRepository.findByCityAndState(dto.getCity(), dto.getCity());

        if (destination == null) {
            destination = new Destination();
            destination.setCity(dto.getCity());
            destination.setState(dto.getState());
            destinationRepository.save(destination);
        }
        return destination;
    }
}
