drop database ChatAppDB;
create database ChatAppDB;
use ChatAppDB;

CREATE TABLE Users(
	ID INT NOT NULL AUTO_INCREMENT,
	Username VARCHAR(255) NOT NULL UNIQUE,
    Pass VARCHAR(255) NOT NULL,
    Salt VARCHAR(255),
    User_role ENUM('Admin', 'User') NOT NULL,
    primary key(ID)
    );

CREATE TABLE Chats(
	ID INT NOT NULL AUTO_INCREMENT,
	ChatName VARCHAR(255) NOT NULL,
    CreatorID INT NOT NULL,
    primary key(ID),
    FOREIGN KEY (CreatorID) REFERENCES Users(ID)
    );
    
CREATE TABLE Messages(
	ID INT NOT NULL AUTO_INCREMENT,
	Content VARCHAR(1000) NOT NULL,
    SenderID INT NOT NULL,
    ChatID INT NOT NULL,
    Time_of_sending TIMESTAMP,
    PRIMARY KEY (ID),
	FOREIGN KEY (SenderID) REFERENCES Users(ID),
    FOREIGN KEY (ChatID) REFERENCES Chats(ID)
    );
    
CREATE TABLE Users_Chats(
	ID INT NOT NULL AUTO_INCREMENT,
    UserID INT NOT NULL,
    ChatID INT NOT NULL,
    PRIMARY KEY (ID),
	FOREIGN KEY (UserID) REFERENCES Users(ID),
    FOREIGN KEY (ChatID) REFERENCES Chats(ID)
    );
    
select * from Chats;

select * from Users_Chats;

select * from Users;

select * from Messages;


delete from Users_Chats where ID > 0;

delete from Chats where ID > 0;

delete from Messages where ID > 0;

