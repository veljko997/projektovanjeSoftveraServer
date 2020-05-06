CREATE DATABASE FifaRangList;

CREATE TABLE `fifaranglist`.`user` ( `id` BIGINT NOT NULL AUTO_INCREMENT, `username` VARCHAR(40) UNIQUE, `password` VARCHAR(80), `active` BOOL, `administrator` BOOL, PRIMARY KEY (`id`) ); 
CREATE TABLE `fifaranglist`.`selection` ( `id` BIGINT NOT NULL AUTO_INCREMENT, `name` VARCHAR(50) UNIQUE, `points` INT, `confederation` VARCHAR(50), `ranking` INT, `userID` BIGINT, `active` BOOL, PRIMARY KEY (`id`), CONSTRAINT `userID_constraint` FOREIGN KEY (`userID`) REFERENCES `fifaranglist`.`user`(`id`) ON UPDATE CASCADE ON DELETE RESTRICT ); 
CREATE TABLE `fifaranglist`.`matches` ( `id` BIGINT NOT NULL AUTO_INCREMENT, `host` BIGINT, `away` BIGINT, `hostGoals` INT, `awayGoals` INT, `date` DATE, `matchtype` VARCHAR(40), `userID` BIGINT, PRIMARY KEY (`id`), FOREIGN KEY (`host`) REFERENCES `fifaranglist`.`selection`(`id`), FOREIGN KEY (`away`) REFERENCES `fifaranglist`.`selection`(`id`), FOREIGN KEY (`userID`) REFERENCES `fifaranglist`.`user`(`id`) );


/*+ Password je administrator */
INSERT INTO USER(username,PASSWORD,ACTIVE,administrator) VALUE ('administrator','200ceb26807d6bf99fd6f4f0d1ca54d4',TRUE,TRUE);

INSERT INTO selection (NAME,confederation,points,userID,ACTIVE) VALUES('Argentina','SOUTHAMERICA',0,1,1);
INSERT INTO selection (NAME,confederation,points,userID,ACTIVE) VALUES('Brasil','SOUTHAMERICA',0,1,1);
INSERT INTO selection (NAME,confederation,points,userID,ACTIVE) VALUES('Serbia','EUROPE',0,1,1);
INSERT INTO selection (NAME,confederation,points,userID,ACTIVE) VALUES('Belgium','EUROPE',0,1,1);
INSERT INTO selection (NAME,confederation,points,userID,ACTIVE) VALUES('Australia','OCEANIA',0,1,1);
INSERT INTO selection (NAME,confederation,points,userID,ACTIVE) VALUES('Nigeria','AFRICA',0,1,1);
INSERT INTO selection (NAME,confederation,points,userID,ACTIVE) VALUES('Yugoslavia','EUROPE',0,1,0);

INSERT INTO matches(HOST,away,hostGoals,awayGoals,DATE,matchtype,userID) VALUES  (5,6,2,2,'2020-01-01','FriendlyGame',1);
INSERT INTO matches(HOST,away,hostGoals,awayGoals,DATE,matchtype,userID) VALUES  (1,2,1,2,'2019-03-05','ConfederationCup',1);
INSERT INTO matches(HOST,away,hostGoals,awayGoals,DATE,matchtype,userID) VALUES  (3,4,0,0,'2018-03-07','ConfederationCup',1);
INSERT INTO matches(HOST,away,hostGoals,awayGoals,DATE,matchtype,userID) VALUES  (6,2,3,1,'2020-03-02','ConfederationCup',1);