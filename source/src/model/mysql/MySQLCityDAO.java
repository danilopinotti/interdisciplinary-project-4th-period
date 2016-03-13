package model.mysql;

import model.City;
import model.CityDAO;
import model.State;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by danilopinotti on 17/11/15.
 */
public class MySQLCityDAO extends MySQLMainDatabase implements CityDAO {
    @Override
    public City findById(Integer id){
        City result = null;
        openDB();

        try{
            pstm = conn.prepareStatement("SELECT * FROM cities WHERE id = ?");
            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();

            if(rs.next())
                result  = new City(rs.getInt("id"), rs.getString("name"), State.findById(rs.getInt("states_id")));
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeDB();
        }

        return result;
    }

    @Override
    public List<City> all() {
        ArrayList<City> result = new ArrayList<>();
        openDB();

        try{
            ResultSet rs = stm.executeQuery("SELECT * FROM cities ORDER BY name ASC;");
            while(rs.next()){
                City c  = new City(rs.getInt("id"), rs.getString("name"), State.findById(rs.getInt("states_id")));
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
