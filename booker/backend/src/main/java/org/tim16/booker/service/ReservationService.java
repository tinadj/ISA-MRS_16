package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.tim16.booker.dto.FlightReservationDTO;
import org.tim16.booker.dto.HotelReservationDTO;
import org.tim16.booker.controller.RentACarController;
import org.tim16.booker.dto.RacReservationDTO;
import org.tim16.booker.model.airline.*;
import org.tim16.booker.model.hotel.HotelReservation;
import org.tim16.booker.model.hotel.Room;
import org.tim16.booker.model.rent_a_car.BranchOffice;
import org.tim16.booker.model.rent_a_car.RentACarReservation;
import org.tim16.booker.model.rent_a_car.Vehicle;
import org.tim16.booker.model.users.RegisteredUser;
import org.tim16.booker.model.users.Reservation;
import org.tim16.booker.repository.*;

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
    private RoomRepository roomRepository;

    @Autowired
    private BranchOfficeRepository branchOfficeRepository;

    @Autowired
    private RacReservationRepository racReservationRepository;

    @Autowired
    private HotelReservationRepository hotelReservationRepository;

    @Autowired
    private FlightReservationRepository flightReservationRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirlineService airlineService;


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

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public ResponseEntity<Boolean> reserveFlight(FlightReservationDTO dto) {
        Reservation reservation = new Reservation();
        RegisteredUser user = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        FlightReservation flightReservation = setUpFlightReservation(dto);
        if (flightReservation == null) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

        user.getReservations().add(reservation);
        reservation.setUser(user);

        flightReservationRepository.save(flightReservation);
        flightReservation.setReservation(reservation);
        reservation.setFlightReservation(flightReservation);

        reservationRepository.save(reservation);
        userService.save(user);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public ResponseEntity<Boolean> reserveRoom(HotelReservationDTO dto)
    {
        Reservation reservation = new Reservation();
        RegisteredUser user = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        HotelReservation hotelreservation = setUpHotelReservation(dto);
        if (hotelreservation == null){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

        user.getReservations().add(reservation);
        reservation.setUser(user);

        hotelReservationRepository.save(hotelreservation);
        hotelreservation.setReservation(reservation);
        reservation.setHotelReservation(hotelreservation);

        reservationRepository.save(reservation);
        userService.save(user);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    private HotelReservation setUpHotelReservation(HotelReservationDTO dto)
    {
        HotelReservation hotelReservation = new HotelReservation();

        Room room = roomRepository.getOne(dto.getRoom());

        if(room == null)
            return null;


        Integer r_id = room.getId();
        hotelReservation.setRoomID(r_id);

        hotelReservation.setCheckinDate(dto.getCheckinDate());
        hotelReservation.setNights(dto.getNights());
        hotelReservation.setHotel(room.getHotel().getName());

        Float price = room.getPrice() * dto.getNights();
        if(room.getDiscount() != 0)
        {
            price = price - (price / 100 * room.getDiscount());
        }
        hotelReservation.setTotalPrice(price);

        return hotelReservation;
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

    // Incijalizacija flight rezervacije
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public FlightReservation setUpFlightReservation(FlightReservationDTO dto) {
        FlightReservation flightReservation = new FlightReservation();

        Flight flight = flightRepository.getOne(dto.getFlightId());

        if (!isTicketAvailable(dto.getTicketId()))
            return null;

        if(!deleteQuickTicket(flight.getAirline().getId(), dto.getTicketId()))
            return null;

        Ticket t = null;
        for(Ticket tic: flight.getTickets()){
            if(tic.getId().equals(dto.getTicketId()))
                t = tic;
        }

        flightReservation.setCarryOn(dto.getCarryOn());
        flightReservation.setChecked(dto.getChecked());
        flightReservation.setFlight(flight);
        flightReservation.setFirstName(dto.getFirstName());
        flightReservation.setLastName(dto.getLastName());
        flightReservation.setPassport(dto.getPassport());
        flightReservation.setTicket(t);

        Airline airline = airlineService.findOne(flight.getAirline().getId());
        float co = 0;
        float c = 0;

        for(LuggagePrice lp : airline.getLuggagePrices()){
            if(lp.getType() == LuggageType.CARRY_ON)
                co = lp.getPrice();

            if(lp.getType() == LuggageType.CHECKED)
                c = lp.getPrice();
        }

        float price = t.getPrice() * (1 - t.getDiscount()/100) + dto.getCarryOn() * co + dto.getChecked() * c;
        flightReservation.setTotalPrice(price);

        return flightReservation;
    }

    //Brisanje iz airline ako je brza rez
    private boolean deleteQuickTicket(int airlineId, int ticketId){
        Airline airline = airlineService.findOne(airlineId);

        if (airline == null) {
            return false;
        }

        Ticket tic = null;
        for(Ticket t : airline.getDiscountTickets()){
            if(t.getId().equals(ticketId)){
                tic = t;
                break;
            }
        }

        if(tic == null) {
            return true;
        }
        airline.getDiscountTickets().remove(tic);
        airlineService.update(airline);
        return true;
    }

    /*
    Provera da li je neko u medjuvremenu rezervisao kartu
     */
    private boolean isTicketAvailable(Integer id) {
        for (FlightReservation reservation: flightReservationRepository.findAll()) {
            if (reservation.getTicket().getId().equals(id)) {
                return false;
            }
        }
        return true;
    }

}
