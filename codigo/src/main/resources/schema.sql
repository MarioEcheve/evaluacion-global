CREATE TABLE USERS (
	id_user varchar(100) PRIMARY KEY,
	name VARCHAR ( 100 ),
	password VARCHAR ( 100 )  NOT NULL,
	token VARCHAR  NOT NULL,
	email VARCHAR ( 100 ) NOT NULL,
	created date,
	modified date,
	last_login date,
	isactive boolean
);

CREATE TABLE PHONE (
	id_phone varchar(100)  PRIMARY KEY,
	number_phone VARCHAR ( 100 ) NOT NULL,
	citycode VARCHAR ( 100 )  NOT NULL,
	contrycode VARCHAR ( 100 ) NOT NULL,
	id_user  varchar (100),
	FOREIGN KEY (id_user)
      REFERENCES USERS (id_user)
);