package org.tim16.booker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.tim16.booker.model.users.UserType;
import org.tim16.booker.model.utility.UserDiscounts;
import org.tim16.booker.repository.UserDiscountsRepository;


@Service
public class UserDiscountsService {

    @Autowired
    UserDiscountsRepository repository;

    public UserDiscounts findOne(Integer id) { return repository.getOne(id); }

    public UserDiscounts findByUserType(String type) {
        if (type.equalsIgnoreCase(UserType.REGULAR.toString()))
            return repository.findByUserType(UserType.REGULAR);
        else if (type.equalsIgnoreCase(UserType.BRONZE.toString()))
            return repository.findByUserType(UserType.BRONZE);
        else if (type.equalsIgnoreCase(UserType.SILVER.toString()))
            return repository.findByUserType(UserType.SILVER);
        else if (type.equalsIgnoreCase(UserType.GOLD.toString()))
            return repository.findByUserType(UserType.GOLD);
        else
            return null;
    }

    public UserDiscounts create(UserDiscounts discounts) {
        return repository.save(discounts);
    }

    public UserDiscounts update(UserDiscounts discounts) {
        return repository.save(discounts);
    }
}
