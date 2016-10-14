
package tikape.runko.domain;
import java.sql.Timestamp;import java.sql.Timestamp;

public class Keskustelualue {
    private Integer tunnus;
    private String nimi;
    private Integer viestienMaara;
    private Timestamp viimeisinViesti;
    //private int viesteja;

    public Keskustelualue(Integer tunnus, String nimi, Integer viestienMaara, Timestamp viimeisinViesti ) {
        this.tunnus = tunnus;
        this.nimi = nimi;
        this.viestienMaara = viestienMaara;
        this.viimeisinViesti = viimeisinViesti;
    }

    public Integer getTunnus() {
        return tunnus;
    }
    

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    public Integer getViestienMaara() {
        return viestienMaara;
    }
    
    public void setViestienMaara(Integer viestienMaara) {
        this.viestienMaara = viestienMaara;
    }
    
    
    public Timestamp getViimeisinViesti() {
        return viimeisinViesti;
    }
    
    public void setViimeisinViesti(Timestamp viimeisinViesti) {
        this.viimeisinViesti = viimeisinViesti;
    }
    
}
