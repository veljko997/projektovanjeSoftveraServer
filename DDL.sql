CREATE DATABASE FifaRangList;

CREATE TABLE `fifaranglist`.`user` ( `id` BIGINT NOT NULL AUTO_INCREMENT, `username` VARCHAR(40) UNIQUE, `password` VARCHAR(80), `active` BOOL, `administrator` BOOL, PRIMARY KEY (`id`) ); 
CREATE TABLE `fifaranglist`.`selection` ( `id` BIGINT NOT NULL AUTO_INCREMENT, `name` VARCHAR(50) UNIQUE, `points` INT, `confederation` VARCHAR(50), `ranking` INT, `userID` BIGINT, `active` BOOL, PRIMARY KEY (`id`), CONSTRAINT `userID_constraint` FOREIGN KEY (`userID`) REFERENCES `fifaranglist`.`user`(`id`) ON UPDATE CASCADE ON DELETE RESTRICT ); 
CREATE TABLE `fifaranglist`.`matches` ( `id` BIGINT NOT NULL AUTO_INCREMENT, `host` BIGINT, `away` BIGINT, `hostGoals` INT, `awayGoals` INT, `date` DATE, `matchtype` VARCHAR(40), `userID` BIGINT, PRIMARY KEY (`id`), FOREIGN KEY (`host`) REFERENCES `fifaranglist`.`selection`(`id`), FOREIGN KEY (`away`) REFERENCES `fifaranglist`.`selection`(`id`), FOREIGN KEY (`userID`) REFERENCES `fifaranglist`.`user`(`id`) );


/*+ Password je administrator */
INSERT INTO USER(username,PASSWORD,ACTIVE,administrator) VALUE ('administrator','200ceb26807d6bf99fd6f4f0d1ca54d4',TRUE,TRUE)

