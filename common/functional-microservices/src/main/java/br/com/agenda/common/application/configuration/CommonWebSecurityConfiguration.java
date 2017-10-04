package br.com.agenda.common.application.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

import br.com.agenda.common.application.security.LogoutSuccessHandler;
import feign.RequestInterceptor;

/**
 * 
 * @author rodrigo.p.fraga
 */
@Configuration
@EnableOAuth2Sso
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnMissingClass("br.com.agenda.usuario.application.configuration.OAuth2ServerConfiguration")
public class CommonWebSecurityConfiguration extends WebSecurityConfigurerAdapter
{
    
    /**
     * 
     */
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
	
	/*-------------------------------------------------------------------
	 * 		 						BEANS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 * @param oauth2ClientContext
	 * @param details
	 * @return
	 */
	@Bean
	public OAuth2RestTemplate oauth2RestTemplate( OAuth2ProtectedResourceDetails details, OAuth2ClientContext oauth2ClientContext )
	{
		return new OAuth2RestTemplate( details, oauth2ClientContext );
	}

	/**
	 * 
	 * @param oAuth2ClientContext
	 * @param resource
	 * @return
	 */
	@Bean
	public RequestInterceptor oauth2FeignRequestInterceptor( OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails resource )
	{
		return new OAuth2FeignRequestInterceptor( oAuth2ClientContext, resource );
	}
	
	/*-------------------------------------------------------------------
	 * 		 						OVERRIDES
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception 
	{
	    return super.authenticationManagerBean();//Just to avoid the default user creation
	}
	
	/**
	 * Override this method to configure {@link WebSecurity}. For example, if you wish to
	 * ignore certain requests.
	 */
	@Override
	public void configure( WebSecurity web ) throws Exception 
	{
		web.ignoring()
			.antMatchers( "/**/favicon.ico", "/static/**", "/modules/**", "/webjars/**", "/broker/**/*.js", "/bundles/**");
	}

	/**
	 * 
	 */
	@Override
	public void configure( HttpSecurity httpSecurity ) throws Exception
	{
		httpSecurity.csrf().disable();

		httpSecurity
            .authorizeRequests()
                .anyRequest()
                        .authenticated()
            .and()
                .logout()
                	.logoutSuccessHandler( this.logoutSuccessHandler )
                    .logoutUrl( "/logout" );
	}
}
