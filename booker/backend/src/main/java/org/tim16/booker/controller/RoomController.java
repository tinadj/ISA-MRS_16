package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.comparator.*;
import org.tim16.booker.comparator.RoomPrice;
import org.tim16.booker.dto.RoomDTO;
import org.tim16.booker.dto.RoomSearchParamsDTO;
import org.tim16.booker.model.hotel.*;
import org.tim16.booker.service.HotelReservationService;
import org.tim16.booker.service.HotelService;
import org.tim16.booker.service.RoomService;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@RestController
@RequestMapping(value = "/api/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelReservationService reservationService;

    @GetMapping(path = "/all")
    public ResponseEntity<List<Room>> getAll() {
        return new ResponseEntity<>(roomService.findAll(), HttpStatus.OK);
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addNewRoom(@RequestBody RoomDTO roomDTO){

        Hotel hotel = hotelService.findOne(roomDTO.getHotelId());

        /* Provera da li je uneti sprat veci od broja spratova koje hotel ima */
        int hotelfloors = hotel.getFloors();

        if (hotelfloors < roomDTO.getFloor())
        {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        /* Provera koliko ima prostora u hotelu da se doda nova soba (maxRoomNum) */
        int existingrooms = 0;

        for (int i = 0; i < hotel.getRooms().size(); i++)
        {
            existingrooms += 1;
        }

        if (existingrooms == hotel.getMaxRoomsNum())
        {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        /* Provera da li je broj kreveta veci od 4 (hotel maksimalno moze imati cetvorokrevetnu sobu) */
        if (roomDTO.getBeds() > 4)
        {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        /* Provera da li ovaj broj sobe vec postoji */
        int roomnum = roomDTO.getRoomNum();

        for(Room r : hotel.getRooms())
        {
            if(roomnum == r.getRoomNum())
            {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }

        Room room = new Room();
        room.setBalcony(roomDTO.getBalcony());
        room.setBeds(roomDTO.getBeds());
        room.setDiscount(roomDTO.getDiscount());
        room.setFloor(roomDTO.getFloor());
        room.setHotel(hotel);
        room.setRoomNum(roomDTO.getRoomNum());

        /* Kreiranje set<ExtraService> koji se preuzima iz podataka roomDTO, i dodavanje tog seta u room model */

        Set<ExtraService> roomservices = roomExtraServicesSet(roomDTO);

        room.setExtraServices(roomservices);

        /*  **********************************************************************************************      */

        /* Cena je 0 na pocetku
           1: Dodaje se basic cena za samu sobu na ukupnu cenu
           2: Prolazi se kroz sve ES sobe i za svaki koji postoji -> uzima se cena tog ES iz hotela i dodaje na ukupnu cenu
        */
        float price = 0.0f;

        price += roomDTO.getPrice();

        for(ExtraService es : roomservices)
        {
            for(ExtraServicePrice esp : hotel.getExtraServicePrices())
            {
                if(es.equals(esp.getType()))
                {
                    price += esp.getPrice();
                }
            }

        }

        room.setPrice(price);

        /* **** */

        roomService.create(room);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable Integer id)
    {
        Room room = roomService.findOne(id);

        if (room == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Room> updateRoom(@RequestBody RoomDTO roomDTO)
    {
        try
        {
            Room room = roomService.findOne(roomDTO.getId());

            room.setFloor(roomDTO.getFloor());
            room.setRoomNum(roomDTO.getRoomNum());
            room.setBeds(roomDTO.getBeds());
            room.setBalcony(roomDTO.getBalcony());
            room.setPrice(roomDTO.getPrice());
            room.setDiscount(roomDTO.getDiscount());
            Hotel hotel = hotelService.findOne(roomDTO.getHotelId());
            room.setHotel(hotel);

            Set<ExtraService> roomservices = roomExtraServicesSet(roomDTO);

            room.setExtraServices(roomservices);

            hotel.removeRoom(roomDTO.getId());
            hotel.add(room);
            hotelService.update(hotel);

            return new ResponseEntity<>(roomService.update(room), HttpStatus.OK);

        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "is-reserved/{id}")
    @PreAuthorize("hasAuthority('HOTEL_ADMIN')")
    public HttpStatus isReserved(@PathVariable Integer id)
    {
        try{
            /*
            for(HotelReservation reservation : reservationService.findAll())
            {
                if(reservation.getRoom().getId().equals(id))
                    return HttpStatus.FORBIDDEN;
            }
            */
            return HttpStatus.OK;
        }
        catch(EntityNotFoundException e)
        {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @DeleteMapping(path = "/remove/{id}")
    public ResponseEntity<List<Room>> removeRoom(@PathVariable Integer id)
    {
        Room room = roomService.findOne(id);

        if (room == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Hotel hotel = hotelService.findOne(room.getHotel().getId());
        hotel.removeRoom(room.getId());

        hotelService.update(hotel);
        roomService.remove(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* Funkcija koja ucitava ekstraservise iz roomDTO i vraca taj set<extraservices> */
    public Set<ExtraService> roomExtraServicesSet(RoomDTO roomDTO)
    {
        Set<ExtraService> roomservices = new HashSet<>();

        if(roomDTO.getBreakfast().equals(true))
        {
            roomservices.add(ExtraService.BREAKFAST);
        }

        if(roomDTO.getHotel_restaurant().equals(true))
        {
            roomservices.add(ExtraService.HOTEL_RESTAURANT);
        }

        if(roomDTO.getAirport_transfer().equals(true))
        {
            roomservices.add(ExtraService.AIRPORT_TRANSFER);
        }

        if(roomDTO.getParking().equals(true))
        {
            roomservices.add(ExtraService.PARKING);
        }

        if(roomDTO.getPool().equals(true))
        {
            roomservices.add(ExtraService.POOL);
        }

        if(roomDTO.getWellness_spa().equals(true))
        {
            roomservices.add(ExtraService.WELLNESS_SPA);
        }

        if(roomDTO.getWifi().equals(true))
        {
            roomservices.add(ExtraService.WIFI);
        }

        if(roomDTO.getTv().equals(true))
        {
            roomservices.add(ExtraService.TV);
        }

        if(roomDTO.getMinibar().equals(true))
        {
            roomservices.add(ExtraService.MINIBAR);
        }

        return roomservices;
    }

    /* Funkcija kojoj se prosledjuje hotelID, a koja vraca sve sobe tog hotela u ArrayList<Room> */
    private List<Room> getRooms(Integer hotelID)
    {
        Hotel hotel = hotelService.findOne(hotelID);
        List<Room> rooms = new ArrayList<>();

        if(hotel != null)
        {
            for(Room r : hotel.getRooms())
            {
                rooms.add(r);
            }
        }

        return rooms;
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<List<Room>> search(@RequestBody RoomSearchParamsDTO dto)
    {
        Hotel hotel = hotelService.findOne(dto.getHotelID());

        if (hotel == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Room> rooms = getRooms(dto.getHotelID());
        List<Room> result = getRooms(dto.getHotelID());


        /* Datumi da li se poklapaju */
        if (dto.getCheckinDate() != null && dto.getCheckoutDate() != null)
        {
            result = searchByDates(rooms, result, dto.getCheckinDate(), dto.getCheckoutDate());
        }


        /* Ukoliko je cena sobe van okvira zeljene cene -> soba se izbacuje iz liste za pretragu */
        if (dto.getMinPrice() != 0 && dto.getMaxPrice()!= 0) {
            for (Room r : rooms) {
                if (r.getPrice() < dto.getMinPrice() || r.getPrice() >= dto.getMaxPrice()) {
                    result.remove(r);
                }
            }
        }

        /* Da li je balkon ukljucen ili ne */
        if(dto.isBalcony())
        {
            for(Room r : rooms)
            {
                if(r.getBalcony() == false)
                {
                    result.remove(r);
                }
            }
        }
        else if (!dto.isBalcony()) {
            for (Room r : rooms) {
                if (r.getBalcony() == true) {
                    result.remove(r);
                }
            }
        }


        rooms = new ArrayList<>();
        rooms = result;

        /* Da li su ukljuceni odredjeni ekstraservisi */

        if(dto.isBreakfast())
        {
            boolean checker = false;

            for(Room r : rooms)
            {
                Set<ExtraService> extraset = new HashSet<ExtraService>();
                extraset = r.getExtraServices();

                for(ExtraService e : extraset)
                {
                    if(e.equals(ExtraService.BREAKFAST))
                    {
                        checker = true;
                    }
                }

                if(checker == false)
                {
                    result.remove(r);
                }

            }
        }
        else
        {
            boolean checker = false;

            for(Room r : rooms)
            {
                Set<ExtraService> extraset = new HashSet<ExtraService>();
                extraset = r.getExtraServices();

                for(ExtraService e : extraset)
                {
                    if(e.equals(ExtraService.BREAKFAST))
                    {
                        checker = true;
                    }
                }

                if(checker == true)
                {
                    result.remove(r);
                }
            }
        }

        rooms = new ArrayList<>();
        rooms = result;

        /* AIRPORT TRANSFER */
        if(dto.isAirport_transfer())
        {
            boolean checker = false;

            for(Room r : rooms)
            {
                Set<ExtraService> extraset = new HashSet<ExtraService>();
                extraset = r.getExtraServices();

                for(ExtraService e : extraset)
                {
                    if(e.equals(ExtraService.AIRPORT_TRANSFER))
                    {
                        checker = true;
                    }
                }

                if(checker == false)
                {
                    result.remove(r);
                }

            }
        }
        else {
            boolean checker = false;

            for (Room r : rooms) {
                Set<ExtraService> extraset = new HashSet<ExtraService>();
                extraset = r.getExtraServices();

                for (ExtraService e : extraset) {
                    if (e.equals(ExtraService.AIRPORT_TRANSFER)) {
                        checker = true;
                    }
                }

                if (checker == true) {
                    result.remove(r);
                }
            }
        }

        rooms = new ArrayList<>();
        rooms = result;

        /* HOTEL RESTAURANT */
        if(dto.isHotel_restaurant())
        {
            boolean checker = false;

            for(Room r : rooms)
            {
                Set<ExtraService> extraset = new HashSet<ExtraService>();
                extraset = r.getExtraServices();

                for(ExtraService e : extraset)
                {
                    if(e.equals(ExtraService.HOTEL_RESTAURANT))
                    {
                        checker = true;
                    }
                }

                if(checker == false)
                {
                    result.remove(r);
                }

            }
        }
        else {
            boolean checker = false;

            for (Room r : rooms) {
                Set<ExtraService> extraset = new HashSet<ExtraService>();
                extraset = r.getExtraServices();

                for (ExtraService e : extraset) {
                    if (e.equals(ExtraService.HOTEL_RESTAURANT)) {
                        checker = true;
                    }
                }

                if (checker == true) {
                    result.remove(r);
                }
            }
        }

        rooms = new ArrayList<>();
        rooms = result;

        /* MINIBAR */
        if(dto.isMinibar())
        {
            boolean checker = false;

            for(Room r : rooms)
            {
                Set<ExtraService> extraset = new HashSet<ExtraService>();
                extraset = r.getExtraServices();

                for(ExtraService e : extraset)
                {
                    if(e.equals(ExtraService.MINIBAR))
                    {
                        checker = true;
                    }
                }

                if(checker == false)
                {
                    result.remove(r);
                }

            }
        }
        else {
            boolean checker = false;

            for (Room r : rooms) {
                Set<ExtraService> extraset = new HashSet<ExtraService>();
                extraset = r.getExtraServices();

                for (ExtraService e : extraset) {
                    if (e.equals(ExtraService.MINIBAR)) {
                        checker = true;
                    }
                }

                if (checker == true) {
                    result.remove(r);
                }
            }
        }

        rooms = new ArrayList<>();
        rooms = result;

        /* PARKING */
        if(dto.isParking())
        {
            boolean checker = false;

            for(Room r : rooms)
            {
                Set<ExtraService> extraset = new HashSet<ExtraService>();
                extraset = r.getExtraServices();

                for(ExtraService e : extraset)
                {
                    if(e.equals(ExtraService.PARKING))
                    {
                        checker = true;
                    }
                }

                if(checker == false)
                {
                    result.remove(r);
                }

            }
        }
        else {
            boolean checker = false;

            for (Room r : rooms) {
                Set<ExtraService> extraset = new HashSet<ExtraService>();
                extraset = r.getExtraServices();

                for (ExtraService e : extraset) {
                    if (e.equals(ExtraService.PARKING)) {
                        checker = true;
                    }
                }

                if (checker == true) {
                    result.remove(r);
                }
            }
        }

        rooms = new ArrayList<>();
        rooms = result;

        /* POOL */
        if(dto.isPool())
        {
            boolean checker = false;

            for(Room r : rooms)
            {
                Set<ExtraService> extraset = new HashSet<ExtraService>();
                extraset = r.getExtraServices();

                for(ExtraService e : extraset)
                {
                    if(e.equals(ExtraService.POOL))
                    {
                        checker = true;
                    }
                }

                if(checker == false)
                {
                    result.remove(r);
                }

            }
        }
        else {
            boolean checker = false;

            for (Room r : rooms) {
                Set<ExtraService> extraset = new HashSet<ExtraService>();
                extraset = r.getExtraServices();

                for (ExtraService e : extraset) {
                    if (e.equals(ExtraService.POOL)) {
                        checker = true;
                    }
                }

                if (checker == true) {
                    result.remove(r);
                }
            }
        }

        rooms = new ArrayList<>();
        rooms = result;

        /* TV */
        if(dto.isTv())
        {
            boolean checker = false;

            for(Room r : rooms)
            {
                Set<ExtraService> extraset = new HashSet<ExtraService>();
                extraset = r.getExtraServices();

                for(ExtraService e : extraset)
                {
                    if(e.equals(ExtraService.TV))
                    {
                        checker = true;
                    }
                }

                if(checker == false)
                {
                    result.remove(r);
                }

            }
        }
        else {
            boolean checker = false;

            for (Room r : rooms) {
                Set<ExtraService> extraset = new HashSet<ExtraService>();
                extraset = r.getExtraServices();

                for (ExtraService e : extraset) {
                    if (e.equals(ExtraService.TV)) {
                        checker = true;
                    }
                }

                if (checker == true) {
                    result.remove(r);
                }
            }
        }

        rooms = new ArrayList<>();
        rooms = result;

        /* WELLNESS SPA */
        if(dto.isWellness_spa())
        {
            boolean checker = false;

            for(Room r : rooms)
            {
                Set<ExtraService> extraset = new HashSet<ExtraService>();
                extraset = r.getExtraServices();

                for(ExtraService e : extraset)
                {
                    if(e.equals(ExtraService.WELLNESS_SPA))
                    {
                        checker = true;
                    }
                }

                if(checker == false)
                {
                    result.remove(r);
                }

            }
        }
        else {
            boolean checker = false;

            for (Room r : rooms) {
                Set<ExtraService> extraset = new HashSet<ExtraService>();
                extraset = r.getExtraServices();

                for (ExtraService e : extraset) {
                    if (e.equals(ExtraService.WELLNESS_SPA)) {
                        checker = true;
                    }
                }

                if (checker == true) {
                    result.remove(r);
                }
            }
        }

        rooms = new ArrayList<>();
        rooms = result;

        /* WIFI */
        if(dto.isWifi())
        {
            boolean checker = false;

            for(Room r : rooms)
            {
                Set<ExtraService> extraset = new HashSet<ExtraService>();
                extraset = r.getExtraServices();

                for(ExtraService e : extraset)
                {
                    if(e.equals(ExtraService.WIFI))
                    {
                        checker = true;
                    }
                }

                if(checker == false)
                {
                    result.remove(r);
                }

            }
        }
        else {
            boolean checker = false;

            for (Room r : rooms) {
                Set<ExtraService> extraset = new HashSet<ExtraService>();
                extraset = r.getExtraServices();

                for (ExtraService e : extraset) {
                    if (e.equals(ExtraService.WIFI)) {
                        checker = true;
                    }
                }

                if (checker == true) {
                    result.remove(r);
                }
            }
        }

        /* Sortiranje po odredjenom kriterijumu */

        if (dto.getCriteria() == 0) {
            RoomPrice.RoomPriceAscending(result);
        }
        else if (dto.getCriteria() == 1) {
            RoomPrice.RoomPriceDescending(result);
        }
        else if (dto.getCriteria() == 2) {
            Collections.sort(result, new RoomBeds());
        }
        else if (dto.getCriteria() == 3) {
            Collections.sort(result, new RoomBeds());
            Collections.reverse(result);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private List<Room> searchByDates(List<Room> rooms, List<Room> result, Date checkinDate, Date checkoutDate)
    {
        for(Room r : rooms)
        {
            if(isReserved(r, checkinDate, checkoutDate))
            {
                result.remove(r);
            }
        }
        return result;
    }

    private boolean isReserved(Room room, Date checkinDate, Date checkoutDate)
    {
        /*
        for(HotelReservation reservation : reservationService.findAll())
        {
            if(reservation.getRoom().getId().equals(room.getId()))
            {
                Date checkoutdate = calculateCheckoutDate(reservation);

                if(reservation.getCheckinDate().before(checkoutDate) && checkoutdate.after(checkinDate))
                {
                    return true;
                }
            }
        }
        */
        return false;
    }

    // Funkcija koja izracunava datum povratka vozila na osnovu checkina i broja noci
    private Date calculateCheckoutDate(HotelReservation reservation)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(reservation.getCheckinDate());
        c.add(Calendar.DATE, reservation.getNights());

        return c.getTime();
    }

}
