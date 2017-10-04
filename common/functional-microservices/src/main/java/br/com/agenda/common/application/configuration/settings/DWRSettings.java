package br.com.agenda.common.application.configuration.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * @author rodrigo
 */
@Component
@ConfigurationProperties(prefix="dwr")
public class DWRSettings 
{
	/*-------------------------------------------------------------------
	 * 		 					SETTINGS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
    private boolean debug = false;
    /**
     * 
     */
    private boolean scriptCompressed = true;
    /**
     * 
     */
    private boolean crossDomainSessionSecurity = true;
    
	/*-------------------------------------------------------------------
	 * 		 				 GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	/**
	 * @return the debug
	 */
	public boolean isDebug()
	{
		return debug;
	}
	/**
	 * @param debug the debug to set
	 */
	public void setDebug( boolean debug )
	{
		this.debug = debug;
	}
	/**
	 * @return the scriptCompressed
	 */
	public boolean isScriptCompressed()
	{
		return scriptCompressed;
	}
	/**
	 * @param scriptCompressed the scriptCompressed to set
	 */
	public void setScriptCompressed( boolean scriptCompressed )
	{
		this.scriptCompressed = scriptCompressed;
	}
	/**
	 * @return the crossDomainSessionSecurity
	 */
	public boolean isCrossDomainSessionSecurity()
	{
		return crossDomainSessionSecurity;
	}
	/**
	 * @param crossDomainSessionSecurity the crossDomainSessionSecurity to set
	 */
	public void setCrossDomainSessionSecurity( boolean crossDomainSessionSecurity )
	{
		this.crossDomainSessionSecurity = crossDomainSessionSecurity;
	}
}