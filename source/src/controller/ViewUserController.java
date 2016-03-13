package controller;

import aux.Validations;
import aux.dialogs.WarningDialog;
import aux.exceptions.FormException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.User;


public class ViewUserController {

    @FXML
    private Button btnCancel;

    @FXML
    private TextField textName;

    @FXML
    private Button btnSave;

    @FXML
    private PasswordField textPassword;

    @FXML
    private TextField textEmail;

    @FXML
    private Label labelTitle;

    @FXML
    private GridPane viewUserWindow;

    private User user;

    @FXML
    void actionBtnOK(ActionEvent event) {
        try {
            Validations.isEmpty(textName.getText());
            Validations.isEmpty(textEmail.getText());
            Validations.isEmail(textEmail.getText());

            if (user == null) {  //if create
                user = new User();
                user.setPassword(textPassword.getText());
            }

            user.setName(textName.getText());
            user.setEmail(textEmail.getText());
            user.save();
            viewUserWindow.getScene().getWindow().hide();
        }catch(FormException e){
            Alert alert = new WarningDialog(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void actionCancel(ActionEvent event) {
        viewUserWindow.getScene().getWindow().hide();
    }

    void setTitle(String title){
        labelTitle.setText(title);
    }

    void setUser(User u){
        this.user = u;
        textName.setText(u.getName());
        textEmail.setText(u.getEmail());
        textPassword.setDisable(true);
    }

}
