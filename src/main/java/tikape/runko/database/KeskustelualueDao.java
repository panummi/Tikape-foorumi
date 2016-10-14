
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Keskustelualue;
import java.sql.Timestamp;

public class KeskustelualueDao implements Dao<Keskustelualue, Integer> {

    private Database database;
    private Keskustelualue keskustelualue;

    public KeskustelualueDao(Database database) {
        this.database = database;
    }

    @Override
    public Keskustelualue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelualue WHERE tunnus = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer tunnus = rs.getInt("tunnus");
        String nimi = rs.getString("nimi");
        Integer luku = countViestit(tunnus);
        Timestamp viimeisinViesti = viimeisinViesti(tunnus);

        Keskustelualue k = new Keskustelualue(tunnus, nimi, luku, viimeisinViesti);

        rs.close();
        stmt.close();
        connection.close();

        return k;
    }

    
    public List<Keskustelualue> findAllFrom() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelualue");

        ResultSet rs = stmt.executeQuery();
        List<Keskustelualue> keskustelualueet = new ArrayList<>();
        while (rs.next()) {
            Integer tunnus = rs.getInt("tunnus");
            String nimi = rs.getString("nimi");
            Integer luku = countViestit(tunnus);
            Timestamp viimeisinViesti = viimeisinViesti(tunnus);

            keskustelualueet.add(new Keskustelualue(tunnus, nimi, luku, viimeisinViesti));
        }

        rs.close();
        stmt.close();
        connection.close();
        
        return keskustelualueet;
    }
    
    @Override
    
    public List<Keskustelualue> findAll(Integer key) throws SQLException {
        return null;
    }
    
    
    
        //@Override
    public Integer countViestit(Integer tunnus) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM Viesti, Viestiketju, Keskustelualue WHERE Viesti.viestiketju = Viestiketju.tunnus AND Viestiketju.keskustelualue = Keskustelualue.tunnus AND Keskustelualue.tunnus =" + tunnus);
        ResultSet rs = stmt.executeQuery();
        Integer luku = rs.getInt(1);
        rs.close();
        stmt.close();
        connection.close();
        return luku;
        
    }
    
    public Timestamp viimeisinViesti(Integer tunnus) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT datetime(Viesti.aika, 'localtime') FROM Viesti, Viestiketju, Keskustelualue WHERE Viesti.viestiketju = Viestiketju.tunnus AND Viestiketju.keskustelualue = Keskustelualue.tunnus AND Keskustelualue.tunnus =" + tunnus + " ORDER BY Viesti.aika DESC LIMIT 1");
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

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
    
    public void submitAlue(String key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Keskustelualue (nimi) VALUES (?)");
        stmt.setString(1, key);
        stmt.executeUpdate();
        //rs.close();
        stmt.close();
        connection.close();
    }
    
}
