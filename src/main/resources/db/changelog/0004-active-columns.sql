ALTER TABLE app_category
    ADD COLUMN is_active BOOLEAN NOT NULL default false;

ALTER TABLE app_product
    ADD COLUMN is_active BOOLEAN NOT NULL default false;
