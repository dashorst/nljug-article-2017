package nl.topicus.nljug.web.pages;

import org.apache.wicket.Application;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.PropertyModel;

import nl.topicus.nljug.domain.Cart;
import nl.topicus.nljug.domain.Cheese;
import nl.topicus.nljug.services.CheeseDao;
import nl.topicus.nljug.services.CheeseDaoImpl;
import nl.topicus.nljug.web.CheesrSession;
import nl.topicus.nljug.web.components.ShoppingCartPanel;

public class Index extends WebPage {
	private static final long serialVersionUID = 1L;

	public Index() {
		CheeseDao dao = new CheeseDaoImpl();
		DataView<Cheese> items = new DataView<Cheese>("items", new ListDataProvider<>(dao.getCheeses()), 5) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(Item<Cheese> item) {
				item.add(new Label("name", PropertyModel.of(item.getModel(), "name")));
				item.add(new Label("description", PropertyModel.of(item.getModel(), "description")));
				item.add(new Label("price", PropertyModel.of(item.getModel(), "price")));

				item.add(new Link<Cheese>("add", item.getModel()) {
					private static final long serialVersionUID = 1L;

					{
						setMarkupId("add-" + getModelObject().getName().toLowerCase().replaceAll(" ", "-"));
					}

					@Override
					public void onClick() {
						Cheese cheese = getModelObject();
						getCart().getContents().add(cheese);
					}
				});
			}
		};
		add(items);
		add(new PagingNavigator("navigator", items));
		add(new ShoppingCartPanel("cart", PropertyModel.of(this, "session.cart")));
		add(new Link<Void>("checkout") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisible(getCart().getContents().size() > 0);
			}

			@Override
			public void onClick() {
				setResponsePage(new CheckoutPage(getCart()));
			}
		});
	}

	protected Cart getCart() {
		return CheesrSession.get().getCart();
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(JavaScriptHeaderItem
				.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
	}
}
