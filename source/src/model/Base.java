package model;

import java.sql.Timestamp;

/**
 * Created by danilopinotti on 04/11/15.
 */
public class Base {
    protected Integer id;
    protected Timestamp created_at;
    protected Timestamp last_update;

    public Base(Integer id, Timestamp created_at, Timestamp last_update){
        this.id = id;
        this.created_at = created_at;
        this.last_update = last_update;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Timestamp getCreatedAt() {
        return created_at;
    }
    public void setCreatedAt(Timestamp created_at) {
        this.created_at = created_at;
    }
    public Timestamp getLastUpdate() {
        return last_update;
    }
    public void setLastUpdate(Timestamp last_update) {
        this.last_update = last_update;
    }
}
