package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.dto.RoomDTO;
import org.tim16.booker.model.hotel.Hotel;
import org.tim16.booker.model.hotel.Room;
import org.tim16.booker.service.HotelService;
import org.tim16.booker.service.RoomService;

import javax.persistence.EntityNotFoundException;
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

        roomService.create(roomDTO);
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

    /*

    @PostMapping(value = "/remove", consumes = MediaType.APPLICATION_JSON_VALUE, produces = ?)
    public ResponseEntity removeRoom(@RequestBody RoomDTO roomDTO)
    {
        roomService.remove(roomDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    */

    //room =roomservice.findOne(id);
    // if(!roomService.isRoomTaken()) roomService.delete(room);
    // return new ResponseEntity(HttpStatus.BAD_REQUEST);

}
