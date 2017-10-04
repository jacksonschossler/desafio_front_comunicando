/**
 * 
 */
package br.com.agenda.common.application.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * @author thiago
 *
 */
@Component
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler 
{
	   @Override
	   public void onLogoutSuccess( HttpServletRequest request, HttpServletResponse response, Authentication authentication ) throws IOException, ServletException 
	   {

		   //perform other required operation
		   String URL = request.getContextPath() + "/../usuario/logout";
		   response.setStatus( HttpStatus.OK.value() );
		   response.sendRedirect( URL );
		   response.getWriter().flush();	   
	       
		   // do whatever you want
	       super.onLogoutSuccess( request, response, authentication );
	   }
}
