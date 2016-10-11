
package tikape.runko.domain;

public class Keskustelualue {
    private Integer tunnus;
    private String nimi;

    public Keskustelualue(Integer tunnus, String nimi) {
        this.tunnus = tunnus;
        this.nimi = nimi;
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
    
   // public Integer getViestit() {
   //     return KeskustelualueDao.countViestit();
   // }    
    
}
