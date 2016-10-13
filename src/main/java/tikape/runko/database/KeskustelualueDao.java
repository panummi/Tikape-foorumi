
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Keskustelualue;

public class KeskustelualueDao implements Dao<Keskustelualue, Integer> {

    private Database database;

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

        Keskustelualue k = new Keskustelualue(tunnus, nimi);

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

            keskustelualueet.add(new Keskustelualue(tunnus, nimi));
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
    public Integer countViestit(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM Viesti, Viestiketju, Keskustelualue WHERE Viesti.viestiketju = Viestiketju.tunnus AND Viestiketju.keskustelualue = Keskustelualue.tunnus AND Keskustelualue.tunnus = ?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        Integer luku = rs.getInt(1);
        rs.close();
        stmt.close();
        connection.close();
        //if (columnValue == null) {
        //    return 0;
        //}
        return luku;
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
