--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.21
-- Dumped by pg_dump version 9.4.21
-- Started on 2021-11-29 19:24:13 +08

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 1 (class 3079 OID 12854)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 3151 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 183 (class 1259 OID 245157)
-- Name: acl_class; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.acl_class (
    id bigint NOT NULL,
    class character varying(100) NOT NULL,
    class_id_type character varying(100)
);


ALTER TABLE public.acl_class OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 245155)
-- Name: acl_class_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.acl_class_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.acl_class_id_seq OWNER TO postgres;

--
-- TOC entry 3152 (class 0 OID 0)
-- Dependencies: 182
-- Name: acl_class_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.acl_class_id_seq OWNED BY public.acl_class.id;


--
-- TOC entry 187 (class 1259 OID 245192)
-- Name: acl_entry; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.acl_entry (
    id bigint NOT NULL,
    acl_object_identity bigint NOT NULL,
    ace_order integer NOT NULL,
    sid bigint NOT NULL,
    mask integer NOT NULL,
    granting boolean NOT NULL,
    audit_success boolean NOT NULL,
    audit_failure boolean NOT NULL
);


ALTER TABLE public.acl_entry OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 245190)
-- Name: acl_entry_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.acl_entry_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.acl_entry_id_seq OWNER TO postgres;

--
-- TOC entry 3153 (class 0 OID 0)
-- Dependencies: 186
-- Name: acl_entry_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.acl_entry_id_seq OWNED BY public.acl_entry.id;


--
-- TOC entry 185 (class 1259 OID 245167)
-- Name: acl_object_identity; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.acl_object_identity (
    id bigint NOT NULL,
    object_id_class bigint NOT NULL,
    object_id_identity character varying(36) NOT NULL,
    parent_object bigint,
    owner_sid bigint,
    entries_inheriting boolean NOT NULL
);


ALTER TABLE public.acl_object_identity OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 245165)
-- Name: acl_object_identity_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.acl_object_identity_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.acl_object_identity_id_seq OWNER TO postgres;

--
-- TOC entry 3154 (class 0 OID 0)
-- Dependencies: 184
-- Name: acl_object_identity_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.acl_object_identity_id_seq OWNED BY public.acl_object_identity.id;


--
-- TOC entry 181 (class 1259 OID 245147)
-- Name: acl_sid; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.acl_sid (
    id bigint NOT NULL,
    principal boolean NOT NULL,
    sid character varying(100) NOT NULL
);


ALTER TABLE public.acl_sid OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 245145)
-- Name: acl_sid_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.acl_sid_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.acl_sid_id_seq OWNER TO postgres;

--
-- TOC entry 3155 (class 0 OID 0)
-- Dependencies: 180
-- Name: acl_sid_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.acl_sid_id_seq OWNED BY public.acl_sid.id;


--
-- TOC entry 179 (class 1259 OID 245123)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: cloud
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO cloud;

--
-- TOC entry 191 (class 1259 OID 245234)
-- Name: oauth_access_token; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.oauth_access_token (
    token_id character varying(255),
    token bytea,
    authentication_id character varying(255) NOT NULL,
    user_name character varying(255),
    client_id character varying(255),
    authentication bytea,
    refresh_token character varying(255)
);


ALTER TABLE public.oauth_access_token OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 245254)
-- Name: oauth_approvals; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.oauth_approvals (
    userid character varying(255),
    clientid character varying(255),
    scope character varying(255),
    status character varying(10),
    expiresat timestamp without time zone,
    lastmodifiedat timestamp without time zone
);


ALTER TABLE public.oauth_approvals OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 245218)
-- Name: oauth_client_details; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.oauth_client_details (
    client_id character varying(255) NOT NULL,
    resource_ids character varying(255),
    client_secret character varying(255),
    scope character varying(255),
    authorized_grant_types character varying(255),
    web_server_redirect_uri character varying(255),
    authorities character varying(255),
    access_token_validity integer,
    refresh_token_validity integer,
    additional_information character varying(4096),
    autoapprove character varying(255)
);


ALTER TABLE public.oauth_client_details OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 245226)
-- Name: oauth_client_token; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.oauth_client_token (
    token_id character varying(255),
    token bytea,
    authentication_id character varying(255) NOT NULL,
    user_name character varying(255),
    client_id character varying(255)
);


ALTER TABLE public.oauth_client_token OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 245248)
-- Name: oauth_code; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.oauth_code (
    code character varying(255),
    authentication bytea
);


