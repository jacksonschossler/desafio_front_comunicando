package br.com.agenda.categoria.domain.entity;

import org.junit.Test;

public class CategoriaTests {

	
	@Test
	public void test() {
		final Categoria categoria = new Categoria();
		categoria.setNome("salário");
		System.out.println(categoria.getNome());
	}
	
}
