package model.mysql;

import model.Product;
import model.ProductDAO;
import model.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danilopinotti on 18/11/15.
 */
public class MySQLProductDao extends MySQLMainDatabase implements ProductDAO{
    @Override
    public void create(Product product) {
        openDB();
        try{
            if(product.getType().getId() != 1) {
                pstm = conn.prepareStatement("INSERT INTO products " +
                        "VALUES(NULL,? ,?, ? ,? ,? ,NOW() ,NOW())");
                pstm.setString(1, product.getName());
                pstm.setInt(2, product.getType().getId());
                pstm.setInt(3, product.getProductBulk().getId());
                pstm.setFloat(4, product.getUnitCost());
                pstm.setFloat(5, product.getStockAmount());
            }
            else{
                pstm = conn.prepareStatement("INSERT INTO products " +
                        "VALUES(NULL,? ,?, NULL ,? ,? ,NOW() ,NOW())");
                pstm.setString(1, product.getName());
                pstm.setInt(2, product.getType().getId());
                pstm.setFloat(3, product.getUnitCost());
                pstm.setFloat(4, product.getStockAmount());
            }
            pstm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeDB();
        }
    }

    @Override
    public void delete(Product product) {
        openDB();

        try{
            pstm = conn.prepareStatement("DELETE FROM products WHERE id = ?");
            pstm.setInt(1, product.getId());
            pstm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeDB();
        }
    }

    @Override
    public void update(Product product) {
        openDB();
        try{
            if(product.getType().getId() != 1) {
                pstm = conn.prepareStatement("UPDATE products " +
                        "SET name = ?, types_id = ?, unit_cost = ?, stock_amount = ?, last_update = NOW(), products_id_bulk = ? " +
                        "WHERE id = ?");
                pstm.setString(1, product.getName());
                pstm.setInt(2, product.getType().getId());
                pstm.setFloat(3, product.getUnitCost());
                pstm.setFloat(4, product.getStockAmount());
                pstm.setFloat(5, product.getProductBulk().getId());
                pstm.setInt(6, product.getId());
                pstm.executeUpdate();
            }
            else{
                pstm = conn.prepareStatement("UPDATE products " +
                        "SET name = ?, types_id = ?, unit_cost = ?, stock_amount = ?, last_update = NOW()" +
                        "WHERE id = ?");
                pstm.setString(1, product.getName());
                pstm.setInt(2, product.getType().getId());
                pstm.setFloat(3, product.getUnitCost());
                pstm.setFloat(4, product.getStockAmount());
                pstm.setInt(5, product.getId());
                pstm.executeUpdate();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeDB();
        }
    }

    @Override
    public List<Product> all() {
        List<Product> result = new ArrayList<>();
        openDB();
        try{
            pstm = conn.prepareStatement("SELECT * FROM products ORDER BY id ASC");
            ResultSet rs = pstm.executeQuery();
            while(rs.next()) {
                result.add(new Product( rs.getInt("id"),
                        rs.getString("name"),
                        Type.findById(rs.getInt("types_id")),
                        Product.findById(rs.getInt("products_id_bulk")),
                        rs.getFloat("unit_cost"),
                        rs.getFloat("stock_amount"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("last_update")
                ));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeDB();
        }
        return result;
    }

    @Override
    public Product findById(Integer id) {
        if(id == null)
            return null;
        Product result = null;
        openDB();

        try{
            pstm = conn.prepareStatement("SELECT * FROM products WHERE id = ?");
            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();

            if(rs.next()) {
                result = new Product(rs.getInt("id"),
                        rs.getString("name"),
                        Type.findById(rs.getInt("types_id")),
                        Product.findById(rs.getInt("products_id_bulk")),
                        rs.getFloat("unit_cost"),
                        rs.getFloat("stock_amount"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("last_update")
                );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeDB();
        }
        return result;
    }

    @Override
    public List<Product> findByName(String name) {
        List<Product> result = new ArrayList<>();
        openDB();
        try{
            pstm = conn.prepareStatement("SELECT * FROM products WHERE name LIKE ? ORDER BY id ASC");
            pstm.setString(1, "%"+name+"%");
            ResultSet rs = pstm.executeQuery();
            while(rs.next()) {
                result.add(new Product( rs.getInt("id"),
                        rs.getString("name"),
                        Type.findById(rs.getInt("types_id")),
                        Product.findById(rs.getInt("products_id_bulk")),
                        rs.getFloat("unit_cost"),
                        rs.getFloat("stock_amount"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("last_update")
                ));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeDB();
        }
        return result;
    }

    @Override
    public List<Product> findByType(Integer type_id) {
        List<Product> result = new ArrayList<>();
        openDB();
        try{
            pstm = conn.prepareStatement("SELECT * FROM products WHERE types_id = ? ORDER BY id ASC");
            pstm.setInt(1, type_id);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()) {
                result.add(new Product( rs.getInt("id"),
                        rs.getString("name"),
                        Type.findById(rs.getInt("types_id")),
                        Product.findById(rs.getInt("products_id_bulk")),
                        rs.getFloat("unit_cost"),
                        rs.getFloat("stock_amount"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("last_update")
                ));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeDB();
        }
        return result;
    }
}
