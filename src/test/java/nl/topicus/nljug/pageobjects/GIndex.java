package nl.topicus.nljug.pageobjects;

import java.util.List;
import java.util.NoSuchElementException;

import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

// tag::buy[]
@Location("")
public class GIndex {
	// tag::buy[]
	@FindBy(css = "a[id^=add]")
	private List<GrapheneElement> addLinks;

	// end::buy[]

	@FindBy(css = "a[id^=remove]")
	private List<GrapheneElement> removeLinks;

	@FindBy(css = "input[type=button]")
	private GrapheneElement checkout;

	// tag::buy[]
	public void addCheese(String cheese) {
		addLinks.stream()
			.filter(a -> a.getAttribute("id").contains(safeCheeseId(cheese)))
			.findFirst()
			.orElseThrow(() -> new NoSuchElementException("Add " + cheese + " link"))
			.click();
	}
	// end::buy[]

	public void removeCheese(String cheese) {
		removeLinks.stream()
			.filter(a -> a.getAttribute("id").contains(safeCheeseId(cheese)))
			.findFirst()
			.orElseThrow(() -> new NoSuchElementException("Remove " + cheese + " link"))
			.click();
	}

	private String safeCheeseId(String cheese) {
		return cheese.toLowerCase().replaceAll(" ", "-");
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
	// tag::buy[]
}
// end::buy[]
