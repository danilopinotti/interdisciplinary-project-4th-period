package model;

import java.util.List;

/**
 * Created by danilopinotti on 17/11/15.
 */
public interface CityDAO {
    City findById(Integer id);
    List<City> all();
}
