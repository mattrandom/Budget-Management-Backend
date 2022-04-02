create TABLE properties_rooms (
  property_entity_id BIGINT NOT NULL,
   rooms_id BIGINT NOT NULL
);

alter table properties add single_friendly BIT(1) NULL;

alter table properties add sold BIT(1) NULL;

alter table properties_rooms add CONSTRAINT uc_properties_rooms_rooms UNIQUE (rooms_id);

alter table properties_rooms add CONSTRAINT fk_proroo_on_property_entity FOREIGN KEY (property_entity_id) REFERENCES properties (id);

alter table properties_rooms add CONSTRAINT fk_proroo_on_room_entity FOREIGN KEY (rooms_id) REFERENCES rooms (id);

alter table properties drop COLUMN rooms;

alter table properties drop COLUMN single;