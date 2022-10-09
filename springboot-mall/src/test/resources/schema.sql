CREATE TABLE IF NOT EXISTS Product
(
	productId         INT             NOT NULL  PRIMARY KEY AUTO_INCREMENT,
	productName       VARCHAR(128)    NOT NULL,
	category          VARCHAR(32)     NOT NULL,
	imageUrl          VARCHAR(256)    NOT NULL,
	price             INT             NOT NULL,
	stock             INT             NOT NULL,
	description       VARCHAR(1024),  
	createdDate       VARCHAR(32)     NOT NULL,
	lastModifiedDate  VARCHAR(32)     NOT NULL
);

CREATE TABLE IF NOT EXISTS User
(
	userId            INT             NOT NULL  PRIMARY KEY AUTO_INCREMENT,
	email             VARCHAR(256)    NOT NULL  UNIQUE,
	password          VARCHAR(256)    NOT NULL,
	createdDate       VARCHAR(32)     NOT NULL,
	lastModifiedDate  VARCHAR(32)     NOT NULL
);
