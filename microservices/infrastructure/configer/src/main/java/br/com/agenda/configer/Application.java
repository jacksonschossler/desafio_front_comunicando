package br.com.agenda.configer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.core.env.AbstractEnvironment;

/**
 * 
 * @author rodrigo.p.fraga
 */
@EnableConfigServer
@SpringBootApplication
public class Application
{
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		SpringApplication.run( Application.class, args );
	}
}
