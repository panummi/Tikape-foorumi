/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Opiskelija;
import tikape.runko.domain.Viesti;
import tikape.runko.domain.Viestiketju;

/**
 *
 * @author Reetta
 */
public class ViestiDao implements Dao<Viesti, Integer> {
    
    private Database database;
    private Dao<Viestiketju, Integer>  viestiketjuDao;
    
    public ViestiDao(Database database, Dao<Viestiketju, Integer>  viestiketjuDao) {
        this.database = database;
        this.viestiketjuDao = viestiketjuDao;
    }
    
    @Override
    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti, Viestiketju WHERE Viesti.viestiketju = Viestiketju.tunnus AND viestiketju.tunnus = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        String sisalto = rs.getString("sisältö");
        Timestamp aika = rs.getTimestamp("aika");
        String lahettaja = rs.getString("lähettäjä");
        
        Viesti v = new Viesti(sisalto, lahettaja, aika);
        rs.close();
        stmt.close();
        connection.close();

        return v;
    }
    
    @Override
    public List<Viesti> findAll(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Viesti.* FROM Viesti, Viestiketju WHERE Viesti.viestiketju = Viestiketju.tunnus AND Viestiketju.tunnus = ?");
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            String sisalto = rs.getString("sisältö");
            Timestamp aika = rs.getTimestamp("aika");
            String lahettaja = rs.getString("lähettäjä");
            //Keskustelualue keskustelualue = rs.(keskustelualue");

            viestit.add(new Viesti(sisalto, lahettaja, aika));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }
    
    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
    
}
