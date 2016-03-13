package model;

/**
 * Created by danilopinotti on 04/11/15.
 */
public class Status extends Base {
    private String name;

    public Status(Integer id, String name) {
        super(id, null, null);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Status findById(Integer id){
        Status status = new Status(1,"abc");

        return status;

    }
}
