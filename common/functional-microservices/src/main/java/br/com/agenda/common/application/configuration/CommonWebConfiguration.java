package br.com.agenda.common.application.configuration;

import java.util.Locale;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.spring.DwrClassPathBeanDefinitionScanner;
import org.directwebremoting.spring.DwrSpringServlet;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import br.com.agenda.common.application.configuration.settings.DWRSettings;
import br.com.agenda.common.application.dwr.DwrAnnotationPostProcessor;

/**
 * 
 * @author rodrigo
 */
@Configuration
public class CommonWebConfiguration extends WebMvcConfigurerAdapter
{
	/*-------------------------------------------------------------------
	 * 		 						BEANS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 * @return
	 */
	@Bean
    public FilterRegistrationBean forwardedHeaderFilter() 
	{
        FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
        filterRegBean.setFilter(new ForwardedHeaderFilter());
        filterRegBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegBean;
    }
	
	// ---------
	// Locale
	// ---------
	/**
	 * 
	 * @return
	 */
	@Bean
	public LocaleResolver localeResolver()
	{
		final FixedLocaleResolver localeResolver = new FixedLocaleResolver();
		localeResolver.setDefaultLocale( new Locale( "pt", "BR" ) );
		return localeResolver;
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor()
	{
		final LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName( "lang" );
		return localeChangeInterceptor;
	}

	// ---------
	// DWR
	// ---------
	/**
	 * Process all spring beans with @RemoteProxy
	 * 
	 * @return
	 */
	@Bean
	public DwrAnnotationPostProcessor dwrAnnotationPostProcessor( ApplicationContext applicationContext )
	{
		final BeanDefinitionRegistry beanDefinitionRegistry = ( BeanDefinitionRegistry ) applicationContext.getAutowireCapableBeanFactory();
		final ClassPathBeanDefinitionScanner scanner = new DwrClassPathBeanDefinitionScanner( beanDefinitionRegistry );
		scanner.addIncludeFilter( new AnnotationTypeFilter( DataTransferObject.class ) );
		scanner.scan( "br.com.agenda.*.domain" );

		return new DwrAnnotationPostProcessor();
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	public ServletRegistrationBean dwrSpringServletRegistration( DWRSettings dwrSettings )
	{
		final ServletRegistrationBean registration = new ServletRegistrationBean( new DwrSpringServlet(), "/broker/*" );
		registration.addInitParameter( "debug", String.valueOf( dwrSettings.isDebug() ) );
		registration.addInitParameter( "scriptCompressed", String.valueOf( dwrSettings.isScriptCompressed() ) );
		registration.addInitParameter( "crossDomainSessionSecurity", String.valueOf( dwrSettings.isCrossDomainSessionSecurity() ) );
		registration.setName( "dwrSpringServlet" );
		return registration;
	}

	/*-------------------------------------------------------------------
	 * 		 						OVERRIDES
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Override
	public void addInterceptors( InterceptorRegistry registry )
	{
		registry.addInterceptor( this.localeChangeInterceptor() );
	}
}
