package br.com.agenda.usuario.application.security;

import java.util.Collection;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import br.com.agenda.usuario.domain.entity.Usuario;

/**
 * @author emanuelvictor
 *
 */
public class ContextHolder
{	
	/*-------------------------------------------------------------------
	 * 		 						BEHAVIORS
	 *-------------------------------------------------------------------*/
	/**
	 * O principal também pode ser um aplicativo alterar isso
	 * 
	 * @return
	 */
	public static Usuario getAuthenticatedUser()
	{
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if ( authentication != null )
		{
			if ( authentication instanceof OAuth2Authentication )
			{
				final Authentication userAuthentication = ((OAuth2Authentication) authentication).getUserAuthentication();

				if ( userAuthentication.getDetails() != null && userAuthentication.getDetails() instanceof Long )
				{
					return new Usuario( (Long) userAuthentication.getDetails(), userAuthentication.getName() );
				}
				else
				{
					return new Usuario( userAuthentication.getName() );
				}
			}
			
			if ( authentication.getPrincipal() instanceof Usuario )
			{
				return ( Usuario ) authentication.getPrincipal();
			}
		}

		throw new InsufficientAuthenticationException( "There is no user authenticated." );//FIXME Localize it
	}

	/**
	 * 
	 * @param usuario
	 * @param authorities
	 */
	public static void authenticate( Usuario usuario, Collection<? extends GrantedAuthority> authorities )
	{
		SecurityContextHolder.createEmptyContext();
		SecurityContextHolder.getContext().setAuthentication( new UsernamePasswordAuthenticationToken( usuario, authorities ) );
	}
}