ALTER TABLE public.oauth_code OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 245242)
-- Name: oauth_refresh_token; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.oauth_refresh_token (
    token_id character varying(255),
    token bytea,
    authentication bytea
);


ALTER TABLE public.oauth_refresh_token OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 245079)
-- Name: revinfo; Type: TABLE; Schema: public; Owner: cloud; Tablespace: 
--

CREATE TABLE public.revinfo (
    rev bigint NOT NULL,
    revtstmp bigint
);


ALTER TABLE public.revinfo OWNER TO cloud;

--
-- TOC entry 174 (class 1259 OID 245084)
-- Name: sec_menu; Type: TABLE; Schema: public; Owner: cloud; Tablespace: 
--

CREATE TABLE public.sec_menu (
    id bigint NOT NULL,
    created_date timestamp without time zone,
    deleted_date timestamp without time zone,
    updated_date timestamp without time zone,
    version integer DEFAULT 0,
    icon character varying(255),
    priority integer,
    search_key character varying(255),
    title character varying(255),
    url character varying(255),
    parent_id bigint
);


ALTER TABLE public.sec_menu OWNER TO cloud;

--
-- TOC entry 175 (class 1259 OID 245093)
-- Name: sec_role; Type: TABLE; Schema: public; Owner: cloud; Tablespace: 
--

CREATE TABLE public.sec_role (
    id bigint NOT NULL,
    description character varying(255),
    name character varying(255)
);


ALTER TABLE public.sec_role OWNER TO cloud;

--
-- TOC entry 176 (class 1259 OID 245101)
-- Name: sec_user; Type: TABLE; Schema: public; Owner: cloud; Tablespace: 
--

CREATE TABLE public.sec_user (
    id bigint NOT NULL,
    created_date timestamp without time zone,
    deleted_date timestamp without time zone,
    updated_date timestamp without time zone,
    version integer DEFAULT 0,
    created_by character varying(255),
    updated_by character varying(255),
    is_enabled character(1),
    user_password character varying(255) NOT NULL,
    user_name character varying(255) NOT NULL
);


ALTER TABLE public.sec_user OWNER TO cloud;

--
-- TOC entry 177 (class 1259 OID 245110)
-- Name: sec_user_aud; Type: TABLE; Schema: public; Owner: cloud; Tablespace: 
--

CREATE TABLE public.sec_user_aud (
    id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    is_enabled character(1),
    user_password character varying(255),
    user_name character varying(255)
);


ALTER TABLE public.sec_user_aud OWNER TO cloud;

--
-- TOC entry 188 (class 1259 OID 245210)
-- Name: t_cache; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.t_cache (
    c_key character varying(200) NOT NULL,
    c_value character varying,
    map_name character varying(50),
    c_date time with time zone
);


ALTER TABLE public.t_cache OWNER TO postgres;

--
-- TOC entry 178 (class 1259 OID 245118)
-- Name: users_roles; Type: TABLE; Schema: public; Owner: cloud; Tablespace: 
--

