/**
 * 
 */
package br.com.agenda.common.application.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import br.com.agenda.common.application.i18n.MessageSourceHolder;

/**
 * @author thiag
 *
 */
@Component
public class AuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler
{
	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void onAuthenticationFailure( HttpServletRequest request, HttpServletResponse response, AuthenticationException exception ) throws IOException, ServletException
	{
		if ( exception instanceof BadCredentialsException )
		{
			//response.setContentType( "text/plain; charset=iso-8859-1" );
			response.sendError( HttpServletResponse.SC_UNAUTHORIZED, MessageSourceHolder.getMessage( "credenciaisInvalidas") );
		}
	}
}
