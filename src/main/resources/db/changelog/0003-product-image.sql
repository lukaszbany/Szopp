CREATE TABLE app_product_image
(
    id          BIGSERIAL PRIMARY KEY,
    filename    TEXT UNIQUE                        NOT NULL,
    description TEXT,
    product_id  BIGINT references app_product (id) NOT NULL,
    image_order INTEGER                            NOT NULL
);
