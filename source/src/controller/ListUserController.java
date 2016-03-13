package controller;

import aux.Validations;
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
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class ListUserController {

    @FXML
    private GridPane listProductWindow;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnRemove;

    @FXML
    private TextField textFieldSearch;

    @FXML
    private Button btnCreate;

    @FXML
    private TableView<User> tableUsers;

    @FXML
    void actionCreateUser(ActionEvent event) throws IOException {
        showViewUser("Criar", null);
    }

    @FXML
    void actionEditUser(ActionEvent event) throws IOException {
        try {
            Validations.isSelected(tableUsers);
            showViewUser("Atualizar", tableUsers.getSelectionModel().getSelectedItem());
        }
        catch (FormException e){
            Alert alert = new WarningDialog(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void actionRemoveUser(ActionEvent event) {
        try {
            Validations.isSelected(tableUsers);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja remover este usuário ?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.YES) {
                //delete current record in database
                User toRemove = tableUsers.getSelectionModel().getSelectedItem();
                toRemove.delete();
                updateUserList(User.all());
            }
        }catch(FormException e){
            Alert notSelected = new WarningDialog(e.getMessage());
            notSelected.showAndWait();
        }
    }

    @FXML
    void actionFindUser(ActionEvent event) {
        updateUserList(
                User.findByName(textFieldSearch.getText())
        );
    }

    @FXML
    void initialize(){
        updateUserList(User.all());
    }

    private void updateUserList(List<User> elements){
        ObservableList<User> oList = FXCollections.observableArrayList(elements);
        tableUsers.getColumns().get(0).setCellValueFactory(
                new PropertyValueFactory<>("id")    //atributo do model. O mesmo que esta aqui temq eu estar no model e no banco
        );
        tableUsers.getColumns().get(1).setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        tableUsers.getColumns().get(2).setCellValueFactory(
                new PropertyValueFactory<>("email")
        );
        tableUsers.getColumns().get(3).setCellValueFactory(
                new PropertyValueFactory<>("lastAccessString")
        );
        tableUsers.getColumns().get(4).setCellValueFactory(
                new PropertyValueFactory<>("createdAtString")
        );
        tableUsers.setItems(oList);
    }

    private void showViewUser(String buttonTitle, User user) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewUserWindow.fxml"));
        Parent root = loader.load();
        ViewUserController controller = loader.getController();
        controller.setTitle(buttonTitle + " Usuario");
        if(user != null){
            controller.setUser(user);
        }
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("/view/assets/images/icon.png"));
        stage.setTitle(buttonTitle + " Usuario");
        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateUserList(User.all());
            }
        });
        stage.show();
    }

}
