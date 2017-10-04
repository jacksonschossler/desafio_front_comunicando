-- Table: registro.registro

-- DROP TABLE registro.registro;

CREATE TABLE registro.registro
(
  id bigserial NOT NULL,
  atualizacao timestamp without time zone,
  criacao timestamp without time zone NOT NULL,
  versao bigint NOT NULL,
  categoria bigint,
  data timestamp without time zone NOT NULL,
  descricao character varying(255),
  tipo integer,
  valor real NOT NULL,
  CONSTRAINT registro_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE registro.registro
  OWNER TO "financeiro-registro";

  

-- Table: registro.registro_auditado

-- DROP TABLE registro.registro_auditado;

CREATE TABLE registro.registro_auditado
(
  id bigint NOT NULL,
  revisao bigint NOT NULL,
  tipo_revisao smallint,
  categoria bigint,
  data timestamp without time zone,
  descricao character varying(255),
  tipo integer,
  valor real,
  CONSTRAINT registro_auditado_pkey PRIMARY KEY (id, revisao),
  CONSTRAINT fkfydi88anl6qa6uodvqxj22mt6 FOREIGN KEY (revisao)
      REFERENCES registro.revisao (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE registro.registro_auditado
  OWNER TO "financeiro-registro";

  
