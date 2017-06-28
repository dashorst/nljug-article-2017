package nl.topicus.nljug.web.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import nl.topicus.nljug.domain.Cart;
import nl.topicus.nljug.domain.Cheese;

public class ShoppingCartPanel extends GenericPanel<Cart> {
	private static final long serialVersionUID = 1L;

	public ShoppingCartPanel(String id, IModel<Cart> model) {
		super(id, model);

		setOutputMarkupId(true);

		add(new ListView<Cheese>("items", PropertyModel.of(model, "contents")) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Cheese> item) {
				item.add(new Label("name", PropertyModel.of(item.getModel(), "name")));
				item.add(new Label("price", PropertyModel.of(item.getModel(), "price")));

				item.add(new AjaxLink<Void>("remove") {
					private static final long serialVersionUID = 1L;

					{
						Cheese cheese = item.getModelObject();
						String safeCheeseName = cheese.getName().toLowerCase().replaceAll(" ", "-");
						setMarkupId("remove-" + safeCheeseName);
					}

					@Override
					public void onClick(AjaxRequestTarget t) {
						ShoppingCartPanel.this.getModelObject().getContents().remove(item.getIndex());
						t.add(ShoppingCartPanel.this);
					}
				});
			}
		});
		add(new Label("total", PropertyModel.of(model, "total")));
	}
}
