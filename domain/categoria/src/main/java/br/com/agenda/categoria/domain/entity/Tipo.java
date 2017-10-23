package br.com.agenda.categoria.domain.entity;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * Enum de de tipo
 * @author Jackson
 *
 */
@DataTransferObject(type = "enum")
public enum Tipo {

	RECEITA,	//0
	DESPESA		//1
}
