CREATE TABLE IF NOT EXISTS revisao
(
  id bigserial NOT NULL,
  id_usuario bigint,
  "timestamp" bigint NOT NULL,
  CONSTRAINT revisao_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS entidade_revisao
(
  revisao bigint NOT NULL,
  entidade character varying(255),
  CONSTRAINT fks6354amh77svj71q8xjc2y49q FOREIGN KEY (revisao)
  REFERENCES revisao (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
);