CREATE TABLE public.order
(
    order_id SERIAL,
    order_price double precision NOT NULL,
    order_time character varying COLLATE pg_catalog."default" NOT NULL,
    book_id bigint NOT NULL,
    created_by character varying COLLATE pg_catalog."default",
    created_date timestamp without time zone,
    last_modified_by character varying COLLATE pg_catalog."default",
    last_modified_date timestamp without time zone,
    CONSTRAINT order_pk PRIMARY KEY (order_id)
);