package controller;

import aux.Validations;
import aux.dialogs.RemoveDialog;
import aux.dialogs.WarningDialog;
import aux.exceptions.FormException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Product;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ListProductController {

    @FXML
    private TextField textFieldSearch;

    @FXML
    private TableView<Product> tableProducts;


    @FXML
    private GridPane listProductWindow;

    @FXML
    public void initialize(){
        updateStockList(Product.all());
        setupListeners();
    }

    @FXML
    void actionCreateProduct(ActionEvent event) throws IOException{
        showViewProduct("Criar", null);
    }

    @FXML
    void actionEditProduct(ActionEvent event) throws IOException{
        try {
            Validations.isSelected(tableProducts);
            showViewProduct("Atualizar", tableProducts.getSelectionModel().getSelectedItem());
        }catch(FormException e){
            Alert alert = new WarningDialog(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void actionRemoveProduct(ActionEvent event) throws IOException{
        try {
            Validations.isSelected(tableProducts);

            Alert alert = new RemoveDialog("produto");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                Product toRemove = tableProducts.getSelectionModel().getSelectedItem();
                toRemove.delete();
                updateStockList(Product.all());
            }
        }catch(FormException e){
            Alert alert = new WarningDialog(e.getMessage());
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    @FXML
    void actionFindProduct(ActionEvent event) {
            updateStockList(Product.findByName(textFieldSearch.getText()));
    }

    private void setupListeners(){
        textFieldSearch.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                updateStockList(Product.findByName(textFieldSearch.getText()));
            }
        });
    }

    private void updateStockList(List<Product> elements){
        ObservableList<Product> oList = FXCollections.observableArrayList(elements);
        tableProducts.getColumns().get(0).setCellValueFactory(
                new PropertyValueFactory<>("id")    //atributo do model. O mesmo que esta aqui temq eu estar no model e no banco
        );
        tableProducts.getColumns().get(1).setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        tableProducts.getColumns().get(2).setCellValueFactory(
                new PropertyValueFactory<>("typeName")
        );
        tableProducts.getColumns().get(3).setCellValueFactory(
                new PropertyValueFactory<>("productBulkName")
        );
        tableProducts.getColumns().get(4).setCellValueFactory(
                new PropertyValueFactory<>("unitCost")
        );

        tableProducts.getColumns().get(5).setCellValueFactory(
                new PropertyValueFactory<>("stockAmount")
        );

        tableProducts.setItems(oList);
    }

    private void showViewProduct(String buttonTitle, Product product) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewProductWindow.fxml"));
        Parent root = loader.load();
        ViewProductController controller = loader.getController();
        controller.setTitle(buttonTitle+" Produto");
        if(product != null){
            controller.setProduct(product);
        }
        stage.setScene(new Scene(root));
        stage.setTitle(buttonTitle + " Produto");
        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateStockList(Product.all());
            }
        });
        stage.show();
    }

}