create TABLE rents (
  id BIGINT AUTO_INCREMENT NOT NULL,
   user_id BIGINT NULL,
   tenant_name VARCHAR(255) NULL,
   tenant_surname VARCHAR(255) NULL,
   is_rent BIT(1) NULL,
   is_paid BIT(1) NULL,
   room_id BIGINT NULL,
   CONSTRAINT pk_rents PRIMARY KEY (id)
);

alter table properties add is_single_friendly BIT(1) NULL;

alter table properties add is_sold BIT(1) NULL;

alter table rooms add property_id BIGINT NULL;

alter table rents add CONSTRAINT FK_RENTS_ON_ROOM FOREIGN KEY (room_id) REFERENCES rooms (id);

alter table rents add CONSTRAINT FK_RENTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

alter table rooms add CONSTRAINT FK_ROOMS_ON_PROPERTY FOREIGN KEY (property_id) REFERENCES properties (id);

alter table properties_rooms drop FOREIGN KEY fk_proroo_on_property_entity;

alter table properties_rooms drop FOREIGN KEY fk_proroo_on_room_entity;

drop table properties_rooms;

alter table properties drop COLUMN single_friendly;

alter table properties drop COLUMN sold;