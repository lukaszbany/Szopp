CREATE TABLE app_category
(
    id                 BIGSERIAL PRIMARY KEY,
    name               TEXT NOT NULL,
    description        TEXT,
    parent_category_id BIGINT references app_category (id)
);


CREATE TABLE app_product
(
    id          BIGSERIAL PRIMARY KEY,
    name        TEXT          NOT NULL,
    description TEXT,
    price       NUMERIC(9, 2) NOT NULL,
    category_id BIGINT references app_category (id),
    in_stock    INTEGER       NOT NULL default 0
);

CREATE TABLE app_customer
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT references app_user (id),
    first_name   TEXT,
    last_name    TEXT,
    email        TEXT,
    phone        TEXT,
    city         TEXT,
    zip_code     TEXT,
    type         TEXT NOT NULL default 'INDIVIDUAL',
    street       TEXT,
    company_name TEXT,
    nip          TEXT
);

CREATE TABLE app_order
(
    id           BIGSERIAL PRIMARY KEY,
    status       TEXT                     NOT NULL,
    date_created TIMESTAMP WITH TIME ZONE NOT NULL,
    customer_id  BIGINT references app_customer (id)
);

CREATE TABLE app_order_item
(
    id       BIGSERIAL PRIMARY KEY,
    order_id BIGINT references app_order (id),
    price    NUMERIC(9, 2) NOT NULL,
    quantity INTEGER       NOT NULL
);