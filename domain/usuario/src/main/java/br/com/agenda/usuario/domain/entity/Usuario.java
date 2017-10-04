package br.com.agenda.usuario.domain.entity;

import java.util.Collections;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.Param;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.agenda.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @since 02/06/2014
 * @version 1.0
 */
@Data
@Entity
@Audited
@DataTransferObject(javascript = "Usuario", params={
	@Param(name="exclude", value="senha"),
	@Param(name="exclude", value="password"),
	@Param(name="exclude", value="username")
})
@EqualsAndHashCode(callSuper = true)
public class Usuario extends AbstractEntity implements UserDetails
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -453259664329431429L;

	public static final int VALID_HOURS_CODIGO = 24;

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	// Basic
	/**
	 * 
	 */
	@Column(nullable = true, length = 150)
	private String nome;
	/**
	 * 
	 */
	@NotBlank
	@JsonIgnore
	@Length(min = 6)
	@Column(nullable = false, length = 255)
	private String senha;
	/**
	 * 
	 */
	@Email(message = "Email inválido")
	@Column(length = 100, unique = true)
	private String email;

	/*-------------------------------------------------------------------
	 * 		 					CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	public Usuario()
	{
		super();
	}

	/**
	 * 
	 * @param id
	 */
	public Usuario( Long id )
	{
		super( id );
	}
	
	/**
	 * 
	 * @param id
	 */
	public Usuario( String email )
	{
		this.email = email;
	}

	/**
	 *
	 * @param id
	 * @param email
	 * @param grantedAuthorities
	 */
	public Usuario( Long id, String email )
	{
		this( email );
		this.id = id;
	}

	/*-------------------------------------------------------------------
	 *							BEHAVIORS
	 *-------------------------------------------------------------------*/
	/**
	 * Tratamento para quando a conta estiver excluída (é o mesmo que como se estivesse expirada)
	 */
	@Override
	@Transient
	@JsonIgnore
	public boolean isAccountNonExpired()
	{
		return true;
	}

	/**
	 * Tratamento para quando a conta estiver bloqueada, a data de hoje deve estar entra a data de desbloqueio e a data de bloqueio
	 */
	@Override
	@Transient
	@JsonIgnore
	public boolean isAccountNonLocked()
	{
		return true;
	}

	/**
	 * As credenciais estão expiradas quando o usuário foi assinalado para dever alterar a senha (alterarSenha ==true), ou a data de hoje for posterior a data de expiração da senha
	 */
	@Override
	@Transient
	@JsonIgnore
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	/**
	 * 
	 */
	@Override
	@Transient
	@JsonIgnore
	public boolean isEnabled()
	{
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	@Override
	@Transient
	@JsonIgnore
	public String getPassword()
	{
		return this.senha;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Override
	@Transient
	@JsonIgnore
	public String getUsername()
	{
		return this.getEmail();
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
	 */
	@Override
	@Transient
	@JsonIgnore
	public Set<PerfilUsuario> getAuthorities()
	{
		return Collections.singleton( PerfilUsuario.USER );
	}

}
