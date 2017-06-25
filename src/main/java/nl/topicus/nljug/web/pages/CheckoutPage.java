package nl.topicus.nljug.web.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import nl.topicus.nljug.domain.Cart;
import nl.topicus.nljug.services.CountryDaoImpl;
import nl.topicus.nljug.web.components.ShoppingCartPanel;

public class CheckoutPage extends WebPage {
	private static final long serialVersionUID = 1L;

	private Cart cart;

	public CheckoutPage(Cart cartParam) {
		this.cart = cartParam;

		add(new FeedbackPanel("feedback"));
		Form<Void> form = new Form<>("form");
		add(form);

		form.add(new TextField<>("name", PropertyModel.of(this, "cart.name")).setRequired(true));

		form.add(new TextField<>("street", PropertyModel.of(this, "cart.street")).setRequired(true));

		form.add(new TextField<>("city", PropertyModel.of(this, "cart.city")).setRequired(true));

		form.add(new DropDownChoice<>("country", PropertyModel.of(this, "cart.country"),
				new CountryDaoImpl().getCountries(), new ChoiceRenderer<>("name")).setRequired(true));

		add(new ShoppingCartPanel("cart", PropertyModel.of(this, "cart")));

		form.add(new Link<Void>("cancel") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(Index.class);
			}
		});

		form.add(new SubmitLink("order") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				// place order in backend
				cart.getContents().clear();
				setResponsePage(Index.class);
			}
		});
	}
}
