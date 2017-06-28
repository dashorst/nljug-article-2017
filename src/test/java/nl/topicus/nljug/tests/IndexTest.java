package nl.topicus.nljug.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import nl.topicus.nljug.CheesrDeployment;
import nl.topicus.nljug.Camera;
import nl.topicus.nljug.pageobjects.GIndex;

// tag::simple[]
@RunWith(Arquillian.class)
public class IndexTest { 
// end::simple[]
	// tag::deployment[]
	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return CheesrDeployment.createWar();
	}
	// end::deployment[]

	// tag::webdriver[]

	@Drone
	private WebDriver browser;
	// end::webdriver[]

	// tag::simple[]

	@Test
	public void step1EmptyCart(@InitialPage GIndex index) {
		Camera.takeScreenshot(browser, "cheesr-1-home.png");

		Camera.takeScreenshot(browser, "cheesr-1-home-navigator.png", By.id("navigator"));

		// nothing was selected, so the checkout button should not be present
		assertFalse(index.checkoutPresent());
	}
	// end::simple[]

	// tag::buy[]
	@Test
	public void step2AddToEmptyCart(@InitialPage GIndex index) {
		Camera.takeScreenshot(browser, "cheesr-2-cart-0-empty.png", index.byCart());

		index.addCheese("edam");

		Camera.takeScreenshot(browser, "cheesr-2-cart-1-edam.png", index.byCart());

		// assert that the checkout button is present
		assertTrue(index.checkoutPresent());

		index.removeCheese("edam");
		assertFalse(index.checkoutPresent());

	}
	// end::buy[]
// tag::simple[]
}
// end::simple[]
