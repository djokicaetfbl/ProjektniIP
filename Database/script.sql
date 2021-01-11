#drop schema sharingpotentialhazardsystemdb;
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema SharingPotentialHazardSystemDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema SharingPotentialHazardSystemDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `SharingPotentialHazardSystemDB` DEFAULT CHARACTER SET utf8 ;
USE `SharingPotentialHazardSystemDB` ;

-- -----------------------------------------------------
-- Table `SharingPotentialHazardSystemDB`.`table1`
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table `SharingPotentialHazardSystemDB`.`user_group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SharingPotentialHazardSystemDB`.`user_group` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `key` VARCHAR(45) NOT NULL,
  `active` TINYINT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SharingPotentialHazardSystemDB`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SharingPotentialHazardSystemDB`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(1024) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `active` TINYINT NOT NULL,
  `user_group_iduser_group` INT NOT NULL,
  `blocked` TINYINT NULL,
  `approved` TINYINT NULL,
  `picture` VARCHAR(255) NULL,
  `signInCounter` INT NULL,
  `country` VARCHAR(45) NULL,
  `region` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `emergency_notification` TINYINT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_user_group_idx` (`user_group_iduser_group` ASC) VISIBLE,
  CONSTRAINT `fk_user_user_group`
    FOREIGN KEY (`user_group_iduser_group`)
    REFERENCES `SharingPotentialHazardSystemDB`.`user_group` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SharingPotentialHazardSystemDB`.`token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SharingPotentialHazardSystemDB`.`token` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `token` VARCHAR(45) NULL,
  `creation_time` TIMESTAMP NULL,
  `expiration_time` TIMESTAMP NULL,
  `active` TINYINT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_token_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_token_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `SharingPotentialHazardSystemDB`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SharingPotentialHazardSystemDB`.`post_group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SharingPotentialHazardSystemDB`.`post_group` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `key` VARCHAR(45) NULL,
  `active` TINYINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SharingPotentialHazardSystemDB`.`post`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SharingPotentialHazardSystemDB`.`post` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `contents` VARCHAR(1024) NULL,
  `user_id` INT NOT NULL,
  `active` TINYINT NULL,
  `post_group_idpost_group` INT NOT NULL,
  `share_time` DATETIME NULL,
  `emergency_notification` TINYINT NULL,
  `geographic_latitude` FLOAT NULL,
  `geographic_longitude` FLOAT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_post_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_post_post_group1_idx` (`post_group_idpost_group` ASC) VISIBLE,
  CONSTRAINT `fk_post_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `SharingPotentialHazardSystemDB`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_post_post_group1`
    FOREIGN KEY (`post_group_idpost_group`)
    REFERENCES `SharingPotentialHazardSystemDB`.`post_group` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SharingPotentialHazardSystemDB`.`danger_post_group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SharingPotentialHazardSystemDB`.`danger_post_group` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `active` TINYINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SharingPotentialHazardSystemDB`.`post_has_danger_post_group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SharingPotentialHazardSystemDB`.`post_has_danger_post_group` (
  `post_id` INT NOT NULL,
  `danger_post_group_id` INT NOT NULL,
  PRIMARY KEY (`post_id`, `danger_post_group_id`),
  INDEX `fk_post_has_danger_post_group_danger_post_group1_idx` (`danger_post_group_id` ASC) VISIBLE,
  INDEX `fk_post_has_danger_post_group_post1_idx` (`post_id` ASC) VISIBLE,
  CONSTRAINT `fk_post_has_danger_post_group_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `SharingPotentialHazardSystemDB`.`post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_post_has_danger_post_group_danger_post_group1`
    FOREIGN KEY (`danger_post_group_id`)
    REFERENCES `SharingPotentialHazardSystemDB`.`danger_post_group` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SharingPotentialHazardSystemDB`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SharingPotentialHazardSystemDB`.`comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(1024) NULL,
  `share_time` TIMESTAMP NULL,
  `picture` LONGBLOB NULL,
  `user_id` INT NOT NULL,
  `post_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comment_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_comment_post1_idx` (`post_id` ASC) VISIBLE,
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `SharingPotentialHazardSystemDB`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `SharingPotentialHazardSystemDB`.`post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SharingPotentialHazardSystemDB`.`image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SharingPotentialHazardSystemDB`.`image` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(1024) NULL,
  `active` TINYINT NULL,
  `post_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_image_post1_idx` (`post_id` ASC) VISIBLE,
  CONSTRAINT `fk_image_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `SharingPotentialHazardSystemDB`.`post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SharingPotentialHazardSystemDB`.`emergency_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SharingPotentialHazardSystemDB`.`emergency_category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SharingPotentialHazardSystemDB`.`emergency_call`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SharingPotentialHazardSystemDB`.`emergency_call` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  `time` DATETIME NULL,
  `location` VARCHAR(255) NULL,
  `description` VARCHAR(255) NULL,
  `pictureURL` VARCHAR(2048) NULL,
  `blocked` TINYINT NULL,
  `fake_call` TINYINT NULL,
  `share_time` DATETIME NULL,
  `emergency_category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_emergency_call_emergency_category1_idx` (`emergency_category_id` ASC) VISIBLE,
  CONSTRAINT `fk_emergency_call_emergency_category1`
    FOREIGN KEY (`emergency_category_id`)
    REFERENCES `SharingPotentialHazardSystemDB`.`emergency_category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;







ALTER TABLE user auto_increment = 1;
ALTER TABLE user_group auto_increment = 1;
ALTER TABLE post_group auto_increment = 1;
ALTER TABLE post auto_increment = 1;
ALTER TABLE image auto_increment = 1;
ALTER TABLE comment auto_increment = 1;
ALTER TABLE danger_post_group auto_increment = 1;
ALTER TABLE emergency_call auto_increment = 1;
ALTER TABLE emergency_category auto_increment = 1;
ALTER TABLE post_has_danger_post_group auto_increment = 1;
ALTER TABLE token auto_increment = 1;



insert into user_group values (0,"admin","admin",1);
insert into user_group values (0,"user","user",1);
insert into user values (0,"Bowser"," King Koopa","admin","admin","admin@gmail.com",1,1,0,1,"https://restcountries.eu/data/irl.svg",1,"BA","Republika Srpska","Banja Luka",1); # admin
insert into user values (0,"Test","Test","test","ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff","djoleipetf@gmail.com",1,2,0,1,"https://restcountries.eu/data/irl.svg",1,"BA","Republika Srpska","Banja Luka",1); # obican korisnik
insert into user values (0,"Test","Test","test2","ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff","djordjepalavestra@gmail.com",1,2,0,1,"https://restcountries.eu/data/irl.svg",1,"BA","Republika Srpska","Banja Luka",1);

insert into post_group values (0,"rss","rss",1);
insert into post_group values (0,"text","text",1);
insert into post_group values (0,"image","image",1);
insert into post_group values (0,"video","video",1);

insert into danger_post_group values (0,"Tree on the road",true);
insert into danger_post_group values (0,"Thunderstorm",true);
insert into danger_post_group values (0,"The flood",true);
insert into danger_post_group values (0,"Fire",true);

insert into emergency_category values(0,"physical assistance in repairing the damage");
insert into emergency_category values(0,"collecting clothes");
insert into emergency_category values(0,"collecting basic foodstuffs");
insert into emergency_category values(0,"transport");

select * from user;