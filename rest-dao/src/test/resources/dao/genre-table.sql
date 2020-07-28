CREATE TABLE public.genre
(
    genre_id Serial,
    genre_name character varying COLLATE pg_catalog."default",
    created_by character varying COLLATE pg_catalog."default",
    created_date timestamp without time zone,
    last_modified_by character varying COLLATE pg_catalog."default",
    last_modified_date timestamp without time zone,
    CONSTRAINT genre_pk PRIMARY KEY (genre_id)
);