CREATE TABLE public.users_roles (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE public.users_roles OWNER TO cloud;

--
-- TOC entry 2964 (class 2604 OID 245160)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.acl_class ALTER COLUMN id SET DEFAULT nextval('public.acl_class_id_seq'::regclass);


--
-- TOC entry 2966 (class 2604 OID 245195)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.acl_entry ALTER COLUMN id SET DEFAULT nextval('public.acl_entry_id_seq'::regclass);


--
-- TOC entry 2965 (class 2604 OID 245170)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.acl_object_identity ALTER COLUMN id SET DEFAULT nextval('public.acl_object_identity_id_seq'::regclass);


--
-- TOC entry 2963 (class 2604 OID 245150)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.acl_sid ALTER COLUMN id SET DEFAULT nextval('public.acl_sid_id_seq'::regclass);


--
-- TOC entry 3131 (class 0 OID 245157)
-- Dependencies: 183
-- Data for Name: acl_class; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3156 (class 0 OID 0)
-- Dependencies: 182
-- Name: acl_class_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.acl_class_id_seq', 1, false);


--
-- TOC entry 3135 (class 0 OID 245192)
-- Dependencies: 187
-- Data for Name: acl_entry; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3157 (class 0 OID 0)
-- Dependencies: 186
-- Name: acl_entry_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.acl_entry_id_seq', 1, false);


--
-- TOC entry 3133 (class 0 OID 245167)
-- Dependencies: 185
-- Data for Name: acl_object_identity; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3158 (class 0 OID 0)
-- Dependencies: 184
-- Name: acl_object_identity_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.acl_object_identity_id_seq', 1, false);


--
-- TOC entry 3129 (class 0 OID 245147)
-- Dependencies: 181
-- Data for Name: acl_sid; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3159 (class 0 OID 0)
-- Dependencies: 180
-- Name: acl_sid_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.acl_sid_id_seq', 1, false);


--
-- TOC entry 3160 (class 0 OID 0)
-- Dependencies: 179
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: cloud
--

SELECT pg_catalog.setval('public.hibernate_sequence', 2, true);


--
-- TOC entry 3139 (class 0 OID 245234)
-- Dependencies: 191
-- Data for Name: oauth_access_token; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.oauth_access_token (token_id, token, authentication_id, user_name, client_id, authentication, refresh_token) VALUES ('2fd36d4cda71324cb7111ee6cc120420', '\xaced0005737200436f72672e737072696e676672616d65776f726b2e73656375726974792e6f61757468322e636f6d6d6f6e2e44656661756c744f4175746832416363657373546f6b656e0cb29e361b24face0200064c00156164646974696f6e616c496e666f726d6174696f6e74000f4c6a6176612f7574696c2f4d61703b4c000a65787069726174696f6e7400104c6a6176612f7574696c2f446174653b4c000c72656672657368546f6b656e74003f4c6f72672f737072696e676672616d65776f726b2f73656375726974792f6f61757468322f636f6d6d6f6e2f4f417574683252656672657368546f6b656e3b4c000573636f706574000f4c6a6176612f7574696c2f5365743b4c0009746f6b656e547970657400124c6a6176612f6c616e672f537472696e673b4c000576616c756571007e00057870737200176a6176612e7574696c2e4c696e6b6564486173684d617034c04e5c106cc0fb0200015a000b6163636573734f72646572787200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f40000000000001770800000002000000017400036a746974001b344d2d7046424c4863346358476345305477357648477a4a4f595978007372000e6a6176612e7574696c2e44617465686a81014b597419030000787077080000017d6e01ab98787372004c6f72672e737072696e676672616d65776f726b2e73656375726974792e6f61757468322e636f6d6d6f6e2e44656661756c744578706972696e674f417574683252656672657368546f6b656e2fdf47639dd0c9b70200014c000a65787069726174696f6e71007e0002787200446f72672e737072696e676672616d65776f726b2e73656375726974792e6f61757468322e636f6d6d6f6e2e44656661756c744f417574683252656672657368546f6b656e73e10e0a6354d45e0200014c000576616c756571007e0005787074018465794a68624763694f694a49557a49314e694973496e523563434936496b705856434a392e65794a68645751694f6c73695a3246305a58646865534a644c434a316332567958323568625755694f694a6a624739315a47466b62576c754969776963324e76634755694f6c7369556b564252434973496c645353565246496c3073496d463061534936496a524e4c584247516b7849597a526a5745646a52544255647a563253456436536b395a57534973496d5634634349364d5459304d4463334e6a6b304d69776964584e6c636b6c6b496a6f784c434a686458526f62334a7064476c6c6379493657794a535430784658315654525649694c434a53543078465830464554556c4f496c3073496d703061534936496e4574616c4531593231525444686e6431706d59567078576d5a3162553566536a523154534973496d4e7361575675644639705a434936496d4e736233566b496e302e573478617969545f4853414c4b592d324d6170796b5a3746494d706d5f38743373656f5243476b366d5a557371007e000c77080000017e05ed459778737200256a6176612e7574696c2e436f6c6c656374696f6e7324556e6d6f6469666961626c65536574801d92d18f9b80550200007872002c6a6176612e7574696c2e436f6c6c656374696f6e7324556e6d6f6469666961626c65436f6c6c656374696f6e19420080cb5ef71e0200014c0001637400164c6a6176612f7574696c2f436f6c6c656374696f6e3b7870737200176a6176612e7574696c2e4c696e6b656448617368536574d86cd75a95dd2a1e020000787200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000103f400000000000027400045245414474000557524954457874000662656172657274015465794a68624763694f694a49557a49314e694973496e523563434936496b705856434a392e65794a68645751694f6c73695a3246305a58646865534a644c434a316332567958323568625755694f694a6a624739315a47466b62576c754969776963324e76634755694f6c7369556b564252434973496c645353565246496c3073496d5634634349364d54597a4f4449794f4445304d79776964584e6c636b6c6b496a6f784c434a686458526f62334a7064476c6c6379493657794a535430784658315654525649694c434a53543078465830464554556c4f496c3073496d703061534936496a524e4c584247516b7849597a526a5745646a52544255647a563253456436536b395a57534973496d4e7361575675644639705a434936496d4e736233566b496e302e627157674f5457635532445561477a367573754f6247495631506c6d722d5257422d4e664d7064734e6463', 'bc2a15e973e7bffc33ad4015ef43d3af', 'cloudadmin', 'cloud', '\xaced0005737200416f72672e737072696e676672616d65776f726b2e73656375726974792e6f61757468322e70726f76696465722e4f417574683241757468656e7469636174696f6ebd400b02166252130200024c000d73746f7265645265717565737474003c4c6f72672f737072696e676672616d65776f726b2f73656375726974792f6f61757468322f70726f76696465722f4f4175746832526571756573743b4c00127573657241757468656e7469636174696f6e7400324c6f72672f737072696e676672616d65776f726b2f73656375726974792f636f72652f41757468656e7469636174696f6e3b787200476f72672e737072696e676672616d65776f726b2e73656375726974792e61757468656e7469636174696f6e2e416273747261637441757468656e7469636174696f6e546f6b656ed3aa287e6e47640e0200035a000d61757468656e746963617465644c000b617574686f7269746965737400164c6a6176612f7574696c2f436f6c6c656374696f6e3b4c000764657461696c737400124c6a6176612f6c616e672f4f626a6563743b787000737200266a6176612e7574696c2e436f6c6c656374696f6e7324556e6d6f6469666961626c654c697374fc0f2531b5ec8e100200014c00046c6973747400104c6a6176612f7574696c2f4c6973743b7872002c6a6176612e7574696c2e436f6c6c656374696f6e7324556e6d6f6469666961626c65436f6c6c656374696f6e19420080cb5ef71e0200014c00016371007e00047870737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a657870000000027704000000027372001f636f6d2e636c6f75642e757365726d616e6167656d656e742e526f6c65564f3d8d860b85be0f370200014c00046e616d657400124c6a6176612f6c616e672f537472696e673b7870740009524f4c455f555345527371007e000d74000a524f4c455f41444d494e7871007e000c707372003a6f72672e737072696e676672616d65776f726b2e73656375726974792e6f61757468322e70726f76696465722e4f41757468325265717565737400000000000000010200075a0008617070726f7665644c000b617574686f72697469657371007e00044c000a657874656e73696f6e7374000f4c6a6176612f7574696c2f4d61703b4c000b726564697265637455726971007e000e4c00077265667265736874003b4c6f72672f737072696e676672616d65776f726b2f73656375726974792f6f61757468322f70726f76696465722f546f6b656e526571756573743b4c000b7265736f7572636549647374000f4c6a6176612f7574696c2f5365743b4c000d726573706f6e7365547970657371007e0016787200386f72672e737072696e676672616d65776f726b2e73656375726974792e6f61757468322e70726f76696465722e426173655265717565737436287a3ea37169bd0200034c0008636c69656e74496471007e000e4c001172657175657374506172616d657465727371007e00144c000573636f706571007e00167870740005636c6f7564737200256a6176612e7574696c2e436f6c6c656374696f6e7324556e6d6f6469666961626c654d6170f1a5a8fe74f507420200014c00016d71007e00147870737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f400000000000037708000000040000000274000a6772616e745f7479706574000870617373776f7264740008757365726e616d6574000a636c6f756461646d696e78737200256a6176612e7574696c2e436f6c6c656374696f6e7324556e6d6f6469666961626c65536574801d92d18f9b80550200007871007e0009737200176a6176612e7574696c2e4c696e6b656448617368536574d86cd75a95dd2a1e020000787200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000103f4000000000000274000452454144740005575249544578017371007e0025770c000000103f40000000000002737200426f72672e737072696e676672616d65776f726b2e73656375726974792e636f72652e617574686f726974792e53696d706c654772616e746564417574686f7269747900000000000002260200014c0004726f6c6571007e000e7870740009524f4c455f555345527371007e002a74000a524f4c455f41444d494e787371007e001c3f40000000000000770800000010000000007870707371007e0025770c000000103f4000000000000174000767617465776179787371007e0025770c000000103f40000000000000787372004f6f72672e737072696e676672616d65776f726b2e73656375726974792e61757468656e7469636174696f6e2e557365726e616d6550617373776f726441757468656e7469636174696f6e546f6b656e00000000000002260200024c000b63726564656e7469616c7371007e00054c00097072696e636970616c71007e00057871007e0003017371007e00077371007e000b0000000277040000000271007e000f71007e00117871007e0036737200176a6176612e7574696c2e4c696e6b6564486173684d617034c04e5c106cc0fb0200015a000b6163636573734f726465727871007e001c3f400000000000067708000000080000000271007e001e71007e001f71007e002071007e002178007073720028636f6d2e636c6f75642e757365726d616e6167656d656e742e5573657244657461696c73496d706ce716510f3ce38a850200055a0007656e61626c65644c000269647400104c6a6176612f6c616e672f4c6f6e673b4c000870617373776f726471007e000e4c0005726f6c657371007e00164c0008757365726e616d6571007e000e7870007372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000000000000174003c243261243034246a456e464c376e446e3237374d4d735849456f702f4f36416e52325a3979433964445a544c584e5464534b35534f6f484f524e574b7371007e0025770c000000103f4000000000000271007e000f71007e00117874000a636c6f756461646d696e', 'f9750e7e5a4130ba4b1c0e4179b5284c');


--
-- TOC entry 3142 (class 0 OID 245254)
-- Dependencies: 194
-- Data for Name: oauth_approvals; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3137 (class 0 OID 245218)
-- Dependencies: 189
-- Data for Name: oauth_client_details; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) VALUES ('cloud', 'gateway', '$2a$04$vnuiQGAq7IYI865RDVpk3eIjiIY65V5tGkz3d6V0YIypx1iUBMmQ.', 'READ,WRITE', 'refresh_token,password,client_credentials', NULL, 'ROLE_ADMIN,ROLE_USER', 60000000, 60000000, 'null', '');


