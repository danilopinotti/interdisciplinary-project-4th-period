package controller;

import aux.Validations;
import aux.dialogs.ErrorDialog;
import aux.exceptions.FormException;
import aux.exceptions.LoginException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;

import java.io.IOException;
import java.util.Optional;

public class LoginController {

    @FXML
    private GridPane loginWindowRoot;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passwordField;

    public void initialize(){
        System.out.println("opened");
    }

    public void actionLogin(){
        try{
            Validations.isEmpty(userField.getText());
            Validations.isEmail(userField.getText());
            Validations.isEmpty(passwordField.getText());

            User user = User.autenticate(userField.getText(), passwordField.getText());
            showMainWindow(user);
        }
        catch(FormException e){
            Alert alert = new ErrorDialog(e.getMessage());
            Optional<ButtonType> result = alert.showAndWait();
        }
        catch(LoginException e){
            Alert alert = new ErrorDialog(e.getMessage());
            Optional<ButtonType> result = alert.showAndWait();
        }
        catch(IOException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public void showMainWindow(User user) throws IOException {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainWindow.fxml"));
        Parent root = loader.load();
        IndexController controller = loader.getController();
        controller.setUser(user);

        stage.setScene(new Scene(root));
        stage.setTitle("Sistema");
        stage.getIcons().add(new Image("/view/assets/images/icon.png"));

        stage.show();

        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                user.updateLastAccess();
            }
        });

        loginWindowRoot.getScene().getWindow().hide();
    }

}
