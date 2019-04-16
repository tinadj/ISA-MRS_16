package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tim16.booker.dto.RoomDTO;
import org.tim16.booker.service.RoomService;

@RestController
@RequestMapping(value = "/api/room")
public class RoomController {

    @Autowired
    private RoomService roomService;


    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addNewRoom(@RequestBody RoomDTO roomDTO){

        roomService.create(roomDTO);
        return new ResponseEntity(HttpStatus.OK);
    }


    //room =roomservice.findOne(id);
    // if(!roomService.isRoomTaken()) roomService.delete(room);
    // return new ResponseEntity(HttpStatus.BAD_REQUEST);

}
