package app.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.dev7.enchant.doga.parser.util.FileTreeUtil;
import jp.dev7.enchant.doga.parser.util.Props;
import net.arnx.jsonic.JSON;

import com.google.common.base.Function;

@SuppressWarnings("serial")
public class ModelListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        List<String> list = getList();
        Collections.sort(list);

        JSON.encode(list, resp.getWriter());
        resp.flushBuffer();
    }

    private List<String> getList() throws IOException {
        final File dataDir = Props.dataDir();
        return FileTreeUtil.scanDir(dataDir, new Function<File, String>() {
            @Override
            public String apply(File input) {
                String name = input.getAbsolutePath().replace(
                        dataDir.getAbsolutePath(), "");
                if (name.toLowerCase().endsWith(".fsc")
                        || name.toLowerCase().endsWith(".l2p")
                        || name.toLowerCase().endsWith(".l2c")
                        || name.toLowerCase().endsWith(".l3p")
                        || name.toLowerCase().endsWith(".l3c")) {

                    return "model" + name.replace(File.separatorChar, '/')
                            + ".json";

                } else {
                    return null;
                }
            }
        });
    }

}