--
-- TOC entry 3138 (class 0 OID 245226)
-- Dependencies: 190
-- Data for Name: oauth_client_token; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3141 (class 0 OID 245248)
-- Dependencies: 193
-- Data for Name: oauth_code; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3140 (class 0 OID 245242)
-- Dependencies: 192
-- Data for Name: oauth_refresh_token; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.oauth_refresh_token (token_id, token, authentication) VALUES ('f9750e7e5a4130ba4b1c0e4179b5284c', '\xaced00057372004c6f72672e737072696e676672616d65776f726b2e73656375726974792e6f61757468322e636f6d6d6f6e2e44656661756c744578706972696e674f417574683252656672657368546f6b656e2fdf47639dd0c9b70200014c000a65787069726174696f6e7400104c6a6176612f7574696c2f446174653b787200446f72672e737072696e676672616d65776f726b2e73656375726974792e6f61757468322e636f6d6d6f6e2e44656661756c744f417574683252656672657368546f6b656e73e10e0a6354d45e0200014c000576616c75657400124c6a6176612f6c616e672f537472696e673b787074018465794a68624763694f694a49557a49314e694973496e523563434936496b705856434a392e65794a68645751694f6c73695a3246305a58646865534a644c434a316332567958323568625755694f694a6a624739315a47466b62576c754969776963324e76634755694f6c7369556b564252434973496c645353565246496c3073496d463061534936496a524e4c584247516b7849597a526a5745646a52544255647a563253456436536b395a57534973496d5634634349364d5459304d4463334e6a6b304d69776964584e6c636b6c6b496a6f784c434a686458526f62334a7064476c6c6379493657794a535430784658315654525649694c434a53543078465830464554556c4f496c3073496d703061534936496e4574616c4531593231525444686e6431706d59567078576d5a3162553566536a523154534973496d4e7361575675644639705a434936496d4e736233566b496e302e573478617969545f4853414c4b592d324d6170796b5a3746494d706d5f38743373656f5243476b366d5a557372000e6a6176612e7574696c2e44617465686a81014b597419030000787077080000017e05ed459778', '\xaced0005737200416f72672e737072696e676672616d65776f726b2e73656375726974792e6f61757468322e70726f76696465722e4f417574683241757468656e7469636174696f6ebd400b02166252130200024c000d73746f7265645265717565737474003c4c6f72672f737072696e676672616d65776f726b2f73656375726974792f6f61757468322f70726f76696465722f4f4175746832526571756573743b4c00127573657241757468656e7469636174696f6e7400324c6f72672f737072696e676672616d65776f726b2f73656375726974792f636f72652f41757468656e7469636174696f6e3b787200476f72672e737072696e676672616d65776f726b2e73656375726974792e61757468656e7469636174696f6e2e416273747261637441757468656e7469636174696f6e546f6b656ed3aa287e6e47640e0200035a000d61757468656e746963617465644c000b617574686f7269746965737400164c6a6176612f7574696c2f436f6c6c656374696f6e3b4c000764657461696c737400124c6a6176612f6c616e672f4f626a6563743b787000737200266a6176612e7574696c2e436f6c6c656374696f6e7324556e6d6f6469666961626c654c697374fc0f2531b5ec8e100200014c00046c6973747400104c6a6176612f7574696c2f4c6973743b7872002c6a6176612e7574696c2e436f6c6c656374696f6e7324556e6d6f6469666961626c65436f6c6c656374696f6e19420080cb5ef71e0200014c00016371007e00047870737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a657870000000027704000000027372001f636f6d2e636c6f75642e757365726d616e6167656d656e742e526f6c65564f3d8d860b85be0f370200014c00046e616d657400124c6a6176612f6c616e672f537472696e673b7870740009524f4c455f555345527371007e000d74000a524f4c455f41444d494e7871007e000c707372003a6f72672e737072696e676672616d65776f726b2e73656375726974792e6f61757468322e70726f76696465722e4f41757468325265717565737400000000000000010200075a0008617070726f7665644c000b617574686f72697469657371007e00044c000a657874656e73696f6e7374000f4c6a6176612f7574696c2f4d61703b4c000b726564697265637455726971007e000e4c00077265667265736874003b4c6f72672f737072696e676672616d65776f726b2f73656375726974792f6f61757468322f70726f76696465722f546f6b656e526571756573743b4c000b7265736f7572636549647374000f4c6a6176612f7574696c2f5365743b4c000d726573706f6e7365547970657371007e0016787200386f72672e737072696e676672616d65776f726b2e73656375726974792e6f61757468322e70726f76696465722e426173655265717565737436287a3ea37169bd0200034c0008636c69656e74496471007e000e4c001172657175657374506172616d657465727371007e00144c000573636f706571007e00167870740005636c6f7564737200256a6176612e7574696c2e436f6c6c656374696f6e7324556e6d6f6469666961626c654d6170f1a5a8fe74f507420200014c00016d71007e00147870737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f400000000000037708000000040000000274000a6772616e745f7479706574000870617373776f7264740008757365726e616d6574000a636c6f756461646d696e78737200256a6176612e7574696c2e436f6c6c656374696f6e7324556e6d6f6469666961626c65536574801d92d18f9b80550200007871007e0009737200176a6176612e7574696c2e4c696e6b656448617368536574d86cd75a95dd2a1e020000787200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000103f4000000000000274000452454144740005575249544578017371007e0025770c000000103f40000000000002737200426f72672e737072696e676672616d65776f726b2e73656375726974792e636f72652e617574686f726974792e53696d706c654772616e746564417574686f7269747900000000000002260200014c0004726f6c6571007e000e7870740009524f4c455f555345527371007e002a74000a524f4c455f41444d494e787371007e001c3f40000000000000770800000010000000007870707371007e0025770c000000103f4000000000000174000767617465776179787371007e0025770c000000103f40000000000000787372004f6f72672e737072696e676672616d65776f726b2e73656375726974792e61757468656e7469636174696f6e2e557365726e616d6550617373776f726441757468656e7469636174696f6e546f6b656e00000000000002260200024c000b63726564656e7469616c7371007e00054c00097072696e636970616c71007e00057871007e0003017371007e00077371007e000b0000000277040000000271007e000f71007e00117871007e0036737200176a6176612e7574696c2e4c696e6b6564486173684d617034c04e5c106cc0fb0200015a000b6163636573734f726465727871007e001c3f400000000000067708000000080000000271007e001e71007e001f71007e002071007e002178007073720028636f6d2e636c6f75642e757365726d616e6167656d656e742e5573657244657461696c73496d706ce716510f3ce38a850200055a0007656e61626c65644c000269647400104c6a6176612f6c616e672f4c6f6e673b4c000870617373776f726471007e000e4c0005726f6c657371007e00164c0008757365726e616d6571007e000e7870007372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000000000000174003c243261243034246a456e464c376e446e3237374d4d735849456f702f4f36416e52325a3979433964445a544c584e5464534b35534f6f484f524e574b7371007e0025770c000000103f4000000000000271007e000f71007e00117874000a636c6f756461646d696e');


