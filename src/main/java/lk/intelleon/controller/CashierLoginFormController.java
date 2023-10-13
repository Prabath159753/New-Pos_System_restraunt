package lk.intelleon.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import lk.intelleon.bo.BOFactory;
import lk.intelleon.bo.custom.UserBO;
import lk.intelleon.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 11:31 PM
 **/

public class CashierLoginFormController {
    public JFXTextField txtUserId;
    public JFXPasswordField txtPassword;
    public JFXButton btnCancel;
    public JFXButton btnLogin;
    public static String userId;

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    public void loginOnAction(ActionEvent actionEvent) {
        try {

            User activeUsers = userBO.getActiveUsers(txtUserId.getText(), txtPassword.getText());
            userId=txtUserId.getText();
            if (activeUsers!=null){
                Stage stage = (Stage) btnCancel.getScene().getWindow();
                stage.close();

                Scene scene = new Scene( FXMLLoader.load( getClass ( ).getResource ( "/view/CashierForm.fxml" ) ) );
                Stage ps = new Stage( );
                ps.setScene( scene );
                ps.setTitle("Super_Market_Cashier");
                ps.show();

            }else {
                new Alert( Alert.AlertType.WARNING,"Something went Wrong. Please Contact Admin !  " ).show();
            }
        } catch (IOException throwables ) {
            throwables.printStackTrace ( );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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
        if ( Pattern.compile( "^[a-zA-Z0-9]{2,15}$").matcher( txtUserId.getText()).matches()) {
            btnLogin.setDisable ( false );
            txtUserId.setFocusColor( Paint.valueOf( "blue"));
        }else {
            txtUserId.setFocusColor(Paint.valueOf("red"));
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
