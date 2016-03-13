package model.mysql;

import model.State;
import model.StateDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danilopinotti on 17/11/15.
 */
public class MySQLStateDAO extends MySQLMainDatabase implements StateDAO{
    @Override
    public State findById(Integer id){
        State result = null;
        openDB();

        try{
            pstm = conn.prepareStatement("SELECT * FROM states WHERE id = ?");
            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();

            if(rs.next())
                result  = new State(rs.getInt("id"), rs.getString("acronym"));
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeDB();
        }

        return result;
    }

    @Override
    public List<State> all() {
        ArrayList<State> result = new ArrayList<>();
        openDB();

        try{
            ResultSet rs = stm.executeQuery("SELECT * FROM states ORDER BY name ASC;");
            while(rs.next()){
                State c  = new State(rs.getInt("id"), rs.getString("acronym"));
                result.add(c);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeDB();
        }

        return result;
    }
}
