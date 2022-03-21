create TABLE expenses (
  id BIGINT AUTO_INCREMENT NOT NULL,
   amount DECIMAL NULL,
   expense_date datetime NULL,
   expense_category VARCHAR(255) NULL,
   user_id BIGINT NULL,
   CONSTRAINT pk_expenses PRIMARY KEY (id)
);

alter table expenses add CONSTRAINT FK_EXPENSES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);