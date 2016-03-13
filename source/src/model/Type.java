package model;

import model.mysql.MySQLTypeDAO;

import java.util.List;

/**
 * Created by danilopinotti on 04/11/15.
 */
public class Type extends Base {
    private String name;
    private Float weight;

    private static TypeDAO dao = new MySQLTypeDAO();

    public Type(Integer id, String name, Float weight) {
        super(id, null, null);
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String toString(){
        return this.name;
    }

    public static Type findById(Integer id){
        return dao.findById(id);
    }

    public static List<Type> all(){
        return dao.all();
    }
}
