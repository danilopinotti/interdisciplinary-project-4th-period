package model;

import java.util.List;

/**
 * Created by danilopinotti on 18/11/15.
 */
public interface ProductDAO {
    void create(Product p);

    void delete(Product product);

    Product findById(Integer id);

    void update(Product product);

    List<Product> all();

    List<Product> findByName(String name);

    List<Product> findByType(Integer type_id);
}
