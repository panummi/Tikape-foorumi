
package tikape.runko.domain;

import tikape.runko.database.KeskustelualueDao;

public class Keskustelualue {
    private Integer tunnus;
    private String nimi;
    //private int viesteja;

    public Keskustelualue(Integer tunnus, String nimi) {
        this.tunnus = tunnus;
        this.nimi = nimi;
        //this.viesteja = KeskustelualueDao.countViestit(tunnus);
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
    
}
