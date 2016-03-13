package model;

import java.util.List;

/**
 * Created by danilopinotti on 17/11/15.
 */
public interface StateDAO {
    State findById(Integer id);
    List<State> all();
}
