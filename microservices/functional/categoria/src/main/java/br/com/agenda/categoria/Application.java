package br.com.agenda.categoria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import br.com.agenda.common.CommonConfiguration;
import br.com.agenda.common.domain.entity.IEntity;
import br.com.agenda.registro.application.restful.IRegistroResource;

/**
 * 
 * @author rodrigo.p.fraga
 */
@EnableFeignClients(clients= {
		IRegistroResource.class
})
@EnableDiscoveryClient
@EntityScan(basePackageClasses = {
	IEntity.class,
	Application.class
})
@SpringBootApplication(scanBasePackageClasses = {
	CommonConfiguration.class,
	Application.class
})

public class Application
{
	/**
	 * 
	 * @param args
	 */
	public static void main( String[] args )
	{
		SpringApplication.run( Application.class, args );
	}
	/*-------------------------------------------------------------------
	 * 		 					  BEANS
	 *-------------------------------------------------------------------*/
}
