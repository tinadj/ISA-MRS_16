package org.tim16.booker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tim16.booker.comparator.HotelCity;
import org.tim16.booker.comparator.HotelName;
import org.tim16.booker.comparator.HotelState;
import org.tim16.booker.dto.HotelDTO;
import org.tim16.booker.dto.HotelPricesDTO;
import org.tim16.booker.dto.HotelsSearchParamsDTO;
import org.tim16.booker.model.hotel.*;
import org.tim16.booker.model.utility.Destination;
import org.tim16.booker.service.DestinationService;
import org.tim16.booker.service.HotelService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping(value = "/api/hotels")
public class HotelController {

    @Autowired
    private HotelService service;
    @Autowired
    private DestinationService destinationService;

    @GetMapping(path = "/all")
    public ResponseEntity<List<Hotel>> getAll()
    {
        List<Hotel> hotels = service.findAll();
        Collections.sort(hotels, new HotelName());
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @PostMapping(path = "/add", consumes="application/json")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<Hotel> add(@RequestBody HotelDTO dto) {
        Hotel hotel = new Hotel();
        hotel.setName(dto.getName());
        hotel.setDescription(dto.getDescription());
        hotel.setLatitude(dto.getLatitude());
        hotel.setLongitude(dto.getLongitude());
        hotel.setFloors(dto.getFloors());
        hotel.setMaxRoomsNum(dto.getMaxRoomsNum());

        /* SETOVANJE EXTRASERVICE-A HOTELA NA 0 */

        ExtraServicePrice breakfast = new ExtraServicePrice(ExtraService.BREAKFAST, 0.0f);
        ExtraServicePrice restaurant = new ExtraServicePrice(ExtraService.HOTEL_RESTAURANT, 0.0f);
        ExtraServicePrice airportTransfer = new ExtraServicePrice(ExtraService.AIRPORT_TRANSFER, 0.0f);
        ExtraServicePrice parking = new ExtraServicePrice(ExtraService.PARKING, 0.0f);
        ExtraServicePrice pool = new ExtraServicePrice(ExtraService.POOL, 0.0f);
        ExtraServicePrice wellnessSpa = new ExtraServicePrice(ExtraService.WELLNESS_SPA, 0.0f);
        ExtraServicePrice wifi = new ExtraServicePrice(ExtraService.WIFI, 0.0f);
        ExtraServicePrice tv = new ExtraServicePrice(ExtraService.TV, 0.0f);
        ExtraServicePrice minibar = new ExtraServicePrice(ExtraService.MINIBAR, 0.0f);

        HashSet<ExtraServicePrice> extraprices = new HashSet<>();

        extraprices.add(breakfast);
        extraprices.add(restaurant);
        extraprices.add(airportTransfer);
        extraprices.add(parking);
        extraprices.add(pool);
        extraprices.add(wellnessSpa);
        extraprices.add(wifi);
        extraprices.add(tv);
        extraprices.add(minibar);

        hotel.setExtraServicePrices(extraprices);

        /* ******************************************* */

        Destination destination = destinationService.findByCityAndState(dto.getAddress().getCity(), dto.getAddress().getCity());

        if (destination == null) {
            destination = new Destination();
            destination.setCity(dto.getAddress().getCity());
            destination.setState(dto.getAddress().getState());
            destinationService.create(destination);
        }
        hotel.setAddress(destination);

        try {
            hotel = service.create(hotel);
            return new ResponseEntity<>(hotel, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path =  "/{id}")
    public ResponseEntity<Hotel> getHotel(@PathVariable Integer id) {
        Hotel hotel = service.findOne(id);

        if (hotel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/extraprices")
    public ResponseEntity<HotelPricesDTO> getHotelPrices(@PathVariable Integer id)
    {
        Hotel hotel = service.findOne(id);

        if (hotel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HotelPricesDTO dto = new HotelPricesDTO();

        dto.setId(id);

        /* Prolazim kroz ExtraServices hotela[id], i tamo gde se tip poklapa - uzimam cenu tog extraservisa od hotela da bih vratio frontendu informacije */
        for(ExtraServicePrice es : hotel.getExtraServicePrices())
        {
            if(es.getType().equals(ExtraService.BREAKFAST))
            {
                dto.setBreakfast(es.getPrice());
            }
            else if(es.getType().equals(ExtraService.HOTEL_RESTAURANT))
            {
                dto.setHotelRestaurant(es.getPrice());
            }
            else if(es.getType().equals(ExtraService.AIRPORT_TRANSFER))
            {
                dto.setAirportTransfer(es.getPrice());
            }
            else if(es.getType().equals(ExtraService.PARKING))
            {
                dto.setParking(es.getPrice());
            }
            else if(es.getType().equals(ExtraService.POOL))
            {
                dto.setPool(es.getPrice());
            }
            else if(es.getType().equals(ExtraService.WELLNESS_SPA))
            {
                dto.setWellnessSpa(es.getPrice());
            }
            else if(es.getType().equals(ExtraService.WIFI))
            {
                dto.setWifi(es.getPrice());
            }
            else if(es.getType().equals(ExtraService.TV))
            {
                dto.setTv(es.getPrice());
            }
            else if(es.getType().equals(ExtraService.MINIBAR))
            {
                dto.setMinibar(es.getPrice());
            }
        }

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/rooms")
    public ResponseEntity<List<Room>> getRooms(@PathVariable Integer id) {
        Hotel hotel = service.findOne(id);

        if (hotel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Room> rooms = new ArrayList<>();
        for (Room r: hotel.getRooms()) {
            rooms.add(r);
        }

        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }


    @PutMapping(path = "/update")
    public ResponseEntity<Hotel> update(@RequestBody HotelDTO dto) {

        try {
            Hotel hotel = service.findOne(dto.getId());

            hotel.setName(dto.getName());
            hotel.setDescription(dto.getDescription());
            hotel.setLatitude(dto.getLatitude());
            hotel.setLongitude(dto.getLongitude());

            Destination destination = destinationService.findByCityAndState(dto.getAddress().getCity(), dto.getAddress().getCity());

            if (destination == null) {
                destination = new Destination();
                destination.setCity(dto.getAddress().getCity());
                destination.setState(dto.getAddress().getState());
                destinationService.create(destination);
            }
            hotel.setAddress(destination);

            return new ResponseEntity<>(service.update(hotel), HttpStatus.OK);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/search")
    public ResponseEntity<List<Hotel>> searchHotels(@RequestBody HotelsSearchParamsDTO dto)
    {

        List<Hotel> samplehotels = service.findAll();
        List<Hotel> hotels = service.findAll();


        if(!dto.getName().isEmpty())
        {
            for(Hotel h : samplehotels)
            {
                if(!h.getName().toLowerCase().contains(dto.getName().toLowerCase()))
                {
                    hotels.remove(h);
                }
            }
        }

        if(!dto.getCity().isEmpty())
        {
            for(Hotel h : samplehotels)
            {
                if(!h.getAddress().getCity().equalsIgnoreCase(dto.getCity().toLowerCase()))
                {
                    hotels.remove(h);
                }
            }
        }

        if(!dto.getState().isEmpty())
        {
            for(Hotel h : samplehotels)
            {
                if(!h.getAddress().getState().equalsIgnoreCase(dto.getState().toLowerCase()))
                {
                    hotels.remove(h);
                }
            }
        }


        if (dto.getCriteria() == 0) {
            Collections.sort(hotels, new HotelName());
        }
        else if (dto.getCriteria() == 1) {
            Collections.sort(hotels, new HotelName());
            Collections.reverse(hotels);
        }
        else if (dto.getCriteria() == 2) {
            Collections.sort(hotels, new HotelCity());
        }
        else if (dto.getCriteria() == 3) {
            Collections.sort(hotels, new HotelCity());
            Collections.reverse(hotels);
        }
        else if (dto.getCriteria() == 4) {
            Collections.sort(hotels, new HotelState());
        }
        else if (dto.getCriteria() == 5) {
            Collections.sort(hotels, new HotelState());
            Collections.reverse(hotels);
        }
        else {
            Collections.sort(hotels, new HotelName());
        }



        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @PutMapping(path = "/updateExtraServices")
    public ResponseEntity<Hotel> updateExtraServices(@RequestBody HotelPricesDTO dto) {

        try {
            Hotel hotel = service.findOne(dto.getId());

            HashSet<ExtraServicePrice> extraprices = new HashSet<>();

            for(ExtraServicePrice es : hotel.getExtraServicePrices())
            {
                if(es.getType().equals(ExtraService.BREAKFAST))
                {
                    es.setPrice(dto.getBreakfast());
                    es.setType(ExtraService.BREAKFAST);

                    extraprices.add(es);

                }
                else if(es.getType().equals(ExtraService.HOTEL_RESTAURANT))
                {
                    es.setPrice(dto.getHotelRestaurant());
                    es.setType(ExtraService.HOTEL_RESTAURANT);

                    extraprices.add(es);
                }
                else if(es.getType().equals(ExtraService.AIRPORT_TRANSFER))
                {
                    es.setPrice(dto.getAirportTransfer());
                    es.setType(ExtraService.AIRPORT_TRANSFER);

                    extraprices.add(es);
                }
                else if(es.getType().equals(ExtraService.PARKING))
                {
                    es.setPrice(dto.getParking());
                    es.setType(ExtraService.PARKING);

                    extraprices.add(es);
                }
                else if(es.getType().equals(ExtraService.POOL))
                {
                    es.setPrice(dto.getPool());
                    es.setType(ExtraService.POOL);

                    extraprices.add(es);
                }
                else if(es.getType().equals(ExtraService.WELLNESS_SPA))
                {
                    es.setPrice(dto.getWellnessSpa());
                    es.setType(ExtraService.WELLNESS_SPA);

                    extraprices.add(es);
                }
                else if(es.getType().equals(ExtraService.WIFI))
                {
                    es.setPrice(dto.getWifi());
                    es.setType(ExtraService.WIFI);

                    extraprices.add(es);
                }
                else if(es.getType().equals(ExtraService.TV))
                {
                    es.setPrice(dto.getTv());
                    es.setType(ExtraService.TV);

                    extraprices.add(es);
                }
                else if(es.getType().equals(ExtraService.MINIBAR))
                {
                    es.setPrice(dto.getMinibar());
                    es.setType(ExtraService.MINIBAR);

                    extraprices.add(es);
                }
            }

            hotel.setExtraServicePrices(extraprices);

            return new ResponseEntity<>(service.update(hotel), HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
