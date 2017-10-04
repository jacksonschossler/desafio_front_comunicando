package br.com.agenda.usuario.domain.service;

import java.time.LocalDateTime;
import java.util.Calendar;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import br.com.agenda.common.application.i18n.MessageSourceHolder;
import br.com.agenda.usuario.application.restful.IUsuarioResource;
import br.com.agenda.usuario.application.security.ContextHolder;
import br.com.agenda.usuario.domain.entity.Usuario;
import br.com.agenda.usuario.domain.repository.IAccountMailRepository;
import br.com.agenda.usuario.domain.repository.IUsuarioRepository;
import br.com.agenda.usuario.infrastructure.http.HTTPContextHolder;

/**
 * 
 * @author rodrigo@eits.com.br
 */
@Service
@RemoteProxy
@Transactional
public class UsuarioService implements IUsuarioResource
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
	 * Password encoder
	 */
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	//Repositories
	/**
	 * 
	 */
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IAccountMailRepository accountMailRepository;
	
	/**
	 * 
	 */
	@Autowired
	private MessageSource messageSource;
	
	/*-------------------------------------------------------------------
	 *				 		     SERVICES
	 *-------------------------------------------------------------------*/
	//--------------
	// Manutenção de usuário
	//--------------
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Override
//	@Cacheable("usuario")
	@Transactional(readOnly=true)
//	@PreAuthorize("hasAnyAuthority('" + PerfisVigentee.ADMINISTRADOR + ", " + PerfisVigisan.OPERACIONAL + ", " + PerfisVigiepi.OPERACIONAL + "')")
	public Usuario findUsuarioById( long id )
	{
		final Usuario user = this.usuarioRepository.findOne( id );
		Assert.notNull( user, MessageSourceHolder.getMessage("repository.notFoundById", id));
		return user;
	}
	
	/**
	 * 
	 * @param pageable
	 * @param filters
	 * @return
	 */
	@Override
	@Transactional(readOnly=true)
	public Page<Usuario> listUsuariosByFilters( String filter, PageRequest pageRequest )
	{
		return this.usuarioRepository.listByFilters( filter, pageRequest );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Usuario insertUsuario( Usuario usuario )
	{
		Assert.notNull( usuario, this.messageSource.getMessage( "usuario.null", null, LocaleContextHolder.getLocale() ) );
		Assert.notNull( usuario.getEmail(), this.messageSource.getMessage( "usuario.emailNull", null, LocaleContextHolder.getLocale() ) );

		usuario.setSenha( "tempPassword" );

		usuario = this.usuarioRepository.save( usuario );
		
		this.accountMailRepository.sendNewUserAccount( usuario, HTTPContextHolder.getServerURL(), LocaleContextHolder.getLocale() );
		
		return usuario;
	}
	
	/**
	 * Atualiza um registro de {@link Usuario}
	 * @param usuario
	 * @return usuario
	 */
	public Usuario updateUsuario( Usuario usuario )
	{
		Assert.notNull( usuario.getId(), this.messageSource.getMessage( "usuario.null", null, LocaleContextHolder.getLocale() ) );
		Assert.notNull( usuario.getEmail(), this.messageSource.getMessage( "usuario.emailNull", null, LocaleContextHolder.getLocale() ) );

		Usuario usuarioSaved = this.usuarioRepository.findOne( usuario.getId() );
		
		usuarioSaved.setEmail( usuario.getEmail() );
		
		return this.usuarioRepository.save( usuarioSaved );
	}

	/**
	 * Busca usuário por endereço de e-mail
	 */
	@Override
//	@Cacheable("usuario")
	@Transactional(readOnly=true)
	public Usuario findUsuarioByEmail( String email )
	{
		return this.usuarioRepository.findByEmail( email );
	}
	
	/**
	 * Ativa um usuário
	 */
	@Override
	public void activateUsuario( long id )
	{
	}

	/**
	 * Inativa um usuário
	 */
	@Override
	public void deactivateUsuario( long id )
	{
		final Usuario usuario = this.usuarioRepository.findByEmail( ContextHolder.getAuthenticatedUser().getEmail() );
		
		Assert.isTrue( usuario.getId().longValue() != id, this.messageSource.getMessage( "usuario.deactivateHimself" , null , LocaleContextHolder.getLocale() ) );
		
	}
}
