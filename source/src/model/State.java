package model;

import model.mysql.MySQLStateDAO;

import java.util.List;

/**
 * Created by danilopinotti on 04/11/15.
 */
public class State extends Base{
    private String acronym;

    private static StateDAO dao = new MySQLStateDAO();

    public State(Integer id, String acronym){
        super(id, null, null);
        this.acronym = acronym;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public static State findById(Integer id){
        return dao.findById(id);
    }

    public static List<State> all(){
        return dao.all();
    }
}
