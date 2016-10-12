
package tikape.runko.domain;
import java.sql.Timestamp;


public class Viestiketju {
    private Integer tunnus;
    private Timestamp aika;
    private String otsikko;
    private Keskustelualue keskustelualue; 

    public Viestiketju(Integer tunnus, String otsikko, Timestamp aika) {
        this.aika = aika;
        this.otsikko = otsikko;
        this.tunnus = tunnus;
        //this.keskustelualue = keskustelualue;
    }

    public Integer getTunnus() {
        return tunnus;
    }
    
    public Timestamp getAika() {
        return aika;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }
    
    public Keskustelualue getKeskustelualue() {
        return keskustelualue;
    }

    
}
