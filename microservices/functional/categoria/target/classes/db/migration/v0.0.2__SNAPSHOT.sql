CREATE TABLE IF NOT EXISTS categoria.categoria
(
  id bigserial NOT NULL,
  atualizacao timestamp without time zone,
  criacao timestamp without time zone NOT NULL,
  versao bigint NOT NULL,
  desativada boolean,
  descricao character varying(255),
  nome character varying(50) NOT NULL,
  tipo integer NOT NULL,
  CONSTRAINT categoria_pkey PRIMARY KEY (id),
  CONSTRAINT uk_prx5elpv558ah8pk8x18u56yc UNIQUE (nome)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE categoria.categoria
  OWNER TO "financeiro-categoria";

  
CREATE TABLE IF NOT EXISTS categoria.categoria_auditado
(
  id bigint NOT NULL,
  revisao bigint NOT NULL,
  tipo_revisao smallint,
  desativada boolean,
  descricao character varying(255),
  nome character varying(50),
  tipo integer,
  CONSTRAINT categoria_auditado_pkey PRIMARY KEY (id, revisao),
  CONSTRAINT fkdpuexarwi6nt2w0xjd188w066 FOREIGN KEY (revisao)
      REFERENCES categoria.revisao (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE categoria.categoria_auditado
  OWNER TO "financeiro-categoria";

  
  

