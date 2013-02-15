package app;

import java.awt.Desktop;
import java.net.URI;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;

import app.servlet.ModelListServlet;
import app.servlet.ModelServlet;

public class JsonTestServer {

    public static void main(String[] args) throws Exception {
        String baseDir = "..";
        int port = 8080;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-b") || args[i].equals("--baseDir")) {
                baseDir = args[i + 1];
            }
            if (args[i].equals("-p") || args[i].equals("--port")) {
                port = Integer.parseInt(args[i + 1]);
            }
        }

        new JsonTestServer().start(baseDir, port);
    }

    public void start(String baseDir, int port) throws Exception {
        HandlerList handlers = new HandlerList();

        ResourceHandler rh = new ResourceHandler();
        rh.setResourceBase(baseDir);
        handlers.addHandler(rh);

        ServletHandler sh = new ServletHandler();
        sh.addServletWithMapping(ModelListServlet.class, "/modelList");
        sh.addServletWithMapping(ModelServlet.class, "/model/*");
        handlers.addHandler(sh);

        Server server = new Server(port);
        server.setHandler(handlers);
        server.start();

        Desktop.getDesktop().browse(
                new URI("http://localhost:" + port
                        + "/json-test-server/index.html"));
    }

}
