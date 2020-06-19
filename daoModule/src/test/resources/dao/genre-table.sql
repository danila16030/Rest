CREATE TABLE public.genre
(
    genre_id Serial,
    genre_name character varying COLLATE pg_catalog."default",
    CONSTRAINT genre_pk PRIMARY KEY (genre_id)
);
