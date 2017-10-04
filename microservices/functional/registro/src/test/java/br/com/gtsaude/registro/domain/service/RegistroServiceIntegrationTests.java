package br.com.gtsaude.registro.domain.service;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.Assert;

import br.com.agenda.categoria.domain.entity.Categoria;
import br.com.agenda.registro.domain.entity.Registro;
import br.com.agenda.registro.domain.service.RegistroService;
import br.com.gtsaude.registro.domain.AbstractIntegrationTests;

public class RegistroServiceIntegrationTests extends AbstractIntegrationTests {

	@Autowired
	private RegistroService registroService;
	
	
	private Categoria categoria = new Categoria();
	
	
	@Test
	@Sql({"/dataset/registro/registros.sql"
		})
	public void inserirRegistroMustPass() {
		Registro registro = new Registro();
		registro.setCategoria(new Categoria(20L));
		registro.setData(LocalDateTime.now());
		registro.setDescricao("TESTETESTExxxxxxxx2");
		registro.setValor(1576677);
		registroService.insertRegistro(registro);		
		
	}
	
	//@Test(expected = )
	public void inserirRegistroMustFail() {
		
		Registro registro = new Registro();
		Registro registroSaved = this.registroService.insertRegistro(registro);

		assertNotNull(registroSaved);
		
		
	}
	
	@Test
	public void listRegistroByFiltersMustPass() {
		registroService.listRegistroById(null, null, null, null);
	}
	
	public void findRegistroByIdMustPass() {
		
		
	}
	
	public void findRegistroByIdMustFail() {
		
		
	}
	
	public void removeRegistroMustPass() {
		
	}
	
	
	
	
	
}
