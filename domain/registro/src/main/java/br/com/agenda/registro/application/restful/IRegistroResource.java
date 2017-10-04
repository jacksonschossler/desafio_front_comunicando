package br.com.agenda.registro.application.restful;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.agenda.registro.domain.entity.Registro;

@Component
@Path("/registro")
@FeignClient("registro")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface IRegistroResource {
	
	
	@GET
	@Path("/{id}")
	public Registro findRegistroById( @PathParam("id") Long id);
	
	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Registro insertRegistro(Registro registro);
	
	@GET
	@Path("/verifica/{id}")
	public Boolean verificaCategoriaAssociada( @PathParam("id") Long id);
	
	//listCategoriaById VAI SER CHAMADO VIA REST
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public Page<Registro> listRegistroById( @QueryParam("mes") Integer mes,
											@QueryParam("ano") Integer ano,
											@QueryParam("categoria") Long categoria,
											PageRequest pageRequest
											);
	
	
	@DELETE
	@Path("/{id}")
	public void removeRegistro(@PathParam("id") Long id);
	
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Registro updateRegistro(Registro registro);
	
	
	
}