--
-- TOC entry 3121 (class 0 OID 245079)
-- Dependencies: 173
-- Data for Name: revinfo; Type: TABLE DATA; Schema: public; Owner: cloud
--

INSERT INTO public.revinfo (rev, revtstmp) VALUES (2, 1638184828112);


--
-- TOC entry 3122 (class 0 OID 245084)
-- Dependencies: 174
-- Data for Name: sec_menu; Type: TABLE DATA; Schema: public; Owner: cloud
--



--
-- TOC entry 3123 (class 0 OID 245093)
-- Dependencies: 175
-- Data for Name: sec_role; Type: TABLE DATA; Schema: public; Owner: cloud
--

INSERT INTO public.sec_role (id, description, name) VALUES (1, 'ADMIN', 'ROLE_ADMIN');
INSERT INTO public.sec_role (id, description, name) VALUES (2, 'USER', 'ROLE_USER');


--
-- TOC entry 3124 (class 0 OID 245101)
-- Dependencies: 176
-- Data for Name: sec_user; Type: TABLE DATA; Schema: public; Owner: cloud
--

INSERT INTO public.sec_user (id, created_date, deleted_date, updated_date, version, created_by, updated_by, is_enabled, user_password, user_name) VALUES (1, '2021-11-29 19:20:28.038', NULL, NULL, 0, NULL, NULL, 'N', '$2a$04$jEnFL7nDn277MMsXIEop/O6AnR2Z9yC9dDZTLXNTdSK5SOoHORNWK', 'cloudadmin');


