package lk.intelleon.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.intelleon.bo.BOFactory;
import lk.intelleon.bo.custom.RefBO;
import lk.intelleon.dto.RefDTO;
import lk.intelleon.dto.tm.RefTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * @author : Kavishka Prabath
 * @created : 10/6/2023 - 12:37 PM
 **/

public class ManageRefFormController {
    public AnchorPane root;
    public JFXTextField txtRefId;
    public JFXTextField txtRefName;
    public JFXTextField txtIdNumber;
    public JFXTextField txtTel;
    public JFXTextField txtAddress;
    public JFXButton btnClear;
    public JFXButton btnUpdate;
    public JFXButton btnSave;
    public TableView tblRef;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colIdNumber;
    public TableColumn colTel;
    public TableColumn colAddress;
    public TableColumn colOption;

    RefBO refBO = (RefBO) BOFactory.getInstance().getBO(BOFactory.BOType.REF);

    public void initialize(){
        colId.setCellValueFactory ( new PropertyValueFactory<>( "refId" ) );
        colName.setCellValueFactory ( new PropertyValueFactory<>( "name" ) );
        colIdNumber.setCellValueFactory ( new PropertyValueFactory<>( "idNumber" ) );
        colTel.setCellValueFactory ( new PropertyValueFactory<>( "tel" ) );
        colAddress.setCellValueFactory ( new PropertyValueFactory<>( "address" ) );
        colOption.setCellValueFactory ( new PropertyValueFactory<>( "btn" ) );
        colOption.setStyle ( "-fx-alignment:center" );

        getALlRefs();
        setRefId();
        btnUpdate.setDisable(true);

        tblRef.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    setData( ( RefTM ) newValue );
                    btnUpdate.setDisable(false);
                    btnSave.setDisable(true);
                    txtRefId.setEditable(false);
                });
    }

    public void getALlRefs(){
        try {
            ArrayList<RefDTO> allRefs = refBO.getAllRefs();

            ObservableList<RefTM> refTMS = FXCollections.observableArrayList();

            for (RefDTO refDTO : allRefs){
                JFXButton delete = new JFXButton ( "DELETE" );
                refTMS.add(new RefTM(
                        refDTO.getRefId(),refDTO.getName(),
                        refDTO.getIdNumber(),refDTO.getTel(),
                        refDTO.getAddress(),delete
                ));
                delete.setStyle ( "-fx-background-color: #ff7675;-fx-cursor: hand" );
                delete.setOnAction ( (e) ->{
                    try {
                        boolean b = refBO.deleteRef(refDTO.getRefId());

                        if (b){
                            new Alert( Alert.AlertType.CONFIRMATION,"Ref Delete ! " ).show ();
                            getALlRefs();
                            clear();
                        }else {
                            new Alert ( Alert.AlertType.WARNING,"Delete Fail ! " ).show ();
                        }

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });
            }
            tblRef.setItems(refTMS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setData ( RefTM newValue ) {
        try {
            txtRefId.setText ( String.valueOf ( newValue.getRefId () ) );
            txtRefName.setText ( newValue.getName () );
            txtIdNumber.setText ( newValue.getIdNumber () );
            txtTel.setText (String.valueOf(newValue.getTel ()));
            txtAddress.setText ( newValue.getAddress () );
        }catch ( NullPointerException e ){

        }
    }

    private void setRefId(){
        try {
            String s = refBO.generateRefId();
            txtRefId.setText( String.valueOf( s ) );
        } catch ( SQLException throwables ) {
            throwables.printStackTrace( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace( );
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

        if(isValidRef()){
            RefDTO refDTO = new RefDTO(
                    txtRefId.getText(),
                    txtRefName.getText(),
                    txtIdNumber.getText(),
                    Integer.parseInt(txtTel.getText()),
                    txtAddress.getText()
            );

            try {
                if (refBO.addRef(refDTO)){
                    new Alert( Alert.AlertType.CONFIRMATION , "Ref Saved ! " ).show( );
                    getALlRefs();
                    clear();
                }else {
                    new Alert( Alert.AlertType.ERROR , "Fail ! " ).show( );
                }
            } catch (Exception e) {
                new Alert( Alert.AlertType.ERROR , "Try Again ! " ).show( );
            }
        }
    }

    private boolean isValidRef(){
        boolean isValid = false;
        if (Pattern.compile( "^(R)[0-9]{1,8}" ).matcher( txtRefId.getText( ) ).matches( )){
            if (Pattern.compile( "^[A-z .]{1,35}" ).matcher( txtRefName.getText( ) ).matches( )) {
                if (Pattern.compile( "^[0-9Vv]{1,12}" ).matcher( txtIdNumber.getText( ) ).matches( )) {
                    if (Pattern.compile( "^[0-9]{9,10}" ).matcher( txtTel.getText( ) ).matches( )) {
                        if (Pattern.compile( "^[A-z0-9!@#&–?/*$^+., ]{1,50}" ).matcher( txtAddress.getText( ) ).matches( )) {

                            isValid = true;

                        }else {
                            txtAddress.setFocusColor( Paint.valueOf( "red" ) );
                            txtAddress.requestFocus();
                        }
                    }else {
                        txtTel.setFocusColor( Paint.valueOf( "red" ) );
                        txtTel.requestFocus();
                    }
                }else {
                    txtIdNumber.setFocusColor( Paint.valueOf( "red" ) );
                    txtIdNumber.requestFocus();
                }
            }else {
                txtRefName.setFocusColor( Paint.valueOf( "red" ) );
                txtRefName.requestFocus();
            }
        }else {
            txtRefId.setFocusColor( Paint.valueOf( "red" ) );
            txtRefId.requestFocus();
        }

        return isValid;
    }

    public void searchRefIdOnAction(ActionEvent actionEvent) {
        try {
            RefDTO refDTO = refBO.searchRef(txtRefId.getText());

            if (refDTO !=null){
                btnUpdate.setDisable(false);
                btnSave.setDisable(true);
                txtRefId.setEditable(false);

                txtRefName.setText(refDTO.getName());
                txtIdNumber.setText(refDTO.getIdNumber());
                txtTel.setText(String.valueOf(refDTO.getTel()));
                txtAddress.setText(refDTO.getAddress());
            }
        } catch (Exception e) {
            new Alert ( Alert.AlertType.WARNING,"Empty Ref ! " ).show ();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (isValidRef()){
            RefDTO refDTO = new RefDTO(
                    txtRefId.getText(),
                    txtRefName.getText(),
                    txtIdNumber.getText(),
                    Integer.parseInt(txtTel.getText()),
                    txtAddress.getText()
            );

            try {
                if (refBO.updateRef(refDTO)){
                    new Alert( Alert.AlertType.CONFIRMATION , "Ref Update ! " ).show( );
                    getALlRefs();
                    clear();
                    btnUpdate.setDisable(true);
                    btnSave.setDisable(false);
                    txtRefId.setEditable(true);
                }else {
                    new Alert( Alert.AlertType.ERROR , "Fail ! " ).show( );
                }
            } catch (Exception e) {
                new Alert( Alert.AlertType.ERROR , "Try Again ! " ).show( );
            }
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clear();
    }

    private void clear(){
        txtTel.setText("");
        txtRefName.setText("");
        txtAddress.setText("");
        txtIdNumber.setText("");
        btnUpdate.setDisable(true);
        btnSave.setDisable(false);
        txtRefId.setEditable(true);
        setRefId();
    }

}
