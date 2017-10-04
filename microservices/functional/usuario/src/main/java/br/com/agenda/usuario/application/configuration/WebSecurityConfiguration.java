package br.com.agenda.usuario.application.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.agenda.common.application.security.AuthenticationFailureHandler;
import br.com.agenda.common.application.security.AuthenticationSuccessHandler;

/**
 * 
 * @author rodrigo
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter
{
	/*-------------------------------------------------------------------
	 * 		 					ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
	 * Password encoder
	 */
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	/**
	 * 
	 */
    @Autowired
    private UserDetailsService userDetailsService;
    
    /**
     * 
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    
    /**
     * 
     */
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    
    
	/*-------------------------------------------------------------------
	 * 		 						BEANS
	 *-------------------------------------------------------------------*/	

    
	/*-------------------------------------------------------------------
	 * 		 					 OVERRIDES
	 *-------------------------------------------------------------------*/
    
    /**
     * 
     */
    @Override
	public void configure( AuthenticationManagerBuilder builder ) throws Exception
    {
    	builder
    		.userDetailsService( this.userDetailsService )
    		.passwordEncoder( this.passwordEncoder );
	}
    
	/**
	 * Override this method to configure {@link WebSecurity}. For example, if you wish to
	 * ignore certain requests.
	 */
	public void configure( WebSecurity web ) throws Exception
	{
		web.ignoring()
			.antMatchers( "/**/favicon.ico", "/static/**", "/webjars/**", "/broker/**/*.js", "/bundles/**",
					  "/oauth/uncache_approvals", "/oauth/cache_approvals", "/keep-alive", "/password-recovery**/**", "/reset-password");
	}

	/**
	 *
	 */
	@Override
	protected void configure( HttpSecurity httpSecurity ) throws Exception
	{
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();

		httpSecurity
			.authorizeRequests()
				.anyRequest()
					.authenticated()
			.and()
				.formLogin()
					.usernameParameter( "email" )
					.passwordParameter( "senha" )
					.loginPage( "/autenticacao" )
					.loginProcessingUrl( "/authenticate" )
					.failureHandler( this.authenticationFailureHandler )
					.successHandler( this.authenticationSuccessHandler )
				.permitAll()
			.and()
				.logout()
					.logoutUrl( "/logout" );
	}
}