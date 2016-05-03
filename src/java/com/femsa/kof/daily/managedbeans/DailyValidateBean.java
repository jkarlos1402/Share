/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femsa.kof.daily.managedbeans;

import com.femsa.kof.share.dao.ShareCatPaisDAO;
import com.femsa.kof.share.pojos.ShareCatPais;
import com.femsa.kof.share.pojos.ShareUsuario;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

/**
 * Bean administrado que se encarga del manejo de la vista para la validación de
 * la información de Daily
 *
 * @author TMXIDSJPINAM
 */
@ManagedBean
@ViewScoped
public class DailyValidateBean {

    private DashboardModel model;

    private final ShareUsuario usuario;

    /**
     * Creates a new instance of DailyValidateBean
     */
    public DailyValidateBean() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        usuario = (ShareUsuario) httpSession.getAttribute("session_user");
    }

    /**
     * Obtiene el estatus de cada pais y genera la vista correspondiente
     */
    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExpressionFactory ef = fc.getApplication().getExpressionFactory();
        ELContext eLContext = fc.getELContext();
        model = new DefaultDashboardModel();
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();
        ShareCatPaisDAO catPaisDAO = new ShareCatPaisDAO();
        boolean status;
        Dashboard dashboard = (Dashboard) fc.getViewRoot().findComponent("boardValidate");
        dashboard.getChildren().clear();
        Calendar calendario = Calendar.getInstance();
        calendario.set(Calendar.HOUR, 10);
        calendario.set(Calendar.MINUTE, 50);
        if (usuario != null) {
            int i = 1;
            for (ShareCatPais pais : usuario.getPaises()) {
                try {
                    status = catPaisDAO.getStatusInfoPais(pais);
                    Panel panel = (Panel) fc.getApplication().createComponent(Panel.COMPONENT_TYPE);

                    panel.setId("panel" + pais.getNombre().replaceAll(" ", "") + System.currentTimeMillis());
                    panel.setHeader(pais.getNombre());
                    HtmlOutputText outputText = (HtmlOutputText) fc.getApplication().createComponent(HtmlOutputText.COMPONENT_TYPE);
                    if (status) {
                        PanelGrid panelGrid = (PanelGrid) fc.getApplication().createComponent(PanelGrid.COMPONENT_TYPE);
                        panelGrid.setColumns(2);
                        HtmlOutputText textAceptado = (HtmlOutputText) fc.getApplication().createComponent(HtmlOutputText.COMPONENT_TYPE);
                        textAceptado.setValue("CONFIRMED");
                        panelGrid.getChildren().add(textAceptado);
//                        if (Calendar.getInstance().compareTo(calendario) <= 0) {
                        CommandButton button = (CommandButton) fc.getApplication().createComponent(CommandButton.COMPONENT_TYPE);
                        button.setValue("DECLINE");
                        button.setActionExpression(ef.createMethodExpression(eLContext, "#{dailyValidateBean.declinarInfoPais('" + pais.getNombre() + "')}", null, new Class[]{String.class}));
                        button.setConfirmationScript("PrimeFaces.confirm({source:this, header:'Decline information of " + pais.getNombre() + "', message:'Are you sure?', icon:'ui-icon-alert'});return false;");
                        button.setUpdate("boardValidate");
                        panelGrid.getChildren().add(button);
//                        }
                        panel.getChildren().add(panelGrid);
                    } else {
                        outputText.setValue("DECLINED");
                        panel.getChildren().add(outputText);
                    }

                    dashboard.getChildren().add(panel);
                    switch (i % 2) {
                        case 1:
                            column1.addWidget(panel.getId());
                            break;
                        default:
                            column2.addWidget(panel.getId());
                            break;
                    }
                    i++;
                } catch (IllegalArgumentException ex) {

                }
            }
        }
        model.addColumn(column1);
        model.addColumn(column2);
    }

    public DashboardModel getModel() {
        return model;
    }

    public void setModel(DashboardModel model) {
        this.model = model;
    }

    /**
     * Cambia el estaus de la información del pais indicado a DECLINED
     *
     * @param nombrePais Nombre del pais al cual se declinará la información
     */
    public void declinarInfoPais(String nombrePais) {
        ShareCatPaisDAO paisDAO = new ShareCatPaisDAO();
        paisDAO.setStatusInfoPais(nombrePais);
        init();
    }
}
