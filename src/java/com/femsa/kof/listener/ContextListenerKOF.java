package com.femsa.kof.listener;

import com.femsa.kof.share.dao.ShareCatCategoriasDAO;
import com.femsa.kof.share.dao.ShareCatPaisDAO;
import com.femsa.kof.share.dao.ShareCatCanalesDAO;
import com.femsa.kof.util.CatalogLoader;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class ContextListenerKOF implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Boolean flagLoadInfShare = false;
        context.setAttribute("flag_load_share", flagLoadInfShare);
        CatalogLoader.loadCatalogs("share");
        CatalogLoader.loadCatalogs("daily");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        ClassLoader cl = Thread.currentThread().getContextClassLoader();
//         Loop through all drivers
//        Enumeration<Driver> drivers = DriverManager.getDrivers();
//        while (drivers.hasMoreElements()) {
//            Driver driver = drivers.nextElement();
//            if (driver.getClass().getClassLoader() == cl) {
//                 This driver was registered by the webapp's ClassLoader, so deregister it:
//                try {
//                    System.out.println("Deregistering JDBC driver" + driver);
//                    DriverManager.deregisterDriver(driver);
//                } catch (SQLException ex) {
//                    
//                    System.out.println("Error deregistering JDBC driver " + ex.getMessage());
//                }
//            } else {
//                 driver was not registered by the webapp's ClassLoader and may be in use elsewhere
//                System.out.println("Not deregistering JDBC driver {} as it does not belong to this webapp's ClassLoader" + driver);
//            }
//        }
    }
}
