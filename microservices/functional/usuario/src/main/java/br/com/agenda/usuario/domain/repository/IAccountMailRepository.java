package br.com.agenda.usuario.domain.repository;
import java.util.Locale;
import java.util.concurrent.Future;

import br.com.agenda.usuario.domain.entity.Usuario;

/**
 * Interface para o envio de e-mails
 *
 * @author rodrigo@eits.com.br
 * @since 02/10/2014
 * @version 1.0
 */
public interface IAccountMailRepository
{
    /*-------------------------------------------------------------------
     *                          BEHAVIORS
     *-------------------------------------------------------------------*/
    /**
     * 
     * @param usuario
     * @param context
     * @param locale
     * @return
     */
    public Future<Void> sendNewUserAccount( Usuario usuario,  String context, Locale locale );
    
    /**
     * 
     * @param user
     * @param context
     * @param locale
     * @return
     */
    public Future<Void> sendAccountRecoveryCodePassword( Usuario usuario, String context, Locale locale );
}