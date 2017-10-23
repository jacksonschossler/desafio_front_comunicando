package br.com.agenda.categoria.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;


import br.com.agenda.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Audited
@DataTransferObject(javascript = "Categoria")
@EqualsAndHashCode(callSuper = true)
public class Categoria extends AbstractEntity implements Serializable
{/**
	 * 
	 */
	private static final long serialVersionUID = -2461123288355009831L;
	
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	// Basic
	
	/**
	 * Nome da categoria
	 */
	@NotNull(message = "Informe um nome para categoria")
	@Length(max=144)
	@Column(nullable = false, length = 144, unique=true)
	private String nome;
	
	/**
	 * Descrição da categoria
	 */
	@Column(nullable = true)
	private String descricao;
	
	/**
	 * Tipo da categoria
	 */
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private Tipo tipo;
	
	/**
	 * Desativada ou não
	 */
	@Column(nullable = false)
	private Boolean desativada;
	
	
	
	
	
	
	
	
	/*-------------------------------------------------------------------
	 * 		 					CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	public Categoria()
	{
		super();
	}
	
	public Categoria(Long id)
	{
		super(id);
	}
	
	
	/*-------------------------------------------------------------------
	 *							BEHAVIORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * muda o status da categoria (ativada/desativada)
	 */
	public void mudaStatusCategoria() {
		this.desativada = !this.desativada;
	}

}