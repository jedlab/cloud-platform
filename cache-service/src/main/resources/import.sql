CREATE TABLE public.t_cache (
    c_key character varying(200) NOT NULL,
    c_value character varying,
    map_name character varying(50),
    c_date time with time zone
);

ALTER TABLE ONLY public.t_cache
    ADD CONSTRAINT t_cache_pkey PRIMARY KEY (c_key);