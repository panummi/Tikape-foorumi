
package tikape.runko.domain;

public class Viestiketju {
    private Integer tunnus;
    private Integer aika;
    private String otsikko;

    public Viestiketju(Integer tunnus, String otsikko, Integer aika) {
        this.aika = aika;
        this.otsikko = otsikko;
        this.tunnus = tunnus;
    }

    public Integer getTunnus() {
        return tunnus;
    }
    
    public Integer getAika() {
        return aika;
    }
    

    public String getOtsikko() {
        return otsikko;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    
}
