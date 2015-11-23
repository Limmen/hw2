CREATE TABLE member(
    username VARCHAR(30) PRIMARY KEY NOT NULL,
    password VARCHAR(30) NOT NULL);
    
CREATE TABLE item(
  itemID SERIAL PRIMARY KEY,
  description VARCHAR(50),
  itemname VARCHAR(30) NOT NULL,
  price FLOAT NOT NULL);
  
CREATE TABLE listeditem(
  itemID INTEGER NOT NULL REFERENCES item(itemID),
  seller VARCHAR(30) NOT NULL REFERENCES member(username),
  PRIMARY KEY(itemID,seller));
  
CREATE TABLE solditem(
  solditemID SERIAL PRIMARY KEY,
  seller VARCHAR(30) NOT NULL REFERENCES member(username),
  buyer VARCHAR(30) NOT NULL REFERENCES member(username),
  itemID INTEGER NOT NULL REFERENCES item(itemID));
  
CREATE TABLE wish(
  wishID SERIAL PRIMARY KEY,
  itemID INTEGER NOT NULL REFERENCES item(itemID),
  wisher VARCHAR(30) NOT NULL REFERENCES member(username));

CREATE TABLE account(
  username VARCHAR(30) PRIMARY KEY NOT NULL REFERENCES member(username),
  balance FLOAT NOT NULL);
