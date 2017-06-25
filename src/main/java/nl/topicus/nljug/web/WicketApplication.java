package nl.topicus.nljug.web;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

import nl.topicus.nljug.web.pages.Index;

public class WicketApplication extends WebApplication
{
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return Index.class;
	}

	@Override
	public void init()
	{
		super.init();
	}
	
	@Override
	public Session newSession(Request request, Response response) {
		return new CheesrSession(request);
	}
}
