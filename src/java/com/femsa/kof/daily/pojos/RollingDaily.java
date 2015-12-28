package com.femsa.kof.daily.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RollingDaily implements Serializable{
    
    private RvvdReclasifDiasOp diasOperativos;
    private List<RvvdStRolling> stRollings;

    public List<RvvdStRolling> getStRollings() {
        return stRollings;
    }

    public void setStRollings(List<RvvdStRolling> stRollings) {
        this.stRollings = stRollings;
    }
    
    public RvvdReclasifDiasOp getDiasOperativos() {
        return diasOperativos;
    }

    public void setDiasOperativos(RvvdReclasifDiasOp diasOperativos) {
        this.diasOperativos = diasOperativos;
    }    
        
    public void addRolling(RvvdStRolling rolling){
        if(stRollings == null){
            stRollings = new ArrayList<RvvdStRolling>();
        }
        stRollings.add(rolling);
    }
}
