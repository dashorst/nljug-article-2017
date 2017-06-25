package nl.topicus.nljug.pageobjects;

import java.util.List;
import java.util.NoSuchElementException;

import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

@Location("")
public class GIndex {
	@FindBy(css = "a[id^=add]")
	private List<GrapheneElement> addLinks;

	@FindBy(css = "input[type=button]")
	private GrapheneElement checkout;

	public void buy(String cheese) {
		addLinks.stream().filter(a -> a.getAttribute("id").contains(cheese.toLowerCase().replaceAll(" ", "-")))
				.findFirst().orElseThrow(() -> new NoSuchElementException("Add " + cheese + " link")).click();
	}

	public boolean checkoutPresent() {
		return checkout.isPresent();
	}

	public void checkout() {
		checkout.click();
	}

	public By byCart() {
		return By.id("cart");
	}
}
