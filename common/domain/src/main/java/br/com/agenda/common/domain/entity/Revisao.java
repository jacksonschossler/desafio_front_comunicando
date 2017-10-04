package br.com.agenda.common.domain.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.ModifiedEntityNames;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import br.com.agenda.common.infrastructure.hibernate.envers.EntityTrackingRevisionListener;
import lombok.Data;

/**
 * 
 * @author rodrigo@eits.com.br
 * @since 06/12/2012
 * @version 1.0
 */
@Data
@Entity
@org.hibernate.envers.RevisionEntity(EntityTrackingRevisionListener.class)
public class Revisao implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4193623660483050410L;

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Id
	@RevisionNumber
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	/**
	 * 
	 */
	@RevisionTimestamp
	private long timestamp;
	
	/**
	 * 
	 */
	private Long idUsuario;

	/**
	 * 
	 */
	@ModifiedEntityNames
	@Fetch(FetchMode.JOIN)
	@Column(name = "entidade")
	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "entidade_revisao", joinColumns = @JoinColumn(name = "revisao"))
	private Set<String> entidadesModificadas = new HashSet<String>();

	/*-------------------------------------------------------------------
	 * 		 					CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 * @param id
	 */
	public Revisao()
	{
	}

	/*-------------------------------------------------------------------
	 *				 		     BEHAVIORS
	 *-------------------------------------------------------------------*/
}