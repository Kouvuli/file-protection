package com.example.fileprotection.Controllers;


import com.example.fileprotection.DAO.AccountDAO;
import com.example.fileprotection.Entities.Account;
import com.example.fileprotection.Utils.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PasswordController implements Initializable {

    @FXML
    private PasswordField passwordTxt;

    private String path;
    private String name;
    CryptoUtil cryptoUtil;
    FileUtil fileUtil;
    DialogUtil dialogUtil;
    private static int passCount=0;
    @FXML
    void cancelHandler(ActionEvent event) {
        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    void confirmHandler(ActionEvent event) throws IOException {
        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        AccountDAO dao=new AccountDAO();
        Account account=dao.getAccountByFilePathAndFileName(path,name);
        File file = new File(path + "/" + name);

        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        if(account.getExpireAt().isAfter(LocalDateTime.now())){
            dialogUtil.showAlert("Error", "File will be available at: " + account.getExpireAt().format(formatter) + "\n You have " + (10 - passCount) + " more tries and the file will be delete", Alert.AlertType.ERROR);
            return;
        }
        if(!BCrypt.checkpw(passwordTxt.getText(),account.getPassword())){
          dialogUtil.showAlert("Error","Incorrect password!", Alert.AlertType.ERROR);
//          Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();

          passCount++;
          if(passCount>=10){
              FileUtils.forceDelete(file);
              dao.delData(account);
              passCount=0;
              dialogUtil.showAlert("Delete file","File is deleted!", Alert.AlertType.INFORMATION);

              window.close();

          }
          else if(passCount%5==0){
            long waitingTime=1;
            dialogUtil.showAlert("Error", "Try too many time! Waiting " + waitingTime + " more minutes to retry", Alert.AlertType.ERROR);
            dao.updateExpireTime(account,account.getExpireAt().plusMinutes(waitingTime));

          }
        return;
        }

        String content=fileUtil.readFile(file);
        String decryptContent=cryptoUtil.decryptData(passwordTxt.getText(),account.getCreateAt(),content);
        file.setWritable(true);
        dao.delData(account);
        fileUtil.writeFile(decryptContent,file);
        if(!file.canWrite()){
            dialogUtil.showAlert("Warning","Cannot unlock file", Alert.AlertType.WARNING);
        }else{
            dialogUtil.showAlert("Info","Unlock file succesfully",Alert.AlertType.INFORMATION);
        }

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


}
