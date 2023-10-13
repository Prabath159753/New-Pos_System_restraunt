package lk.intelleon.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
import lk.intelleon.bo.custom.ProductBO;
import lk.intelleon.dto.ProductDTO;
import lk.intelleon.dto.tm.ProductTM;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * @author : Kavishka Prabath
 * @created : 10/6/2023 - 1:37 PM
 **/

public class ManageProductFromController {
    public AnchorPane root;
    public JFXTextField txtProductId;
    public JFXTextField txtProductName;
    public JFXTextField txtProductDescription;
    public JFXTextField txtSpec;
    public JFXTextField txtDisplayName;
    public JFXTextField txtQty;
    public JFXTextField txtPrice;
    public JFXTextField txtBrands;
    public JFXCheckBox checkAvailability;
    public JFXCheckBox checkState;
    public JFXButton btnSave;
    public JFXButton btnClear;
    public JFXButton btnUpdate;
    public TableView tblManageProduct;
    public TableColumn colProductId;
    public TableColumn colName;
    public TableColumn colDescription;
    public TableColumn colSpec;
    public TableColumn colDisplayName;
    public TableColumn colAvailability;
    public TableColumn colActiveState;
    public TableColumn colBrands;
    public TableColumn colQty;
    public TableColumn colPrice;
    public TableColumn colOption;

    ProductBO productBO = (ProductBO) BOFactory.getInstance().getBO(BOFactory.BOType.PRODUCT);

