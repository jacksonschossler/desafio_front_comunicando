package br.com.agenda.common.application.security;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

/**
 * 
 * @author rodrigo@eits.com.br
 */
@Component
public class AuthenticationSuccessHandler  implements org.springframework.security.web.authentication.AuthenticationSuccessHandler
{
	/**
	 * 
	 */
	private static final Logger LOG = Logger.getLogger( AuthenticationSuccessHandler.class.getName() );
	
	/*-------------------------------------------------------------------
	 * 		 					 ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	protected Log logger = LogFactory.getLog(this.getClass());
	 
	private RequestCache requestCache = new HttpSessionRequestCache();
 
	/*-------------------------------------------------------------------
	 * 		 					BEHAVIORS
	 *-------------------------------------------------------------------*/
	
	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	@Override
	public void onAuthenticationSuccess( HttpServletRequest request, HttpServletResponse response, Authentication authentication ) throws IOException, ServletException
	{
		LOG.fine( "onAuthenticationSuccess :: "+authentication );
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);

		// Use the DefaultSavedRequest URL
		if ( savedRequest != null )
		{
			String targetUrl = savedRequest.getRedirectUrl();
			response.getWriter().print( ( targetUrl != null && targetUrl.indexOf( "client_id=" ) > 1 ) ? targetUrl.substring( targetUrl.indexOf( "client_id=" ) + 10, targetUrl.length() ) : "" );
		}
		else
		{
			response.getWriter().print( "" );
		}
	}
}
