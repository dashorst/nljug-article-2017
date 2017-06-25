package nl.topicus.nljug.web;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import nl.topicus.nljug.domain.Cart;

public class CheesrSession extends WebSession {
	private static final long serialVersionUID = 1L;

	private Cart cart;

	public CheesrSession(Request request) {
		super(request);
	}

	public Cart getCart() {
		if (cart == null)
			cart = new Cart();
		return cart;
	}

	public static CheesrSession get() {
		return (CheesrSession) WebSession.get();
	}
}
