package org.tim16.booker.service.interfaces;

import org.tim16.booker.model.rent_a_car.RentACar;

import java.util.List;

public interface IRentACarService {

    public RentACar findOne(Integer id);
    public List<RentACar> findAll();
    public RentACar create(RentACar rentACar);
    public RentACar update(RentACar rentACar);
    public void remove(Integer id);
}
