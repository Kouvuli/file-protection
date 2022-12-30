package com.example.fileprotection.Controllers;


import com.example.fileprotection.DAO.AccountDAO;
import com.example.fileprotection.Entities.Account;
import com.example.fileprotection.Utils.CryptoUtil;
import com.example.fileprotection.Utils.DialogUtil;
import com.example.fileprotection.Utils.FileUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class NewPassController implements Initializable {
    @FXML
    private PasswordField passwordTxt;

    @FXML
    private PasswordField retypePasswordTxt;

    private String path;
    private String name;
    CryptoUtil cryptoUtil;
    FileUtil fileUtil;
    DialogUtil dialogUtil;
    @FXML
    void cancelHandler(ActionEvent event) {
        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    void confirmHandler(ActionEvent event) throws IOException {
        if(!isPasswordValid()){
            dialogUtil.showAlert("Error","Password invalid!", Alert.AlertType.ERROR);
            return;
        }
        AccountDAO dao=new AccountDAO();
        Timestamp now=Timestamp.from(Instant.now());
        String password=passwordTxt.getText();

        dao.insertAccount(new Account(path,name, now, LocalDateTime.now(), BCrypt.hashpw(password,BCrypt.gensalt(12))));
        File file = new File(path + "/" + name);
        Path directory= Paths.get(path);
        Files.setAttribute(directory, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
        String content=fileUtil.readFile(file);
        String encryptContent=cryptoUtil.encyptData(passwordTxt.getText(),now,content);
        fileUtil.writeFile(encryptContent,file);

        boolean result=file.setReadOnly();
        if(!result){
            dialogUtil.showAlert("Warning","Cannot lock file", Alert.AlertType.WARNING);
        }else{
            dialogUtil.showAlert("Info","Lock file succesfully",Alert.AlertType.INFORMATION);
        }
        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cryptoUtil=new CryptoUtil();
        fileUtil = new FileUtil();
        dialogUtil=new DialogUtil();
    }

    public void setValue(String path,String name){
        this.path=path;
        this.name=name;
    }

    public boolean isPasswordValid(){
          if(passwordTxt.getText().equals(retypePasswordTxt.getText())){
              return true;
          }
          else if(passwordTxt.getText().isBlank()||retypePasswordTxt.getText().isBlank()){
              return false;

          }
          return false;
    }
}
