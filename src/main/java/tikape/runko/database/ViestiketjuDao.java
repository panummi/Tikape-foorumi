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

        Viestiketju v = new Viestiketju(tunnus, otsikko, aika);

        rs.close();
        stmt.close();
        connection.close();

        return v;
    }

    @Override
    public List<Viestiketju> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju");

        ResultSet rs = stmt.executeQuery();
        List<Viestiketju> viestiketjut = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("tunnus");
            String nimi = rs.getString("otsikko");
            Timestamp aika = rs.getTimestamp("aika");
            //Keskustelualue keskustelualue = rs.(keskustelualue");

            viestiketjut.add(new Viestiketju(id, nimi, aika));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestiketjut;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

    
}
