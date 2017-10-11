package br.com.agenda.registro.domain.service;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.Assert;
import org.junit.Assert;

import br.com.agenda.categoria.application.restful.ICategoriaResource;
import br.com.agenda.categoria.domain.entity.Categoria;
import br.com.agenda.common.application.i18n.MessageSourceHolder;
import br.com.agenda.registro.application.restful.IRegistroResource;
import br.com.agenda.registro.domain.entity.Registro;
import br.com.agenda.registro.domain.repository.IRegistroRepository;

@Service
@RemoteProxy
@Transactional
public class RegistroService implements IRegistroResource {

	@Autowired
	private ICategoriaResource categoriaResource;
	
	@Autowired
	private IRegistroRepository registroRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public Registro insertRegistro(Registro registro) {
		Assert.assertNotNull(this.messageSource.getMessage("registro.null",null,LocaleContextHolder.getLocale()), registro );
//		if (verificaCategoriaAssociada(19L)) {
//			registro = this.registroRepository.save(registro);
//		}
		registro = this.registroRepository.save(registro);
		return registro;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Registro findRegistroById(Long id) {
		final Registro registro = this.registroRepository.findOne(id);
		Assert.assertNotNull(MessageSourceHolder.getMessage("repository.notFoundById", id),registro );
		registro.setCategoria(this.categoriaResource.findCategoriaById(registro.getCategoria().getId()));

		return registro;
	}

	
	
	
	public Page<Categoria> listCategoriaByFiltersDesativada(String nome, PageRequest pageRequest){
		Page<Categoria> cat = this.categoriaResource.listCategoriaByFiltersDesativada(nome, pageRequest);
		return cat;
		}
	
	
	

	
	

	@Override
	public Page<Registro> listRegistroById(Integer mes, Integer ano, Long categoria, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		//final Categoria categoriaSet = this.categoriaResource.findCategoriaById(categoria);
		Page<Registro> registros = this.registroRepository.listByFilters(mes, ano,  categoria, pageRequest);
		for (Registro registro: registros.getContent()) {
			registro.setCategoria(this.categoriaResource.findCategoriaById(registro.getCategoria().getId()));
		}
		return registros;
	}

	@Override
	public void removeRegistro(Long id) {
		Assert.assertNotNull("ID DO REGISTRO NÃO ENCONTRADO", id);
		Registro registro = this.registroRepository.findOne(id);
		
		this.registroRepository.delete(registro);
		
	}

	@Override
	public Registro updateRegistro(Registro registro) {
		Assert.assertNotNull( this.messageSource.getMessage( "registroId.null", null, LocaleContextHolder.getLocale() ),registro.getId() );
		Assert.assertNotNull( this.messageSource.getMessage( "registro.tipoNull", null, LocaleContextHolder.getLocale() ) , registro.getTipo());
		Assert.assertNotNull(  this.messageSource.getMessage( "registro.categoriaNull", null, LocaleContextHolder.getLocale() ) ,registro.getCategoria());
		Assert.assertNotNull( this.messageSource.getMessage( "registro.dataNull", null, LocaleContextHolder.getLocale() ),registro.getData() );
		Assert.assertNotNull(this.messageSource.getMessage( "registro.valorNull", null, LocaleContextHolder.getLocale() ) , registro.getValor());

		Registro registroSaved = this.registroRepository.findOne(registro.getId());
		
		registroSaved.setTipo(registro.getTipo());
		registroSaved.setCategoria(registro.getCategoria());
		registroSaved.setData(registro.getData());
		registroSaved.setValor(registro.getValor());
		registroSaved.setDescricao(registro.getDescricao());
		
		return this.registroRepository.save(registroSaved);
	}
	
	
//	public Categoria findCategoriaByIdRegistro (Long id) {
//		//return categoriaResource.findCategoriaById(id);
//		Categoria categoria = categoriaResource.findCategoriaById(id);
//		return categoria;
//	}
	
	public Boolean verificaCategoriaAssociada(Long id) {
		
		Boolean verifica = true;
		final Page<Registro> pageRegistro = registroRepository.listByFilters(null,null,id,null);

		Assert.assertTrue("Não pode deletar, categoria associada à um ou mais registros",pageRegistro.getTotalElements()==0);

		verifica = false;


		return verifica;
	}




}
