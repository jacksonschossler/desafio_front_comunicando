package br.com.agenda.common.application.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;

/**
 * 
 * @author rodrigo
 */
@Configuration
@EnableResourceServer
public class CommonOAuth2ResourceConfiguration extends ResourceServerConfigurerAdapter
{
	/*-------------------------------------------------------------------
	 * 		 						BEANS
	 *-------------------------------------------------------------------*/
	
	/*-------------------------------------------------------------------
	 * 		 						OVERRIDES
	 *-------------------------------------------------------------------*/	
	/**
	 * Add resource-server specific properties (like a resource id). The defaults should work for many applications, but
	 * you might want to change at least the resource id.
	 * 
	 * @param resources configurer for the resource server
	 * @throws Exception if there is a problem
	 */
	public void configure( ResourceServerSecurityConfigurer resourceServer ) throws Exception
	{
	}
	
	/**
	 * Use this to configure the access rules for secure resources. 
	 * By default all resources <i>not</i> in "/oauth/**" are protected (but no specific rules about scopes are given, for instance). 
	 * You also get an {@link OAuth2WebSecurityExpressionHandler} by default.
	 * 
	 * @param http the current http filter configuration
	 * @throws Exception if there is a problem
	 */
	@Override
    public void configure( HttpSecurity httpSecurity ) throws Exception 
	{
		httpSecurity.csrf().disable();

		httpSecurity
			.authorizeRequests()
				.antMatchers( "/api/syncer" )
					.permitAll()
			.and()
				.antMatcher( "/api/**" )
				.authorizeRequests()
					.anyRequest()
						.authenticated()			
			.and()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				
//		httpSecurity
//				.authorizeRequests()
//					.antMatchers( "/api/syncer" )
//						.permitAll()
//					.antMatchers( "/api/**" )		
//						.authenticated()
//				.and()
//					.sessionManagement()
//						.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
//		httpSecurity
//		.antMatcher( "/api/**" )
//			.authorizeRequests()
//				.anyRequest()
//					.authenticated()
//			.and()
//				.sessionManagement()
//					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}
}