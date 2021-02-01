DROP DATABASE IF EXISTS erobotics_1;
CREATE DATABASE erobotics_1;
USE erobotics_1;

## Create Acounts table
CREATE TABLE Accounts
(
  FIRST_NAME VARCHAR(20) NOT NULL,
  LAST_NAME VARCHAR(20) NOT NULL,
  USER_EMAIL VARCHAR(128) NOT NULL,
  USER_NAME VARCHAR(20) NOT NULL,
  ACTIVE    BIT NOT NULL,
  ENCRYTED_PASSWORD  VARCHAR(128) NOT NULL,
  USER_ROLE VARCHAR(20) NOT NULL
) ;
 
ALTER TABLE Accounts
  ADD PRIMARY KEY (USER_NAME) ;

INSERT INTO Accounts
VALUES ('John', 'Doe', 'john@mail.com', 'customer1', 1,
'$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'ROLE_CUSTOMER');
 
INSERT INTO Accounts
VALUES ('Ram', 'Kumar', 'ram@mail.com', 'admin1', 1,
'$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'ROLE_ADMIN');
##---------------------------------------
 
CREATE TABLE Products
(
  CODE        VARCHAR(20) NOT NULL,
  IMAGE       LONGBLOB,
  NAME        VARCHAR(255) NOT NULL,
  PRICE       DOUBLE PRECISION NOT NULL,
  CREATE_DATE DATETIME NOT NULL,
  CATEGORY_ID VARCHAR(20) NOT NULL
) ;
 
ALTER TABLE Products
  ADD PRIMARY KEY (CODE) ;
-- alter table Products
--   add constraint Products_CAT_FK foreign key (CATEGORY_ID)
--   references Product_Category (ID); 
##---------------------------------------
## Create Order table
CREATE TABLE Orders
(
  ID               VARCHAR(50) NOT NULL,
  AMOUNT           DOUBLE PRECISION NOT NULL,
  CUSTOMER_ADDRESS VARCHAR(255) NOT NULL,
  CUSTOMER_EMAIL   VARCHAR(128) NOT NULL,
  CUSTOMER_NAME    VARCHAR(255) NOT NULL,
  CUSTOMER_PHONE   VARCHAR(128) NOT NULL,
  ORDER_DATE       DATETIME NOT NULL,
  ORDER_NUM        INTEGER NOT NULL
) ;
ALTER TABLE Orders
  ADD PRIMARY KEY (ID) ;
ALTER TABLE Orders
  ADD CONSTRAINT ORDER_UK UNIQUE (ORDER_NUM) ;
##---------------------------------------
 
## Create Order Details table
CREATE TABLE Order_Details
(
  ID         VARCHAR(50) NOT NULL,
  AMOUNT     DOUBLE PRECISION NOT NULL,
  PRICE      DOUBLE PRECISION NOT NULL,
  QUANITY    INTEGER NOT NULL,
  ORDER_ID   VARCHAR(50) NOT NULL,
  PRODUCT_ID VARCHAR(20) NOT NULL
) ;
ALTER TABLE Order_Details
  ADD PRIMARY KEY (ID) ;
ALTER TABLE Order_Details
  ADD CONSTRAINT ORDER_DETAIL_ORD_FK FOREIGN KEY (ORDER_ID)
  REFERENCES Orders (ID);
ALTER TABLE Order_Details
  ADD CONSTRAINT ORDER_DETAIL_PROD_FK FOREIGN KEY (PRODUCT_ID)
  REFERENCES Products (CODE);
  
##--  
CREATE TABLE Product_Category (
    ID VARCHAR(50),
    NAME VARCHAR(255)
);
ALTER TABLE Product_Category
  ADD PRIMARY KEY (ID) ;

INSERT INTO Product_Category VALUES (1, 'Drone Parts');
INSERT INTO Product_Category VALUES (2, 'Sensors');
INSERT INTO Product_Category VALUES (2, 'Electronic Component');
INSERT INTO Product_Category VALUES (2, 'Instuments and Tools');
INSERT INTO Product_Category VALUES (2, 'Wires & Cable');
INSERT INTO Product_Category VALUES (2, 'Motor Drivers & Actuator');
##---  
CREATE TABLE Product_Details (
    ID VARCHAR(50),
    DESCRIPTION TEXT,
    SPECIFICATION TEXT,    
    PRODUCT_ID VARCHAR(20) NOT NULL
);
ALTER TABLE Product_Details
  ADD PRIMARY KEY (ID) ;
-- ALTER TABLE Product_Details
--   ADD CONSTRAINT Product_Details_PROD_FK FOREIGN KEY (PRODUCT_ID)
--   REFERENCES Products (CODE);
##---------------------------------------  
