package com.example.fileprotection;

import com.example.fileprotection.Controllers.OTPController;
import com.example.fileprotection.Utils.CryptoUtil;
import com.example.fileprotection.Utils.EmailUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private CryptoUtil cryptoUtil=new CryptoUtil();
    @Override
    public void start(Stage stage) throws IOException {

        char b= (char) ('a'+13);

        String k=new StringBuilder("a").append(b).toString();
        String token=cryptoUtil.genRandomAlphaNumeric(10);
        System.out.println(token);
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/example/fileprotection/layouts/otp-dialog.fxml"));
        OTPController controller=new OTPController();
        controller.setValue(token);
        loader.setController(controller);

        Parent root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene editScene = new Scene(root);
        window.setTitle("OTP");
        window.setScene(editScene);
        window.showAndWait();

    }

    public static void main(String[] args) {
        launch();
    }
}