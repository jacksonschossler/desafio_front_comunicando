package br.com.agenda.usuario.domain.entity;

import org.springframework.security.core.GrantedAuthority;

public enum PerfilUsuario implements GrantedAuthority
{
	USER("USER"),
	ADMIN("ADMIN");

	private final String roleName;

	PerfilUsuario( String roleName )
	{
		this.roleName = roleName;
	};

	@Override
	public String getAuthority()
	{
		return "ROLE_" + this.roleName;
	}
}