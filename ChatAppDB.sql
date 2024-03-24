drop database ChatAppDB;
create database ChatAppDB;
use ChatAppDB;

CREATE TABLE Users(
	ID INT NOT NULL AUTO_INCREMENT,
	Username VARCHAR(255) NOT NULL,
    Pass VARCHAR(255) NOT NULL,
    User_role ENUM('Admin', 'User') NOT NULL,
    primary key(ID)
    );

CREATE TABLE Chats(
	ID INT NOT NULL AUTO_INCREMENT,
	ChatName VARCHAR(255) NOT NULL,
    primary key(ID)
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
