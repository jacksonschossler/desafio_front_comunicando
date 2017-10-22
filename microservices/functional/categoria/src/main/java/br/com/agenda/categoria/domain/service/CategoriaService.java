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

@Service
@RemoteProxy
@Transactional
public class CategoriaService implements ICategoriaResource {

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/

	/**
	 * Repositorio de Categoria
	 */
	@Autowired
	private ICategoriaRepository categoriaRepository;

	/**
	 * Resource de Registro
	 */
	@Autowired
	private IRegistroResource registroResource;

	/**
	 * messageSource
	 */
	@Autowired
	private MessageSource messageSource;

	/*-------------------------------------------------------------------
	 *				 		     SERVICES
	 *-------------------------------------------------------------------*/

	/**
	 * Serviço para buscar uma categoria
	 * 
	 * @param id
	 *            return categoria
	 */
	@Override
	@Transactional(readOnly = true)
	public Categoria findCategoriaById(Long id) {
		final Categoria categoria = this.categoriaRepository.findOne(id);
		Assert.notNull(categoria, MessageSourceHolder.getMessage("repository.notFoundById", id));

		return categoria;
	}

	/**
	 * Serviço para retornar uma lista de categorias
	 * 
	 * @param nome
	 * @param pageRequest
	 *            return categorias
	 */
	public Page<Categoria> listCategoriaByFilters(String nome, PageRequest pageRequest) {
		return this.categoriaRepository.listByFilters(nome, pageRequest);
	}

	/**
	 * Serviço para retornar uma lista de categorias com filtros especificos
	 * 
	 * @param tipo
	 * @param nome
	 * @param descricao
	 * @param pageRequest
	 *            return categorias
	 */
	public Page<Categoria> listCategoriaByFiltersFull(Tipo tipo, String nome, String descricao,
			PageRequest pageRequest) {
		return this.categoriaRepository.listByFiltersFull(tipo, nome, descricao, pageRequest);
	}

	/**
	 * Serviço que retorna uma lista de categorias desativadas
	 * 
	 * @param nome
	 * @param pageRequest
	 *            return categorias
	 */
	@Override
	public Page<Categoria> listCategoriaByFiltersDesativada(String nome, PageRequest pageRequest) {
		return this.categoriaRepository.listByFiltersDesativada(nome, pageRequest);
	}

	/**
	 * Serviço para inserir uma categoria
	 * 
	 * @param categoria
	 *            return categoria
	 */
	public Categoria insertCategoria(Categoria categoria) {
		Assert.notNull(categoria,
				this.messageSource.getMessage("categoria.null", null, LocaleContextHolder.getLocale()));
		categoria.setDesativada(false);
		categoria = this.categoriaRepository.save(categoria);

		return categoria;
	}

	/**
	 * Serviço para remover uma categoria
	 * 
	 * @param id
	 *            return void
	 */
	public void removeCategoria(Long id) {
		Assert.notNull(id, "Não encontrou ID para remover");
		Categoria categoria = this.findCategoriaById(id);

		Assert.isTrue(!this.registroResource.verificaCategoriaAssociada(id),
				"Não pode deletar, categoria associada à um ou mais registrosRemove");
		this.categoriaRepository.delete(categoria);

	}

	/**
	 * Serviço para atualizar categoria
	 * 
	 * @param categoria
	 *            return categoria
	 */
	public Categoria updateCategoria(Categoria categoria) {
		Assert.notNull(categoria.getId(),
				this.messageSource.getMessage("categoria.null", null, LocaleContextHolder.getLocale()));
		Assert.notNull(categoria.getTipo(),
				this.messageSource.getMessage("categoria.tipoNull", null, LocaleContextHolder.getLocale()));
		Assert.notNull(categoria.getNome(),
				this.messageSource.getMessage("categoria.nomeNull", null, LocaleContextHolder.getLocale()));

		Categoria categoriaSaved = this.categoriaRepository.findOne(categoria.getId());

		categoriaSaved.setTipo(categoria.getTipo());
		categoriaSaved.setNome(categoria.getNome());
		categoriaSaved.setDescricao(categoria.getDescricao());
		categoriaSaved.setDesativada(categoria.getDesativada());

		return this.categoriaRepository.save(categoriaSaved);
	}

	/**
	 * Serviço para desativar categoria
	 * 
	 * @param id
	 *            return categoria
	 */
	public Categoria updateCategoriaToDesativada(Long id) {
		Assert.notNull(id, this.messageSource.getMessage("categoria.null", null, LocaleContextHolder.getLocale()));
		Categoria categoriaSaved = categoriaRepository.findOne(id);
		categoriaSaved.setDesativada(!categoriaSaved.getDesativada());
		return this.categoriaRepository.save(categoriaSaved);
	}

	/**
	 * Serviço para ativar categoria
	 * 
	 * @param id
	 *            return categoria
	 */
	public Categoria updateCategoriaToAtivada(Long id) {
		Assert.notNull(id, this.messageSource.getMessage("categoria.null", null, LocaleContextHolder.getLocale()));
		Categoria categoriaSaved = categoriaRepository.findOne(id);
		Assert.isTrue(categoriaSaved.getDesativada(), "a categoria já esta ativada");
		categoriaSaved.setDesativada(false);
		return this.categoriaRepository.save(categoriaSaved);
	}

}
