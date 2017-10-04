package br.com.agenda.usuario.application.controller;


import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.agenda.usuario.domain.service.UsuarioService;

/**
 * 
 * @author rodrigo
 */
@Controller
public class NavigationController
{
	/*-------------------------------------------------------------------
	 * 		 				 		ATTRIBUTES
	 *-------------------------------------------------------------------*/

	/**
	 *
	 */
	@Autowired
	private UsuarioService usuarioService;
	
	/*-------------------------------------------------------------------
	 * 		 					CONTROLLERS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public ModelAndView home()
	{
		return new ModelAndView( "modules/usuario/index" );
	}
	
	/**
	 * 
	 */
	@RequestMapping(value = "/autenticacao")
	public ModelAndView authentication()
	{
		return new ModelAndView( "modules/autenticacao/index" );
	}

	/**
	 *
	 * @return
	 */
	@RequestMapping( value="/keep-alive", method=RequestMethod.GET )
	public @ResponseBody String keepAlive()
	{
		return "OK";
	}
}
