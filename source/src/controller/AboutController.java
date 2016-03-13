package controller;

import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class AboutController {

    @FXML
    private GridPane aboutWindow;

    @FXML
    void actionHyperlink(ActionEvent event) {
        try {
            new ProcessBuilder("x-www-browser", "www.monsterhunterbrasil.com").start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void actionClose(ActionEvent event){
        aboutWindow.getScene().getWindow().hide();
    }

}
