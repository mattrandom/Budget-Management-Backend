create TABLE properties (
  id BIGINT AUTO_INCREMENT NOT NULL,
   postal_code VARCHAR(255) NULL,
   rooms INT NULL,
   single BIT(1) NULL,
   city VARCHAR(255) NULL,
   street VARCHAR(255) NULL,
   house VARCHAR(255) NULL,
   user_id BIGINT NULL,
   CONSTRAINT pk_properties PRIMARY KEY (id)
);

alter table properties add CONSTRAINT FK_PROPERTIES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);