create TABLE rooms (
  id BIGINT AUTO_INCREMENT NOT NULL,
   user_id BIGINT NULL,
   cost DECIMAL NULL,
   room_type VARCHAR(255) NULL,
   CONSTRAINT pk_rooms PRIMARY KEY (id)
);

alter table rooms add CONSTRAINT FK_ROOMS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);