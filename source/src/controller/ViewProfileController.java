package controller;

import aux.Encrypts;
import aux.Validations;
import aux.dialogs.ConfirmationDialog;
import aux.dialogs.ErrorDialog;
import aux.dialogs.InformationDialog;
import aux.exceptions.FormException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.User;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class ViewProfileController {

    @FXML
    private TextField textName;

    @FXML
    private PasswordField textPassword;

    @FXML
    private PasswordField textPasswordNew;

    @FXML
    private TextField textEmail;

    @FXML
    private PasswordField textPasswordNewAgain;

    @FXML
    private Label labelTitle;

    @FXML
    private GridPane viewUserWindow;

    private User user;

    @FXML
    void actionBtnOK(ActionEvent event) throws NoSuchAlgorithmException {
        try {
            Validations.isEmpty(textName.getText());
            Validations.isEmpty(textEmail.getText());
            Validations.isEmail(textEmail.getText());

            user.setName(textName.getText());
            user.setEmail(textEmail.getText());

            Alert alert = new ConfirmationDialog("Tem certeza que deseja aplicar estas alterações ?");
            Optional<ButtonType> resultAlert = alert.showAndWait();
            if(resultAlert.get() == ButtonType.YES) {
                if (Encrypts.SHA1(textPassword.getText()).equals(user.getPassword()) && textPasswordNew.getText().equals(textPasswordNewAgain.getText())) {
                    user.setPassword(textPasswordNew.getText());
                    user.updatePassword();
                }
                else{
                    Alert passwd = new InformationDialog("Senha não alterada");
                    passwd.showAndWait();
                }
                user.save();
                viewUserWindow.getScene().getWindow().hide();
            }

        }catch(FormException e){
            Alert alert = new ErrorDialog(e.getMessage());
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
    }

}
