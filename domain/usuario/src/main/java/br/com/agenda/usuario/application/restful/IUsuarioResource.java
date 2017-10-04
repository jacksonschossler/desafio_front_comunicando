package br.com.agenda.usuario.application.restful;

import javax.ws.rs.Consumes;
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

import br.com.agenda.usuario.domain.entity.Usuario;

/**
 * @author rodrigo.p.fraga
 */
@Component
@Path("/usuarios")
@FeignClient("usuario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface IUsuarioResource
{
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{id}")
	public Usuario findUsuarioById( @PathParam("id") long id );
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	@GET
	@Path("/by-email/{email}")
	public Usuario findUsuarioByEmail( @PathParam("email") String email );

	
	/**
	 * 
	 * @param usuario
	 * @return
	 */
	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Usuario insertUsuario( Usuario usuario );
	
	/**
	 * 
	 * @param usuario
	 * @return
	 */
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Usuario updateUsuario( Usuario usuario );
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/activate/{id}")
	public void activateUsuario( @PathParam("id") long id );
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/deactivate/{id}")
	public void deactivateUsuario( @PathParam("id") long id );
	
	/**
	 * 
	 * @param filter
	 * @param pageable
	 * @return
	 */
	@GET
	public Page<Usuario> listUsuariosByFilters( @QueryParam("filter") String filter, @QueryParam("pageRequest") PageRequest pageRequest );
}
