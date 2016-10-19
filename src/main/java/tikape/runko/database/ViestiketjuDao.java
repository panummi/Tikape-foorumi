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
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Keskustelualue;
import tikape.runko.domain.Viestiketju;
import java.sql.Timestamp;


public class ViestiketjuDao implements Dao<Viestiketju, Integer> {

    private Database database;
    private Dao<Keskustelualue, Integer> keskustelualueDao;
    //private Keskustelualue keskustelualue;

    public ViestiketjuDao(Database database, Dao<Keskustelualue, Integer> keskustelualueDao) {
        this.database = database;
        this.keskustelualueDao = keskustelualueDao;
    }

    
    @Override
    public Viestiketju findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju WHERE tunnus = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer tunnus = rs.getInt("tunnus");
        String otsikko = rs.getString("otsikko");
        Timestamp aika = rs.getTimestamp("aika");
        Integer luku = countViestit(tunnus);
        Timestamp viimeisinViesti = viimeisinViesti(tunnus);

        Viestiketju v = new Viestiketju(tunnus, otsikko, aika, luku, viimeisinViesti);

        rs.close();
        stmt.close();
        connection.close();

        return v;
    }

    @Override
    public List<Viestiketju> findAll(Integer key) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Viestiketju.* FROM Viestiketju, Keskustelualue, Viesti WHERE Keskustelualue.tunnus = Viestiketju.keskustelualue AND Viestiketju.tunnus = Viesti.viestiketju AND Keskustelualue.tunnus = ? GROUP BY Viestiketju.otsikko ORDER BY Viesti.aika  DESC LIMIT 10");
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();
        List<Viestiketju> viestiketjut = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("tunnus");
            String nimi = rs.getString("otsikko");
            Timestamp aika = rs.getTimestamp("aika");
            Integer luku = countViestit(id);
            Timestamp viimeisinViesti = viimeisinViesti(id);

            viestiketjut.add(new Viestiketju(id, nimi, aika, luku, viimeisinViesti));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestiketjut;
        
    }

    
    public void submitKetju(String name, Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viestiketju (otsikko, keskustelualue, aika) VALUES (?, ?, ?)");
        Timestamp lisays = new Timestamp(System.currentTimeMillis());
        stmt.setInt(2, key);
        stmt.setString(1, name);
        stmt.setTimestamp(3, lisays);
        stmt.executeUpdate();
        //rs.close();
        stmt.close();
        connection.close();
    }
     
    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
    
    public Integer countViestit(Integer tunnus) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*)  FROM Viesti, Viestiketju WHERE Viestiketju.tunnus = Viesti.viestiketju AND Viestiketju.tunnus =" + tunnus);
        ResultSet rs = stmt.executeQuery();
        Integer luku = rs.getInt(1);
        rs.close();
        stmt.close();
        connection.close();
        return luku;
    }
    
    public Timestamp viimeisinViesti(Integer tunnus) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT datetime(Viesti.aika, 'localtime') FROM Viesti, Viestiketju WHERE Viesti.viestiketju = Viestiketju.tunnus  AND Viestiketju.tunnus =" + tunnus + " ORDER BY Viesti.aika DESC LIMIT 1");
        ResultSet rs = stmt.executeQuery();
        if (!rs.isBeforeFirst() ) {    
            return null;
        } 
        String viestiString = rs.getString(1);
        Timestamp viimeisinViesti = Timestamp.valueOf(viestiString);
        //Timestamp viimeisinViesti = rs.getTimestamp(1);
        rs.close();
        stmt.close();
        connection.close();
        return viimeisinViesti;
    }

    public Integer findLatest() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju ORDER BY tunnus DESC LIMIT 1");
        ResultSet rs = stmt.executeQuery();
        Integer luku = rs.getInt(1);
        rs.close();
        stmt.close();
        connection.close();

        return luku;
    }
}
