package br.com.agenda.usuario.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.agenda.usuario.domain.AbstractIntegrationTests;
import br.com.agenda.usuario.domain.entity.Usuario;

/**
 * 
 * @author rodrigo@eits.com.br
 */
public class UsuarioServiceIntegrationTests extends AbstractIntegrationTests
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Autowired
	private UsuarioService usuarioService;
	
	/*-------------------------------------------------------------------
	 *				 		      TESTS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@Sql({
		"/dataset/usuario/usuarios.sql"
	})
	public void findUsuarioByIdMustFail()
	{
		this.usuarioService.findUsuarioById( 9999L );
	
		Assert.fail( "Should not find a user with id 9999" );
	}
}
