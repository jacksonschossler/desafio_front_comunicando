package br.com.agenda.usuario.application.configuration;

import java.security.KeyPair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import br.com.agenda.usuario.domain.service.ClientService;

/**
 * 
 * @author rodrigo@eits.com.br
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfiguration extends AuthorizationServerConfigurerAdapter
{
	/*-------------------------------------------------------------------
	 * 		 					ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Autowired
	private AuthenticationManager authenticationManager;
	
	/**
	 * 
	 */
    @Autowired
    private UserDetailsService userDetailsService;
	
	/**
	 * 
	 */
	@Autowired
	private ClientService clientDetailsService;
	
	/*-------------------------------------------------------------------
	 * 		 						BEANS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 * @return
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter()
	{
		final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		
		//just to create a user authentication with our userDetailsService
		final DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
	    final DefaultUserAuthenticationConverter userTokenConverter = new DefaultUserAuthenticationConverter();
	    userTokenConverter.setUserDetailsService(this.userDetailsService);
	    accessTokenConverter.setUserTokenConverter(userTokenConverter);
	    jwtAccessTokenConverter.setAccessTokenConverter(accessTokenConverter);

		//The public/private key
        final KeyPair keyPair = new KeyStoreKeyFactory(
        	new ClassPathResource("server.jks"), "dabm123".toCharArray()
        ).getKeyPair("masterkey", "dabm123".toCharArray());
        jwtAccessTokenConverter.setKeyPair(keyPair);
        
		return jwtAccessTokenConverter;
	}

	/*-------------------------------------------------------------------
	 * 		 						OVERRIDES
	 *-------------------------------------------------------------------*/
	/**
	 * Configure the non-security features of the Authorization Server endpoints, like token store, token
	 * customizations, user approvals and grant types. You shouldn't need to do anything by default, unless you need
	 * password grants, in which case you need to provide an {@link AuthenticationManager}.
	 * 
	 * @param endpoints the endpoints configurer
	 */
	@Override
	public void configure( AuthorizationServerEndpointsConfigurer authorizationServerEndpointsConfigurer ) throws Exception
	{
		authorizationServerEndpointsConfigurer
			.userDetailsService( this.userDetailsService )
			.authenticationManager( this.authenticationManager )
			.accessTokenConverter( this.accessTokenConverter() );
	}

	/**
	 * Configure the security of the Authorization Server, which means in practical terms the /oauth/token endpoint. The
	 * /oauth/authorize endpoint also needs to be secure, but that is a normal user-facing endpoint and should be
	 * secured the same way as the rest of your UI, so is not covered here. The default settings cover the most common
	 * requirements, following recommendations from the OAuth2 spec, so you don't need to do anything here to get a
	 * basic server up and running.
	 * 
	 * @param security a fluent configurer for security features
	 */
	@Override
	public void configure( AuthorizationServerSecurityConfigurer authorizationServerSecurityConfigurer ) throws Exception
	{
		authorizationServerSecurityConfigurer
			.realm( "GT Saude - Autenticação" )
			.tokenKeyAccess("permitAll()") //turns the "/oauth/token_key" open
			.checkTokenAccess("isAuthenticated()") //protect the "/oauth/token"
			.allowFormAuthenticationForClients();
	}

	/**
	 * Configure the {@link ClientDetailsService}, e.g. declaring individual clients and their properties. Note that
	 * password grant is not enabled (even if some clients are allowed it) unless an {@link AuthenticationManager} is
	 * supplied to the {@link #configure(AuthorizationServerEndpointsConfigurer)}. At least one client, or a fully
	 * formed custom {@link ClientDetailsService} must be declared or the server will not start.
	 * 
	 * @param clients the client details configurer
	 */
	@Override
	public void configure( ClientDetailsServiceConfigurer clientDetailsServiceConfigurer ) throws Exception
	{
		clientDetailsServiceConfigurer
			.withClientDetails( this.clientDetailsService );
	}
}