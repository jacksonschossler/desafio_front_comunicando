package br.com.agenda.categoria.application.restful;

import javax.print.attribute.standard.Media;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import br.com.agenda.categoria.domain.entity.Categoria;
import br.com.agenda.categoria.domain.entity.Tipo;


@Component
@Path("/categoria")
@FeignClient("categoria")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ICategoriaResource {

	@GET
	@Path("/{id}")
	public Categoria findCategoriaById( @PathParam("id") Long id);
	

	
	@GET
	@Path("/listCategoriaByFiltersDesativada/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Page<Categoria> listCategoriaByFiltersDesativada(@QueryParam("nome") String nome,
															@QueryParam("pageRequest")PageRequest pageRequest			
													);
		
}
