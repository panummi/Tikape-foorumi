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
import tikape.runko.domain.Opiskelija;
import tikape.runko.domain.Viesti;

/**
 *
 * @author Reetta
 */
public class ViestiDao implements Dao<Viesti, Integer> {
    
    private Database database;
    
    public ViestiDao(Database database) {
        this.database = database;
    }
    
    @Override
    public Viesti findOne(Integer key) throws SQLException {
        return null;
    }
    
    @Override
    public List<Viesti> findAll() throws SQLException {
        return null;
    }
    
    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
    
}