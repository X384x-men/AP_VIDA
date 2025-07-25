-- MySQL Workbench Synchronization
-- Generated: 2023-12-26 19:09
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: mafuf

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE TABLE IF NOT EXISTS `ap`.`evento` (
  `idevento` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `fecha` VARCHAR(45) NULL DEFAULT NULL,
  `tipo` VARCHAR(45) NULL DEFAULT NULL,
  `descripcion` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idevento`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `ap`.`evento_has_solicitud` (
  `evento_idevento` BIGINT(20) NOT NULL,
  `solicitud_idSolicitud` BIGINT(20) NOT NULL,
  `tipo` VARCHAR(45) NULL DEFAULT NULL,
  `empleado_ap_idEmpleadoAP` INT(11) NOT NULL,
  `usuariosacceso_idusuariosAcceso` BIGINT(20) NOT NULL,
  PRIMARY KEY (`evento_idevento`, `solicitud_idSolicitud`, `empleado_ap_idEmpleadoAP`, `usuariosacceso_idusuariosAcceso`),
  INDEX `fk_evento_has_solicitud_solicitud1_idx` (`solicitud_idSolicitud` ASC) VISIBLE,
  INDEX `fk_evento_has_solicitud_evento1_idx` (`evento_idevento` ASC) VISIBLE,
  INDEX `fk_evento_has_solicitud_empleado_ap1_idx` (`empleado_ap_idEmpleadoAP` ASC) VISIBLE,
  INDEX `fk_evento_has_solicitud_usuariosacceso1_idx` (`usuariosacceso_idusuariosAcceso` ASC) VISIBLE,
  CONSTRAINT `fk_evento_has_solicitud_evento1`
    FOREIGN KEY (`evento_idevento`)
    REFERENCES `ap`.`evento` (`idevento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_evento_has_solicitud_solicitud1`
    FOREIGN KEY (`solicitud_idSolicitud`)
    REFERENCES `ap`.`solicitud` (`idSolicitud`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_evento_has_solicitud_empleado_ap1`
    FOREIGN KEY (`empleado_ap_idEmpleadoAP`)
    REFERENCES `ap`.`empleado_ap` (`idEmpleadoAP`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_evento_has_solicitud_usuariosacceso1`
    FOREIGN KEY (`usuariosacceso_idusuariosAcceso`)
    REFERENCES `ap`.`usuariosacceso` (`idusuariosAcceso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

ALTER TABLE `ap`.`evento_has_solicitud` 
DROP FOREIGN KEY `fk_evento_has_solicitud_evento1`,
DROP FOREIGN KEY `fk_evento_has_solicitud_solicitud1`,
DROP FOREIGN KEY `fk_evento_has_solicitud_usuariosacceso1`;

ALTER TABLE `ap`.`evento` 
CHANGE COLUMN `fecha` `fecha` DATETIME NULL DEFAULT now() ;

ALTER TABLE `ap`.`evento_has_solicitud` 
DROP FOREIGN KEY `fk_evento_has_solicitud_empleado_ap1`;

ALTER TABLE `ap`.`evento_has_solicitud` ADD CONSTRAINT `fk_evento_has_solicitud_evento1`
  FOREIGN KEY (`evento_idevento`)
  REFERENCES `ap`.`evento` (`idevento`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_evento_has_solicitud_solicitud1`
  FOREIGN KEY (`solicitud_idSolicitud`)
  REFERENCES `ap`.`solicitud` (`idSolicitud`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_evento_has_solicitud_empleado_ap1`
  FOREIGN KEY (`empleado_ap_idEmpleadoAP`)
  REFERENCES `ap`.`empleado_ap` (`idEmpleadoAP`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_evento_has_solicitud_usuariosacceso1`
  FOREIGN KEY (`usuariosacceso_idusuariosAcceso`)
  REFERENCES `ap`.`usuariosacceso` (`idusuariosAcceso`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

ALTER TABLE `ap`.`evento_has_solicitud` 
DROP FOREIGN KEY `fk_evento_has_solicitud_evento1`,
DROP FOREIGN KEY `fk_evento_has_solicitud_solicitud1`,
DROP FOREIGN KEY `fk_evento_has_solicitud_usuariosacceso1`,
DROP FOREIGN KEY `fk_evento_has_solicitud_empleado_ap1`;

ALTER TABLE `ap`.`evento` 
CHANGE COLUMN `fecha` `fecha` DATETIME NULL DEFAULT now() ;

ALTER TABLE `ap`.`evento_has_solicitud` 
DROP COLUMN `usuariosacceso_idusuariosAcceso`,
DROP COLUMN `empleado_ap_idEmpleadoAP`,
ADD COLUMN `RFCAsegurado` VARCHAR(45) NULL DEFAULT NULL AFTER `tipo`,
ADD COLUMN `RFCEmpleado` VARCHAR(45) NULL DEFAULT NULL AFTER `RFCAsegurado`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`evento_idevento`, `solicitud_idSolicitud`),
DROP INDEX `fk_evento_has_solicitud_usuariosacceso1_idx` ,
DROP INDEX `fk_evento_has_solicitud_empleado_ap1_idx` ;
;

ALTER TABLE `ap`.`evento_has_solicitud` 
ADD CONSTRAINT `fk_evento_has_solicitud_evento1`
  FOREIGN KEY (`evento_idevento`)
  REFERENCES `ap`.`evento` (`idevento`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_evento_has_solicitud_solicitud1`
  FOREIGN KEY (`solicitud_idSolicitud`)
  REFERENCES `ap`.`solicitud` (`idSolicitud`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

