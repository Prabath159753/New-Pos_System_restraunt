package lk.intelleon.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.intelleon.bo.BOFactory;
import lk.intelleon.bo.custom.UserBO;
import lk.intelleon.dto.UserDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 2:52 PM
 **/

public class AdminDashBordFormController {
    public JFXButton btnSystemReports;
    public JFXButton btnManageProduct;
    public JFXButton btnManageCashiers;
    public ImageView bntBack;
    public JFXButton btnCustomer;
    public JFXButton btnManageRef;
    public AnchorPane root;
    public Label lblCashiersCount;
    public Label lblPendingOrderCount;

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    public void initialize(){
        countActiveUses ();
        getAllPendingOrderCount();
    }

    public void countActiveUses(){
        try {
            ArrayList<UserDTO> activeUsers = userBO.getActiveUsers();
            lblCashiersCount.setText ( String.valueOf ( activeUsers.size () ) );
        } catch ( SQLException throwables ) {
            throwables.printStackTrace ( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace ( );
        }
    }

    private void getAllPendingOrderCount(){
        int size = lk.intelleon.controller.CashierFormController.tempTableArray.size( );
        lblPendingOrderCount.setText( String.valueOf( size ) );
    }

    public void setUI(String location){
        try {
            this.root.getChildren ().clear ();
            this.root.getChildren ().add ( FXMLLoader.load ( getClass ().getResource ( "/view/"+location ) ));
        } catch ( IOException e ) {
            e.printStackTrace ( );
        }
    }

    public void btnSystemReportsOnAction(ActionEvent actionEvent) {
        setUI ( "SystemReportForm.fxml" );
    }

    public void btnManageProductOnAction(ActionEvent actionEvent) {
        setUI ( "ManageProductFrom.fxml" );
    }

    public void btnManageCashiersOnAction(ActionEvent actionEvent) {
        setUI ( "ManageUserForm.fxml" );
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) {
        setUI( "ManageCustomerForm.fxml" );
    }

    public void btnManageRefOnAction(ActionEvent actionEvent) {
        setUI ("ManageRefForm.fxml");
    }

    public void bntBackOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) bntBack.getScene ().getWindow ();
        stage.close ();

        try {
            Scene scene = new Scene ( FXMLLoader.load ( getClass ( ).getResource ( "/view/MainDashBoardForm.fxml" ) ) );
            Stage stage1 = new Stage ( );
            stage1.setScene ( scene );
            stage1.setTitle("Super_Market");
            stage1.show ();

        } catch ( IOException e ) {
            e.printStackTrace ( );
        }
    }

}
