package com.femsa.kof.listener;

import com.femsa.kof.util.CatalogLoader;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author TMXIDSJPINAM
 */
@WebListener()
public class ContextListenerKOF implements ServletContextListener {

    /**
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Boolean flagLoadInfShare = false;
        Boolean flagLoadRollingDaily = false;
        Boolean flagLoadOpDaysDaily = false;
        Boolean flagLoadSalesDayly = false;
        context.setAttribute("flag_load_share", flagLoadInfShare);
        context.setAttribute("flag_load_rolling_daily", flagLoadRollingDaily);        
        context.setAttribute("flag_load_opdays_daily", flagLoadOpDaysDaily);        
        context.setAttribute("flag_load_sales_daily", flagLoadSalesDayly);        
        if(CatalogLoader.loadCatalogs("share")){            
            CatalogLoader.loadCatalogs("daily");
        }else{
            context.setAttribute("error_database", CatalogLoader.error);
        }        
    }

    /**
     *
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //comentario
    }
}
