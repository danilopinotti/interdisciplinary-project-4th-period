package model;

import aux.exceptions.LoginException;
import model.mysql.MySQLUserDAO;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by danilopinotti on 04/11/15.
 */
public class User extends Base {
    private String name;
    private String email;
    private String password;
    private Timestamp last_access;
    private static UserDAO dao = new MySQLUserDAO();

//    public User(Integer id, String name, String email, String password) {
//        super(id, null, null);
//        this.name = name;
//        this.email = email;
//        this.password = password;
//    }

    public User(Integer id, String name, String email, String password, Timestamp created_at, Timestamp last_update, Timestamp last_access) {
        super(id, created_at, last_update);
        this.name = name;
        this.email = email;
        this.password = password;
        this.last_access = last_access;
    }

    public User() {
        super(null,null,null);
    }

    public static User autenticate(String email, String password) throws LoginException{
        return dao.autenticate(email, password);
    }

    public void updatePassword(){
        dao.updatePassword(this);
    }

    public static User findById(Integer id){
        return dao.findById(id);
    }

    public static List<User> findByName(String name){
        return dao.findByName(name);
    }

    public static List<User> all(){
        return dao.all();
    }

    public void save(){
        if(dao.findById(id) != null)
            dao.update(this);
        else
            dao.create(this);
    }

    public void delete(){
        dao.delete(this);
    }

    public void updateLastAccess(){
        dao.updateLastAccess(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getLastAccess() {
        return last_access;
    }

    public void setLastAccess(Timestamp last_access) {
        this.last_access = last_access;
    }

    public String getCreatedAtString(){
        if(created_at != null)
            return this.created_at.toString();
        else
            return "-";
    }

    public String getLastAccessString(){
        if(last_access != null)
            return this.last_access.toString();
        else
            return "-";
    }

}
