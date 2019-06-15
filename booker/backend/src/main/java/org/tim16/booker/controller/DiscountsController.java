package org.tim16.booker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.tim16.booker.dto.DiscountsDTO;
import org.tim16.booker.model.users.UserType;
import org.tim16.booker.model.utility.UserDiscounts;
import org.tim16.booker.service.UserDiscountsService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/discounts")
public class DiscountsController {

    private static final String REGULAR = "REGULAR";
    private static final String BRONZE = "BRONZE";
    private static final String SILVER = "SILVER";
    private static final String GOLD = "GOLD";

    @Autowired
    UserDiscountsService discountsService;

    @GetMapping(path = "/get")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<List<UserDiscounts>> getAll() {
        List<UserDiscounts> result = new ArrayList<>();

        UserDiscounts regularUser = discountsService.findByUserType(REGULAR);
        UserDiscounts bronzeUser = discountsService.findByUserType(BRONZE);
        UserDiscounts silverUser = discountsService.findByUserType(SILVER);
        UserDiscounts goldUser = discountsService.findByUserType(GOLD);

        result.add(regularUser);
        result.add(bronzeUser);
        result.add(silverUser);
        result.add(goldUser);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "/get-regular")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<UserDiscounts> getRegular() {
        UserDiscounts discount = discountsService.findByUserType(REGULAR);
        return new ResponseEntity<>(discount, HttpStatus.OK);
    }

    @GetMapping(path = "/get-bronze")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<UserDiscounts> getBronze() {
        UserDiscounts discount = discountsService.findByUserType(BRONZE);
        return new ResponseEntity<>(discount, HttpStatus.OK);
    }

    @GetMapping(path = "/get-silver")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<UserDiscounts> getSilver() {
        UserDiscounts discount = discountsService.findByUserType(SILVER);
        return new ResponseEntity<>(discount, HttpStatus.OK);
    }

    @GetMapping(path = "/get-gold")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<UserDiscounts> getGold() {
        UserDiscounts discount = discountsService.findByUserType(GOLD);
        return new ResponseEntity<>(discount, HttpStatus.OK);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<HttpStatus> create() {
        UserDiscounts regularUser = discountsService.findByUserType(REGULAR);
        UserDiscounts bronzeUser = discountsService.findByUserType(BRONZE);
        UserDiscounts silverUser = discountsService.findByUserType(SILVER);
        UserDiscounts goldUser = discountsService.findByUserType(GOLD);

        if (regularUser == null || bronzeUser == null || silverUser == null || goldUser == null) {
            regularUser = new UserDiscounts(UserType.REGULAR, 0, 0);
            bronzeUser = new UserDiscounts(UserType.BRONZE, 15, 0);
            silverUser = new UserDiscounts(UserType.SILVER, 30, 0);
            goldUser = new UserDiscounts(UserType.GOLD, 45, 0);

            discountsService.create(regularUser);
            discountsService.create(bronzeUser);
            discountsService.create(silverUser);
            discountsService.create(goldUser);

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping(path = "/update")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<List<UserDiscounts>> update(@RequestBody DiscountsDTO dto) {
        UserDiscounts regularUser = discountsService.findByUserType(REGULAR);
        UserDiscounts bronzeUser = discountsService.findByUserType(BRONZE);
        UserDiscounts silverUser = discountsService.findByUserType(SILVER);
        UserDiscounts goldUser = discountsService.findByUserType(GOLD);

        regularUser.setDiscount(dto.getRegularDiscount());
        bronzeUser.setMinPts(dto.getBronzeMinPts());
        bronzeUser.setDiscount(dto.getBronzeDiscount());
        silverUser.setMinPts(dto.getSilverMinPts());
        silverUser.setDiscount(dto.getSilverDiscount());
        goldUser.setMinPts(dto.getGoldMinPts());
        goldUser.setDiscount(dto.getGoldDiscount());

        discountsService.update(regularUser);
        discountsService.update(bronzeUser);
        discountsService.update(silverUser);
        discountsService.update(goldUser);

        List<UserDiscounts> discounts = new ArrayList<>();
        discounts.add(regularUser);
        discounts.add(bronzeUser);
        discounts.add(silverUser);
        discounts.add(goldUser);

        return new ResponseEntity<>(discounts, HttpStatus.OK);
    }

}
