/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femsa.kof.daily.managedbeans;

import com.femsa.kof.share.dao.ShareCatPaisDAO;
import com.femsa.kof.share.pojos.ShareCatPais;
import com.femsa.kof.share.pojos.ShareUsuario;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.security.auth.callback.ConfirmationCallback;
import javax.servlet.http.HttpSession;
import org.primefaces.behavior.ajax.AjaxBehavior;
import org.primefaces.behavior.confirm.ConfirmBehavior;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.confirmdialog.ConfirmDialogRenderer;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

/**
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

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExpressionFactory ef = fc.getApplication().getExpressionFactory();
        ELContext eLContext = fc.getELContext();
        model = new DefaultDashboardModel();
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();
        DashboardColumn column3 = new DefaultDashboardColumn();
        ShareCatPaisDAO catPaisDAO = new ShareCatPaisDAO();
        boolean status;
        if (usuario != null) {
            int i = 1;
            for (ShareCatPais pais : usuario.getPaises()) {
                try {
                    status = catPaisDAO.getStatusInfoPais(pais);
                    Panel panel = (Panel) fc.getApplication().createComponent(Panel.COMPONENT_TYPE);
                    Dashboard dashboard = (Dashboard) fc.getViewRoot().findComponent("boardValidate");
                    panel.setId("panel" + pais.getNombre().replaceAll(" ", "") + System.currentTimeMillis());
                    panel.setHeader(pais.getNombre());
                    HtmlOutputText outputText = (HtmlOutputText) fc.getApplication().createComponent(HtmlOutputText.COMPONENT_TYPE);
                    SelectBooleanCheckbox checkbox = null;
                    HtmlOutputText textoDeclinar = null;
                    if (status) {
                        PanelGrid panelGrid = (PanelGrid) fc.getApplication().createComponent(PanelGrid.COMPONENT_TYPE);
                        panelGrid.setColumns(2);
                        HtmlOutputText textAceptado = (HtmlOutputText) fc.getApplication().createComponent(HtmlOutputText.COMPONENT_TYPE);
                        textAceptado.setValue("CONFIRMED");
                        CommandButton button = (CommandButton) fc.getApplication().createComponent(CommandButton.COMPONENT_TYPE);
                        button.setValue("DECLINE");
                        button.setActionExpression(ef.createMethodExpression(eLContext, "#{dailyValidateBean.declinarInfoPais('" + pais.getNombre() + "')}", null, new Class[]{String.class}));
                        ConfirmBehavior  cb = new ConfirmBehavior();
                        cb.setHeader("Decline information of "+pais.getNombre()+" country");
                        cb.setMessage("Are you sure?");                          
                        panelGrid.getChildren().add(textAceptado);
                        panelGrid.getChildren().add(button);
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
                    ex.printStackTrace();
                }
            }
        }
//        column1.addWidget("sports");
        model.addColumn(column1);
        model.addColumn(column2);
        // model.addColumn(column3);
    }

    public DashboardModel getModel() {
        return model;
    }

    public void setModel(DashboardModel model) {
        this.model = model;
    }

    public void declinarInfoPais(String nombrePais) {
        System.out.println("llego:" + nombrePais);
    }
}
