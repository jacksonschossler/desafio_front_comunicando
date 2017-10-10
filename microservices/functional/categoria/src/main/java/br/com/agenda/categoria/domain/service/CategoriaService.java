package br.com.agenda.categoria.domain.service;

import javax.validation.constraints.AssertFalse;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.agenda.categoria.application.restful.ICategoriaResource;
import br.com.agenda.categoria.domain.entity.Categoria;
import br.com.agenda.categoria.domain.entity.Tipo;
import br.com.agenda.categoria.domain.repository.ICategoriaRepository;
import br.com.agenda.common.application.i18n.MessageSourceHolder;
import br.com.agenda.registro.application.restful.IRegistroResource;
import br.com.agenda.registro.domain.entity.Registro;
import br.com.agenda.categoria.domain.entity.Categoria;
//import br.com.agenda.registro.application.restful.IRegistroResource;


@Service
@RemoteProxy
@Transactional
public class CategoriaService implements ICategoriaResource{
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	

	@Autowired
	private ICategoriaRepository categoriaRepository;
	
	@Autowired
	private IRegistroResource registroResource;
	
	@Autowired
	private MessageSource messageSource;
	
	/*-------------------------------------------------------------------
	 *				 		     SERVICES
	 *-------------------------------------------------------------------*/
	
	
	
	
	@Override
	@Transactional(readOnly=true)
	public Categoria findCategoriaById(Long id) {
		final Categoria categoria = this.categoriaRepository.findOne(id);
		Assert.notNull(categoria, MessageSourceHolder.getMessage("repository.notFoundById", id));
		
		return categoria;
	}
	
	
//	public Page<Categoria> listCategoriaByFilters(Tipo tipo, String nome, String descricao, 
//			Boolean desativada, PageRequest pageRequest)
//	{
//		return this.categoriaRepository.listByFilters(tipo, nome, descricao, desativada, pageRequest);
//		
//		
//	}
	
	public Page<Categoria> listCategoriaByFilters(String nome, PageRequest pageRequest)
	{
		return this.categoriaRepository.listByFilters(nome, pageRequest);
	}
	
	
	public Page<Categoria> listCategoriaByFiltersFull(Tipo tipo, String nome,String descricao, PageRequest pageRequest)
	{
		return this.categoriaRepository.listByFiltersFull(tipo, nome, descricao, pageRequest);
		
		
	}
	
	@Override
	public Page<Categoria>listCategoriaByFiltersDesativada(String nome, PageRequest pageRequest)
	{
		return this.categoriaRepository.listByFiltersDesativada(nome, pageRequest);
	}
	
	
	//insertCategoria(Categoria):Categoria]
	
	public Categoria insertCategoria(Categoria categoria) {
		Assert.notNull(categoria, this.messageSource.getMessage("categoria.null", null, LocaleContextHolder.getLocale() ));
		categoria.setDesativada(false);
		categoria = this.categoriaRepository.save(categoria);
		
		return categoria;
	}
	
	
	//removeCategoria(long):void
	public void removeCategoria(Long id) {
		Assert.notNull(id, "Não encontrou ID para remover");
		Categoria categoria = this.findCategoriaById(id);
		
		//this.registroResource.verificaCategoriaAssociada(id);
		Assert.isTrue(this.registroResource.verificaCategoriaAssociada(id), "Não pode deletar, categoria associada à um ou mais registros");
		this.categoriaRepository.delete(categoria);
//		if (!registroResource.verificaCategoriaAssociada(id)) {
//			Categoria categoria = this.categoriaRepository.findOne(id);
//			this.categoriaRepository.delete(categoria);
//		} 

		
	}
	
	//updateCategoria(Categoria):Categoria	
	public Categoria updateCategoria( Categoria categoria )
	{
		Assert.notNull( categoria.getId(), this.messageSource.getMessage( "categoria.null", null, LocaleContextHolder.getLocale() ) );
		Assert.notNull( categoria.getTipo(), this.messageSource.getMessage( "categoria.tipoNull", null, LocaleContextHolder.getLocale() ) );
		Assert.notNull( categoria.getNome(), this.messageSource.getMessage( "categoria.nomeNull", null, LocaleContextHolder.getLocale() ) );
		
		Categoria categoriaSaved = this.categoriaRepository.findOne(categoria.getId());
		
		categoriaSaved.setTipo(categoria.getTipo());
		categoriaSaved.setNome(categoria.getNome());
		categoriaSaved.setDescricao(categoria.getDescricao());
		categoriaSaved.setDesativada(categoria.getDesativada());

		
		return this.categoriaRepository.save( categoriaSaved );
	}
	
	
	public Categoria updateCategoriaToDesativada (Long id) {
		Assert.notNull( id , this.messageSource.getMessage( "categoria.null", null, LocaleContextHolder.getLocale() ) );
		Categoria categoriaSaved = categoriaRepository.findOne(id);
		//Assert.isTrue(!categoriaSaved.getDesativada(),"a categoria já esta desativada");
		categoriaSaved.setDesativada(!categoriaSaved.getDesativada());
		
		return this.categoriaRepository.save(categoriaSaved);
	}
	
	
	//updateCategoriaToAtivada(Categoria):Categoria
	
//	public Categoria updateCategoriaToAtivada (Categoria categoria) {
//		Assert.notNull( categoria.getId(), this.messageSource.getMessage( "categoria.null", null, LocaleContextHolder.getLocale() ) );
//		categoria.setDesativada(false);
//		return this.categoriaRepository.save(categoria);
//	}
	
	public Categoria updateCategoriaToAtivada (Long id) {
		Assert.notNull( id, this.messageSource.getMessage( "categoria.null", null, LocaleContextHolder.getLocale() ) );
		Categoria categoriaSaved = categoriaRepository.findOne(id);
		Assert.isTrue(categoriaSaved.getDesativada(),"a categoria já esta ativada");
		categoriaSaved.setDesativada(false);
		return this.categoriaRepository.save(categoriaSaved);
	}	
	
	
}
