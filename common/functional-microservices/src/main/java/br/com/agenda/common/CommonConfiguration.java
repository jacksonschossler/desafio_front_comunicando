package br.com.agenda.common;

import java.util.Locale;

import javax.validation.Validator;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import br.com.agenda.common.application.i18n.ResourceBundleMessageSource;

/**
 * 
 * @author rodrigo@eits.com.br
 */
@Configuration
public class CommonConfiguration
{
	/*-------------------------------------------------------------------
	 * 		 					ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/*-------------------------------------------------------------------
	 * 		 						BEANS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 * @return
	 */
    @Bean
    public MessageSource messageSource( Environment environment ) 
    {
    	Locale.setDefault( new Locale("pt", "BR") );
    	
    	final String applicationName = environment.getProperty("spring.application.name");
    	
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setAlwaysUseMessageFormat( true );
        messageSource.setDefaultEncoding( "UTF-8" );
        messageSource.setBasenames(
        	"classpath:i18n/common-exceptions",
        	"classpath:i18n/common-labels",
        	"classpath:i18n/common-messages",

        	"classpath:i18n/"+applicationName+"-exceptions", 
        	"classpath:i18n/"+applicationName+"-labels", 
        	"classpath:i18n/"+applicationName+"-messages" 
        );
        
        return messageSource;
    }
    
    /**
     * 
     * @return
     */
    @Bean
    public Validator validator() 
    {
    	return new LocalValidatorFactoryBean();
    }
    
    /**
     * 
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() 
    {
    	return new BCryptPasswordEncoder();
    }
}