--
-- TOC entry 3125 (class 0 OID 245110)
-- Dependencies: 177
-- Data for Name: sec_user_aud; Type: TABLE DATA; Schema: public; Owner: cloud
--

INSERT INTO public.sec_user_aud (id, rev, revtype, is_enabled, user_password, user_name) VALUES (1, 2, 0, 'N', '$2a$04$jEnFL7nDn277MMsXIEop/O6AnR2Z9yC9dDZTLXNTdSK5SOoHORNWK', 'cloudadmin');


--
-- TOC entry 3136 (class 0 OID 245210)
-- Dependencies: 188
-- Data for Name: t_cache; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3126 (class 0 OID 245118)
-- Dependencies: 178
-- Data for Name: users_roles; Type: TABLE DATA; Schema: public; Owner: cloud
--

INSERT INTO public.users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO public.users_roles (user_id, role_id) VALUES (1, 2);


--
-- TOC entry 2984 (class 2606 OID 245162)
-- Name: acl_class_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.acl_class
    ADD CONSTRAINT acl_class_pkey PRIMARY KEY (id);


--
-- TOC entry 2992 (class 2606 OID 245197)
-- Name: acl_entry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.acl_entry
    ADD CONSTRAINT acl_entry_pkey PRIMARY KEY (id);


