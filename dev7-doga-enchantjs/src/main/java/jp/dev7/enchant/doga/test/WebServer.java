package jp.dev7.enchant.doga.test;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpHeaderValues;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

/**
 * テスト用.
 */
public class WebServer {

	private static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		new WebServer().start();
	}

	public void start() throws Exception {
		final HandlerList list = new HandlerList();
		final ResourceHandler rh = new ResourceHandler() {
			@Override
			public void handle(String target, Request baseRequest,
					HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
				super.handle(target, baseRequest, request, response);
				response.addHeader(HttpHeaders.CACHE_CONTROL,
						HttpHeaderValues.NO_CACHE);
				response.addHeader(HttpHeaders.PRAGMA,
						HttpHeaderValues.NO_CACHE);
				response.addHeader(HttpHeaders.EXPIRES,
						"Thu, 01 Dec 1994 16:00:00 GMT");
			}
		};
		rh.setResourceBase(getClass().getResource("/").toExternalForm());
		list.addHandler(rh);

		final Server server = new Server(PORT);
		server.setHandler(list);
		server.start();

		Desktop.getDesktop().browse(
				new URI("http://localhost:" + PORT + "/test/test.html"));
	}
}
