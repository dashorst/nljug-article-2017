package nl.topicus.nljug.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import nl.topicus.nljug.CheesrDeployment;
import nl.topicus.nljug.ScreenShot;
import nl.topicus.nljug.pageobjects.GIndex;

@RunWith(Arquillian.class)
public class IndexTest {
	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return CheesrDeployment.createWar();
	}

	@Drone
	private WebDriver browser;

	@ArquillianResource
	private URL deploymentUrl;

	@Test
	public void step1EmptyCart(@InitialPage GIndex index) {
		ScreenShot.takeScreenshot(browser, "1-home");

		ScreenShot.takeScreenshot(browser, "1-home-navigator", By.id("navigator"));

		assertFalse(index.checkoutPresent());
	}

	@Test
	public void step2AddToEmptyCart(@InitialPage GIndex index) {
		ScreenShot.takeScreenshot(browser, "2-cart-0-empty", index.byCart());

		index.buy("edam");

		ScreenShot.takeScreenshot(browser, "2-cart-1-edam", index.byCart());

		assertTrue(index.checkoutPresent());
	}
}
