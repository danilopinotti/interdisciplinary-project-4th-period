package model;

import model.mysql.MySQLCityDAO;

import java.util.List;

/**
 * Created by danilopinotti on 04/11/15.
 */
public class City extends Base {

    private static CityDAO dao = new MySQLCityDAO();

    private String name;
    private State state;

    public City(Integer id, String name,State state){
        super(id, null, null);
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public static City findById(Integer id){
       return dao.findById(id);
    }

    public static List<City> all(){
        return dao.all();
    }

}
