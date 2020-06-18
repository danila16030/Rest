CREATE TABLE public.book
(
    title character varying COLLATE pg_catalog."default" NOT NULL,
    book_id Serial,
    price double precision NOT NULL,
    page_number integer NOT NULL,
    author character varying COLLATE pg_catalog."default" NOT NULL,
    writing_date character varying COLLATE pg_catalog."default" NOT NULL,
    description character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT book_pk PRIMARY KEY (book_id)
);