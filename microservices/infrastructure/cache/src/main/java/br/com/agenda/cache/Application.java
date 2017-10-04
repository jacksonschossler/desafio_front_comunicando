package br.com.agenda.cache;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.agenda.cache.configuration.settings.RedisServerSettings;
import redis.embedded.RedisServer;

/**
 * 
 * @author rodrigo.p.fraga
 */
@SpringBootApplication
public class Application
{
    /**
     * The log stream
     */
    private static final Logger LOG = Logger.getLogger(Application.class.getName());
    
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main( String[] args ) throws IOException
	{
		SpringApplication.run( Application.class, args );
	}
	
	/*-------------------------------------------------------------------
	 * 		 					  ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/*-------------------------------------------------------------------
	 * 		 					  BEANS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 * @return
	 */
	@Bean(destroyMethod="stop")
	public RedisServer redisServer( RedisServerSettings redisServerSettings )
	{
		final RedisServer redisServer = RedisServer.builder()
				.port( redisServerSettings.getPort() )
				//TODO Create a properties to set any settings similar to jpa.properties
				//.setting("daemonize no")
				//.setting("appendonly no")
				//.setting("maxheap 128M")
				  .build();
		
		LOG.info( "Starting redis server..." );
		
		try
		{
			redisServer.start();
			LOG.info( "Redis server started at port:"+redisServerSettings.getPort() );
		}
		catch ( Exception e )
		{
			LOG.severe( "Was not possible to start the redis server." );
			e.printStackTrace();
			System.exit( 0 );
		}
		
		return redisServer;
	}
}
