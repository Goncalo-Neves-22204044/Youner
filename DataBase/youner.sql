-- MySQL Script generated by MySQL Workbench
-- Mon May 17 12:43:40 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Youner
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `Youner` ;

-- -----------------------------------------------------
-- Schema Youner
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Youner` DEFAULT CHARACTER SET utf8 ;
USE `Youner` ;

-- -----------------------------------------------------
-- Table `Youner`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Youner`.`User` (
  `email` VARCHAR(45) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `birthday` DATE NOT NULL,
  `country` VARCHAR(100) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`email`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `Youner`.`Game`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Youner`.`Game` (
  `idGame` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `image` VARCHAR(45) NULL,
  PRIMARY KEY (`idGame`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Youner`.`Game_has_User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Youner`.`Game_has_User` (
  `Game_idGame` INT NOT NULL,
  `date` DATETIME NULL,
  `gameScore` INT NULL,
  `User_email` VARCHAR(45) NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  INDEX `fk_Game_has_User_Game_idx` (`Game_idGame` ASC),
  INDEX `fk_Game_has_User_User1_idx` (`User_email` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Game_has_User_Game`
    FOREIGN KEY (`Game_idGame`)
    REFERENCES `Youner`.`Game` (`idGame`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Game_has_User_User1`
    FOREIGN KEY (`User_email`)
    REFERENCES `Youner`.`User` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Youner`.`Question`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Youner`.`Question` (
  `idQuestion` INT NOT NULL AUTO_INCREMENT,
  `questions` INT NOT NULL,
  `answer1` VARCHAR(45) NULL,
  `rightAnswer` CHAR(1) NULL,
  `answer2` VARCHAR(45) NULL,
  `answer3` VARCHAR(45) NULL,
  `image` VARCHAR(45) NULL,
  PRIMARY KEY (`idQuestion`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Youner`.`Question_has_Game`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Youner`.`Question_has_Game` (
  `Game_idGame` INT NOT NULL,
  `Question_idQuestion` INT NOT NULL,
  PRIMARY KEY (`Game_idGame`, `Question_idQuestion`),
  INDEX `fk_Pergunta_has_Game_Game1_idx` (`Game_idGame` ASC),
  INDEX `fk_Question_has_Game_Question1_idx` (`Question_idQuestion` ASC),
  CONSTRAINT `fk_Pergunta_has_Game_Game1`
    FOREIGN KEY (`Game_idGame`)
    REFERENCES `Youner`.`Game` (`idGame`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Question_has_Game_Question1`
    FOREIGN KEY (`Question_idQuestion`)
    REFERENCES `Youner`.`Question` (`idQuestion`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Youner`.`Record`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Youner`.`Record` (
  `idSample` INT NOT NULL AUTO_INCREMENT,
  `User_email` VARCHAR(45) NOT NULL,
  `sampleName` VARCHAR(45) NULL,
  `sampleDuration` TIME NULL,
  `sampleSize` VARCHAR(45) NULL,
  `sampleDate` DATE NULL,
  `sampleLoc` VARCHAR(45) NULL,
  `instrument` VARCHAR(45) NULL,
  PRIMARY KEY (`idSample`),
  CONSTRAINT `fk_Record_User1`
    FOREIGN KEY (`User_email`)
    REFERENCES `Youner`.`User` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
