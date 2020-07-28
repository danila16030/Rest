CREATE TABLE public.user
(
    username character varying COLLATE pg_catalog."default" NOT NULL,
    user_id SERIAL,
    user_password character varying(64) COLLATE pg_catalog."default" NOT NULL DEFAULT 1234,
    role character varying COLLATE pg_catalog."default" NOT NULL DEFAULT 'USER'::character varying,
    created_by character varying COLLATE pg_catalog."default",
    created_date timestamp without time zone,
    last_modified_by character varying COLLATE pg_catalog."default",
    last_modified_date timestamp without time zone,
    CONSTRAINT users_pk PRIMARY KEY (user_id)
);