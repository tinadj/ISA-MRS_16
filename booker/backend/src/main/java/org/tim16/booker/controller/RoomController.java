package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.alps.Ext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.dto.RoomDTO;
import org.tim16.booker.dto.RoomSearchParamsDTO;
import org.tim16.booker.model.hotel.ExtraService;
import org.tim16.booker.model.hotel.Hotel;
import org.tim16.booker.model.hotel.Room;
import org.tim16.booker.service.HotelService;
import org.tim16.booker.service.RoomService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping(value = "/api/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private HotelService hotelService;


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Room>> getAll() {
        return new ResponseEntity<>(roomService.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

        HashSet<ExtraService> roomservices = roomExtraServicesSet(roomDTO);

        /*  **********************************************************************************************      */

        room.setExtraServices(roomservices);

        roomService.create(room);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Room> getRoom(@PathVariable Integer id)
    {
        Room room = roomService.findOne(id);

        if (room == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method =  RequestMethod.PUT)
    public ResponseEntity<Room> updateRoom(@RequestBody RoomDTO roomDTO)
    {
        try
        {
            Room room = roomService.findOne(roomDTO.getId());

            room.setFloor(roomDTO.getFloor());
            room.setRoomNum(roomDTO.getRoomNum());
            room.setBeds(roomDTO.getBeds());
            room.setBalcony(roomDTO.getBalcony());
            room.setDiscount(roomDTO.getDiscount());
            Hotel hotel = hotelService.findOne(roomDTO.getHotelId());
            room.setHotel(hotel);

            HashSet<ExtraService> roomservices = roomExtraServicesSet(roomDTO);

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

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
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
    public HashSet<ExtraService> roomExtraServicesSet(RoomDTO roomDTO)
    {
        HashSet<ExtraService> roomservices = new HashSet<>();

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

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
