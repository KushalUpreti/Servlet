package listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.File;

@WebListener
public class FileLocationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String rootPath = System.getProperty("catalina.home");
        ServletContext ctx = servletContextEvent.getServletContext();
        String relativePath = ctx.getInitParameter("tempfile.dir");
        File file = new File(rootPath + File.separator + relativePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        ctx.setAttribute("FILES_DIR_FILE", file);
        ctx.setAttribute("FILES_DIR", rootPath + File.separator + relativePath);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // Cleanup task if needed
    }
}
