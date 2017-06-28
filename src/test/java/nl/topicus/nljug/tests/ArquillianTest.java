package nl.topicus.nljug.tests;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import nl.topicus.nljug.CheesrDeployment;

// tag::arquillian[]
@RunWith(Arquillian.class)
public class ArquillianTest {
	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return CheesrDeployment.createWar();
	}

	@Test
	public void simpleTest() {
	}
}
// end::arquillian[]
