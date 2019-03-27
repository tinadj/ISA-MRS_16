package org.tim16.booker.model;

public class RentACar {
	
	public String name;
	public String address;
	public String description;

	public RentACar(String name, String address, String description) {
		super();
		this.name = name;
		this.address = address;
		this.description = description;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
