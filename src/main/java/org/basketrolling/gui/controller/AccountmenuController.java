/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.basketrolling.beans.Login;
import org.basketrolling.dao.LoginDAO;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.LoginService;

/**
 *
 * @author Marko
 */
public class AccountmenuController implements Initializable, MainBorderSettable {

    LoginDAO dao;
    LoginService service;

    private Login aktuellerLogin;

    @FXML
    private Label lVorname;

    @FXML
    private Label lNachname;

    @FXML
    private Label lBenutzername;

    @FXML
    private Label lRolle;

    @FXML
    private Button passwortChange;
    
    @FXML
    private BorderPane borderPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new LoginDAO();
        service = new LoginService(dao);
    }

    public void initLogin(Login login) {
        this.aktuellerLogin = login;

        lVorname.setText(login.getVorname());
        lNachname.setText(login.getNachname());
        lBenutzername.setText(login.getBenutzername());
        lRolle.setText(login.getRolle().toString());
    }
    
     public void setMainBorder(BorderPane mainBorderPane) {
        this.borderPane = mainBorderPane;
    }


    public void passwortChange() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/login/passwortcheck.fxml"));
            Parent passwortMenu = loader.load();
            
            PasswortChangeController controller = loader.getController();
            controller.initLogin(aktuellerLogin);
            controller.setMainBorder(borderPane);

            borderPane.setCenter(passwortMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
