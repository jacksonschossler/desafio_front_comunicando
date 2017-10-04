package br.com.agenda.usuario.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.agenda.usuario.domain.entity.Usuario;

/**
 * 
 * @author rodrigo@eits.com.br 
 * @since 22/04/2014
 * @version 1.0
 */
public interface IUsuarioRepository extends JpaRepository<Usuario, Long>
{
	/**
	 * @param filter
	 * @param pageable
	 * @return
	 */
	@Query(value="FROM Usuario usuario " +
				  "WHERE ( FILTER(usuario.id, :filter) = TRUE "
				  	 + "OR FILTER(usuario.nome, :filter) = TRUE "
				  	 + "OR FILTER(usuario.email, :filter) = TRUE)" )
	public Page<Usuario> listByFilters( @Param("filter") String filter, Pageable pageable );
	
	/**
	 * @param login
	 * @return
	 */
	public Usuario findByEmail( String email );
}
