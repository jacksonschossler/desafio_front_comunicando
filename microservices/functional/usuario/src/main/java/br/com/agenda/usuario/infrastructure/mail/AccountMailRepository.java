package br.com.agenda.usuario.infrastructure.mail;

import java.util.Locale;
import java.util.concurrent.Future;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.agenda.usuario.domain.entity.Usuario;
import br.com.agenda.usuario.domain.repository.IAccountMailRepository;

/**
 * @author rodrigo@eits.com.br
 * @since 03/08/2012
 * @version 1.0
 */
@Component
public class AccountMailRepository implements IAccountMailRepository
{
	/*-------------------------------------------------------------------
	 *                          ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
     *
     */
	@Autowired
	private JavaMailSender mailSender;
	/**
     *
     */
	@Autowired
	private TemplateEngine templateEngine;
	/**
     *
     */
	@Value("${spring.mail.from}")
	private String mailFrom;
	
	/**
	 * 
	 */
	@Value("${url.context}")
	private String urlContext;
	
	/**
	 * 
	 */
	@Autowired
	private MessageSource messageSource;

	/*-------------------------------------------------------------------
	 *                          BEHAVIORS
	 *-------------------------------------------------------------------*/
	/**
	 *
	 * @param user
	 */
	@Async
	@Override
	public Future<Void> sendNewUserAccount( final Usuario usuario, String urlContextApp, Locale locale )
	{
		return new AsyncResult<Void>( null );
	}
	
	/**
    *
    * @param user
    */
   @Async
   @Override
   public Future<Void> sendAccountRecoveryCodePassword( final Usuario usuario, final String urlContext, final Locale locale )
   {
		return new AsyncResult<Void>( null );
   }
}