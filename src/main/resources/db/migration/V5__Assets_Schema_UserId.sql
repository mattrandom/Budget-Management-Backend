alter table assets add user_id BIGINT NULL;

alter table assets add CONSTRAINT FK_ASSETS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);