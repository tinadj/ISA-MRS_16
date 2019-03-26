package org.tim16.booker.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tim16.booker.model.RentACar;

@RestController
@RequestMapping("/rent_a_cars")
public class RentACarController {
	
	public ArrayList<RentACar> rac = new ArrayList<RentACar>();
			
	public void init() {
		rac.add(new RentACar("RAC1", "Add1", "D1"));
		rac.add(new RentACar("RAC2", "Add1", "D1"));
		rac.add(new RentACar("RAC3", "Add1", "D1"));
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping(path="/all")
	List<RentACar> all() {
		init();
		return rac;
	}
}
