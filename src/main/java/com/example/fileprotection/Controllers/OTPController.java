package com.example.fileprotection.Controllers;


import com.example.fileprotection.Utils.CryptoUtil;
import com.example.fileprotection.Utils.DialogUtil;
import com.example.fileprotection.Utils.EmailUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OTPController implements Initializable {
    @FXML
    private PasswordField passwordTxt;

    private String pass;

    private DialogUtil dialogUtil;

    private CryptoUtil cryptoUtil;
    private int countPass=0;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cryptoUtil=new CryptoUtil();
        dialogUtil=new DialogUtil();
    }


    @FXML
    void confirmHandler(ActionEvent event) throws IOException {

        if(!passwordTxt.getText().equals(pass)){
            dialogUtil.showAlert("Error","Incorrect password!", Alert.AlertType.ERROR);


            countPass++;
            if(countPass>=5){

                dialogUtil.showAlert("Error","Try too many time! Changing pasword...", Alert.AlertType.ERROR);
                String token=cryptoUtil.genRandomAlphaNumeric(10);
                pass=token;
                countPass=0;
                EmailUtil.sendEmail("Changing password","New password: "+token);

            }


            return ;
        }
        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(OTPController.class.getResource("/com/example/fileprotection/layouts/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    public void setValue(String pass){
        this.pass=pass;
    }
}
