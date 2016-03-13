package controller;

import aux.dialogs.ConfirmationDialog;
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
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Product;
import model.User;


import javax.swing.*;
import java.io.IOException;
import java.util.Optional;

public class IndexController {

    @FXML
    private BorderPane mainWindowRoot;

    @FXML
    private MenuItem menuItemProfileEdit;

    @FXML
    private MenuItem menuItemCreateUser;

    @FXML
    private TableView<Product> tableStock;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    private Button btnManageUsers;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnProvideStock;

    @FXML
    private Button btnSell;

    @FXML
    private MenuItem menuItemListProducts;

    @FXML
    private MenuItem menuItemCreateClient;

    @FXML
    private MenuItem menuItemListClients;

    @FXML
    private Button btnReports;

    @FXML
    private Button btnManageProducts;

    @FXML
    private MenuItem menuItemListUsers;

    @FXML
    private Button btnSack;

    @FXML
    private Label labelUser;

    @FXML
    private Label labelLastAccess;

    @FXML
    private MenuItem menuItemCreateProduct;

    @FXML
    private MenuItem menuItemSackProduct;

    private User user;

    @FXML
    public void actionCreateProduct() throws IOException {
        showViewProduct("Criar");
    }

    @FXML
    public void actionSell(ActionEvent e) {
        System.out.println("test");
    }

    @FXML
    public void actionManageProducts() throws IOException {
        showListProduct();
    }

    @FXML
    void actionListUsers(ActionEvent event) throws IOException {
        showListUser();
    }

    public void initialize(){
        updateStockList();
    }

    public void loadUserInfo(){
        labelUser.setText(user.getName());
        if(user.getLastAccess() != null)
            labelLastAccess.setText(user.getLastAccess().toString());
        else
            labelLastAccess.setVisible(false);
    }

    public void setUser(User u){
        this.user = u;
        loadUserInfo();
    }

    private void updateStockList() {
        ObservableList<Product> oList = FXCollections.observableArrayList(Product.all());
        tableStock.getColumns().get(0).setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        tableStock.getColumns().get(1).setCellValueFactory(
                new PropertyValueFactory<>("typeName")
        );
        tableStock.getColumns().get(2).setCellValueFactory(
                new PropertyValueFactory<>("productBulkName")
        );
        tableStock.getColumns().get(3).setCellValueFactory(
                new PropertyValueFactory<>("stockAmount")
        );
        tableStock.setItems(oList);
    }

    private void showViewProduct(String buttonTitle) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewProductWindow.fxml"));
        Parent root = loader.load();

        stage.getIcons().add(new Image("/view/assets/images/icon.png"));
        stage.setScene(new Scene(root));
        stage.setTitle(buttonTitle + " Produto");

        ViewProductController controller = loader.getController();
        controller.setTitle(buttonTitle + " Produto");

        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateStockList();
            }
        });

        stage.show();
    }



    private void showListProduct() throws IOException{
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/listProductWindow.fxml"));
        Parent root = loader.load();

        stage.getIcons().add(new Image("/view/assets/images/icon.png"));
        stage.setScene(new Scene(root));
        stage.setTitle("Listagem de produtos");

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainWindowRoot.getScene().getWindow());

        ListProductController controllerListProduct = loader.getController();

        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateStockList();
            }
        });

        stage.show();
    }

    private void showListUser() throws IOException{
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/listUserWindow.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.setTitle("Listagem de usuarios");

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainWindowRoot.getScene().getWindow());
        stage.getIcons().add(new Image("/view/assets/images/icon.png"));

        //ListUserController controllerListUser = loader.getController();
        //controllerListUser.setUser(user);

        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                loadUserInfo();
            }
        });

        stage.show();
    }

    @FXML
    void actionAbout(ActionEvent event) throws IOException {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/aboutWindow.fxml"));
        Parent root = loader.load();

        stage.getIcons().add(new Image("/view/assets/images/icon.png"));
        stage.setScene(new Scene(root));
        stage.setTitle("Sobre");

        stage.show();
    }

    @FXML
    void actionSupplyStock(ActionEvent event) throws IOException {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/supplyStockWindow.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.setTitle("Abastecer estoque");

        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateStockList();
            }
        });

        stage.show();
    }

    @FXML
    void actionCreateUser(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewUserWindow.fxml"));
        Parent root = loader.load();
        ViewUserController controller = loader.getController();
        controller.setTitle("Criar usuario");

        stage.getIcons().add(new Image("/view/assets/images/icon.png"));
        stage.setScene(new Scene(root));
        stage.setTitle("Criar usuario");
        stage.show();
    }

    @FXML
    void actionEditProfile(ActionEvent event) throws IOException {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewProfileWindow.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.setTitle("Editar perfil");
        stage.getIcons().add(new Image("/view/assets/images/icon.png"));

        ViewProfileController controllerViewProfile = loader.getController();
        controllerViewProfile.setUser(user);
        controllerViewProfile.setTitle("Editar perfil");

        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                loadUserInfo();
            }
        });

        stage.show();
    }

    @FXML
    void actionSack(ActionEvent event) throws IOException{
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sackWindow.fxml"));
        Parent root = loader.load();
        SackController controllerSack = loader.getController();
        controllerSack.setStage(stage);

        stage.setScene(new Scene(root));
        stage.setTitle("Ensacamendialto de produtos");
        stage.getIcons().add(new Image("/view/assets/images/icon.png"));

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainWindowRoot.getScene().getWindow());

        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateStockList();
            }
        });

        stage.show();
    }

    @FXML
    void actionLogout(ActionEvent event) throws IOException {
        Alert logoutConfirmation = new ConfirmationDialog("Tem certeza de que deseja encerrar a sessão?");
        Optional<ButtonType> resultLogout = logoutConfirmation.showAndWait();
        if (resultLogout.get() == ButtonType.YES) {
            user = null;
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/loginWindow.fxml"));
            Parent root = loader.load();
            stage.setTitle("Login");
            stage.getIcons().add(new Image("/view/assets/images/icon.png"));
            stage.setScene(new Scene(root, 600, 250));
            stage.show();
            mainWindowRoot.getScene().getWindow().hide();
        }
    }

}