    public void initialize(){
        colProductId.setCellValueFactory ( new PropertyValueFactory<>( "productId" ) );
        colName.setCellValueFactory ( new PropertyValueFactory<> ( "name" ) );
        colDescription.setCellValueFactory ( new PropertyValueFactory<> ( "description" ) );
        colSpec.setCellValueFactory ( new PropertyValueFactory<> ( "spec" ) );
        colDisplayName.setCellValueFactory ( new PropertyValueFactory<> ( "displayName" ) );
        colAvailability.setCellValueFactory ( new PropertyValueFactory<> ( "availability" ) );
        colActiveState.setCellValueFactory ( new PropertyValueFactory<> ( "activeState" ) );
        colBrands.setCellValueFactory ( new PropertyValueFactory<> ( "brands" ) );
        colQty.setCellValueFactory ( new PropertyValueFactory<> ( "qty" ) );
        colPrice.setCellValueFactory ( new PropertyValueFactory<> ( "price" ) );
        colOption.setCellValueFactory ( new PropertyValueFactory<> ( "btn" ) );
        colOption.setStyle("-fx-alignment:center");

        getAllProduct();
        generateId();
        btnUpdate.setDisable(true);
        clear();

        tblManageProduct.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    setData( (ProductTM) newValue );
                    btnUpdate.setDisable(false);
                    btnSave.setDisable(true);
                    txtProductId.setEditable(false);
                });
    }

    public void getAllProduct(){
        try {
            ArrayList<ProductDTO> all = productBO.getAllProducts();

            ObservableList< ProductTM > productTMS = FXCollections.observableArrayList ( );

            for ( ProductDTO product:all) {
                JFXButton delete = new JFXButton ( "DELETE" );
                productTMS.add ( new ProductTM (
                        product.getProductId (),product.getName (),
                        product.getDescription (),product.getSpec (),
                        product.getDisplayName (),product.isAvailability (),
                        product.isActiveState (),product.getBrands (),product.getQty(),product.getPrice(),delete
                ) );
                delete.setStyle ( "-fx-background-color: #ff7675;-fx-cursor: hand" );
                delete.setOnAction ( (e) ->{
                    try {
                        boolean b = productBO.deleteProduct(product.getProductId());

                        if (b){
                            new Alert( Alert.AlertType.CONFIRMATION,"Product Delete ! " ).show ();
                            getAllProduct ();
                            clear();
                        }else {
                            new Alert ( Alert.AlertType.WARNING,"Delete Fail ! " ).show ();
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } );
            }
            tblManageProduct.setItems ( productTMS );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateId(){
        try {
            String s = productBO.generateProductId();
            txtProductId.setText(s);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
    }

    public void setData (ProductTM tm){
        try {
            txtProductId.setText(tm.getProductId ());
            txtProductName.setText(tm.getName());
            txtProductDescription.setText(tm.getDescription ());
            txtSpec.setText(tm.getSpec ());
            txtDisplayName.setText(tm.getDisplayName ());
            txtQty.setText(String.valueOf(tm.getQty()));
            txtPrice.setText(String.valueOf(tm.getPrice()));
            txtBrands.setText ( tm.getBrands () );
            checkAvailability.setSelected (tm.isAvailability ());
            checkState.setSelected (tm.isActiveState ());
        } catch (NullPointerException ex) {

        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (isValidProduct()){
            ProductDTO product = new ProductDTO(
                    txtProductId.getText( ) ,
                    txtProductName.getText( ) ,
                    txtProductDescription.getText( ) ,
                    txtSpec.getText( ) ,
                    txtDisplayName.getText( ) ,
                    checkAvailability.isSelected( ) ,
                    checkState.isSelected( ) ,
                    txtBrands.getText( ),
                    Integer.parseInt(txtQty.getText()),
                    BigDecimal.valueOf(Double.valueOf(txtPrice.getText()))
            );
            try {
                if (productBO.addProduct(product)) {
                    new Alert( Alert.AlertType.CONFIRMATION , "Saved ! " ).show( );
                    getAllProduct( );
                    generateId( );
                    clear();
                }
                else {
                    new Alert( Alert.AlertType.ERROR , "Failed ! " ).show( );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValidProduct(){
        boolean isValid = false;
        if (Pattern.compile( "^(P)[0-9]{1,8}" ).matcher( txtProductId.getText( ) ).matches( )) {
            if (txtProductName.getText() != null) {
                if (txtProductDescription.getText() != null) {
                    if (txtSpec.getText() != null) {
                        if (txtDisplayName.getText() != null) {
                            if (Pattern.compile( "^[0-9]{1,20}" ).matcher( txtQty.getText( ) ).matches( )) {
                                if (Pattern.compile( "^[0-9.]{1,20}" ).matcher( txtPrice.getText( ) ).matches( )) {
                                    if (txtBrands.getText() != null) {
                                        isValid = true;
                                    }else {
                                        txtBrands.setFocusColor( Paint.valueOf( "red" ) );
                                        txtBrands.requestFocus();
                                    }
                                }else {
                                    txtPrice.setFocusColor( Paint.valueOf( "red" ) );
                                    txtPrice.requestFocus();
                                }
                            }else {
                                txtQty.setFocusColor( Paint.valueOf( "red" ) );
                                txtQty.requestFocus();
                            }
                        }else {
                            txtDisplayName.setFocusColor( Paint.valueOf( "red" ) );
                            txtDisplayName.requestFocus();
                        }
                    }else {
                        txtSpec.setFocusColor( Paint.valueOf( "red" ) );
                        txtSpec.requestFocus();
                    }
                }else {
                    txtProductDescription.setFocusColor( Paint.valueOf( "red" ) );
                    txtProductDescription.requestFocus();
                }
            }else {
                txtProductName.setFocusColor( Paint.valueOf( "red" ) );
                txtProductName.requestFocus();
            }
        }else {
            txtProductId.setFocusColor( Paint.valueOf( "red" ) );
            txtProductId.requestFocus();
        }

        return isValid;
    }

    public void searchProductOnAction(ActionEvent actionEvent) {
        try {
            ProductDTO productDTO = productBO.searchProduct(txtProductId.getText());

            if (productDTO!=null){
                btnUpdate.setDisable(false);
                btnSave.setDisable(true);
                txtProductId.setEditable(false);

                txtProductName.setText ( productDTO.getName () );
                txtProductDescription.setText ( productDTO.getDescription () );
                txtSpec.setText ( productDTO.getSpec () );
                txtDisplayName.setText ( productDTO.getDisplayName () );
                checkAvailability.setSelected ( productDTO.isAvailability () );
                checkState.setSelected ( productDTO.isActiveState () );
                txtBrands.setText ( productDTO.getBrands () );
            }else {
                new Alert ( Alert.AlertType.ERROR,"Empty ! " ).show ();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (isValidProduct()) {
            ProductDTO product = new ProductDTO (
                    txtProductId.getText ( ) ,
                    txtProductName.getText ( ) ,
                    txtProductDescription.getText ( ) ,
                    txtSpec.getText ( ) ,
                    txtDisplayName.getText ( ) ,
                    checkAvailability.isSelected ( ) ,
                    checkState.isSelected ( ) ,
                    txtBrands.getText ( ),
                    Integer.parseInt(txtQty.getText()),
                    BigDecimal.valueOf(Double.valueOf(txtPrice.getText()))
            );
            try {
                if (productBO.updateProduct(product)){
                    new Alert ( Alert.AlertType.CONFIRMATION,"Update ! " ).show ();
                    getAllProduct ();
                    generateId();
                    clear();
                    btnUpdate.setDisable(true);
                    btnSave.setDisable(false);
                    txtProductId.setEditable(true);
                }else {
                    new Alert ( Alert.AlertType.WARNING,"Fail ! " ).show ();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clear();
    }

    private void clear(){
        txtProductName.setText( null );
        txtProductDescription.setText( null );
        txtSpec.setText( null );
        txtDisplayName.setText( null );
        txtQty.setText( "" );
        txtPrice.setText( "" );
        txtBrands.setText( null );
        checkAvailability.setSelected(false);
        checkState.setSelected(false);
        btnUpdate.setDisable(true);
        btnSave.setDisable(false);
        txtProductId.setEditable(true);
        generateId();
    }

}
