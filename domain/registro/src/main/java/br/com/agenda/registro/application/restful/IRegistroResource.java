package br.com.agenda.registro.application.restful;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;


@Component
@Path("/registro")
@FeignClient("registro")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface IRegistroResource {
	
	


	@GET
	@Path("/verifica/{id}")
	public Boolean verificaCategoriaAssociada( @PathParam("id") Long id);
	
	
}
