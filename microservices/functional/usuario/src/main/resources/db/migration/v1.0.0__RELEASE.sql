

-- Table: usuario.revisao

-- DROP TABLE usuario.revisao;

CREATE TABLE usuario.revisao
(
  id bigserial NOT NULL,
  id_usuario bigint,
  "timestamp" bigint NOT NULL,
  CONSTRAINT revisao_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

-- Table: usuario.entidade_revisao

-- DROP TABLE usuario.entidade_revisao;

CREATE TABLE usuario.entidade_revisao
(
  revisao bigint NOT NULL,
  entidade character varying(255),
  CONSTRAINT fks6354amh77svj71q8xjc2y49q FOREIGN KEY (revisao)
      REFERENCES usuario.revisao (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);


-- Table: usuario.usuario

-- DROP TABLE usuario.usuario;

CREATE TABLE usuario.usuario
(
  id bigserial NOT NULL,
  atualizacao timestamp without time zone,
  criacao timestamp without time zone NOT NULL,
  versao bigint NOT NULL,
  email character varying(100),
  nome character varying(150),
  senha character varying(255) NOT NULL,
  CONSTRAINT usuario_pkey PRIMARY KEY (id),
  CONSTRAINT uk_5171l57faosmj8myawaucatdw UNIQUE (email)
)
WITH (
  OIDS=FALSE
);

-- Table: usuario.usuario_auditado

-- DROP TABLE usuario.usuario_auditado;

CREATE TABLE usuario.usuario_auditado
(
  id bigint NOT NULL,
  revisao bigint NOT NULL,
  tipo_revisao smallint,
  email character varying(100),
  nome character varying(150),
  senha character varying(255),
  CONSTRAINT usuario_auditado_pkey PRIMARY KEY (id, revisao),
  CONSTRAINT fkpva7pxwc5ss69rqcb08inecp1 FOREIGN KEY (revisao)
      REFERENCES usuario.revisao (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);


-------------------------
---- DEFAULT DATA
-------------------------
INSERT INTO usuario.usuario
	VALUES (1, NULL, '2017-01-19 11:36:04.951641', 1, 'admin@admin.com', 'Administrador', '$2a$10$bAdAVLvM.k3DqPaPYi0gnO1OffPSHLref8MElAk.u.fFQ17v9YKC2');
SELECT pg_catalog.setval('usuario.usuario_id_seq', 1, true);