package nl.topicus.nljug.web.pages;

import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
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

	private static final int ITEMS_PER_PAGE = 5;

	public Index() {
		CheeseDao dao = new CheeseDaoImpl();

		ListDataProvider<Cheese> provider = new ListDataProvider<>(dao.getCheeses());

		DataView<Cheese> items = new CheeseDataView("items", provider, ITEMS_PER_PAGE);
		add(items);

		add(new PagingNavigator("navigator", items));

		add(new ShoppingCartPanel("cart", PropertyModel.of(this, "session.cart")));

		add(new CheckoutButton("checkout"));
	}

	protected Cart getCart() {
		return CheesrSession.get().getCart();
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		// always render JQuery into the head so aShot can take a partial
		// screenshot of multiple elements.
		response.render(JavaScriptHeaderItem
				.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
	}

	private final class CheeseDataView extends DataView<Cheese> {
		private static final long serialVersionUID = 1L;

		private CheeseDataView(String id, IDataProvider<Cheese> provider, long itemsPerPage) {
			super(id, provider, itemsPerPage);
		}

		@Override
		protected void populateItem(Item<Cheese> item) {
			item.add(new Label("name", PropertyModel.of(item.getModel(), "name")));
			item.add(new Label("description", PropertyModel.of(item.getModel(), "description")));
			item.add(new Label("price", PropertyModel.of(item.getModel(), "price")));

			item.add(new Link<Cheese>("add", item.getModel()) {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onInitialize() {
					super.onInitialize();

					// make clicking on the link easy by generating an
					// unique ID for each link.
					Cheese cheese = getModelObject();
					String safeCheeseName = cheese.getName().toLowerCase().replaceAll(" ", "-");
					setMarkupId("add-" + safeCheeseName);
				}

				@Override
				public void onClick() {
					Cheese cheese = getModelObject();
					getCart().getContents().add(cheese);
				}
			});
		}
	}

	private final class CheckoutButton extends Link<Void> {
		private static final long serialVersionUID = 1L;

		private CheckoutButton(String id) {
			super(id);
			setOutputMarkupPlaceholderTag(true);
		}

		@Override
		protected void onConfigure() {
			super.onConfigure();

			// only show the checkout button when something was added to the
			// shopping cart
			int count = getCart().getContents().size();
			setVisible(count > 0);
		}

		@Override
		public void onEvent(IEvent<?> event) {
			super.onEvent(event);
			if(event.getPayload() instanceof AjaxRequestTarget) {
				((IPartialPageRequestHandler) event.getPayload()).add(this);
			}
		}

		@Override
		public void onClick() {
			// go to the checkout page
			setResponsePage(new CheckoutPage(getCart()));
		}
	}
}
