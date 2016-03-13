package model.mysql;

import model.Type;
import model.TypeDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danilopinotti on 17/11/15.
 */
public class MySQLTypeDAO extends MySQLMainDatabase implements TypeDAO{

    @Override
    public Type findById(Integer id) {
        Type result = null;
        openDB();

        try{
            pstm = conn.prepareStatement("SELECT * FROM types WHERE id = ?");
            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();

            if(rs.next())
                result  = new Type(rs.getInt("id"), rs.getString("name"), rs.getFloat("weight"));
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeDB();
        }

        return result;
    }

    @Override
    public List<Type> all() {
        ArrayList<Type> result = new ArrayList<>();
        openDB();
        try{
            ResultSet rs = stm.executeQuery("SELECT * FROM types ORDER BY id ASC;");
            while(rs.next())
                result.add(new Type(rs.getInt("id"), rs.getString("name"), rs.getFloat("weight")));

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeDB();
        }
        return result;
    }
}
