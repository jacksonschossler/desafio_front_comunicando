/**
 * 
 */
package br.com.agenda.common.application.aspect;

import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;

import org.apache.axis.AxisFault;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.SQLGrammarException;
import org.json.JSONException;
import org.json.JSONObject;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.expression.AccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import br.com.agenda.common.application.i18n.MessageSourceHolder;


/**
*
* @author Douglas Gatti
*
*/
@Aspect
@Component
public class ExceptionHandlerAspect
{
	/**
	 *
	 */
	private static final Logger LOG = Logger.getLogger( ExceptionHandlerAspect.class.getName() );

	/*-------------------------------------------------------------------
	 * 		 					ATTRIBUTES
	 *-------------------------------------------------------------------*/

	/*-------------------------------------------------------------------
	 * 		 					  ASPECTS
	 *-------------------------------------------------------------------*/
	//---------
	// Database
	//---------
	
	
	/**
	 * @param joinPoint
	 * @param exception
	 */
	@AfterThrowing(pointcut = "within(@org.springframework.stereotype.Service *)", throwing = "exception")
	public void handleException( JoinPoint joinPoint, org.springframework.dao.DuplicateKeyException exception )
	{
		throw new DuplicateKeyException( MessageSourceHolder.getMessage("repository.duplicatedKey") );
	}

	/**
	 * Trata exceções geradas pelo Hibernate antes de enviar para o banco
	 * @param joinPoint
	 * @param exception
	 */
	@AfterThrowing(pointcut = "within(@org.springframework.stereotype.Service *)", throwing = "exception")
	public void handleException( JoinPoint joinPoint, javax.validation.ConstraintViolationException exception )
	{
		final StringBuilder message = new StringBuilder();
		for ( ConstraintViolation<?> constraint : exception.getConstraintViolations() )
		{
			String annotationType = constraint.getConstraintDescriptor().getAnnotation().annotationType().getName();
			//Verifica o tipo da exceção
			if ( annotationType.equals( "javax.validation.constraints.NotNull" ) || annotationType.equals( "org.hibernate.validator.constraints.NotEmpty" ))
			{
				throw new ValidationException( MessageSourceHolder.getMessage( "repository.fieldMustbeSet", new String[]{constraint.getPropertyPath().toString()} ) );
			} 
			else
			{
				message.append( "\n" + constraint.getMessage() );
			}
		}

		throw new ValidationException( message.toString() );
	}


	/**
	 *
	 * @param joinPoint
	 * @param exception
	 */
	@AfterThrowing(pointcut = "within(@org.springframework.stereotype.Service *)", throwing = "exception")
	public void handleException( JoinPoint joinPoint, org.springframework.dao.EmptyResultDataAccessException exception )
	{
		throw new EmptyResultDataAccessException( MessageSourceHolder.getMessage("repository.emptyResult"), 1);
	}

	/**
	 * Trata exceções de Constraint geradas pelo PostgreSQL
	 * @param joinPoint
	 * @param exception
	 */
	@AfterThrowing(pointcut = "within(@org.springframework.stereotype.Service *)", throwing = "exception")
	public void handleException( JoinPoint joinPoint, org.springframework.dao.DataIntegrityViolationException exception )
	{
		//Caso a exceção já tenha sido interceptada por outro Aspecto deve ser ignorada
		if ( exception.getStackTrace()[0].toString().contains( "ExceptionHandlerAspect" ) || exception.getCause() == null )
		{
			return;
		}

		if ( exception.getCause() instanceof ConstraintViolationException )
		{
			final ConstraintViolationException cause = (ConstraintViolationException) exception.getCause();
			final PSQLException sqlException = (PSQLException) cause.getSQLException();

			final String message = sqlException.getServerErrorMessage().getDetail();

			String key;
			//Verifica o código do erro gerado pelo PostgreSQL
			switch ( cause.getSQLState() )
			{
				case "23503": // foreign_key_violation
				{
					key = message.substring(message.indexOf('"') + 1, message.indexOf('.') - 1);
					throw new DataIntegrityViolationException( MessageSourceHolder.getMessage( "repository.foreignKeyViolation", new String[]{key} ) );
				}
				case "23505": // unique_violation
				{
					key = message.substring( message.indexOf('(') + 1, message.indexOf(')') );
					if ( key.startsWith( "lower(" ) )
					{
						key = key.replace( "lower(", "" );
						key = key.replace( "::text", "" );
					}
					throw new DataIntegrityViolationException( MessageSourceHolder.getMessage( "repository.uniqueViolation", new String[]{key} ) );
				}
				case "23502": // not_null_violation
				{
					LOG.info(message);
					LOG.info("Not null violation.");
				}
				default:
				{
					throw new DataIntegrityViolationException( MessageSourceHolder.getMessage("repository.uniqueViolation", new String[]{cause.getSQLState()} ) );
				}
			}
		}

		throw new DataIntegrityViolationException( MessageSourceHolder.getMessage("repository.dataIntegrityViolation" ) );
	}
	
	
	/**
	 * Trata exceções geradas pelo PostgreSQL que indicam que o SQL enviado ao banco de dados é inválido
	 * @param joinPoint
	 * @param exception
	 */
	@AfterThrowing(pointcut = "within(@org.springframework.stereotype.Service *)", throwing = "exception")
	public void handleException( JoinPoint joinPoint, org.springframework.dao.InvalidDataAccessResourceUsageException exception )
	{
		//Caso a exceção já tenha sido interceptada por outro Aspecto deve ser ignorada
		if ( exception.getStackTrace()[0].toString().contains( "ExceptionHandlerAspect" ) || exception.getCause() == null )
		{
			return;
		}

		if ( exception.getCause() instanceof SQLGrammarException )
		{
			final SQLGrammarException cause = (SQLGrammarException) exception.getCause();

			//Verifica o código do erro gerado pelo PostgreSQL
			switch ( cause.getSQLState() )
			{
				case "42P01": // relation does not exist
				{
					throw new InvalidDataAccessResourceUsageException( MessageSourceHolder.getMessage( "repository.relationDoesNotExist", new String[]{} ) );
				}
				default:
				{
					throw new InvalidDataAccessResourceUsageException( MessageSourceHolder.getMessage("repository.relationDoesNotExist", new String[]{cause.getSQLState()} ) );
				}
			}
		}

		throw new InvalidDataAccessResourceUsageException( MessageSourceHolder.getMessage( "repository.relationDoesNotExist" ) );
	}

