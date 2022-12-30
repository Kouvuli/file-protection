package com.example.fileprotection.Controllers;



import com.example.fileprotection.Utils.CryptoUtil;
import com.example.fileprotection.Utils.DialogUtil;
import com.example.fileprotection.Utils.FileUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TextField filePathTxt;

    @FXML
    private TextField fileNameTxt;

    @FXML
    private TextArea contentTxt;

    DialogUtil dialogUtil;
    CryptoUtil cryptoUtil;
    FileUtil fileUtil;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dialogUtil = new DialogUtil();
        cryptoUtil = new CryptoUtil();
        fileUtil=new FileUtil();



    }



    @FXML
    void onCreateFile(ActionEvent event) {
        try{
            File file=new File(filePathTxt.getText()+"/"+fileNameTxt.getText());
            boolean result=file.createNewFile();

            if(!result){
                dialogUtil.showAlert("Warning","File already exists", Alert.AlertType.WARNING);
            }else{
                dialogUtil.showAlert("Info","Create file succesfully",Alert.AlertType.INFORMATION);
            }
        }catch (Exception e){
            dialogUtil.showAlert("Error","Cannot create file please check permission", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onDeleteFile(ActionEvent event) {
        try {
            File file=new File(filePathTxt.getText()+"/"+fileNameTxt.getText());
            if(!fileIsExist()){
                dialogUtil.showAlert("Error","File not exist", Alert.AlertType.ERROR);
            }
            else{
                boolean result=file.delete();
                if(!result){
                    dialogUtil.showAlert("Warning","Cannot delete file", Alert.AlertType.WARNING);
                }else{
                    dialogUtil.showAlert("Info","Delete file succesfully",Alert.AlertType.INFORMATION);
                }
            }

        }catch(Exception e){
            dialogUtil.showAlert("Error","Cannot delete file please check permission", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onLockFile(ActionEvent event) {
        try {
            File file=new File(filePathTxt.getText()+"/"+fileNameTxt.getText());
            if(!fileIsExist()){
                dialogUtil.showAlert("Error","File not exist", Alert.AlertType.ERROR);
            }
            else{

                if(!file.canWrite()){
                    dialogUtil.showAlert("Info","File is already lock",Alert.AlertType.INFORMATION);
                }
                else{
                    Stage window = new Stage();

                    window.initModality(Modality.APPLICATION_MODAL);
                    FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/example/fileprotection/layouts/new-pass-dialog.fxml"));
                    NewPassController controller=new NewPassController();
                    controller.setValue(filePathTxt.getText(),fileNameTxt.getText());
                    loader.setController(controller);

                    Parent root= null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene editScene = new Scene(root);
                    window.setTitle("New password");
                    window.setScene(editScene);
                    window.showAndWait();
//                    AccountDAO dao=new AccountDAO();
//                    Account account=dao.getAccountByFilePathAndFileName(filePathTxt.getText(),fileNameTxt.getText());
//
//                    fileUtil.writeFile(cryptoUtil.encyptData(account.getPassword(),account.getCreateAt(), fileUtil.readFile(file)),file);

                }

            }

        }catch(Exception e){
            dialogUtil.showAlert("Error","Cannot lock file please check permission", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onReadFile(ActionEvent event) {


        try{
            File file=new File(filePathTxt.getText()+"/"+fileNameTxt.getText());
            if(!fileIsExist()){
                dialogUtil.showAlert("Error","File not exist", Alert.AlertType.ERROR);
                return;
            }

            String content=fileUtil.readFile(file);

//            FileReader fileReader=new FileReader(file);
//            BufferedReader bufferedReader=new BufferedReader(fileReader);
//            String content="";
//            String currentLine=bufferedReader.readLine();
//            while(currentLine!=null){
//                content+=currentLine;
//                currentLine= bufferedReader.readLine();
//            }

            contentTxt.setText(content);



        }catch (Exception e){
            dialogUtil.showAlert("Error","Cannot read content", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onUnlockFile(ActionEvent event) {
        try {
            File file=new File(filePathTxt.getText()+"/"+fileNameTxt.getText());
            if(!fileIsExist()){
                dialogUtil.showAlert("Error","File not exist", Alert.AlertType.ERROR);
            }
            else{
                if(file.canWrite()){
                    return;
                }
                Stage window = new Stage();

                window.initModality(Modality.APPLICATION_MODAL);
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/example/fileprotection/layouts/pass-dialog.fxml"));
                PasswordController controller=new PasswordController();
                controller.setValue(filePathTxt.getText(),fileNameTxt.getText());
                loader.setController(controller);

                Parent root= null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene editScene = new Scene(root);
                window.setTitle("Password");
                window.setScene(editScene);
                window.showAndWait();

            }

        }catch(Exception e){
            dialogUtil.showAlert("Error","Cannot unlock file please check permission", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onWriteFile(ActionEvent event) {
        BufferedWriter bufferedWriter=null;

        try{
            File file=new File(filePathTxt.getText()+"/"+fileNameTxt.getText());
            if(!fileIsExist()){
                boolean result=file.createNewFile();
                if(!result){
                    dialogUtil.showAlert("Error","Sorry, something wrong happend!", Alert.AlertType.ERROR);
                    return;
                }
            }

            fileUtil.writeFile(contentTxt.getText(),file);
//            FileWriter fileWriter=new FileWriter(file);
//            bufferedWriter=new BufferedWriter(fileWriter);
//            bufferedWriter.write(contentTxt.getText());
//            bufferedWriter.flush();

            dialogUtil.showAlert("Success","Content written succesfully",Alert.AlertType.INFORMATION);
        }catch (Exception e){
            dialogUtil.showAlert("Error","Cannot write content", Alert.AlertType.ERROR);
        }
    }

    public boolean fileIsExist(){

        File file=new File(filePathTxt.getText()+"/"+fileNameTxt.getText());

        if(filePathTxt.getText().isBlank()||fileNameTxt.getText().isBlank()){
            return false;
        }
        if(file.exists()){
            return true;
        }

        else{
            return false;
        }


    }
}
