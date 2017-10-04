package br.com.gtsaude.categoria.domain.service;

import static org.junit.Assert.assertNotNull;

import javax.validation.ValidationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.agenda.categoria.domain.entity.Categoria;
import br.com.agenda.categoria.domain.entity.Tipo;
import br.com.agenda.categoria.domain.service.CategoriaService;
import br.com.gtsaude.categoria.domain.AbstractIntegrationTests;

public class CategoriaServiceIntegrationTests extends AbstractIntegrationTests {

	@Autowired
	private CategoriaService categoriaService;
	
	@Test
	@Sql({"/dataset/categoria/categorias.sql"
		})
	public void insertCategoriaMustPass() {
		Categoria categoria = new Categoria();
		categoria.setNome("conta de agua");
		categoria.setTipo(Tipo.DESPESA);
		categoria.setDescricao("");
		
		Categoria categoriaSaved = this.categoriaService.insertCategoria(categoria);
		assertNotNull(categoriaSaved);
	}
	
	//@Test(expected = ValidationException.class)
	public void insertCategoriaWithoutNomeMustFail() {
		
		Categoria categoria = new Categoria();
		Categoria categoriaSaved = this.categoriaService.insertCategoria(categoria);
		assertNotNull(categoriaSaved);
	}
	
	//@Test(expected = ValidationException.class)
	public void insertCategoriaWithoutTipoMustFail() {
		
		Categoria categoria = new Categoria();
		categoria.setNome("salário");
		Categoria categoriaSaved = this.categoriaService.insertCategoria(categoria);
		assertNotNull(categoriaSaved);
	}
	
	//@Test
	public void listCategoriaByFiltersMustPass() {
		categoriaService.listCategoriaByFilters(null,null);
	}
	
	//@Test
	public void findCategoriaByIdMustPass() {
		Categoria categoria = categoriaService.findCategoriaById(17L);
		assertNotNull(categoria);
		System.out.println("nome: " +  categoria.getNome() + "  , tipo: " + categoria.getTipo() );
		
	}
	
	//@Test(expected = IllegalArgumentException.class)
	public void findCategoriaByIdMustFail() {
		Categoria categoria = categoriaService.findCategoriaById(39L);
		assertNotNull(categoria);
		System.out.println("nome: " +  categoria.getNome() + "  , tipo: " + categoria.getTipo() );
		
		
		
	}
	
	//@Test
	public void updateCategoriaMustPass() {
		
		Categoria categoria = categoriaService.findCategoriaById(17L);
		categoria.setDescricao("teste de update mustPass");
		assertNotNull(categoriaService.updateCategoria(categoria));
		
	}
	
	//@Test
	public void updateCategoriaToDesativadaMustPass() {
		assertNotNull(categoriaService.updateCategoriaToDesativada(18L));
		System.out.println("A CATEGORIA FOI DESATIVADA: ");
	}
	
	
	//@Test(expected = IllegalArgumentException.class)
	public void updateCategoriaToDesativadaMustFail() {
		assertNotNull(categoriaService.updateCategoriaToDesativada(19L));
		System.out.println("A CATEGORIA FOI DESATIVADA: ");
	}
	
	//@Test
	public void updateCategoriaToAtivadaMustPass() {
		assertNotNull(categoriaService.updateCategoriaToAtivada(20L));
		System.out.println("A CATEGORIA FOI DESATIVADA: ");
	}
	
	//@Test
	public void removeCategoriaMustPass() {
		categoriaService.removeCategoria(20L);
	}
	
	//@Test(expected = nullPointerException.class)
	public void removeCategoriaMustFail() {
		categoriaService.removeCategoria(20L);
	}
	
	
	
}
