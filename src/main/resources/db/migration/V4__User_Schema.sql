create TABLE users (
  id BIGINT AUTO_INCREMENT NOT NULL,
   username VARCHAR(255) NULL,
   password VARCHAR(255) NULL,
   CONSTRAINT pk_users PRIMARY KEY (id)
);