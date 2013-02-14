package app.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.dev7.enchant.doga.converter.L3cConverter;
import jp.dev7.enchant.doga.converter.UnitConverter;
import jp.dev7.enchant.doga.parser.util.Props;

@SuppressWarnings("serial")
public class ModelServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        try {

            String name = req.getRequestURI().replace("/model/", "")
                    .replace(".json", "");
            File file = new File(Props.dataDir(), name);
            if (!file.exists()) {
                resp.sendError(404);
                return;
            }

            if (name.toLowerCase().endsWith(".fsc")
                    || name.toLowerCase().endsWith(".l2p")
                    || name.toLowerCase().endsWith(".l3p")) {

                new UnitConverter().convertAndWriteJson(file, resp.getWriter());

            } else if (name.toLowerCase().endsWith(".l2c")) {

                // TODO
                resp.sendError(404);
                return;

            } else if (name.toLowerCase().endsWith(".l3c")) {

                new L3cConverter().convertAndWriteJson(file, resp.getWriter());

            }

        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            resp.flushBuffer();
        }
    }
}
