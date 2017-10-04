package br.com.agenda.registro.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.Type;
import org.hibernate.boot.archive.scan.spi.ClassDescriptor.Categorization;
import org.hibernate.envers.Audited;

import br.com.agenda.categoria.domain.entity.Categoria;
import br.com.agenda.categoria.domain.entity.Tipo;
import br.com.agenda.common.domain.entity.AbstractEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@Audited
@DataTransferObject(javascript = "Registro")
@EqualsAndHashCode(callSuper = true)
public class Registro extends AbstractEntity implements Serializable{

	
	private static final long serialVersionUID = 3138317258162531643L;
	
	

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	@Column(nullable=false)
	private float valor;
	
	@Column(nullable=false)
	private LocalDateTime data; 
	
	@Column(nullable=true)
	private String descricao;
	
	@Type(type="transientEntity",parameters=@Parameter(name="entity",value="br.com.agenda.categoria.domain.entity.Categoria"))
	@Column
	private Categoria categoria;
	
	//@Type(type="transientEntity",parameters=@Parameter(name="entity",value="br.com.agenda.categoria.domain.entity.Tipo"))
	@Column
	@Enumerated(EnumType.ORDINAL)
	private Tipo tipo;
	
	
	

	
	
	/*-------------------------------------------------------------------
	 * 		 					CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	public Registro()
	{
		super();
	}
	
	
	/*-------------------------------------------------------------------
	 *							BEHAVIORS
	 *-------------------------------------------------------------------*/
	
	
}
