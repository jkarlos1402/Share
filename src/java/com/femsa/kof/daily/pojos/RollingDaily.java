package com.femsa.kof.daily.pojos;

import java.io.Serializable;

public class RollingDaily implements Serializable{
    
    private RvvdReclasifDiasOp diasOperativos;
    

    public RvvdReclasifDiasOp getDiasOperativos() {
        return diasOperativos;
    }

    public void setDiasOperativos(RvvdReclasifDiasOp diasOperativos) {
        this.diasOperativos = diasOperativos;
    }    
        
}
