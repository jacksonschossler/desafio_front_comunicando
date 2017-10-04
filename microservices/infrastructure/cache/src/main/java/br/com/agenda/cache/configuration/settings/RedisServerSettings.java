package br.com.agenda.cache.configuration.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * @author rodrigo
 */
@Component
@ConfigurationProperties(prefix="redis.server")
public class RedisServerSettings
{
	/*-------------------------------------------------------------------
	 * 		 					SETTINGS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
    private int port = 6379;
    
	/*-------------------------------------------------------------------
	 * 		 				 GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
    /**
	 * @return the port
	 */
	public int getPort()
	{
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort( int port )
	{
		this.port = port;
	}
}