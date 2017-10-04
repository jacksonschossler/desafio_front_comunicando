package br.com.agenda.usuario.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.agenda.common.application.i18n.MessageSourceHolder;
import br.com.agenda.usuario.application.security.ContextHolder;
import br.com.agenda.usuario.domain.entity.Usuario;
import br.com.agenda.usuario.domain.repository.IUsuarioRepository;

/**
 * 
 * @author rodrigo@eits.com.br 
 */
@Service
@Transactional
public class AutenticacaoService implements UserDetailsService
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Autowired
	private IUsuarioRepository usuarioRepository;  
	
	/*-------------------------------------------------------------------
	 *				 		     BEHAVIORS
	 *-------------------------------------------------------------------*/
	/**
	 *
	 * @return
	 */
	@Transactional(readOnly=true)
	public Usuario findPrincipal()
	{
		return this.usuarioRepository.findOne( ContextHolder.getAuthenticatedUser().getId() );
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public Usuario loadUserByUsername( String email ) throws UsernameNotFoundException
	{
		try
		{
			final Usuario usuario = this.usuarioRepository.findByEmail( email );
			Assert.notNull( usuario, MessageSourceHolder.getMessage( "repository.userNotFoundByLogin" ) );
			return usuario;
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			throw new UsernameNotFoundException( MessageSourceHolder.getMessage( "repository.userNotFoundByLogin" ), e );
		}
	}
}