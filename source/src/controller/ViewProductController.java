package controller;

import aux.Validations;
import aux.dialogs.ErrorDialog;
import aux.exceptions.FormException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Product;
import model.Type;

public class ViewProductController {

    @FXML
    private TextField unityCost;

    @FXML
    private TextField productName;

    @FXML
    private GridPane viewProductWindow;

    @FXML
    private ComboBox<Type> productType;

    @FXML
    private TextField stockAmount;

    @FXML
    private VBox bulkVBox;

    @FXML
    private ComboBox<Product> bulkProducts;

    @FXML
    private Label labelTitle;

    private Product product = null;

    public void initialize(){
        loadProductTypes(null);
    }

    @FXML
    void actionCancel(ActionEvent event) {
        viewProductWindow.getScene().getWindow().hide();
    }

    @FXML
    void actionComboBoxProductType(ActionEvent event){
        loadBulkProducts(null);
    }

    @FXML
    void actionBtnOK(ActionEvent event) {
        try {
            Validations.isSelected(productType, "Tipo de produto");
            Validations.isEmpty(productName.getText());
            Validations.isEmpty(stockAmount.getText());

            if(product == null)
                product = new Product();

            product.setUnitCost(Float.parseFloat(unityCost.getText()));
            product.setName(productName.getText());
            product.setType(productType.getValue());
            product.setStockAmount(Float.parseFloat(stockAmount.getText()));

            if(productType.getValue().getId() != 1)
                product.setProductBulk(bulkProducts.getValue());

            product.save();
            viewProductWindow.getScene().getWindow().hide();
        }catch(FormException e){
            Alert alert = new ErrorDialog(e.getMessage());
            alert.showAndWait();
        }catch (NumberFormatException ex){
            Alert alert = new ErrorDialog("O valor unitário e a quantidade em estoque deve ser numérico.");
            alert.showAndWait();
        }
    }

    public void loadBulkProducts(Product productBulkSelected){
        if(productType.getSelectionModel().getSelectedItem().getId() != 1){
            ObservableList<Product> oList = FXCollections.observableArrayList(Product.allBulk());
            bulkProducts.setItems(oList);
            bulkProducts.getSelectionModel().select(productBulkSelected);

            bulkVBox.setDisable(false);
        }
        else {
            bulkVBox.setDisable(true);
        }
    }

    public void setProduct(Product product) {
        this.product = product;

        unityCost.setText(product.getUnitCost().toString());
        productName.setText(product.getName());
        stockAmount.setText(product.getStockAmount().toString());

        loadProductTypes(product.getType());
        loadBulkProducts(product.getProductBulk());

        productType.setDisable(true);
        bulkProducts.setDisable(true);
    }

    public void setTitle(String title){
        labelTitle.setText(title);
    }

    public void loadProductTypes(Type typeSelected){
        ObservableList<Type> oList = FXCollections.observableArrayList(Type.all());
        productType.setItems(oList);
        if(typeSelected != null)
            productType.getSelectionModel().select(typeSelected);
    }


    public void setFinalType(Type finalType) {
        productType.setValue(finalType);
        productType.setDisable(true);
    }

    public void setProductBulk(Product bulkProduct){
        bulkProducts.setValue(bulkProduct);
        bulkProducts.setDisable(true);
    }
}


