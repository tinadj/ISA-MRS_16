-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema db_booking
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db_booking
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_booking` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `db_booking` ;

-- -----------------------------------------------------
-- Table `db_booking`.`destinations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`destinations` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(255) NULL DEFAULT NULL,
  `state` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`airlines`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`airlines` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `latitude` DECIMAL(19,2) NULL DEFAULT NULL,
  `longitude` DECIMAL(19,2) NULL DEFAULT NULL,
  `name` VARCHAR(255) NOT NULL,
  `address` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_akufoupt618c8vlcfaa0gvfqq` (`name` ASC) VISIBLE,
  INDEX `FK69ct3xjcei6w5gr52cp6alub1` (`address` ASC) VISIBLE,
  CONSTRAINT `FK69ct3xjcei6w5gr52cp6alub1`
    FOREIGN KEY (`address`)
    REFERENCES `db_booking`.`destinations` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`airline_destinations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`airline_destinations` (
  `airline_id` INT(11) NOT NULL,
  `destination_id` INT(11) NOT NULL,
  PRIMARY KEY (`airline_id`, `destination_id`),
  INDEX `FK1ybym1fne9crn5gmqwcfqiadw` (`destination_id` ASC) VISIBLE,
  CONSTRAINT `FK1ybym1fne9crn5gmqwcfqiadw`
    FOREIGN KEY (`destination_id`)
    REFERENCES `db_booking`.`destinations` (`id`),
  CONSTRAINT `FKbwrpfiwdw787xrw5s4f424lgf`
    FOREIGN KEY (`airline_id`)
    REFERENCES `db_booking`.`airlines` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`flights`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`flights` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `arrival` DATETIME NOT NULL,
  `departure` DATETIME NOT NULL,
  `duration` INT(11) NOT NULL,
  `transfer_num` INT(11) NOT NULL,
  `airline` INT(11) NOT NULL,
  `arrival_destination` INT(11) NOT NULL,
  `departure_destination` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKe5y514tpwwhkdmb9q7klrqnij` (`airline` ASC) VISIBLE,
  INDEX `FKt69mfcksvgaornimvdua9i7qr` (`arrival_destination` ASC) VISIBLE,
  INDEX `FK7gt37l9unvrt0akdxb6lxnn1q` (`departure_destination` ASC) VISIBLE,
  CONSTRAINT `FK7gt37l9unvrt0akdxb6lxnn1q`
    FOREIGN KEY (`departure_destination`)
    REFERENCES `db_booking`.`destinations` (`id`),
  CONSTRAINT `FKe5y514tpwwhkdmb9q7klrqnij`
    FOREIGN KEY (`airline`)
    REFERENCES `db_booking`.`airlines` (`id`),
  CONSTRAINT `FKt69mfcksvgaornimvdua9i7qr`
    FOREIGN KEY (`arrival_destination`)
    REFERENCES `db_booking`.`destinations` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`flight_seats`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`flight_seats` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `seat_letter` VARCHAR(255) NOT NULL,
  `seat_row` INT(11) NOT NULL,
  `travel_class` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 91
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`tickets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`tickets` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `discount` INT(11) NULL DEFAULT NULL,
  `price` FLOAT NULL DEFAULT NULL,
  `reserved` BIT(1) NULL DEFAULT NULL,
  `flight` INT(11) NOT NULL,
  `seat` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK17ccwub9wtjpqb3ydj5gr7l9f` (`flight` ASC) VISIBLE,
  INDEX `FKe0bfmv6ifiyegkst7vs6mq3t2` (`seat` ASC) VISIBLE,
  CONSTRAINT `FK17ccwub9wtjpqb3ydj5gr7l9f`
    FOREIGN KEY (`flight`)
    REFERENCES `db_booking`.`flights` (`id`),
  CONSTRAINT `FKe0bfmv6ifiyegkst7vs6mq3t2`
    FOREIGN KEY (`seat`)
    REFERENCES `db_booking`.`flight_seats` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 91
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`airlines_discount_tickets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`airlines_discount_tickets` (
  `airline_id` INT(11) NOT NULL,
  `discount_tickets_id` INT(11) NOT NULL,
  PRIMARY KEY (`airline_id`, `discount_tickets_id`),
  UNIQUE INDEX `UK_o2oj2tidhctdeqlsb85jb53r3` (`discount_tickets_id` ASC) VISIBLE,
  CONSTRAINT `FKke1xvhambi53olpuhobiv3dmk`
    FOREIGN KEY (`airline_id`)
    REFERENCES `db_booking`.`airlines` (`id`),
  CONSTRAINT `FKonyjcb1eyo0rkavat0m0dufh1`
    FOREIGN KEY (`discount_tickets_id`)
    REFERENCES `db_booking`.`tickets` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`airlines_flights`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`airlines_flights` (
  `airline_id` INT(11) NOT NULL,
  `flights_id` INT(11) NOT NULL,
  PRIMARY KEY (`airline_id`, `flights_id`),
  UNIQUE INDEX `UK_1rytj77afuq7iv692m5p5sl2k` (`flights_id` ASC) VISIBLE,
  CONSTRAINT `FKgpp106ab9n43hr8pfscqtxe7b`
    FOREIGN KEY (`airline_id`)
    REFERENCES `db_booking`.`airlines` (`id`),
  CONSTRAINT `FKnsdqk6uus6np20g4gjklj4toc`
    FOREIGN KEY (`flights_id`)
    REFERENCES `db_booking`.`flights` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`luggage_prices`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`luggage_prices` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `price` FLOAT NULL DEFAULT NULL,
  `type` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`airlines_luggage_prices`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`airlines_luggage_prices` (
  `airline_id` INT(11) NOT NULL,
  `luggage_prices_id` INT(11) NOT NULL,
  PRIMARY KEY (`airline_id`, `luggage_prices_id`),
  UNIQUE INDEX `UK_s9ee4dg7nd1se9ag2gw3et9qt` (`luggage_prices_id` ASC) VISIBLE,
  CONSTRAINT `FK3be83lrqu0tiiyaph9v3gfl22`
    FOREIGN KEY (`airline_id`)
    REFERENCES `db_booking`.`airlines` (`id`),
  CONSTRAINT `FKccp2ul0kcskww2imgy9fy2ali`
    FOREIGN KEY (`luggage_prices_id`)
    REFERENCES `db_booking`.`luggage_prices` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`hotels`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`hotels` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `floors` INT(11) NULL DEFAULT NULL,
  `latitude` DECIMAL(19,2) NULL DEFAULT NULL,
  `longitude` DECIMAL(19,2) NULL DEFAULT NULL,
  `max_rooms_num` INT(11) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `address` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_m7xlqsndengqp2kpd5nux2s3i` (`name` ASC) VISIBLE,
  INDEX `FKesbqixqyela8msem9nlfus6v0` (`address` ASC) VISIBLE,
  CONSTRAINT `FKesbqixqyela8msem9nlfus6v0`
    FOREIGN KEY (`address`)
    REFERENCES `db_booking`.`destinations` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`rent_a_cars`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`rent_a_cars` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `latitude` DECIMAL(19,2) NULL DEFAULT NULL,
  `longitude` DECIMAL(19,2) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `address` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_ru42d042abwa8lvwjb85qtx19` (`name` ASC) VISIBLE,
  INDEX `FKiso309l0jbw8far2h7xm0b6dk` (`address` ASC) VISIBLE,
  CONSTRAINT `FKiso309l0jbw8far2h7xm0b6dk`
    FOREIGN KEY (`address`)
    REFERENCES `db_booking`.`destinations` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`users` (
  `dtype` VARCHAR(31) NOT NULL,
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(255) NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `enabled` BIT(1) NULL DEFAULT NULL,
  `last_password_reset_date` DATETIME NULL DEFAULT NULL,
  `lastname` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NOT NULL,
  `phone_num` INT(11) NULL DEFAULT NULL,
  `profile_picture` VARCHAR(255) NULL DEFAULT NULL,
  `username` VARCHAR(255) NOT NULL,
  `pass_changed` BIT(1) NULL DEFAULT NULL,
  `bonus_pts` INT(11) NULL DEFAULT NULL,
  `type` VARCHAR(255) NULL DEFAULT NULL,
  `rentacar` INT(11) NULL DEFAULT NULL,
  `airline` INT(11) NULL DEFAULT NULL,
  `hotel` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_r43af9ap4edm43mmtq01oddj6` (`username` ASC) VISIBLE,
  INDEX `FKs09vhvbax8qd9nsdb7xvmdt8t` (`rentacar` ASC) VISIBLE,
  INDEX `FKdx46u75d9eypll1k51as0eso0` (`airline` ASC) VISIBLE,
  INDEX `FKqy7u1qave6cf9erbu41pfahrw` (`hotel` ASC) VISIBLE,
  CONSTRAINT `FKdx46u75d9eypll1k51as0eso0`
    FOREIGN KEY (`airline`)
    REFERENCES `db_booking`.`airlines` (`id`),
  CONSTRAINT `FKqy7u1qave6cf9erbu41pfahrw`
    FOREIGN KEY (`hotel`)
    REFERENCES `db_booking`.`hotels` (`id`),
  CONSTRAINT `FKs09vhvbax8qd9nsdb7xvmdt8t`
    FOREIGN KEY (`rentacar`)
    REFERENCES `db_booking`.`rent_a_cars` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`rates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`rates` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `rate_value` INT(11) NOT NULL,
  `user` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKqyyk14j83aq3d12vi0m276i1d` (`user` ASC) VISIBLE,
  CONSTRAINT `FKqyyk14j83aq3d12vi0m276i1d`
    FOREIGN KEY (`user`)
    REFERENCES `db_booking`.`users` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`airlines_rating`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`airlines_rating` (
  `airline_id` INT(11) NOT NULL,
  `rating_id` INT(11) NOT NULL,
  PRIMARY KEY (`airline_id`, `rating_id`),
  UNIQUE INDEX `UK_mxf4v8scr9ao0yfc1icpfh3y` (`rating_id` ASC) VISIBLE,
  CONSTRAINT `FK4cojmkiyo3kxx7nkcry1s5avb`
    FOREIGN KEY (`airline_id`)
    REFERENCES `db_booking`.`airlines` (`id`),
  CONSTRAINT `FKdomstxk8kkil9nrcm6hyplg41`
    FOREIGN KEY (`rating_id`)
    REFERENCES `db_booking`.`rates` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`branch_offices`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`branch_offices` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `latitude` DECIMAL(19,2) NULL DEFAULT NULL,
  `longitude` DECIMAL(19,2) NULL DEFAULT NULL,
  `name` VARCHAR(255) NOT NULL,
  `address` INT(11) NULL DEFAULT NULL,
  `rentacar` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK7l8h8a9e63uw9s53pt0e5n3c1` (`address` ASC) VISIBLE,
  INDEX `FK580x5akws02owe8k8vrj3qbte` (`rentacar` ASC) VISIBLE,
  CONSTRAINT `FK580x5akws02owe8k8vrj3qbte`
    FOREIGN KEY (`rentacar`)
    REFERENCES `db_booking`.`rent_a_cars` (`id`),
  CONSTRAINT `FK7l8h8a9e63uw9s53pt0e5n3c1`
    FOREIGN KEY (`address`)
    REFERENCES `db_booking`.`destinations` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`vehicles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`vehicles` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `brand` VARCHAR(255) NULL DEFAULT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `model` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NOT NULL,
  `price` FLOAT NULL DEFAULT NULL,
  `production_year` INT(11) NULL DEFAULT NULL,
  `seats_num` INT(11) NULL DEFAULT NULL,
  `type` VARCHAR(255) NULL DEFAULT NULL,
  `currently_in_id` INT(11) NULL DEFAULT NULL,
  `rentacar` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK4x24lxgdu2ji3xgoynpcmbqew` (`currently_in_id` ASC) VISIBLE,
  INDEX `FK35kp14d676txcdvvu3mln1q53` (`rentacar` ASC) VISIBLE,
  CONSTRAINT `FK35kp14d676txcdvvu3mln1q53`
    FOREIGN KEY (`rentacar`)
    REFERENCES `db_booking`.`rent_a_cars` (`id`),
  CONSTRAINT `FK4x24lxgdu2ji3xgoynpcmbqew`
    FOREIGN KEY (`currently_in_id`)
    REFERENCES `db_booking`.`branch_offices` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`rent_a_car_reservations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`rent_a_car_reservations` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `days` INT(11) NULL DEFAULT NULL,
  `passanger_num` INT(11) NULL DEFAULT NULL,
  `pick_up_date` DATE NULL DEFAULT NULL,
  `rentacar` VARCHAR(255) NULL DEFAULT NULL,
  `total_price` FLOAT NULL DEFAULT NULL,
  `vehicle_checked` BIT(1) NOT NULL,
  `drop_off_location_id` INT(11) NULL DEFAULT NULL,
  `pick_up_location_id` INT(11) NULL DEFAULT NULL,
  `reservation_id` INT(11) NULL DEFAULT NULL,
  `vehicle_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKoni56h0fp8i8d6tkolbhgehp6` (`drop_off_location_id` ASC) VISIBLE,
  INDEX `FKjamt4a565hvhc7k3w81c27jfo` (`pick_up_location_id` ASC) VISIBLE,
  INDEX `FKya6cx9dmoqlkul131a4we5qi` (`reservation_id` ASC) VISIBLE,
  INDEX `FK392c0wwb2grrf4amj1f8o9xgo` (`vehicle_id` ASC) VISIBLE,
  CONSTRAINT `FK392c0wwb2grrf4amj1f8o9xgo`
    FOREIGN KEY (`vehicle_id`)
    REFERENCES `db_booking`.`vehicles` (`id`),
  CONSTRAINT `FKjamt4a565hvhc7k3w81c27jfo`
    FOREIGN KEY (`pick_up_location_id`)
    REFERENCES `db_booking`.`branch_offices` (`id`),
  CONSTRAINT `FKoni56h0fp8i8d6tkolbhgehp6`
    FOREIGN KEY (`drop_off_location_id`)
    REFERENCES `db_booking`.`branch_offices` (`id`),
  CONSTRAINT `FKya6cx9dmoqlkul131a4we5qi`
    FOREIGN KEY (`reservation_id`)
    REFERENCES `db_booking`.`reservations` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`hotel_reservations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`hotel_reservations` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `check_in_date` DATE NULL DEFAULT NULL,
  `guests` INT(11) NULL DEFAULT NULL,
  `nights` INT(11) NULL DEFAULT NULL,
  `total_price` FLOAT NULL DEFAULT NULL,
  `reservation_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKmsod75vorlmlv6jf7lfdkuupk` (`reservation_id` ASC) VISIBLE,
  CONSTRAINT `FKmsod75vorlmlv6jf7lfdkuupk`
    FOREIGN KEY (`reservation_id`)
    REFERENCES `db_booking`.`reservations` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`reservations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`reservations` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `flight_reservation_id` INT(11) NULL DEFAULT NULL,
  `hotel_reservation_id` INT(11) NULL DEFAULT NULL,
  `rentacar_reservation_id` INT(11) NULL DEFAULT NULL,
  `user` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK4dst665j7mdwgjwmwgmwf59wt` (`flight_reservation_id` ASC) VISIBLE,
  INDEX `FKd6ccw7ql14725lqwqvgumptd0` (`hotel_reservation_id` ASC) VISIBLE,
  INDEX `FKcqbv5shfd9t6sqph9ftyhuj00` (`rentacar_reservation_id` ASC) VISIBLE,
  INDEX `FK2cdwgt04qlahsefjllvkjs648` (`user` ASC) VISIBLE,
  CONSTRAINT `FK2cdwgt04qlahsefjllvkjs648`
    FOREIGN KEY (`user`)
    REFERENCES `db_booking`.`users` (`id`),
  CONSTRAINT `FK4dst665j7mdwgjwmwgmwf59wt`
    FOREIGN KEY (`flight_reservation_id`)
    REFERENCES `db_booking`.`flight_reservations` (`id`),
  CONSTRAINT `FKcqbv5shfd9t6sqph9ftyhuj00`
    FOREIGN KEY (`rentacar_reservation_id`)
    REFERENCES `db_booking`.`rent_a_car_reservations` (`id`),
  CONSTRAINT `FKd6ccw7ql14725lqwqvgumptd0`
    FOREIGN KEY (`hotel_reservation_id`)
    REFERENCES `db_booking`.`hotel_reservations` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`flight_reservations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`flight_reservations` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `carry_on` INT(11) NOT NULL,
  `checked` INT(11) NOT NULL,
  `total_price` FLOAT NULL DEFAULT NULL,
  `flight_id` INT(11) NULL DEFAULT NULL,
  `reservation_id` INT(11) NULL DEFAULT NULL,
  `ticket_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK2kih3wrn3ub1natnutk1y049h` (`flight_id` ASC) VISIBLE,
  INDEX `FK2vc4lp0lt81kvrm95e40mcfbi` (`reservation_id` ASC) VISIBLE,
  INDEX `FK7k0tctw1txto2v0lcf50xbkbj` (`ticket_id` ASC) VISIBLE,
  CONSTRAINT `FK2kih3wrn3ub1natnutk1y049h`
    FOREIGN KEY (`flight_id`)
    REFERENCES `db_booking`.`flights` (`id`),
  CONSTRAINT `FK2vc4lp0lt81kvrm95e40mcfbi`
    FOREIGN KEY (`reservation_id`)
    REFERENCES `db_booking`.`reservations` (`id`),
  CONSTRAINT `FK7k0tctw1txto2v0lcf50xbkbj`
    FOREIGN KEY (`ticket_id`)
    REFERENCES `db_booking`.`tickets` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`airlines_reservations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`airlines_reservations` (
  `airline_id` INT(11) NOT NULL,
  `reservations_id` INT(11) NOT NULL,
  PRIMARY KEY (`airline_id`, `reservations_id`),
  UNIQUE INDEX `UK_hg4p78wx6am47q7hyjk94k05d` (`reservations_id` ASC) VISIBLE,
  CONSTRAINT `FKmc9xrf2gxfebw42j0oxhpgu87`
    FOREIGN KEY (`airline_id`)
    REFERENCES `db_booking`.`airlines` (`id`),
  CONSTRAINT `FKmg3m1inx10hfl1640k6ofxk32`
    FOREIGN KEY (`reservations_id`)
    REFERENCES `db_booking`.`flight_reservations` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`authorities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`authorities` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`discounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`discounts` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `discount` INT(11) NULL DEFAULT NULL,
  `min_pts` INT(11) NULL DEFAULT NULL,
  `user_type` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_ie6k9rtgsmbhf1fgssik4053p` (`user_type` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`extra_service_prices`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`extra_service_prices` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `price` FLOAT NULL DEFAULT NULL,
  `type` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`flights_rating`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`flights_rating` (
  `flight_id` INT(11) NOT NULL,
  `rating_id` INT(11) NOT NULL,
  PRIMARY KEY (`flight_id`, `rating_id`),
  UNIQUE INDEX `UK_qrtqsi3myjdvsha8tp4ivjdlx` (`rating_id` ASC) VISIBLE,
  CONSTRAINT `FK20rm1mp26o3ta3ftw24wu9rvo`
    FOREIGN KEY (`rating_id`)
    REFERENCES `db_booking`.`rates` (`id`),
  CONSTRAINT `FKno3fah631xf1apvwykgfc1sar`
    FOREIGN KEY (`flight_id`)
    REFERENCES `db_booking`.`flights` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`flights_seats`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`flights_seats` (
  `flight_id` INT(11) NOT NULL,
  `seats_id` INT(11) NOT NULL,
  PRIMARY KEY (`flight_id`, `seats_id`),
  UNIQUE INDEX `UK_rnacc13pkmkyhjdp1f5wfrr93` (`seats_id` ASC) VISIBLE,
  CONSTRAINT `FKafbkk6e41wfk5fns38yepnvja`
    FOREIGN KEY (`seats_id`)
    REFERENCES `db_booking`.`flight_seats` (`id`),
  CONSTRAINT `FKanorj367yjwpti88rlkdsyb1k`
    FOREIGN KEY (`flight_id`)
    REFERENCES `db_booking`.`flights` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`ticket_prices`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`ticket_prices` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `price` FLOAT NULL DEFAULT NULL,
  `travel_class` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`flights_ticket_prices`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`flights_ticket_prices` (
  `flight_id` INT(11) NOT NULL,
  `ticket_prices_id` INT(11) NOT NULL,
  PRIMARY KEY (`flight_id`, `ticket_prices_id`),
  UNIQUE INDEX `UK_3ro5a47sglpsrtl3fusk3317v` (`ticket_prices_id` ASC) VISIBLE,
  CONSTRAINT `FKcyfynq5v29luqu7kgx6u1sj26`
    FOREIGN KEY (`ticket_prices_id`)
    REFERENCES `db_booking`.`ticket_prices` (`id`),
  CONSTRAINT `FKmocs711u12w4r3s9bljqlw6cv`
    FOREIGN KEY (`flight_id`)
    REFERENCES `db_booking`.`flights` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`flights_tickets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`flights_tickets` (
  `flight_id` INT(11) NOT NULL,
  `tickets_id` INT(11) NOT NULL,
  PRIMARY KEY (`flight_id`, `tickets_id`),
  UNIQUE INDEX `UK_s7yoxedw5vqm2paba9kna49li` (`tickets_id` ASC) VISIBLE,
  CONSTRAINT `FKf0612y16e6bmc25jga6kfnu1j`
    FOREIGN KEY (`tickets_id`)
    REFERENCES `db_booking`.`tickets` (`id`),
  CONSTRAINT `FKidlfx0jc71u96tb6nna20mwhq`
    FOREIGN KEY (`flight_id`)
    REFERENCES `db_booking`.`flights` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`friendships`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`friendships` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `status` INT(11) NOT NULL,
  `user1` INT(11) NOT NULL,
  `user2` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK4pib4oexnqm7vcd3jvvbfcauf` (`user1` ASC) VISIBLE,
  INDEX `FKc5uoty8uufo14qbs45min70sb` (`user2` ASC) VISIBLE,
  CONSTRAINT `FK4pib4oexnqm7vcd3jvvbfcauf`
    FOREIGN KEY (`user1`)
    REFERENCES `db_booking`.`users` (`id`),
  CONSTRAINT `FKc5uoty8uufo14qbs45min70sb`
    FOREIGN KEY (`user2`)
    REFERENCES `db_booking`.`users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`hibernate_sequence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`hibernate_sequence` (
  `next_val` BIGINT(20) NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`rooms`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`rooms` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `balcony` BIT(1) NULL DEFAULT NULL,
  `beds` INT(11) NULL DEFAULT NULL,
  `discount` INT(11) NULL DEFAULT NULL,
  `floor` INT(11) NULL DEFAULT NULL,
  `room_num` INT(11) NULL DEFAULT NULL,
  `hotel` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKp3inkpl37lxejlqd7lfx7cpc6` (`hotel` ASC) VISIBLE,
  CONSTRAINT `FKp3inkpl37lxejlqd7lfx7cpc6`
    FOREIGN KEY (`hotel`)
    REFERENCES `db_booking`.`hotels` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`room_reservations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`room_reservations` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `room_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKf520kpinewy2hpxke3uh7qmvd` (`room_id` ASC) VISIBLE,
  CONSTRAINT `FKf520kpinewy2hpxke3uh7qmvd`
    FOREIGN KEY (`room_id`)
    REFERENCES `db_booking`.`rooms` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`hotel_reservations_rooms`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`hotel_reservations_rooms` (
  `hotel_reservation_id` INT(11) NOT NULL,
  `rooms_id` INT(11) NOT NULL,
  PRIMARY KEY (`hotel_reservation_id`, `rooms_id`),
  UNIQUE INDEX `UK_aict74r3h9v6oxn5t7akou41g` (`rooms_id` ASC) VISIBLE,
  CONSTRAINT `FKhokcq4pftpgjhtmtdh9b00kbc`
    FOREIGN KEY (`hotel_reservation_id`)
    REFERENCES `db_booking`.`hotel_reservations` (`id`),
  CONSTRAINT `FKkh8ie76gce0j1gl013f8rh3jk`
    FOREIGN KEY (`rooms_id`)
    REFERENCES `db_booking`.`room_reservations` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`hotels_extra_service_prices`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`hotels_extra_service_prices` (
  `hotel_id` INT(11) NOT NULL,
  `extra_service_prices_id` INT(11) NOT NULL,
  PRIMARY KEY (`hotel_id`, `extra_service_prices_id`),
  UNIQUE INDEX `UK_8dtdv3fmxxoa04h3h7lpou9vo` (`extra_service_prices_id` ASC) VISIBLE,
  CONSTRAINT `FK2hw8e1ixe5oldr37vby3ybyad`
    FOREIGN KEY (`hotel_id`)
    REFERENCES `db_booking`.`hotels` (`id`),
  CONSTRAINT `FK56bs4kxld84xak9s67wjrht7k`
    FOREIGN KEY (`extra_service_prices_id`)
    REFERENCES `db_booking`.`extra_service_prices` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`hotels_rating`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`hotels_rating` (
  `hotel_id` INT(11) NOT NULL,
  `rating_id` INT(11) NOT NULL,
  PRIMARY KEY (`hotel_id`, `rating_id`),
  UNIQUE INDEX `UK_7ib1v9awnkki3w21ldov2jlos` (`rating_id` ASC) VISIBLE,
  CONSTRAINT `FKes4b6xrf46hkriyqgbbunlxgw`
    FOREIGN KEY (`hotel_id`)
    REFERENCES `db_booking`.`hotels` (`id`),
  CONSTRAINT `FKhal4vh8aj99apc1sa0v179dcy`
    FOREIGN KEY (`rating_id`)
    REFERENCES `db_booking`.`rates` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`hotels_reservations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`hotels_reservations` (
  `hotel_id` INT(11) NOT NULL,
  `reservations_id` INT(11) NOT NULL,
  PRIMARY KEY (`hotel_id`, `reservations_id`),
  UNIQUE INDEX `UK_3kyklm1qp1hxxbsvthrmnm8ty` (`reservations_id` ASC) VISIBLE,
  CONSTRAINT `FKi72lvjgnk8ls3cl7kogg7t9jn`
    FOREIGN KEY (`hotel_id`)
    REFERENCES `db_booking`.`hotels` (`id`),
  CONSTRAINT `FKm12qf7h50ug4clbokcn9vpkvl`
    FOREIGN KEY (`reservations_id`)
    REFERENCES `db_booking`.`hotel_reservations` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`quick_rent_a_car_reservations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`quick_rent_a_car_reservations` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `discount` INT(11) NULL DEFAULT NULL,
  `drop_off_date` DATE NULL DEFAULT NULL,
  `pick_up_date` DATE NULL DEFAULT NULL,
  `vehicle_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKrt29936vnmp3s9bgxwvknf01l` (`vehicle_id` ASC) VISIBLE,
  CONSTRAINT `FKrt29936vnmp3s9bgxwvknf01l`
    FOREIGN KEY (`vehicle_id`)
    REFERENCES `db_booking`.`vehicles` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`rent_a_cars_rating`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`rent_a_cars_rating` (
  `rentacar_id` INT(11) NOT NULL,
  `rating_id` INT(11) NOT NULL,
  PRIMARY KEY (`rentacar_id`, `rating_id`),
  UNIQUE INDEX `UK_5hukych7ndtt4ebtqf0iorhb` (`rating_id` ASC) VISIBLE,
  CONSTRAINT `FK7993colx24t7w0brvwa18xl0r`
    FOREIGN KEY (`rentacar_id`)
    REFERENCES `db_booking`.`rent_a_cars` (`id`),
  CONSTRAINT `FKaynujg4cj0ehwn93ktn85yt2x`
    FOREIGN KEY (`rating_id`)
    REFERENCES `db_booking`.`rates` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`rent_a_cars_vehicles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`rent_a_cars_vehicles` (
  `rentacar_id` INT(11) NOT NULL,
  `vehicles_id` INT(11) NOT NULL,
  PRIMARY KEY (`rentacar_id`, `vehicles_id`),
  UNIQUE INDEX `UK_nagghauhnxktcs3kvad031rir` (`vehicles_id` ASC) VISIBLE,
  CONSTRAINT `FKo83w2rjbnd8xfiee9udmp0qnm`
    FOREIGN KEY (`vehicles_id`)
    REFERENCES `db_booking`.`vehicles` (`id`),
  CONSTRAINT `FKoice5aqps4t4vwbi8kf5b96ig`
    FOREIGN KEY (`rentacar_id`)
    REFERENCES `db_booking`.`rent_a_cars` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`room_extraservices`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`room_extraservices` (
  `room_id` INT(11) NOT NULL,
  `extraservices` VARCHAR(255) NULL DEFAULT NULL,
  INDEX `FKayukxrcxuxqpyp64e09youvsw` (`room_id` ASC) VISIBLE,
  CONSTRAINT `FKayukxrcxuxqpyp64e09youvsw`
    FOREIGN KEY (`room_id`)
    REFERENCES `db_booking`.`rooms` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`room_prices`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`room_prices` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `from_date` DATETIME NOT NULL,
  `price_per_night` FLOAT NULL DEFAULT NULL,
  `to_date` DATETIME NOT NULL,
  `room` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKhejpa6vrn9xmetlgvjp8v8tr2` (`room` ASC) VISIBLE,
  CONSTRAINT `FKhejpa6vrn9xmetlgvjp8v8tr2`
    FOREIGN KEY (`room`)
    REFERENCES `db_booking`.`rooms` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`room_reservations_extra_services`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`room_reservations_extra_services` (
  `room_reservation_id` INT(11) NOT NULL,
  `extra_services_id` INT(11) NOT NULL,
  PRIMARY KEY (`room_reservation_id`, `extra_services_id`),
  UNIQUE INDEX `UK_hmumcs0lcuivf6uu3wsep2ntq` (`extra_services_id` ASC) VISIBLE,
  CONSTRAINT `FKgm9op3keqa9ikbmtck1aw9og7`
    FOREIGN KEY (`room_reservation_id`)
    REFERENCES `db_booking`.`room_reservations` (`id`),
  CONSTRAINT `FKqwjktd80yrwnamy6eenfxp090`
    FOREIGN KEY (`extra_services_id`)
    REFERENCES `db_booking`.`extra_service_prices` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`rooms_rating`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`rooms_rating` (
  `room_id` INT(11) NOT NULL,
  `rating_id` INT(11) NOT NULL,
  PRIMARY KEY (`room_id`, `rating_id`),
  UNIQUE INDEX `UK_jd3qn6tq2tb1kuegmkpmi2l2w` (`rating_id` ASC) VISIBLE,
  CONSTRAINT `FK8g5a6gp2y25r34dbnb0yw1qgy`
    FOREIGN KEY (`rating_id`)
    REFERENCES `db_booking`.`rates` (`id`),
  CONSTRAINT `FKhlgj8dq27xkq87qub514cduhv`
    FOREIGN KEY (`room_id`)
    REFERENCES `db_booking`.`rooms` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`user_authority`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`user_authority` (
  `user_id` INT(11) NOT NULL,
  `authority_id` BIGINT(20) NOT NULL,
  INDEX `FKil6f39w6fgqh4gk855pstsnmy` (`authority_id` ASC) VISIBLE,
  INDEX `FKhi46vu7680y1hwvmnnuh4cybx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FKhi46vu7680y1hwvmnnuh4cybx`
    FOREIGN KEY (`user_id`)
    REFERENCES `db_booking`.`users` (`id`),
  CONSTRAINT `FKil6f39w6fgqh4gk855pstsnmy`
    FOREIGN KEY (`authority_id`)
    REFERENCES `db_booking`.`authorities` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`users_friends`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`users_friends` (
  `registered_user_id` INT(11) NOT NULL,
  `friends_id` INT(11) NOT NULL,
  PRIMARY KEY (`registered_user_id`, `friends_id`),
  UNIQUE INDEX `UK_eslx46xq9voscm63qgdtr5c28` (`friends_id` ASC) VISIBLE,
  CONSTRAINT `FKcgu71m2uxhsoc51uur6iisu8l`
    FOREIGN KEY (`friends_id`)
    REFERENCES `db_booking`.`friendships` (`id`),
  CONSTRAINT `FKegw2xxw196gm7recar5dnhgmn`
    FOREIGN KEY (`registered_user_id`)
    REFERENCES `db_booking`.`users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`users_rates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`users_rates` (
  `registered_user_id` INT(11) NOT NULL,
  `rates_id` INT(11) NOT NULL,
  PRIMARY KEY (`registered_user_id`, `rates_id`),
  UNIQUE INDEX `UK_2vd6evd6wx7l09tbj5ju50m2y` (`rates_id` ASC) VISIBLE,
  CONSTRAINT `FK2grjevlfdeo85j3mrko2eudyw`
    FOREIGN KEY (`rates_id`)
    REFERENCES `db_booking`.`rates` (`id`),
  CONSTRAINT `FKahihebc6jpmj3xsowmviv9ne7`
    FOREIGN KEY (`registered_user_id`)
    REFERENCES `db_booking`.`users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`users_reservations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`users_reservations` (
  `registered_user_id` INT(11) NOT NULL,
  `reservations_id` INT(11) NOT NULL,
  PRIMARY KEY (`registered_user_id`, `reservations_id`),
  UNIQUE INDEX `UK_3goe1sku3r0mq5dycjmkb3epv` (`reservations_id` ASC) VISIBLE,
  CONSTRAINT `FKa6v8lh9igd0e61uhbuyxjsak`
    FOREIGN KEY (`reservations_id`)
    REFERENCES `db_booking`.`reservations` (`id`),
  CONSTRAINT `FKp5dgi41g6blhxjsij9rvb6lv4`
    FOREIGN KEY (`registered_user_id`)
    REFERENCES `db_booking`.`users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`vehicles_rating`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`vehicles_rating` (
  `vehicle_id` INT(11) NOT NULL,
  `rating_id` INT(11) NOT NULL,
  PRIMARY KEY (`vehicle_id`, `rating_id`),
  UNIQUE INDEX `UK_1yoatxqyabdrf3qc7tah77uok` (`rating_id` ASC) VISIBLE,
  CONSTRAINT `FK5vkv7yflfkdd95wen7jm67hek`
    FOREIGN KEY (`vehicle_id`)
    REFERENCES `db_booking`.`vehicles` (`id`),
  CONSTRAINT `FKa8579ccnwmtxeelfr8213yp0n`
    FOREIGN KEY (`rating_id`)
    REFERENCES `db_booking`.`rates` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_booking`.`verification_tokens`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_booking`.`verification_tokens` (
  `id` INT(11) NOT NULL,
  `token` VARCHAR(255) NOT NULL,
  `user` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKffgjxs3hrn4cxqrettdc8s0la` (`user` ASC) VISIBLE,
  CONSTRAINT `FKffgjxs3hrn4cxqrettdc8s0la`
    FOREIGN KEY (`user`)
    REFERENCES `db_booking`.`users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `airline_destinations`
--

DROP TABLE IF EXISTS `airline_destinations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `airline_destinations` (
  `airline_id` int(11) NOT NULL,
  `destination_id` int(11) NOT NULL,
  PRIMARY KEY (`airline_id`,`destination_id`),
  KEY `FK1ybym1fne9crn5gmqwcfqiadw` (`destination_id`),
  CONSTRAINT `FK1ybym1fne9crn5gmqwcfqiadw` FOREIGN KEY (`destination_id`) REFERENCES `destinations` (`id`),
  CONSTRAINT `FKbwrpfiwdw787xrw5s4f424lgf` FOREIGN KEY (`airline_id`) REFERENCES `airlines` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `airline_destinations`
--

LOCK TABLES `airline_destinations` WRITE;
/*!40000 ALTER TABLE `airline_destinations` DISABLE KEYS */;
INSERT INTO `airline_destinations` VALUES (1,9),(1,10);
/*!40000 ALTER TABLE `airline_destinations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:37

-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `airlines`
--

DROP TABLE IF EXISTS `airlines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `airlines` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `latitude` decimal(19,2) DEFAULT NULL,
  `longitude` decimal(19,2) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `address` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_akufoupt618c8vlcfaa0gvfqq` (`name`),
  KEY `FK69ct3xjcei6w5gr52cp6alub1` (`address`),
  CONSTRAINT `FK69ct3xjcei6w5gr52cp6alub1` FOREIGN KEY (`address`) REFERENCES `destinations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `airlines`
--

LOCK TABLES `airlines` WRITE;
/*!40000 ALTER TABLE `airlines` DISABLE KEYS */;
INSERT INTO `airlines` VALUES (1,'',44.79,44.79,'AirSerbia',3);
/*!40000 ALTER TABLE `airlines` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:26
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `airlines_discount_tickets`
--

DROP TABLE IF EXISTS `airlines_discount_tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `airlines_discount_tickets` (
  `airline_id` int(11) NOT NULL,
  `discount_tickets_id` int(11) NOT NULL,
  PRIMARY KEY (`airline_id`,`discount_tickets_id`),
  UNIQUE KEY `UK_o2oj2tidhctdeqlsb85jb53r3` (`discount_tickets_id`),
  CONSTRAINT `FKke1xvhambi53olpuhobiv3dmk` FOREIGN KEY (`airline_id`) REFERENCES `airlines` (`id`),
  CONSTRAINT `FKonyjcb1eyo0rkavat0m0dufh1` FOREIGN KEY (`discount_tickets_id`) REFERENCES `tickets` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `airlines_discount_tickets`
--

LOCK TABLES `airlines_discount_tickets` WRITE;
/*!40000 ALTER TABLE `airlines_discount_tickets` DISABLE KEYS */;
/*!40000 ALTER TABLE `airlines_discount_tickets` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:37
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `airlines_flights`
--

DROP TABLE IF EXISTS `airlines_flights`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `airlines_flights` (
  `airline_id` int(11) NOT NULL,
  `flights_id` int(11) NOT NULL,
  PRIMARY KEY (`airline_id`,`flights_id`),
  UNIQUE KEY `UK_1rytj77afuq7iv692m5p5sl2k` (`flights_id`),
  CONSTRAINT `FKgpp106ab9n43hr8pfscqtxe7b` FOREIGN KEY (`airline_id`) REFERENCES `airlines` (`id`),
  CONSTRAINT `FKnsdqk6uus6np20g4gjklj4toc` FOREIGN KEY (`flights_id`) REFERENCES `flights` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `airlines_flights`
--

LOCK TABLES `airlines_flights` WRITE;
/*!40000 ALTER TABLE `airlines_flights` DISABLE KEYS */;
INSERT INTO `airlines_flights` VALUES (1,1),(1,2);
/*!40000 ALTER TABLE `airlines_flights` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:20
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `airlines_luggage_prices`
--

DROP TABLE IF EXISTS `airlines_luggage_prices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `airlines_luggage_prices` (
  `airline_id` int(11) NOT NULL,
  `luggage_prices_id` int(11) NOT NULL,
  PRIMARY KEY (`airline_id`,`luggage_prices_id`),
  UNIQUE KEY `UK_s9ee4dg7nd1se9ag2gw3et9qt` (`luggage_prices_id`),
  CONSTRAINT `FK3be83lrqu0tiiyaph9v3gfl22` FOREIGN KEY (`airline_id`) REFERENCES `airlines` (`id`),
  CONSTRAINT `FKccp2ul0kcskww2imgy9fy2ali` FOREIGN KEY (`luggage_prices_id`) REFERENCES `luggage_prices` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `airlines_luggage_prices`
--

LOCK TABLES `airlines_luggage_prices` WRITE;
/*!40000 ALTER TABLE `airlines_luggage_prices` DISABLE KEYS */;
INSERT INTO `airlines_luggage_prices` VALUES (1,1),(1,2);
/*!40000 ALTER TABLE `airlines_luggage_prices` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:24
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `airlines_rating`
--

DROP TABLE IF EXISTS `airlines_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `airlines_rating` (
  `airline_id` int(11) NOT NULL,
  `rating_id` int(11) NOT NULL,
  PRIMARY KEY (`airline_id`,`rating_id`),
  UNIQUE KEY `UK_mxf4v8scr9ao0yfc1icpfh3y` (`rating_id`),
  CONSTRAINT `FK4cojmkiyo3kxx7nkcry1s5avb` FOREIGN KEY (`airline_id`) REFERENCES `airlines` (`id`),
  CONSTRAINT `FKdomstxk8kkil9nrcm6hyplg41` FOREIGN KEY (`rating_id`) REFERENCES `rates` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `airlines_rating`
--

LOCK TABLES `airlines_rating` WRITE;
/*!40000 ALTER TABLE `airlines_rating` DISABLE KEYS */;
/*!40000 ALTER TABLE `airlines_rating` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:34
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `airlines_reservations`
--

DROP TABLE IF EXISTS `airlines_reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `airlines_reservations` (
  `airline_id` int(11) NOT NULL,
  `reservations_id` int(11) NOT NULL,
  PRIMARY KEY (`airline_id`,`reservations_id`),
  UNIQUE KEY `UK_hg4p78wx6am47q7hyjk94k05d` (`reservations_id`),
  CONSTRAINT `FKmc9xrf2gxfebw42j0oxhpgu87` FOREIGN KEY (`airline_id`) REFERENCES `airlines` (`id`),
  CONSTRAINT `FKmg3m1inx10hfl1640k6ofxk32` FOREIGN KEY (`reservations_id`) REFERENCES `flight_reservations` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `airlines_reservations`
--

LOCK TABLES `airlines_reservations` WRITE;
/*!40000 ALTER TABLE `airlines_reservations` DISABLE KEYS */;
/*!40000 ALTER TABLE `airlines_reservations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:31
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `authorities` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorities`
--

LOCK TABLES `authorities` WRITE;
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
INSERT INTO `authorities` VALUES (1,'SYS_ADMIN'),(2,'RAC_ADMIN'),(3,'RAC_ADMIN'),(4,'AIRLINE_ADMIN'),(5,'HOTEL_ADMIN'),(6,'USER'),(7,'USER');
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:35
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `branch_offices`
--

DROP TABLE IF EXISTS `branch_offices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `branch_offices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` decimal(19,2) DEFAULT NULL,
  `longitude` decimal(19,2) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `address` int(11) DEFAULT NULL,
  `rentacar` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7l8h8a9e63uw9s53pt0e5n3c1` (`address`),
  KEY `FK580x5akws02owe8k8vrj3qbte` (`rentacar`),
  CONSTRAINT `FK580x5akws02owe8k8vrj3qbte` FOREIGN KEY (`rentacar`) REFERENCES `rent_a_cars` (`id`),
  CONSTRAINT `FK7l8h8a9e63uw9s53pt0e5n3c1` FOREIGN KEY (`address`) REFERENCES `destinations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch_offices`
--

LOCK TABLES `branch_offices` WRITE;
/*!40000 ALTER TABLE `branch_offices` DISABLE KEYS */;
INSERT INTO `branch_offices` VALUES (1,44.79,44.79,'Main',5,1),(2,47.50,19.04,'Secondary',6,1),(3,47.50,19.04,'BudaMain',7,2);
/*!40000 ALTER TABLE `branch_offices` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:17
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `destinations`
--

DROP TABLE IF EXISTS `destinations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `destinations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `destinations`
--

LOCK TABLES `destinations` WRITE;
/*!40000 ALTER TABLE `destinations` DISABLE KEYS */;
INSERT INTO `destinations` VALUES (1,'Belgrade','Serbia'),(2,'Budapest','Hungary'),(3,'Belgrade','Serbia'),(4,'Belgrade','Serbia'),(5,'Belgrade','Serbia'),(6,'Hungary','Serbia'),(7,'Budapest','Hungary'),(8,'Budapest','Hungary'),(9,'Belgrade','Serbia'),(10,'Budapest','Hungary');
/*!40000 ALTER TABLE `destinations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:16
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `discounts`
--

DROP TABLE IF EXISTS `discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `discounts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `discount` int(11) DEFAULT NULL,
  `min_pts` int(11) DEFAULT NULL,
  `user_type` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ie6k9rtgsmbhf1fgssik4053p` (`user_type`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discounts`
--

LOCK TABLES `discounts` WRITE;
/*!40000 ALTER TABLE `discounts` DISABLE KEYS */;
INSERT INTO `discounts` VALUES (1,0,0,0),(2,0,15,1),(3,0,30,2),(4,0,45,3);
/*!40000 ALTER TABLE `discounts` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:27
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `extra_service_prices`
--

DROP TABLE IF EXISTS `extra_service_prices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `extra_service_prices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` float DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `extra_service_prices`
--

LOCK TABLES `extra_service_prices` WRITE;
/*!40000 ALTER TABLE `extra_service_prices` DISABLE KEYS */;
INSERT INTO `extra_service_prices` VALUES (1,0,3),(2,0,4),(3,0,5),(4,0,7),(5,0,8),(6,0,2),(7,0,0),(8,0,1),(9,0,6);
/*!40000 ALTER TABLE `extra_service_prices` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:17
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `flight_reservations`
--

DROP TABLE IF EXISTS `flight_reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `flight_reservations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `carry_on` int(11) NOT NULL,
  `checked` int(11) NOT NULL,
  `total_price` float DEFAULT NULL,
  `flight_id` int(11) DEFAULT NULL,
  `reservation_id` int(11) DEFAULT NULL,
  `ticket_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2kih3wrn3ub1natnutk1y049h` (`flight_id`),
  KEY `FK2vc4lp0lt81kvrm95e40mcfbi` (`reservation_id`),
  KEY `FK7k0tctw1txto2v0lcf50xbkbj` (`ticket_id`),
  CONSTRAINT `FK2kih3wrn3ub1natnutk1y049h` FOREIGN KEY (`flight_id`) REFERENCES `flights` (`id`),
  CONSTRAINT `FK2vc4lp0lt81kvrm95e40mcfbi` FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`id`),
  CONSTRAINT `FK7k0tctw1txto2v0lcf50xbkbj` FOREIGN KEY (`ticket_id`) REFERENCES `tickets` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flight_reservations`
--

LOCK TABLES `flight_reservations` WRITE;
/*!40000 ALTER TABLE `flight_reservations` DISABLE KEYS */;
/*!40000 ALTER TABLE `flight_reservations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:20
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `flight_seats`
--

DROP TABLE IF EXISTS `flight_seats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `flight_seats` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seat_letter` varchar(255) NOT NULL,
  `seat_row` int(11) NOT NULL,
  `travel_class` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flight_seats`
--

LOCK TABLES `flight_seats` WRITE;
/*!40000 ALTER TABLE `flight_seats` DISABLE KEYS */;
INSERT INTO `flight_seats` VALUES (1,'B',5,'ECONOMY'),(2,'C',10,'ECONOMY'),(3,'B',3,'BUSINESS'),(4,'C',1,'FIRST'),(5,'D',11,'ECONOMY'),(6,'B',8,'ECONOMY'),(7,'A',9,'ECONOMY'),(8,'A',1,'FIRST'),(9,'B',4,'BUSINESS'),(10,'C',4,'BUSINESS'),(11,'A',6,'ECONOMY'),(12,'D',8,'ECONOMY'),(13,'A',8,'ECONOMY'),(14,'B',10,'ECONOMY'),(15,'D',3,'BUSINESS'),(16,'B',1,'FIRST'),(17,'D',7,'ECONOMY'),(18,'A',3,'BUSINESS'),(19,'D',6,'ECONOMY'),(20,'C',5,'ECONOMY'),(21,'B',7,'ECONOMY'),(22,'B',2,'BUSINESS'),(23,'B',9,'ECONOMY'),(24,'B',6,'ECONOMY'),(25,'C',2,'BUSINESS'),(26,'D',10,'ECONOMY'),(27,'A',4,'BUSINESS'),(28,'D',4,'BUSINESS'),(29,'C',7,'ECONOMY'),(30,'A',2,'BUSINESS'),(31,'C',8,'ECONOMY'),(32,'D',9,'ECONOMY'),(33,'A',10,'ECONOMY'),(34,'A',11,'ECONOMY'),(35,'A',2,'FIRST'),(36,'A',7,'ECONOMY'),(37,'C',9,'ECONOMY'),(38,'C',3,'BUSINESS'),(39,'C',5,'BUSINESS'),(40,'D',2,'FIRST'),(41,'C',6,'ECONOMY'),(42,'D',5,'BUSINESS'),(43,'A',5,'BUSINESS'),(44,'A',5,'ECONOMY'),(45,'B',5,'BUSINESS'),(46,'A',4,'BUSINESS'),(47,'B',5,'ECONOMY'),(48,'C',8,'ECONOMY'),(49,'C',2,'BUSINESS'),(50,'C',5,'BUSINESS'),(51,'D',5,'BUSINESS'),(52,'A',10,'ECONOMY'),(53,'C',5,'ECONOMY'),(54,'C',7,'ECONOMY'),(55,'A',2,'FIRST'),(56,'C',4,'BUSINESS'),(57,'A',6,'ECONOMY'),(58,'A',3,'BUSINESS'),(59,'D',3,'BUSINESS'),(60,'B',6,'ECONOMY'),(61,'B',3,'BUSINESS'),(62,'B',10,'ECONOMY'),(63,'B',4,'BUSINESS'),(64,'C',1,'FIRST'),(65,'D',4,'BUSINESS'),(66,'C',6,'ECONOMY'),(67,'C',3,'BUSINESS'),(68,'D',9,'ECONOMY'),(69,'B',8,'ECONOMY'),(70,'A',9,'ECONOMY'),(71,'D',10,'ECONOMY'),(72,'B',2,'BUSINESS'),(73,'A',2,'BUSINESS'),(74,'D',2,'FIRST'),(75,'B',5,'BUSINESS'),(76,'A',8,'ECONOMY'),(77,'D',8,'ECONOMY'),(78,'C',10,'ECONOMY'),(79,'A',1,'FIRST'),(80,'D',6,'ECONOMY'),(81,'A',7,'ECONOMY'),(82,'C',9,'ECONOMY'),(83,'A',11,'ECONOMY'),(84,'B',1,'FIRST'),(85,'D',7,'ECONOMY'),(86,'D',11,'ECONOMY'),(87,'A',5,'BUSINESS'),(88,'B',9,'ECONOMY'),(89,'B',7,'ECONOMY'),(90,'A',5,'ECONOMY');
/*!40000 ALTER TABLE `flight_seats` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:33
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `flights`
--

DROP TABLE IF EXISTS `flights`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `flights` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `arrival` datetime NOT NULL,
  `departure` datetime NOT NULL,
  `duration` int(11) NOT NULL,
  `transfer_num` int(11) NOT NULL,
  `airline` int(11) NOT NULL,
  `arrival_destination` int(11) NOT NULL,
  `departure_destination` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe5y514tpwwhkdmb9q7klrqnij` (`airline`),
  KEY `FKt69mfcksvgaornimvdua9i7qr` (`arrival_destination`),
  KEY `FK7gt37l9unvrt0akdxb6lxnn1q` (`departure_destination`),
  CONSTRAINT `FK7gt37l9unvrt0akdxb6lxnn1q` FOREIGN KEY (`departure_destination`) REFERENCES `destinations` (`id`),
  CONSTRAINT `FKe5y514tpwwhkdmb9q7klrqnij` FOREIGN KEY (`airline`) REFERENCES `airlines` (`id`),
  CONSTRAINT `FKt69mfcksvgaornimvdua9i7qr` FOREIGN KEY (`arrival_destination`) REFERENCES `destinations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flights`
--

LOCK TABLES `flights` WRITE;
/*!40000 ALTER TABLE `flights` DISABLE KEYS */;
INSERT INTO `flights` VALUES (1,'2019-06-20 00:30:00','2019-06-19 23:50:00',0,0,1,10,9),(2,'2019-06-20 00:30:00','2019-06-19 23:50:00',0,0,1,9,10);
/*!40000 ALTER TABLE `flights` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:28
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `flights_rating`
--

DROP TABLE IF EXISTS `flights_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `flights_rating` (
  `flight_id` int(11) NOT NULL,
  `rating_id` int(11) NOT NULL,
  PRIMARY KEY (`flight_id`,`rating_id`),
  UNIQUE KEY `UK_qrtqsi3myjdvsha8tp4ivjdlx` (`rating_id`),
  CONSTRAINT `FK20rm1mp26o3ta3ftw24wu9rvo` FOREIGN KEY (`rating_id`) REFERENCES `rates` (`id`),
  CONSTRAINT `FKno3fah631xf1apvwykgfc1sar` FOREIGN KEY (`flight_id`) REFERENCES `flights` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flights_rating`
--

LOCK TABLES `flights_rating` WRITE;
/*!40000 ALTER TABLE `flights_rating` DISABLE KEYS */;
/*!40000 ALTER TABLE `flights_rating` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:18
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `flights_seats`
--

DROP TABLE IF EXISTS `flights_seats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `flights_seats` (
  `flight_id` int(11) NOT NULL,
  `seats_id` int(11) NOT NULL,
  PRIMARY KEY (`flight_id`,`seats_id`),
  UNIQUE KEY `UK_rnacc13pkmkyhjdp1f5wfrr93` (`seats_id`),
  CONSTRAINT `FKafbkk6e41wfk5fns38yepnvja` FOREIGN KEY (`seats_id`) REFERENCES `flight_seats` (`id`),
  CONSTRAINT `FKanorj367yjwpti88rlkdsyb1k` FOREIGN KEY (`flight_id`) REFERENCES `flights` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flights_seats`
--

LOCK TABLES `flights_seats` WRITE;
/*!40000 ALTER TABLE `flights_seats` DISABLE KEYS */;
INSERT INTO `flights_seats` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24),(1,25),(1,26),(1,27),(1,28),(1,29),(1,30),(1,31),(1,32),(1,33),(1,34),(1,35),(1,36),(1,37),(1,38),(1,39),(1,40),(1,41),(1,42),(1,43),(1,44),(1,45),(2,46),(2,47),(2,48),(2,49),(2,50),(2,51),(2,52),(2,53),(2,54),(2,55),(2,56),(2,57),(2,58),(2,59),(2,60),(2,61),(2,62),(2,63),(2,64),(2,65),(2,66),(2,67),(2,68),(2,69),(2,70),(2,71),(2,72),(2,73),(2,74),(2,75),(2,76),(2,77),(2,78),(2,79),(2,80),(2,81),(2,82),(2,83),(2,84),(2,85),(2,86),(2,87),(2,88),(2,89),(2,90);
/*!40000 ALTER TABLE `flights_seats` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:30
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `flights_ticket_prices`
--

DROP TABLE IF EXISTS `flights_ticket_prices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `flights_ticket_prices` (
  `flight_id` int(11) NOT NULL,
  `ticket_prices_id` int(11) NOT NULL,
  PRIMARY KEY (`flight_id`,`ticket_prices_id`),
  UNIQUE KEY `UK_3ro5a47sglpsrtl3fusk3317v` (`ticket_prices_id`),
  CONSTRAINT `FKcyfynq5v29luqu7kgx6u1sj26` FOREIGN KEY (`ticket_prices_id`) REFERENCES `ticket_prices` (`id`),
  CONSTRAINT `FKmocs711u12w4r3s9bljqlw6cv` FOREIGN KEY (`flight_id`) REFERENCES `flights` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flights_ticket_prices`
--

LOCK TABLES `flights_ticket_prices` WRITE;
/*!40000 ALTER TABLE `flights_ticket_prices` DISABLE KEYS */;
INSERT INTO `flights_ticket_prices` VALUES (1,1),(1,2),(1,3),(2,4),(2,5),(2,6);
/*!40000 ALTER TABLE `flights_ticket_prices` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:41
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `flights_tickets`
--

DROP TABLE IF EXISTS `flights_tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `flights_tickets` (
  `flight_id` int(11) NOT NULL,
  `tickets_id` int(11) NOT NULL,
  PRIMARY KEY (`flight_id`,`tickets_id`),
  UNIQUE KEY `UK_s7yoxedw5vqm2paba9kna49li` (`tickets_id`),
  CONSTRAINT `FKf0612y16e6bmc25jga6kfnu1j` FOREIGN KEY (`tickets_id`) REFERENCES `tickets` (`id`),
  CONSTRAINT `FKidlfx0jc71u96tb6nna20mwhq` FOREIGN KEY (`flight_id`) REFERENCES `flights` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flights_tickets`
--

LOCK TABLES `flights_tickets` WRITE;
/*!40000 ALTER TABLE `flights_tickets` DISABLE KEYS */;
INSERT INTO `flights_tickets` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24),(1,25),(1,26),(1,27),(1,28),(1,29),(1,30),(1,31),(1,32),(1,33),(1,34),(1,35),(1,36),(1,37),(1,38),(1,39),(1,40),(1,41),(1,42),(1,43),(1,44),(1,45),(2,46),(2,47),(2,48),(2,49),(2,50),(2,51),(2,52),(2,53),(2,54),(2,55),(2,56),(2,57),(2,58),(2,59),(2,60),(2,61),(2,62),(2,63),(2,64),(2,65),(2,66),(2,67),(2,68),(2,69),(2,70),(2,71),(2,72),(2,73),(2,74),(2,75),(2,76),(2,77),(2,78),(2,79),(2,80),(2,81),(2,82),(2,83),(2,84),(2,85),(2,86),(2,87),(2,88),(2,89),(2,90);
/*!40000 ALTER TABLE `flights_tickets` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:38
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `friendships`
--

DROP TABLE IF EXISTS `friendships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `friendships` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) NOT NULL,
  `user1` int(11) NOT NULL,
  `user2` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4pib4oexnqm7vcd3jvvbfcauf` (`user1`),
  KEY `FKc5uoty8uufo14qbs45min70sb` (`user2`),
  CONSTRAINT `FK4pib4oexnqm7vcd3jvvbfcauf` FOREIGN KEY (`user1`) REFERENCES `users` (`id`),
  CONSTRAINT `FKc5uoty8uufo14qbs45min70sb` FOREIGN KEY (`user2`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friendships`
--

LOCK TABLES `friendships` WRITE;
/*!40000 ALTER TABLE `friendships` DISABLE KEYS */;
/*!40000 ALTER TABLE `friendships` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:21
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (7);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:21
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hotel_reservations`
--

DROP TABLE IF EXISTS `hotel_reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hotel_reservations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `check_in_date` date DEFAULT NULL,
  `guests` int(11) DEFAULT NULL,
  `nights` int(11) DEFAULT NULL,
  `total_price` float DEFAULT NULL,
  `reservation_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmsod75vorlmlv6jf7lfdkuupk` (`reservation_id`),
  CONSTRAINT `FKmsod75vorlmlv6jf7lfdkuupk` FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel_reservations`
--

LOCK TABLES `hotel_reservations` WRITE;
/*!40000 ALTER TABLE `hotel_reservations` DISABLE KEYS */;
/*!40000 ALTER TABLE `hotel_reservations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:29
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hotel_reservations_rooms`
--

DROP TABLE IF EXISTS `hotel_reservations_rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hotel_reservations_rooms` (
  `hotel_reservation_id` int(11) NOT NULL,
  `rooms_id` int(11) NOT NULL,
  PRIMARY KEY (`hotel_reservation_id`,`rooms_id`),
  UNIQUE KEY `UK_aict74r3h9v6oxn5t7akou41g` (`rooms_id`),
  CONSTRAINT `FKhokcq4pftpgjhtmtdh9b00kbc` FOREIGN KEY (`hotel_reservation_id`) REFERENCES `hotel_reservations` (`id`),
  CONSTRAINT `FKkh8ie76gce0j1gl013f8rh3jk` FOREIGN KEY (`rooms_id`) REFERENCES `room_reservations` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel_reservations_rooms`
--

LOCK TABLES `hotel_reservations_rooms` WRITE;
/*!40000 ALTER TABLE `hotel_reservations_rooms` DISABLE KEYS */;
/*!40000 ALTER TABLE `hotel_reservations_rooms` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:40
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hotels`
--

DROP TABLE IF EXISTS `hotels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hotels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `floors` int(11) DEFAULT NULL,
  `latitude` decimal(19,2) DEFAULT NULL,
  `longitude` decimal(19,2) DEFAULT NULL,
  `max_rooms_num` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `address` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_m7xlqsndengqp2kpd5nux2s3i` (`name`),
  KEY `FKesbqixqyela8msem9nlfus6v0` (`address`),
  CONSTRAINT `FKesbqixqyela8msem9nlfus6v0` FOREIGN KEY (`address`) REFERENCES `destinations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotels`
--

LOCK TABLES `hotels` WRITE;
/*!40000 ALTER TABLE `hotels` DISABLE KEYS */;
INSERT INTO `hotels` VALUES (1,'',10,44.79,20.46,50,'Hotel Moskva',4);
/*!40000 ALTER TABLE `hotels` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:22
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hotels_extra_service_prices`
--

DROP TABLE IF EXISTS `hotels_extra_service_prices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hotels_extra_service_prices` (
  `hotel_id` int(11) NOT NULL,
  `extra_service_prices_id` int(11) NOT NULL,
  PRIMARY KEY (`hotel_id`,`extra_service_prices_id`),
  UNIQUE KEY `UK_8dtdv3fmxxoa04h3h7lpou9vo` (`extra_service_prices_id`),
  CONSTRAINT `FK2hw8e1ixe5oldr37vby3ybyad` FOREIGN KEY (`hotel_id`) REFERENCES `hotels` (`id`),
  CONSTRAINT `FK56bs4kxld84xak9s67wjrht7k` FOREIGN KEY (`extra_service_prices_id`) REFERENCES `extra_service_prices` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotels_extra_service_prices`
--

LOCK TABLES `hotels_extra_service_prices` WRITE;
/*!40000 ALTER TABLE `hotels_extra_service_prices` DISABLE KEYS */;
INSERT INTO `hotels_extra_service_prices` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9);
/*!40000 ALTER TABLE `hotels_extra_service_prices` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:43
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hotels_rating`
--

DROP TABLE IF EXISTS `hotels_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hotels_rating` (
  `hotel_id` int(11) NOT NULL,
  `rating_id` int(11) NOT NULL,
  PRIMARY KEY (`hotel_id`,`rating_id`),
  UNIQUE KEY `UK_7ib1v9awnkki3w21ldov2jlos` (`rating_id`),
  CONSTRAINT `FKes4b6xrf46hkriyqgbbunlxgw` FOREIGN KEY (`hotel_id`) REFERENCES `hotels` (`id`),
  CONSTRAINT `FKhal4vh8aj99apc1sa0v179dcy` FOREIGN KEY (`rating_id`) REFERENCES `rates` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotels_rating`
--

LOCK TABLES `hotels_rating` WRITE;
/*!40000 ALTER TABLE `hotels_rating` DISABLE KEYS */;
/*!40000 ALTER TABLE `hotels_rating` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:26
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hotels_reservations`
--

DROP TABLE IF EXISTS `hotels_reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hotels_reservations` (
  `hotel_id` int(11) NOT NULL,
  `reservations_id` int(11) NOT NULL,
  PRIMARY KEY (`hotel_id`,`reservations_id`),
  UNIQUE KEY `UK_3kyklm1qp1hxxbsvthrmnm8ty` (`reservations_id`),
  CONSTRAINT `FKi72lvjgnk8ls3cl7kogg7t9jn` FOREIGN KEY (`hotel_id`) REFERENCES `hotels` (`id`),
  CONSTRAINT `FKm12qf7h50ug4clbokcn9vpkvl` FOREIGN KEY (`reservations_id`) REFERENCES `hotel_reservations` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotels_reservations`
--

LOCK TABLES `hotels_reservations` WRITE;
/*!40000 ALTER TABLE `hotels_reservations` DISABLE KEYS */;
/*!40000 ALTER TABLE `hotels_reservations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:19
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `luggage_prices`
--

DROP TABLE IF EXISTS `luggage_prices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `luggage_prices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` float DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `luggage_prices`
--

LOCK TABLES `luggage_prices` WRITE;
/*!40000 ALTER TABLE `luggage_prices` DISABLE KEYS */;
INSERT INTO `luggage_prices` VALUES (1,0,1),(2,0,0);
/*!40000 ALTER TABLE `luggage_prices` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:41
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `quick_rent_a_car_reservations`
--

DROP TABLE IF EXISTS `quick_rent_a_car_reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `quick_rent_a_car_reservations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `discount` int(11) DEFAULT NULL,
  `drop_off_date` date DEFAULT NULL,
  `pick_up_date` date DEFAULT NULL,
  `vehicle_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrt29936vnmp3s9bgxwvknf01l` (`vehicle_id`),
  CONSTRAINT `FKrt29936vnmp3s9bgxwvknf01l` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quick_rent_a_car_reservations`
--

LOCK TABLES `quick_rent_a_car_reservations` WRITE;
/*!40000 ALTER TABLE `quick_rent_a_car_reservations` DISABLE KEYS */;
INSERT INTO `quick_rent_a_car_reservations` VALUES (1,5,'2019-06-28','2019-06-26',1),(2,15,'2019-07-14','2019-07-09',2),(3,5,'2019-06-30','2019-06-29',4);
/*!40000 ALTER TABLE `quick_rent_a_car_reservations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:34
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `rates`
--

DROP TABLE IF EXISTS `rates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rates` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rate_value` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqyyk14j83aq3d12vi0m276i1d` (`user`),
  CONSTRAINT `FKqyyk14j83aq3d12vi0m276i1d` FOREIGN KEY (`user`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rates`
--

LOCK TABLES `rates` WRITE;
/*!40000 ALTER TABLE `rates` DISABLE KEYS */;
INSERT INTO `rates` VALUES (1,4,7),(2,2,7),(3,5,6),(4,5,6),(5,3,6),(6,4,6),(7,5,6);
/*!40000 ALTER TABLE `rates` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:29
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `rent_a_car_reservations`
--

DROP TABLE IF EXISTS `rent_a_car_reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rent_a_car_reservations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `days` int(11) DEFAULT NULL,
  `passanger_num` int(11) DEFAULT NULL,
  `pick_up_date` date DEFAULT NULL,
  `rentacar` varchar(255) DEFAULT NULL,
  `total_price` float DEFAULT NULL,
  `vehicle_checked` bit(1) NOT NULL,
  `drop_off_location_id` int(11) DEFAULT NULL,
  `pick_up_location_id` int(11) DEFAULT NULL,
  `reservation_id` int(11) DEFAULT NULL,
  `vehicle_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoni56h0fp8i8d6tkolbhgehp6` (`drop_off_location_id`),
  KEY `FKjamt4a565hvhc7k3w81c27jfo` (`pick_up_location_id`),
  KEY `FKya6cx9dmoqlkul131a4we5qi` (`reservation_id`),
  KEY `FK392c0wwb2grrf4amj1f8o9xgo` (`vehicle_id`),
  CONSTRAINT `FK392c0wwb2grrf4amj1f8o9xgo` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`id`),
  CONSTRAINT `FKjamt4a565hvhc7k3w81c27jfo` FOREIGN KEY (`pick_up_location_id`) REFERENCES `branch_offices` (`id`),
  CONSTRAINT `FKoni56h0fp8i8d6tkolbhgehp6` FOREIGN KEY (`drop_off_location_id`) REFERENCES `branch_offices` (`id`),
  CONSTRAINT `FKya6cx9dmoqlkul131a4we5qi` FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rent_a_car_reservations`
--

LOCK TABLES `rent_a_car_reservations` WRITE;
/*!40000 ALTER TABLE `rent_a_car_reservations` DISABLE KEYS */;
INSERT INTO `rent_a_car_reservations` VALUES (1,5,3,'2019-06-03','BgTopCars',55,_binary '',2,1,1,1),(2,3,2,'2019-06-26','BudaCars',30,_binary '\0',3,3,2,4),(3,3,3,'2019-05-22','BgTopCars',33,_binary '',1,2,3,1),(4,9,3,'2019-06-05','BudaCars',72,_binary '\0',3,3,4,5),(5,9,3,'2019-06-05','BudaCars',90,_binary '\0',3,3,5,4),(6,4,4,'2019-06-29','BudaCars',32,_binary '\0',3,3,6,5);
/*!40000 ALTER TABLE `rent_a_car_reservations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:32
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `rent_a_cars`
--

DROP TABLE IF EXISTS `rent_a_cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rent_a_cars` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `latitude` decimal(19,2) DEFAULT NULL,
  `longitude` decimal(19,2) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `address` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ru42d042abwa8lvwjb85qtx19` (`name`),
  KEY `FKiso309l0jbw8far2h7xm0b6dk` (`address`),
  CONSTRAINT `FKiso309l0jbw8far2h7xm0b6dk` FOREIGN KEY (`address`) REFERENCES `destinations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rent_a_cars`
--

LOCK TABLES `rent_a_cars` WRITE;
/*!40000 ALTER TABLE `rent_a_cars` DISABLE KEYS */;
INSERT INTO `rent_a_cars` VALUES (1,'',44.79,20.46,'BgTopCars',1),(2,'In city center',47.50,19.04,'BudaCars',8);
/*!40000 ALTER TABLE `rent_a_cars` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:31
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `rent_a_cars_rating`
--

DROP TABLE IF EXISTS `rent_a_cars_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rent_a_cars_rating` (
  `rentacar_id` int(11) NOT NULL,
  `rating_id` int(11) NOT NULL,
  PRIMARY KEY (`rentacar_id`,`rating_id`),
  UNIQUE KEY `UK_5hukych7ndtt4ebtqf0iorhb` (`rating_id`),
  CONSTRAINT `FK7993colx24t7w0brvwa18xl0r` FOREIGN KEY (`rentacar_id`) REFERENCES `rent_a_cars` (`id`),
  CONSTRAINT `FKaynujg4cj0ehwn93ktn85yt2x` FOREIGN KEY (`rating_id`) REFERENCES `rates` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rent_a_cars_rating`
--

LOCK TABLES `rent_a_cars_rating` WRITE;
/*!40000 ALTER TABLE `rent_a_cars_rating` DISABLE KEYS */;
INSERT INTO `rent_a_cars_rating` VALUES (1,1),(1,3),(2,5);
/*!40000 ALTER TABLE `rent_a_cars_rating` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:36
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `rent_a_cars_vehicles`
--

DROP TABLE IF EXISTS `rent_a_cars_vehicles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rent_a_cars_vehicles` (
  `rentacar_id` int(11) NOT NULL,
  `vehicles_id` int(11) NOT NULL,
  PRIMARY KEY (`rentacar_id`,`vehicles_id`),
  UNIQUE KEY `UK_nagghauhnxktcs3kvad031rir` (`vehicles_id`),
  CONSTRAINT `FKo83w2rjbnd8xfiee9udmp0qnm` FOREIGN KEY (`vehicles_id`) REFERENCES `vehicles` (`id`),
  CONSTRAINT `FKoice5aqps4t4vwbi8kf5b96ig` FOREIGN KEY (`rentacar_id`) REFERENCES `rent_a_cars` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rent_a_cars_vehicles`
--

LOCK TABLES `rent_a_cars_vehicles` WRITE;
/*!40000 ALTER TABLE `rent_a_cars_vehicles` DISABLE KEYS */;
INSERT INTO `rent_a_cars_vehicles` VALUES (1,1),(1,2),(1,3),(2,4),(2,5);
/*!40000 ALTER TABLE `rent_a_cars_vehicles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:33
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `reservations`
--

DROP TABLE IF EXISTS `reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `reservations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `flight_reservation_id` int(11) DEFAULT NULL,
  `hotel_reservation_id` int(11) DEFAULT NULL,
  `rentacar_reservation_id` int(11) DEFAULT NULL,
  `user` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4dst665j7mdwgjwmwgmwf59wt` (`flight_reservation_id`),
  KEY `FKd6ccw7ql14725lqwqvgumptd0` (`hotel_reservation_id`),
  KEY `FKcqbv5shfd9t6sqph9ftyhuj00` (`rentacar_reservation_id`),
  KEY `FK2cdwgt04qlahsefjllvkjs648` (`user`),
  CONSTRAINT `FK2cdwgt04qlahsefjllvkjs648` FOREIGN KEY (`user`) REFERENCES `users` (`id`),
  CONSTRAINT `FK4dst665j7mdwgjwmwgmwf59wt` FOREIGN KEY (`flight_reservation_id`) REFERENCES `flight_reservations` (`id`),
  CONSTRAINT `FKcqbv5shfd9t6sqph9ftyhuj00` FOREIGN KEY (`rentacar_reservation_id`) REFERENCES `rent_a_car_reservations` (`id`),
  CONSTRAINT `FKd6ccw7ql14725lqwqvgumptd0` FOREIGN KEY (`hotel_reservation_id`) REFERENCES `hotel_reservations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservations`
--

LOCK TABLES `reservations` WRITE;
/*!40000 ALTER TABLE `reservations` DISABLE KEYS */;
INSERT INTO `reservations` VALUES (1,NULL,NULL,1,7),(2,NULL,NULL,2,7),(3,NULL,NULL,3,6),(4,NULL,NULL,4,6),(5,NULL,NULL,5,6),(6,NULL,NULL,6,6);
/*!40000 ALTER TABLE `reservations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:29
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `room_extraservices`
--

DROP TABLE IF EXISTS `room_extraservices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `room_extraservices` (
  `room_id` int(11) NOT NULL,
  `extraservices` varchar(255) DEFAULT NULL,
  KEY `FKayukxrcxuxqpyp64e09youvsw` (`room_id`),
  CONSTRAINT `FKayukxrcxuxqpyp64e09youvsw` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_extraservices`
--

LOCK TABLES `room_extraservices` WRITE;
/*!40000 ALTER TABLE `room_extraservices` DISABLE KEYS */;
/*!40000 ALTER TABLE `room_extraservices` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:24
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `room_prices`
--

DROP TABLE IF EXISTS `room_prices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `room_prices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_date` datetime NOT NULL,
  `price_per_night` float DEFAULT NULL,
  `to_date` datetime NOT NULL,
  `room` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhejpa6vrn9xmetlgvjp8v8tr2` (`room`),
  CONSTRAINT `FKhejpa6vrn9xmetlgvjp8v8tr2` FOREIGN KEY (`room`) REFERENCES `rooms` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_prices`
--

LOCK TABLES `room_prices` WRITE;
/*!40000 ALTER TABLE `room_prices` DISABLE KEYS */;
/*!40000 ALTER TABLE `room_prices` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:43
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `room_reservations`
--

DROP TABLE IF EXISTS `room_reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `room_reservations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf520kpinewy2hpxke3uh7qmvd` (`room_id`),
  CONSTRAINT `FKf520kpinewy2hpxke3uh7qmvd` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_reservations`
--

LOCK TABLES `room_reservations` WRITE;
/*!40000 ALTER TABLE `room_reservations` DISABLE KEYS */;
/*!40000 ALTER TABLE `room_reservations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:22
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `room_reservations_extra_services`
--

DROP TABLE IF EXISTS `room_reservations_extra_services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `room_reservations_extra_services` (
  `room_reservation_id` int(11) NOT NULL,
  `extra_services_id` int(11) NOT NULL,
  PRIMARY KEY (`room_reservation_id`,`extra_services_id`),
  UNIQUE KEY `UK_hmumcs0lcuivf6uu3wsep2ntq` (`extra_services_id`),
  CONSTRAINT `FKgm9op3keqa9ikbmtck1aw9og7` FOREIGN KEY (`room_reservation_id`) REFERENCES `room_reservations` (`id`),
  CONSTRAINT `FKqwjktd80yrwnamy6eenfxp090` FOREIGN KEY (`extra_services_id`) REFERENCES `extra_service_prices` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_reservations_extra_services`
--

LOCK TABLES `room_reservations_extra_services` WRITE;
/*!40000 ALTER TABLE `room_reservations_extra_services` DISABLE KEYS */;
/*!40000 ALTER TABLE `room_reservations_extra_services` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:39
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rooms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `balcony` bit(1) DEFAULT NULL,
  `beds` int(11) DEFAULT NULL,
  `discount` int(11) DEFAULT NULL,
  `floor` int(11) DEFAULT NULL,
  `room_num` int(11) DEFAULT NULL,
  `hotel` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp3inkpl37lxejlqd7lfx7cpc6` (`hotel`),
  CONSTRAINT `FKp3inkpl37lxejlqd7lfx7cpc6` FOREIGN KEY (`hotel`) REFERENCES `hotels` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms`
--

LOCK TABLES `rooms` WRITE;
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:44
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `rooms_rating`
--

DROP TABLE IF EXISTS `rooms_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rooms_rating` (
  `room_id` int(11) NOT NULL,
  `rating_id` int(11) NOT NULL,
  PRIMARY KEY (`room_id`,`rating_id`),
  UNIQUE KEY `UK_jd3qn6tq2tb1kuegmkpmi2l2w` (`rating_id`),
  CONSTRAINT `FK8g5a6gp2y25r34dbnb0yw1qgy` FOREIGN KEY (`rating_id`) REFERENCES `rates` (`id`),
  CONSTRAINT `FKhlgj8dq27xkq87qub514cduhv` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms_rating`
--

LOCK TABLES `rooms_rating` WRITE;
/*!40000 ALTER TABLE `rooms_rating` DISABLE KEYS */;
/*!40000 ALTER TABLE `rooms_rating` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:42
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ticket_prices`
--

DROP TABLE IF EXISTS `ticket_prices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ticket_prices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` float DEFAULT NULL,
  `travel_class` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket_prices`
--

LOCK TABLES `ticket_prices` WRITE;
/*!40000 ALTER TABLE `ticket_prices` DISABLE KEYS */;
INSERT INTO `ticket_prices` VALUES (1,100,0),(2,50,1),(3,25,2),(4,25,2),(5,50,1),(6,100,0);
/*!40000 ALTER TABLE `ticket_prices` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:42
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tickets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `discount` int(11) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `reserved` bit(1) DEFAULT NULL,
  `flight` int(11) NOT NULL,
  `seat` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK17ccwub9wtjpqb3ydj5gr7l9f` (`flight`),
  KEY `FKe0bfmv6ifiyegkst7vs6mq3t2` (`seat`),
  CONSTRAINT `FK17ccwub9wtjpqb3ydj5gr7l9f` FOREIGN KEY (`flight`) REFERENCES `flights` (`id`),
  CONSTRAINT `FKe0bfmv6ifiyegkst7vs6mq3t2` FOREIGN KEY (`seat`) REFERENCES `flight_seats` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (1,0,25,_binary '\0',1,23),(2,0,25,_binary '\0',1,36),(3,0,25,_binary '\0',1,20),(4,0,100,_binary '\0',1,40),(5,0,50,_binary '\0',1,10),(6,0,25,_binary '\0',1,34),(7,0,50,_binary '\0',1,42),(8,0,50,_binary '\0',1,28),(9,0,50,_binary '\0',1,43),(10,0,50,_binary '\0',1,3),(11,0,25,_binary '\0',1,12),(12,0,25,_binary '\0',1,31),(13,0,25,_binary '\0',1,44),(14,0,25,_binary '\0',1,2),(15,0,25,_binary '\0',1,17),(16,0,25,_binary '\0',1,13),(17,0,25,_binary '\0',1,37),(18,0,25,_binary '\0',1,14),(19,0,50,_binary '\0',1,18),(20,0,25,_binary '\0',1,21),(21,0,100,_binary '\0',1,8),(22,0,25,_binary '\0',1,26),(23,0,100,_binary '\0',1,16),(24,0,25,_binary '\0',1,32),(25,0,50,_binary '\0',1,30),(26,0,50,_binary '\0',1,27),(27,0,25,_binary '\0',1,29),(28,0,25,_binary '\0',1,7),(29,0,25,_binary '\0',1,5),(30,0,50,_binary '\0',1,15),(31,0,25,_binary '\0',1,24),(32,0,50,_binary '\0',1,25),(33,0,25,_binary '\0',1,19),(34,0,100,_binary '\0',1,4),(35,0,25,_binary '\0',1,11),(36,0,50,_binary '\0',1,9),(37,0,50,_binary '\0',1,22),(38,0,50,_binary '\0',1,45),(39,0,25,_binary '\0',1,41),(40,0,25,_binary '\0',1,33),(41,0,25,_binary '\0',1,1),(42,0,100,_binary '\0',1,35),(43,0,50,_binary '\0',1,38),(44,0,50,_binary '\0',1,39),(45,0,25,_binary '\0',1,6),(46,0,100,_binary '\0',2,64),(47,0,25,_binary '\0',2,71),(48,0,25,_binary '\0',2,76),(49,0,50,_binary '\0',2,50),(50,0,50,_binary '\0',2,67),(51,0,50,_binary '\0',2,63),(52,0,25,_binary '\0',2,68),(53,0,25,_binary '\0',2,85),(54,0,50,_binary '\0',2,65),(55,0,25,_binary '\0',2,57),(56,0,50,_binary '\0',2,59),(57,0,25,_binary '\0',2,81),(58,0,50,_binary '\0',2,73),(59,0,25,_binary '\0',2,54),(60,0,25,_binary '\0',2,66),(61,0,25,_binary '\0',2,89),(62,0,25,_binary '\0',2,88),(63,0,25,_binary '\0',2,82),(64,0,25,_binary '\0',2,78),(65,0,50,_binary '\0',2,61),(66,0,25,_binary '\0',2,47),(67,0,50,_binary '\0',2,75),(68,0,25,_binary '\0',2,80),(69,0,50,_binary '\0',2,56),(70,0,50,_binary '\0',2,46),(71,0,100,_binary '\0',2,84),(72,0,25,_binary '\0',2,48),(73,0,25,_binary '\0',2,83),(74,0,100,_binary '\0',2,79),(75,0,25,_binary '\0',2,90),(76,0,25,_binary '\0',2,77),(77,0,50,_binary '\0',2,72),(78,0,25,_binary '\0',2,69),(79,0,50,_binary '\0',2,51),(80,0,25,_binary '\0',2,86),(81,0,50,_binary '\0',2,58),(82,0,25,_binary '\0',2,53),(83,0,25,_binary '\0',2,70),(84,0,50,_binary '\0',2,87),(85,0,25,_binary '\0',2,52),(86,0,25,_binary '\0',2,62),(87,0,100,_binary '\0',2,55),(88,0,50,_binary '\0',2,49),(89,0,100,_binary '\0',2,74),(90,0,25,_binary '\0',2,60);
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:36
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user_authority`
--

DROP TABLE IF EXISTS `user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_authority` (
  `user_id` int(11) NOT NULL,
  `authority_id` bigint(20) NOT NULL,
  KEY `FKil6f39w6fgqh4gk855pstsnmy` (`authority_id`),
  KEY `FKhi46vu7680y1hwvmnnuh4cybx` (`user_id`),
  CONSTRAINT `FKhi46vu7680y1hwvmnnuh4cybx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKil6f39w6fgqh4gk855pstsnmy` FOREIGN KEY (`authority_id`) REFERENCES `authorities` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_authority`
--

LOCK TABLES `user_authority` WRITE;
/*!40000 ALTER TABLE `user_authority` DISABLE KEYS */;
INSERT INTO `user_authority` VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(6,6),(7,7);
/*!40000 ALTER TABLE `user_authority` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:23
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `dtype` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `last_password_reset_date` datetime DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone_num` int(11) DEFAULT NULL,
  `profile_picture` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `pass_changed` bit(1) DEFAULT NULL,
  `bonus_pts` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `rentacar` int(11) DEFAULT NULL,
  `airline` int(11) DEFAULT NULL,
  `hotel` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`),
  KEY `FKs09vhvbax8qd9nsdb7xvmdt8t` (`rentacar`),
  KEY `FKdx46u75d9eypll1k51as0eso0` (`airline`),
  KEY `FKqy7u1qave6cf9erbu41pfahrw` (`hotel`),
  CONSTRAINT `FKdx46u75d9eypll1k51as0eso0` FOREIGN KEY (`airline`) REFERENCES `airlines` (`id`),
  CONSTRAINT `FKqy7u1qave6cf9erbu41pfahrw` FOREIGN KEY (`hotel`) REFERENCES `hotels` (`id`),
  CONSTRAINT `FKs09vhvbax8qd9nsdb7xvmdt8t` FOREIGN KEY (`rentacar`) REFERENCES `rent_a_cars` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('SysAdmin',1,NULL,NULL,_binary '',NULL,NULL,NULL,'$2a$10$bYaRgEluIyoxxPXEBQjmweGm5G1iSHHPjgt5ndNBZF53a9hwCVWvy',NULL,'userDefault.png','sys',_binary '',NULL,NULL,NULL,NULL,NULL),('RentACarAdmin',2,'Belgrade','djkristinaxx+.rac1@gmail.com',_binary '',NULL,'Veinovic','Vanja','$2a$10$QTE4IQfRwwdBgfniKABUqufnVe5C50I7QNNicbnCqFDT2j4oBYMmS',324324,'userDefault.png','rac1',_binary '',NULL,NULL,1,NULL,NULL),('RentACarAdmin',3,'Budapest','djkristinaxx+.rac2@gmail.com',_binary '',NULL,'Veinovic','Sanja','$2a$10$190u5RxiB0Icf3XimOk1Y.iMnp0sAJEUUcv7ejl7ishYsAbH73YKi',3476327,'userDefault.png','rac2',_binary '',NULL,NULL,2,NULL,NULL),('AirlineAdmin',4,'Belgrade','djkristinaxx+.air@gmail.com',_binary '',NULL,'Jankovic','Janko','$2a$10$bxTA3xhS6PzBJswVkUMfIu3T8epZAdmaUYaXVCoVJ3FcseLJYesMi',433443,'userDefault.png','air',_binary '',NULL,NULL,NULL,1,NULL),('HotelAdmin',5,'Belgrade','djkristinaxx+.hot@gmail.com',_binary '',NULL,'Markovic','Marko','$2a$10$6p5LAb1iYPv5MUBKyx8E6ebQS4GKUk8meHGKul7Xgob7VyN.MEa/W',433443,'userDefault.png','h1',_binary '',NULL,NULL,NULL,NULL,1),('RegisteredUser',6,'Belgrade','djkristinaxx+.1@gmail.com',_binary '',NULL,'Ivanovic','Ivana','$2a$10$Y/9pgOU1A0dx9JM8gJ8SEePFdN6qDkejR7Fug3DM6hc.qZKqxWNAm',32324,'userDefault.png','user',NULL,0,'REGULAR',NULL,NULL,NULL),('RegisteredUser',7,'Belgrade','djkristinaxx+.2@gmail.com',_binary '',NULL,'Ivkovic','Ivan','$2a$10$EkKYB6GUWFDFrO7RYkoSq.wn3G.RSLaZyatznJo5CgA3TKwH/1QPu',32324,'userDefault.png','user2',NULL,0,'REGULAR',NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:35
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `users_friends`
--

DROP TABLE IF EXISTS `users_friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users_friends` (
  `registered_user_id` int(11) NOT NULL,
  `friends_id` int(11) NOT NULL,
  PRIMARY KEY (`registered_user_id`,`friends_id`),
  UNIQUE KEY `UK_eslx46xq9voscm63qgdtr5c28` (`friends_id`),
  CONSTRAINT `FKcgu71m2uxhsoc51uur6iisu8l` FOREIGN KEY (`friends_id`) REFERENCES `friendships` (`id`),
  CONSTRAINT `FKegw2xxw196gm7recar5dnhgmn` FOREIGN KEY (`registered_user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_friends`
--

LOCK TABLES `users_friends` WRITE;
/*!40000 ALTER TABLE `users_friends` DISABLE KEYS */;
/*!40000 ALTER TABLE `users_friends` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:25
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `users_rates`
--

DROP TABLE IF EXISTS `users_rates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users_rates` (
  `registered_user_id` int(11) NOT NULL,
  `rates_id` int(11) NOT NULL,
  PRIMARY KEY (`registered_user_id`,`rates_id`),
  UNIQUE KEY `UK_2vd6evd6wx7l09tbj5ju50m2y` (`rates_id`),
  CONSTRAINT `FK2grjevlfdeo85j3mrko2eudyw` FOREIGN KEY (`rates_id`) REFERENCES `rates` (`id`),
  CONSTRAINT `FKahihebc6jpmj3xsowmviv9ne7` FOREIGN KEY (`registered_user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_rates`
--

LOCK TABLES `users_rates` WRITE;
/*!40000 ALTER TABLE `users_rates` DISABLE KEYS */;
/*!40000 ALTER TABLE `users_rates` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:38
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `users_reservations`
--

DROP TABLE IF EXISTS `users_reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users_reservations` (
  `registered_user_id` int(11) NOT NULL,
  `reservations_id` int(11) NOT NULL,
  PRIMARY KEY (`registered_user_id`,`reservations_id`),
  UNIQUE KEY `UK_3goe1sku3r0mq5dycjmkb3epv` (`reservations_id`),
  CONSTRAINT `FKa6v8lh9igd0e61uhbuyxjsak` FOREIGN KEY (`reservations_id`) REFERENCES `reservations` (`id`),
  CONSTRAINT `FKp5dgi41g6blhxjsij9rvb6lv4` FOREIGN KEY (`registered_user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_reservations`
--

LOCK TABLES `users_reservations` WRITE;
/*!40000 ALTER TABLE `users_reservations` DISABLE KEYS */;
INSERT INTO `users_reservations` VALUES (7,1),(7,2),(6,3),(6,4),(6,5),(6,6);
/*!40000 ALTER TABLE `users_reservations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:27
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `vehicles`
--

DROP TABLE IF EXISTS `vehicles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `vehicles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `brand` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` float DEFAULT NULL,
  `production_year` int(11) DEFAULT NULL,
  `seats_num` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `currently_in_id` int(11) DEFAULT NULL,
  `rentacar` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4x24lxgdu2ji3xgoynpcmbqew` (`currently_in_id`),
  KEY `FK35kp14d676txcdvvu3mln1q53` (`rentacar`),
  CONSTRAINT `FK35kp14d676txcdvvu3mln1q53` FOREIGN KEY (`rentacar`) REFERENCES `rent_a_cars` (`id`),
  CONSTRAINT `FK4x24lxgdu2ji3xgoynpcmbqew` FOREIGN KEY (`currently_in_id`) REFERENCES `branch_offices` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicles`
--

LOCK TABLES `vehicles` WRITE;
/*!40000 ALTER TABLE `vehicles` DISABLE KEYS */;
INSERT INTO `vehicles` VALUES (1,'Mini','','Countryman','BgV1',11,2016,5,'MID_SIZE',1,1),(2,'Opel','','Astra J sedan','BgV2',13,2018,5,'FULL_SIZE',1,1),(3,'Jeep','','Grand Cherokee','BgV3',14,2015,5,'SUV',2,1),(4,'Renault','','Megane','V1Buda',10,2013,5,'ECONOMY',3,2),(5,'Citroen','','C5','V1Buda',8,2008,5,'ECONOMY',3,2);
/*!40000 ALTER TABLE `vehicles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:40
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `vehicles_rating`
--

DROP TABLE IF EXISTS `vehicles_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `vehicles_rating` (
  `vehicle_id` int(11) NOT NULL,
  `rating_id` int(11) NOT NULL,
  PRIMARY KEY (`vehicle_id`,`rating_id`),
  UNIQUE KEY `UK_1yoatxqyabdrf3qc7tah77uok` (`rating_id`),
  CONSTRAINT `FK5vkv7yflfkdd95wen7jm67hek` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`id`),
  CONSTRAINT `FKa8579ccnwmtxeelfr8213yp0n` FOREIGN KEY (`rating_id`) REFERENCES `rates` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicles_rating`
--

LOCK TABLES `vehicles_rating` WRITE;
/*!40000 ALTER TABLE `vehicles_rating` DISABLE KEYS */;
INSERT INTO `vehicles_rating` VALUES (1,2),(1,4),(5,6),(4,7);
/*!40000 ALTER TABLE `vehicles_rating` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:38
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_booking
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `verification_tokens`
--

DROP TABLE IF EXISTS `verification_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `verification_tokens` (
  `id` int(11) NOT NULL,
  `token` varchar(255) NOT NULL,
  `user` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKffgjxs3hrn4cxqrettdc8s0la` (`user`),
  CONSTRAINT `FKffgjxs3hrn4cxqrettdc8s0la` FOREIGN KEY (`user`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification_tokens`
--

LOCK TABLES `verification_tokens` WRITE;
/*!40000 ALTER TABLE `verification_tokens` DISABLE KEYS */;
INSERT INTO `verification_tokens` VALUES (1,'04c64e6b-d262-49d0-bfc4-e75d3e2446b7',2),(2,'ab970e67-6e00-4b63-8b20-5a7e99061b6b',3),(3,'2129b090-8a5c-4e16-bb76-2bc9285c4c99',4),(4,'61871eed-4ade-4eec-b445-a824649818d1',5),(5,'24d6a091-dc23-46ab-bc24-a02f816752c9',6),(6,'df49a01b-dbb1-4306-a6a6-e203c0be235e',7);
/*!40000 ALTER TABLE `verification_tokens` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 22:38:31
