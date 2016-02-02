package com.femsa.kof.daily.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TMXIDSJPINAM
 */
public class RollingDaily implements Serializable{
    
    private RvvdReclasifDiasOpTmp diasOperativos;
    private List<RvvdStRollingTmp> stRollings;

    /**
     *
     * @return
     */
    public List<RvvdStRollingTmp> getStRollings() {
        return stRollings;
    }

    /**
     *
     * @param stRollings
     */
    public void setStRollings(List<RvvdStRollingTmp> stRollings) {
        this.stRollings = stRollings;
    }
    
    /**
     *
     * @return
     */
    public RvvdReclasifDiasOpTmp getDiasOperativos() {
        return diasOperativos;
    }

    /**
     *
     * @param diasOperativos
     */
    public void setDiasOperativos(RvvdReclasifDiasOpTmp diasOperativos) {
        this.diasOperativos = diasOperativos;
    }    
        
    /**
     *
     * @param rolling
     */
    public void addRolling(RvvdStRollingTmp rolling){
        if(stRollings == null){
            stRollings = new ArrayList<RvvdStRollingTmp>();
        }
        stRollings.add(rolling);
    }
}
