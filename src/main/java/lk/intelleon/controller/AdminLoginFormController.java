package lk.intelleon.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 2:52 PM
 **/

public class AdminLoginFormController {
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public JFXButton btnCancel;
    public JFXButton btnLogin;

    public void loginOnAction(ActionEvent actionEvent) {
        try {
            String name = txtUserName.getText ( ).trim ( );
            String password = txtPassword.getText ( ).trim ( );
            if ( name.length ( ) > 0 && password.length ( ) > 0 ) {

                if ( name.equalsIgnoreCase ( "Ushan" )
                        && password.equalsIgnoreCase ( "1234" ) ) {
                    Stage stage = ( Stage ) btnCancel.getScene ( ).getWindow ( );
                    stage.close ( );

                    Scene scene = new Scene ( FXMLLoader.load ( getClass ( ).getResource ( "/view/AdminDashBordForm.fxml" ) ) );
                    Stage primaryStage = new Stage ( );
                    primaryStage.setScene ( scene );
                    primaryStage.setTitle("Super_Market_Admin");
                    primaryStage.show ( );

                }
                else {
                    new Alert( Alert.AlertType.WARNING , "Wrong Username or Password. Try Again !!!!" , ButtonType.OK , ButtonType.NO ).show ( );
                }
            }
            else {
                new Alert ( Alert.AlertType.WARNING , "Empty !!!!" , ButtonType.OK , ButtonType.NO ).show ( );
            }
        } catch ( IOException e ) {
            e.printStackTrace ( );
        }
    }

    public void cancelOnActon(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();

        try {
            Scene scene = new Scene ( FXMLLoader.load ( getClass ( ).getResource ( "/view/MainDashBoardForm.fxml" ) ) );
            Stage primaryStage = new Stage( );
            primaryStage.setScene( scene );
            primaryStage.setTitle("Super_Market");
            primaryStage.show( );
        } catch ( IOException e ) {
            e.printStackTrace ( );
        }
    }

    public void txtUserNameKeyReleasedOnAction(KeyEvent keyEvent) {
        if ( Pattern.compile( "^[a-zA-Z0-9]{2,15}$").matcher( txtUserName.getText()).matches()) {
            btnLogin.setDisable ( false );
            txtUserName.setFocusColor( Paint.valueOf( "blue"));
        }else {
            txtUserName.setFocusColor(Paint.valueOf("red"));
            btnLogin.setDisable ( true );
        }
    }

    public void txtPwKeyReleasedOnAction(KeyEvent keyEvent) {
        if (Pattern.compile("^[a-zA-Z0-9!@#&–?/*~$^+]{2,15}$").matcher(txtPassword.getText()).matches()) {
            btnLogin.setDisable ( false );
            txtPassword.setFocusColor(Paint.valueOf("blue"));
        }else {
            txtPassword.setFocusColor( Paint.valueOf( "red"));
            btnLogin.setDisable ( true );
        }
    }

    public void txtUserNameKeyPressedOnAction(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) {
            txtPassword.requestFocus();
        }
    }

    public void txtPasswordKeyPressedOnAction(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) {
            btnLogin.requestFocus();
        }
    }
}
