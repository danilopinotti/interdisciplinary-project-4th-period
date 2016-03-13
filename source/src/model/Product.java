package model;

import model.mysql.MySQLProductDao;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by danilopinotti on 04/11/15.
 */
public class Product extends Base {
    private String name;
    private Type type;
    private Float unit_cost;
    private Float stock_amount;
    private Product product_bulk = null;

    private static ProductDAO dao = new MySQLProductDao();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Float getUnitCost() {
        return unit_cost;
    }

    public void setUnitCost(Float unit_cost) {
        this.unit_cost = unit_cost;
    }

    public Float getStockAmount() {
        return stock_amount;
    }

    public void setStockAmount(Float stock_amount) {
        this.stock_amount = stock_amount;
    }

    public String getTypeName(){
        return this.type.getName();
    }

    public Product(Integer id, String name, Type type, Product product_bulk, Float unit_cost, Float stock_amount, Timestamp created_at, Timestamp last_update) {
        super(id, created_at, last_update);
        this.name = name;
        this.type = type;
        this.product_bulk = product_bulk;
        this.unit_cost = unit_cost;
        this.stock_amount = stock_amount;
    }

    public String toString(){
        return this.name;
    }

    public Product(){
        super(null, null, null);
    }   //Create a empty product

    public void delete(){
        dao.delete(this);
    }

    public void save(){
        if(dao.findById(id) != null)
            dao.update(this);
        else
            dao.create(this);
    }

    public static List<Product> all(){
        return dao.all();
    }

    public static List<Product> findByName(String name){
        return dao.findByName(name);
    }

    public static Product findById(Integer id){
        return dao.findById(id);
    }

    public static List<Product> allBulk() { return findByType(1);}

    public Product getProductBulk() {
        return this.product_bulk;
    }

    public String getProductBulkName(){
        if(product_bulk != null)
            return product_bulk.getName();
        else
            return "-";
    }

    public void setProductBulk(Product product_bulk) {
        this.product_bulk = product_bulk;
    }

    public static List<Product> findByType(Integer type_id){
        return dao.findByType(type_id);
    }

    public static List<Product> findByType(Type type) {
        return dao.findByType(type.getId());
    }
}
