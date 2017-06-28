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

	// tag::buy[]
	@FindBy(css = "input[type=button]")
	private GrapheneElement checkout;

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
	
	public void checkout() {
		checkout.click();
	}
	// tag::buy[]

	public boolean checkoutPresent() {
		return checkout.isPresent();
	}

	public By byCart() {
		return By.cssSelector("div[id=cart],input[type=button]");
	}
}
// end::buy[]