	//---------
	// Segurança
	//---------
	/**
	 * Trata exceções de acesso negado
	 * @param joinPoint
	 * @param exception
	 */
	@AfterThrowing(pointcut = "within(@org.springframework.stereotype.Service *)", throwing = "exception")
	public void handleException( JoinPoint joinPoint, org.springframework.security.access.AccessDeniedException exception )
	{
		throw new AccessDeniedException( MessageSourceHolder.getMessage( "security.accessDenied"  ) );
	}
	
	
	//---------
	// Resources de outros Micro-services
	//---------
	/**
	 * Trata exceções de acesso a resources de outros microserviços
	 * @param joinPoint
	 * @param exception
	 * @throws AccessException 
	 * @throws JSONException 
	 */
	@AfterThrowing(pointcut = "within(@org.springframework.stereotype.Service *) ", throwing = "exception")
	public void handleException( JoinPoint joinPoint, java.lang.RuntimeException  exception ) throws AccessException, JSONException
	{
		String exceptionString = exception.getStackTrace()[0].toString();
		if( exceptionString.contains( "org.springframework.cloud.netflix.feign.ribbon.LoadBalancerFeignClient" ) )
		{
			String microService = exception.getMessage().toString()
					.substring( 
							exception.getMessage().toString().lastIndexOf( ':'),  
							exception.getMessage().toString().length()  
						);			
			
			throw new InvalidDataAccessApiUsageException( MessageSourceHolder.getMessage( "client.AccessException", new String[]{ microService } )  );		
		} 
		else if ( exceptionString.contains( "feign.FeignException" ) )
		{
			if ( exception.getMessage().startsWith( "status 500" ) )
			{
				String json = exception.getMessage().substring( exception.getMessage().indexOf( '{' ) );
				JSONObject obj = new JSONObject( json );
				
				String message = obj.getString( "message" );
				
				throw new RuntimeException( message );
			}
		}
	}
	
	/**
	 * Trata de erros no envio de e-mail
	 * @param joinPoint
	 * @param exception
	 */
	@AfterThrowing(pointcut = "within(@org.springframework.stereotype.Service *) ", throwing = "exception")
	public void handleException( JoinPoint joinPoint, org.springframework.mail.MailSendException  exception )
	{
		String message = exception.getMessage().toString()
				.substring( 
						exception.getMessage().toString().lastIndexOf( ':' ) + 1,  
						exception.getMessage().toString().length()  
					);			
		
		throw new InvalidDataAccessApiUsageException( message );
	}
	
	/**
	 * Trata erros de tentativa de acesso ao webservice sem internet
	 * @param joinPoint
	 * @param exception
	 * @throws AxisFault 
	 */
	@AfterThrowing(pointcut = "within(@org.springframework.stereotype.Service *) ", throwing = "exception")
	public void handleException( JoinPoint joinPoint, org.apache.axis.AxisFault exception ) throws AxisFault
	{
		throw new AxisFault( MessageSourceHolder.getMessage( "axisFault.notConnectedException" ) );
	}
	
}
