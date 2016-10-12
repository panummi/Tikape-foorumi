/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.sql.Timestamp;

/**
 *
 * @author Reetta
 */
public class Viesti {
    
    private String sisalto;
    private Timestamp aika;
    private String lahettaja;
    private Viestiketju viestiketju;
    
    public Viesti(String sisalto, String lahettaja, Timestamp aika) {//+viestiketju
        this.sisalto = sisalto;
        this.lahettaja = lahettaja;
        this.aika = aika;
        //this.viestiketju = viestiketju;
    }
    
    public String getSisalto() {
        return sisalto;
    }
    
    public String getLahettaja() {
        return lahettaja;
    }
    
    public Timestamp getAika() {
        return aika;
    }
    
    public Viestiketju getviestiketju() {
        return viestiketju;
    }
    
}
