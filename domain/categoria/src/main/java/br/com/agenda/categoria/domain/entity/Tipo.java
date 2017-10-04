package br.com.agenda.categoria.domain.entity;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject(type = "enum")
public enum Tipo {

	RECEITA,	//0
	DESPESA		//1
}