--
-- TOC entry 2988 (class 2606 OID 245172)
-- Name: acl_object_identity_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.acl_object_identity
    ADD CONSTRAINT acl_object_identity_pkey PRIMARY KEY (id);


--
-- TOC entry 2980 (class 2606 OID 245152)
-- Name: acl_sid_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.acl_sid
    ADD CONSTRAINT acl_sid_pkey PRIMARY KEY (id);


--
-- TOC entry 3002 (class 2606 OID 245241)
-- Name: oauth_access_token_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.oauth_access_token
    ADD CONSTRAINT oauth_access_token_pkey PRIMARY KEY (authentication_id);


--
-- TOC entry 2998 (class 2606 OID 245225)
-- Name: oauth_client_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.oauth_client_details
    ADD CONSTRAINT oauth_client_details_pkey PRIMARY KEY (client_id);


--
-- TOC entry 3000 (class 2606 OID 245233)
-- Name: oauth_client_token_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.oauth_client_token
    ADD CONSTRAINT oauth_client_token_pkey PRIMARY KEY (authentication_id);


--
-- TOC entry 2968 (class 2606 OID 245083)
-- Name: revinfo_pkey; Type: CONSTRAINT; Schema: public; Owner: cloud; Tablespace: 
--

ALTER TABLE ONLY public.revinfo
    ADD CONSTRAINT revinfo_pkey PRIMARY KEY (rev);


--
-- TOC entry 2970 (class 2606 OID 245092)
-- Name: sec_menu_pkey; Type: CONSTRAINT; Schema: public; Owner: cloud; Tablespace: 
--

ALTER TABLE ONLY public.sec_menu
    ADD CONSTRAINT sec_menu_pkey PRIMARY KEY (id);


--
-- TOC entry 2972 (class 2606 OID 245100)
-- Name: sec_role_pkey; Type: CONSTRAINT; Schema: public; Owner: cloud; Tablespace: 
--

ALTER TABLE ONLY public.sec_role
    ADD CONSTRAINT sec_role_pkey PRIMARY KEY (id);


--
-- TOC entry 2976 (class 2606 OID 245117)
-- Name: sec_user_aud_pkey; Type: CONSTRAINT; Schema: public; Owner: cloud; Tablespace: 
--

