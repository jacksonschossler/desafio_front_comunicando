package br.com.agenda.usuario.domain.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author rodrigo.p.fraga
 */
@Service
@RemoteProxy
@Transactional
public class ClientService implements ClientDetailsService
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/

	/*-------------------------------------------------------------------
	 *				 		     BEHAVIORS
	 *-------------------------------------------------------------------*/
	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientDetailsService#loadClientByClientId(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ClientDetails loadClientByClientId( String clientId ) throws ClientRegistrationException
	{
		System.out.println( clientId );
		//Aplicativo aplicativo = this.aplicativoRepository.findByIdentificador( clientId );

		//Assert.notNull( aplicativo, "Aplicativo com o identificador '" + clientId + "' não encontrado" );

//		if ( !aplicativo.getAtivo() )
//		{
//			throw new AccessDeniedException( aplicativo.getMensagemDesativacao() );
//		}

//		Assert.isTrue( aplicativo.getAtivo(), aplicativo.getMensagemDesativacao() );

		return new ClientDetails()
		{
			private static final long serialVersionUID = -4537808614732926262L;

			@Override
			public boolean isSecretRequired()
			{
				return false;
			}
			
			@Override
			public boolean isScoped()
			{
				return false;
			}
			
			@Override
			public boolean isAutoApprove( String scope )
			{
				return true;
			}
			
			@Override
			public Set<String> getScope()
			{
				return new HashSet<>( Collections.singletonList( "FULL" ) );
			}
			
			@Override
			public Set<String> getResourceIds()
			{
				return null;
			}
			
			@Override
			public Set<String> getRegisteredRedirectUri()
			{
				return null;
			}
			
			@Override
			public Integer getRefreshTokenValiditySeconds()
			{
				return null;
			}
			
			@Override
			public String getClientSecret()
			{
				return null;
			}
			
			@Override
			public String getClientId()
			{
				return clientId;
			}
			
			@Override
			public Set<String> getAuthorizedGrantTypes()
			{
				return new HashSet<>( Arrays.asList( "authorization_code", "refresh_token", "password" ) );
			}
			
			@Override
			public Collection<GrantedAuthority> getAuthorities()
			{
				return new HashSet<>( Collections.singletonList( new SimpleGrantedAuthority( "APP_ADMIN" ) ) );
			}
			
			@Override
			public Map<String, Object> getAdditionalInformation()
			{
				return null;
			}
			
			@Override
			public Integer getAccessTokenValiditySeconds()
			{
				return null;
			}
		};
	}
}
