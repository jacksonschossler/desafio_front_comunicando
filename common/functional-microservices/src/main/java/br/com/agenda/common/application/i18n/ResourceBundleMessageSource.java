package br.com.agenda.common.application.i18n;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import java.util.Locale;
import java.util.Properties;

/**
 * 
 * @author rodrigo
 */
public class ResourceBundleMessageSource extends ReloadableResourceBundleMessageSource
{
	/*-------------------------------------------------------------------
	 * 		 					CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	public ResourceBundleMessageSource()
	{
		MessageSourceHolder.setMessageSource( this );
	}

	/*-------------------------------------------------------------------
	 * 		 					BEHAVIORS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 * @param locale idioma para obter os bundles
	 * @return mapa das chaves e valores dos idiomas
	 */
	public Properties getProperties( Locale locale ) 
	{
        super.clearCacheIncludingAncestors();
        
        final PropertiesHolder propertiesHolder = super.getMergedProperties(locale);
        return propertiesHolder.getProperties();
    }
}