ALTER TABLE ONLY public.sec_user_aud
    ADD CONSTRAINT sec_user_aud_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2974 (class 2606 OID 245109)
-- Name: sec_user_pkey; Type: CONSTRAINT; Schema: public; Owner: cloud; Tablespace: 
--

ALTER TABLE ONLY public.sec_user
    ADD CONSTRAINT sec_user_pkey PRIMARY KEY (id);


--
-- TOC entry 2996 (class 2606 OID 245217)
-- Name: t_cache_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.t_cache
    ADD CONSTRAINT t_cache_pkey PRIMARY KEY (c_key);


--
-- TOC entry 2982 (class 2606 OID 245154)
-- Name: unique_uk_1; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.acl_sid
    ADD CONSTRAINT unique_uk_1 UNIQUE (sid, principal);


--
-- TOC entry 2986 (class 2606 OID 245164)
-- Name: unique_uk_2; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.acl_class
    ADD CONSTRAINT unique_uk_2 UNIQUE (class);


--
-- TOC entry 2990 (class 2606 OID 245174)
-- Name: unique_uk_3; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.acl_object_identity
    ADD CONSTRAINT unique_uk_3 UNIQUE (object_id_class, object_id_identity);


--
-- TOC entry 2994 (class 2606 OID 245199)
-- Name: unique_uk_4; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.acl_entry
    ADD CONSTRAINT unique_uk_4 UNIQUE (acl_object_identity, ace_order);


--
-- TOC entry 2978 (class 2606 OID 245122)
-- Name: users_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: cloud; Tablespace: 
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 3004 (class 2606 OID 245130)
-- Name: fk1tqqojx2q75iy64166aehon7p; Type: FK CONSTRAINT; Schema: public; Owner: cloud
--

ALTER TABLE ONLY public.sec_user_aud
    ADD CONSTRAINT fk1tqqojx2q75iy64166aehon7p FOREIGN KEY (rev) REFERENCES public.revinfo(rev);


--
-- TOC entry 3003 (class 2606 OID 245125)
-- Name: fk4in6t1wnnv9m9754tdkqtyeru; Type: FK CONSTRAINT; Schema: public; Owner: cloud
--

ALTER TABLE ONLY public.sec_menu
    ADD CONSTRAINT fk4in6t1wnnv9m9754tdkqtyeru FOREIGN KEY (parent_id) REFERENCES public.sec_menu(id);


--
-- TOC entry 3006 (class 2606 OID 245140)
-- Name: fkbn4102thhj24wmlnnw59kn1gn; Type: FK CONSTRAINT; Schema: public; Owner: cloud
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT fkbn4102thhj24wmlnnw59kn1gn FOREIGN KEY (user_id) REFERENCES public.sec_user(id);


--
-- TOC entry 3005 (class 2606 OID 245135)
-- Name: fkpcv1lm98nsf8j79nsaqo3h367; Type: FK CONSTRAINT; Schema: public; Owner: cloud
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT fkpcv1lm98nsf8j79nsaqo3h367 FOREIGN KEY (role_id) REFERENCES public.sec_role(id);


--
-- TOC entry 3007 (class 2606 OID 245175)
-- Name: foreign_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.acl_object_identity
    ADD CONSTRAINT foreign_fk_1 FOREIGN KEY (parent_object) REFERENCES public.acl_object_identity(id);


--
-- TOC entry 3008 (class 2606 OID 245180)
-- Name: foreign_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.acl_object_identity
    ADD CONSTRAINT foreign_fk_2 FOREIGN KEY (object_id_class) REFERENCES public.acl_class(id);


--
-- TOC entry 3009 (class 2606 OID 245185)
-- Name: foreign_fk_3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.acl_object_identity
    ADD CONSTRAINT foreign_fk_3 FOREIGN KEY (owner_sid) REFERENCES public.acl_sid(id);


--
-- TOC entry 3010 (class 2606 OID 245200)
-- Name: foreign_fk_4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.acl_entry
    ADD CONSTRAINT foreign_fk_4 FOREIGN KEY (acl_object_identity) REFERENCES public.acl_object_identity(id);


--
-- TOC entry 3011 (class 2606 OID 245205)
-- Name: foreign_fk_5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.acl_entry
    ADD CONSTRAINT foreign_fk_5 FOREIGN KEY (sid) REFERENCES public.acl_sid(id);


--
-- TOC entry 3150 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2021-11-29 19:24:14 +08

--
-- PostgreSQL database dump complete
--

