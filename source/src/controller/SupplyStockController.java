package controller;

import aux.Validations;
import aux.dialogs.ConfirmationDialog;
import aux.dialogs.ErrorDialog;
import aux.dialogs.InformationDialog;
import aux.exceptions.FormException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Product;

import java.util.Optional;

public class SupplyStockController {

    @FXML
    private GridPane supplyStockWindow;

    @FXML
    private ComboBox<Product> bulkProductComboBox;

    @FXML
    private TextField quantityTextField;

    @FXML
    void initialize(){
        loadBulkProducts();
    }

    @FXML
    void actionCancel(){
        supplyStockWindow.getScene().getWindow().hide();
    }

    private void loadBulkProducts() {
        ObservableList<Product> oList = FXCollections.observableArrayList(Product.allBulk());
        bulkProductComboBox.setItems(oList);
    }

    @FXML
    void actionOK(ActionEvent event) {
        try {
            Validations.isSelected(bulkProductComboBox, "Produto bruto");
            Validations.isEmpty(quantityTextField.getText());
            Validations.isNumeric(quantityTextField.getText());

            Product product = bulkProductComboBox.getValue();
            Float newStockAmount = product.getStockAmount() + Float.parseFloat(quantityTextField.getText());
            product.setStockAmount(newStockAmount);

            Alert alert = new ConfirmationDialog("Tem certeza que deseja abastecer "+quantityTextField.getText()+"KG\nde "+product.getName());
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.YES) {
                product.save();

                quantityTextField.setText("");
                Alert confirmedSupply = new InformationDialog("Estoque de "+bulkProductComboBox.getValue().getName()+" abastecido.");
                confirmedSupply.showAndWait();
            }
        }catch (FormException e){
            Alert alert = new ErrorDialog(e.getMessage());
            alert.showAndWait();
        }catch(NumberFormatException e){
            Alert alert = new ErrorDialog("Insira um número válido.");
            alert.showAndWait();
        }
    }

}
