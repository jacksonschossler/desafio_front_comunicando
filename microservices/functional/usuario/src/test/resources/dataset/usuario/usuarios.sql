SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = ON;
SET check_function_bodies = FALSE;
SET client_min_messages = WARNING;
SET default_with_oids = FALSE;

SET search_path = usuario, pg_catalog;

TRUNCATE usuario CASCADE;

INSERT INTO usuario VALUES (1000, NULL, '2017-03-10 15:21:32.418', 0, '1000@email.com', 'Nome usuário', '$2a$10$iNbV27dagJ6tJzoxz5j17.tAlWwyggRVQ/cECqpcoh9/C9pTtD2TG');

INSERT INTO perfil_acesso_usuario VALUES (1000, 'modulo.perfil');


SELECT pg_catalog.setval('usuario_id_seq', 1, TRUE);
