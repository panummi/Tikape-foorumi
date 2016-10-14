
package tikape.runko.domain;
import java.sql.Timestamp;


public class Viestiketju {
    private Integer tunnus;
    private Timestamp aika;
    private String otsikko;
    private Keskustelualue keskustelualue; 
    private Integer viestienMaara;
    private Timestamp viimeisinViesti;

    public Viestiketju(Integer tunnus, String otsikko, Timestamp aika, Integer viestienMaara, Timestamp viimeisinViesti) {
        this.aika = aika;
        this.otsikko = otsikko;
        this.tunnus = tunnus;
        this.viestienMaara = viestienMaara;
        this.viimeisinViesti = viimeisinViesti;
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
    
    public Integer getViestienMaara() {
        return viestienMaara;
    }
    
    public void setViestienMaara(Integer viestienMaara) {
        this.viestienMaara = viestienMaara;
        System.out.println(this.viestienMaara);
    }
    
    public Timestamp getViimeisinViesti() {
        return viimeisinViesti;
    }
    
    public void setViimeisinViesti(Timestamp viimeisinViesti) {
        this.viimeisinViesti = viimeisinViesti;
    }
    

    
}
