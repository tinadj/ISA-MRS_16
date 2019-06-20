package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.tim16.booker.controller.RentACarController;
import org.tim16.booker.dto.RacReservationDTO;
import org.tim16.booker.model.rent_a_car.BranchOffice;
import org.tim16.booker.model.rent_a_car.RentACarReservation;
import org.tim16.booker.model.rent_a_car.Vehicle;
import org.tim16.booker.model.users.RegisteredUser;
import org.tim16.booker.model.users.Reservation;
import org.tim16.booker.repository.BranchOfficeRepository;
import org.tim16.booker.repository.RacReservationRepository;
import org.tim16.booker.repository.ReservationRepository;
import org.tim16.booker.repository.VehicleRepository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BranchOfficeRepository branchOfficeRepository;

    @Autowired
    private RacReservationRepository racReservationRepository;

    @Autowired
    private UserService userService;

    public Reservation findOne(Integer id) { return reservationRepository.getOne(id); }

    public List<Reservation> findAll() { return reservationRepository.findAll(); }

    public void remove(Integer id) {
        reservationRepository.deleteById(id);
    }

    public void update(Reservation reservation) { reservationRepository.save(reservation); }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public ResponseEntity<Boolean> reserveVehicle(RacReservationDTO dto) {
        Reservation reservation = new Reservation();
        RegisteredUser user = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        RentACarReservation rentACarReservation = setUpRACReservation(dto);
        if (rentACarReservation == null) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

        user.getReservations().add(reservation);
        reservation.setUser(user);

        racReservationRepository.save(rentACarReservation);
        rentACarReservation.setReservation(reservation);
        reservation.setRentACarReservation(rentACarReservation);

        reservationRepository.save(reservation);
        userService.save(user);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    // Incijalizacija rent a car rezervacije
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public RentACarReservation setUpRACReservation(RacReservationDTO dto) {
        RentACarReservation rentACarReservation = new RentACarReservation();

        Vehicle vehicle = vehicleRepository.getOne(dto.getVehicle());
        BranchOffice pickUp = branchOfficeRepository.getOne(dto.getPickUpLocation());
        BranchOffice dropOff = branchOfficeRepository.getOne(dto.getDropOffLocation());

        if (vehicle == null || pickUp == null || dropOff == null)
            return null;

        if (!isVehicleAvailable(vehicle.getId(), dto.getPickUpDate(), dto.getDays()))
            return null;

        rentACarReservation.setVehicle(vehicle);
        rentACarReservation.setPickUpDate(dto.getPickUpDate());
        rentACarReservation.setDays(dto.getDays());
        rentACarReservation.setPassangerNum(dto.getPassangerNum());
        rentACarReservation.setPickUpLocation(pickUp);
        rentACarReservation.setDropOffLocation(dropOff);
        rentACarReservation.setRentACar(vehicle.getRentACar().getName());
        rentACarReservation.setVehicleChecked(false);
        rentACarReservation.setTotalPrice(vehicle.getPrice() * dto.getDays());

        return rentACarReservation;
    }

    /*
    Provera da li je neko u medjuvremenu rezervisao vozilo
     */
    private boolean isVehicleAvailable(Integer id, Date pickUp, Integer days) {
        Date returnDate = RentACarController.calculateReturnDate(pickUp, days);
        for (RentACarReservation reservation: racReservationRepository.findAll()) {
            if (reservation.getVehicle().getId().equals(id)) {
                Date returnDateRes = RentACarController.calculateReturnDate(reservation.getPickUpDate(), reservation.getDays());
                if (reservation.getPickUpDate().before(returnDate) && returnDateRes.after(pickUp)) {
                    return false;
                }
            }
        }
        return true;
    }
}