package br.com.gtsaude.registro.domain;

import java.util.Locale;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.agenda.registro.Application;
import br.com.gtsaude.registro.TestApplication;


/**
 * 
 * @author rodrigo@eits.com.br
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
	TestApplication.class, 
	Application.class
})
public abstract class AbstractIntegrationTests
{
	/*-------------------------------------------------------------------
     *                           ATTRIBUTES
     *-------------------------------------------------------------------*/
	
	/*-------------------------------------------------------------------
	 * 		 					CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/*-------------------------------------------------------------------
     *                           BEHAVIORS
     *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Before
	public void beforeTest()
	{
		Locale.setDefault( new Locale("pt", "BR") );
	}
}
