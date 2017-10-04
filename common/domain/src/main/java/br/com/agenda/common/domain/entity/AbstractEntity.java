package br.com.agenda.common.domain.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import br.com.agenda.common.infrastructure.hibernate.types.TransientEntityType;
import lombok.Data;

/**
 * 
 * @author rodrigo@eits.com.br
 * @since 12/01/2017
 */
@Data
@MappedSuperclass
@TypeDefs({
	@TypeDef(name="transientEntity", typeClass=TransientEntityType.class),
})
public abstract class AbstractEntity implements IEntity<Long>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3875941859616104733L;

	/*-------------------------------------------------------------------
	 * 		 					ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
	 * Atributo id para todas as classes filhas
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	/**
	 * 
	 */
	@Column(nullable = false, updatable = false)
	protected Calendar criacao;
	/**
	 * 
	 */
	protected Calendar atualizacao;
	/**
	 * 
	 */
	@NotNull
	@Version
	@Column(nullable=false)
	private Long versao;

	/*-------------------------------------------------------------------
	 * 		 					CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	public AbstractEntity()
	{
	}

	/**
	 * 
	 * @param id
	 */
	public AbstractEntity( Long id )
	{
		this.setId( id );
	}

	/*-------------------------------------------------------------------
	 * 		 					BEHAVIORS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@PrePersist
	protected void refreshCreated()
	{
		if ( this.getCriacao() == null )
		{
			this.setCriacao( Calendar.getInstance() );
		}
	}

	/**
	 * 
	 */
	@PreUpdate
	protected void refreshUpdated()
	{
		this.refreshCreated();
		this.setAtualizacao( Calendar.getInstance() );
	}

	@Override
	public Long getId()
	{
		return id;
	}

	@Override
	public void setId( Long id )
	{
		this.id = id;
	}
}
