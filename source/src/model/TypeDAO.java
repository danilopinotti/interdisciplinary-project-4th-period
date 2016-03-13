package model;

import java.util.List;

/**
 * Created by danilopinotti on 13/11/15.
 */
public interface TypeDAO {
    Type findById(Integer id);
    List<Type> all();
}
