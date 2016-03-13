package controller;

import aux.Validations;
import aux.dialogs.ConfirmationDialog;
import aux.dialogs.ErrorDialog;
import aux.exceptions.FormException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Product;
import model.Type;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;


public class SackController {

    @FXML
    private ComboBox<Product> productBulk;

    @FXML
    private ComboBox<Product> finalProduct;

    @FXML
    private Label quantityAvailable;

    @FXML
    private ComboBox<Type> finalType;

    @FXML
    private Slider quantitySlide;

    @FXML
    private Label quantitySlideValue;

    @FXML
    private AnchorPane sackWindow;

//    @FXML
//    private TextField finalQuantity;

    private Stage stage;

    @FXML
    void initialize(){
        loadBulkProducts();
        loadTypes();
        quantityAvailable.setVisible(false);
        quantitySlideValue.setVisible(false);
//      finalQuantity.setDisable(true);
        setupListeners();
    }

    @FXML
    void actionOK(ActionEvent event) {
        try{
            Validations.isSelected(productBulk, "Produto bruto");
            Validations.isSelected(finalType, "Tipo de produto final");
            Validations.isSelected(finalProduct, "Produto final");

            Product bulk = productBulk.getSelectionModel().getSelectedItem();
            Product resultProduct = finalProduct.getSelectionModel().getSelectedItem();

            Float newBulkAmount = bulk.getStockAmount() - (finalType.getValue().getWeight()*(int)(quantitySlide.getValue()));
            Float newResultProductAmount = resultProduct.getStockAmount() + (int)(quantitySlide.getValue());
            bulk.setStockAmount(newBulkAmount);
            resultProduct.setStockAmount(newResultProductAmount);

            Alert alert = new ConfirmationDialog("Tem certeza que deseja ensacar este produto ?");
            Optional<ButtonType> resultConfirmation = alert.showAndWait();
            if(resultConfirmation.get() == ButtonType.YES) {
                resultProduct.save();
                bulk.save();
            }

            quantitySlide.setValue(0);
            loadQuantityBulk();
            setMaxSliderValue();

        }catch (FormException e){
            Alert alert = new ErrorDialog(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void actionComboBulk(ActionEvent event){
        loadQuantityBulk();
        setMaxSliderValue();
        loadFinalProducts();
    }

    @FXML
    void actionComboTypes(ActionEvent event){
        setMaxSliderValue();
        loadFinalProducts();
    }

    @FXML
    void actionCancel(ActionEvent event) { stage.hide(); }

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setResizable(false);
    }

    private void setMaxSliderValue() {
        if(finalType.getSelectionModel().getSelectedIndex() != -1 && productBulk.getSelectionModel().getSelectedIndex() != -1){
            quantitySlideValue.setVisible(true);
            quantitySlide.setValue(0);
            quantitySlideValue.setText("0");
            quantitySlide.setMax((int)(productBulk.getValue().getStockAmount() / finalType.getValue().getWeight()));
        }
    }

    private void loadQuantityBulk(){    //show quantity of bulk product selected in combobox on label
        quantityAvailable.setVisible(true);
        quantityAvailable.setText(productBulk.getValue().getStockAmount().toString() + " KG");
    }

    public void loadBulkProducts(){
        ObservableList<Product> oList = FXCollections.observableArrayList(Product.allBulk());
        productBulk.setItems(oList);
    }

    private void loadFinalProducts() {
        if(finalType.getValue() != null && productBulk.getValue() != null) {
            try {
                finalProduct.setDisable(false);
                finalProduct.setItems(null);

                ObservableList<Product> oList = FXCollections.observableArrayList();

                //Load ObservableList without respective bulk product
                Iterator<Product> it = Product.findByType(finalType.getValue().getId()).iterator();
                while (it.hasNext()) {
                    Product p = it.next();
                    if (p.getProductBulk().getId() == productBulk.getValue().getId())
                        oList.add(p);
                }

                Validations.isEmpty(oList, "Produto final");
                finalProduct.setItems(oList);
            }catch (FormException e){
                finalProduct.setDisable(true);
                Alert alert = new ConfirmationDialog("Nenhum produto final disponível.\nDeseja criar um agora ?");
                Optional<ButtonType> result= alert.showAndWait();
                if(result.get() == ButtonType.YES)
                    createNewProduct(finalType.getValue(), productBulk.getValue());
            }
        }
    }

    private void createNewProduct(Type finalType, Product bulkProduct){
        try {
            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewProductWindow.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("Listagem de usuarios");

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(sackWindow.getScene().getWindow());

            ViewProductController controllerViewProduct = loader.getController();
            controllerViewProduct.setTitle("Criar novo produto");
            controllerViewProduct.setFinalType(finalType);
            controllerViewProduct.setProductBulk(bulkProduct);

            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    loadFinalProducts();
                }
            });

            stage.show();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void loadTypes(){
        ObservableList<Type> oList = FXCollections.observableArrayList(Type.all());
        oList.remove(0);    //For not load Bulk Product as a final type
        finalType.setItems(oList);
    }

    private void setupListeners(){
        //On slide slider
        quantitySlide.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                Integer slideValue = (int)(quantitySlide.getValue());
                quantitySlideValue.setText(slideValue.toString());
            }
        });
    }
}