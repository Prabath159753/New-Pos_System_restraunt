package lk.intelleon.controller;

import com.jfoenix.controls.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.intelleon.bo.BOFactory;
import lk.intelleon.bo.custom.CustomerBO;
import lk.intelleon.bo.custom.OrderBO;
import lk.intelleon.bo.custom.ProductBO;
import lk.intelleon.bo.custom.RefBO;
import lk.intelleon.db.DBConnection;
import lk.intelleon.dto.*;
import lk.intelleon.dto.tm.TempOrderTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 11:55 PM
 **/

public class CashierFormController {
    public JFXListView list;
    public JFXButton btnCancel;
    public Label lblDate;
    public JFXTextField txtCusId;
    public JFXTextField txtCusContact;
    public JFXTextField txtCusType;
    public JFXTextField txtCusName;
    public JFXTextField txtCusAddress;
    public JFXTextField txtCusCity;
    public JFXTextField txtCusProvince;
    public JFXComboBox cmbSelectProductName;
    public JFXComboBox cmbRef;
    public JFXTextField txtProductId;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQty;
    public JFXTextField txtDiscount;
    public JFXTextField txtOrderQty;
    public JFXButton btnAdd;
    public Label lblTime;
    public TextField txtOrderId;
    public ImageView btnBack;
    public Label lblCashierId;
    public TableView tblTempOrder;
    public TableColumn colPropertyId;
    public TableColumn colProductName;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    public TableColumn colOption;
    public JFXButton btnConfirm;
    public Label lblTotal;
    public JFXButton btnPlaceOrder;
    public JFXButton btnNew;
    public JFXRadioButton rbtCash;
    public ToggleGroup paymentMethod;
    public JFXRadioButton rbtCredit;
    public Label lblBalance;
    public TextField txtPaid;
    public JFXRadioButton rbtCard;
    public JFXComboBox<String> cmbOrderType;

    static ArrayList<TempDataDTO> temps = new ArrayList<>( );
    static ArrayList<TempTableDTO> tempTableArray = new ArrayList<>( );

    String tot;

    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);
    ProductBO productBO = (ProductBO) BOFactory.getInstance().getBO(BOFactory.BOType.PRODUCT);
    OrderBO orderBO = (OrderBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDER);
    RefBO refBO = (RefBO) BOFactory.getInstance().getBO(BOFactory.BOType.REF);

    public void initialize(){
        colPropertyId.setCellValueFactory( new PropertyValueFactory<>( "propertyId" ) );
        colProductName.setCellValueFactory( new PropertyValueFactory<>( "productName" ) );
        colUnitPrice.setCellValueFactory( new PropertyValueFactory<>( "unitPrice" ) );
        colQty.setCellValueFactory( new PropertyValueFactory<>( "qty" ) );
        colDiscount.setCellValueFactory( new PropertyValueFactory<>( "discount" ) );
        colTotal.setCellValueFactory( new PropertyValueFactory<>( "total" ) );
        colOption.setCellValueFactory( new PropertyValueFactory<>( "btn" ) );

        cmbOrderType.getItems().addAll("Dine-In", "Take-Away");

        lblCashierId.setText(  CashierLoginFormController.userId );
        generateDateTime();
        setOrderId();
        setCustomerId();
        getAllPropertyName();
        getAllRefs();
        clear();
        //setBtnValid(true);
    }

    public void generateDateTime() {
        lblDate.setText( LocalDate.now().toString());

        Timeline timeline = new Timeline( new KeyFrame( Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "hh:mm:ss a");
            lblTime.setText( LocalDateTime.now().format( formatter));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount( Animation.INDEFINITE);
        timeline.play();
    }

    private void setOrderId(){
        try {
            String s = orderBO.generateOrderId();
            txtOrderId.setText( s );
        } catch ( SQLException throwables ) {
            throwables.printStackTrace( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace( );
        }
    }

    private void setCustomerId(){
        try {
            String s = customerBO.generateCustomerId();
            txtCusId.setText( s );
        } catch ( SQLException throwables ) {
            throwables.printStackTrace( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace( );
        }
    }

    public void getAllPropertyName(){
        try {
            List<ProductDTO> all = productBO.getAllActiveStateItems();

            ObservableList< String > displayName = FXCollections.observableArrayList( );
            for ( ProductDTO dto:all) {
                displayName.add( dto.getDisplayName() );
            }
            cmbSelectProductName.setItems( displayName );
        } catch (SQLException | ClassNotFoundException throwables ) {
            throwables.printStackTrace();
        }
    }

    public void getAllRefs(){
        try {
            ArrayList<RefDTO> allRefs = refBO.getAllRefs();

            ObservableList< String > refs = FXCollections.observableArrayList( );
            for ( RefDTO dto:allRefs) {
                refs.add( dto.getRefId() );
            }
            cmbRef.setItems( refs );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchCustomerOnAction(ActionEvent actionEvent) {
        try {
            if (customerBO.ifCustomerIdExist(txtCusId.getText())){
                CustomerDTO customerDTO = customerBO.searchCustomer(txtCusId.getText());

                if (customerDTO!=null){
                    txtCusType.setText( customerDTO.getCusType() );
                    txtCusName.setText( customerDTO.getCusName() );
                    txtCusAddress.setText( customerDTO.getCusAddress() );
                    txtCusCity.setText( customerDTO.getCusCity() );
                    txtCusProvince.setText( customerDTO.getCusProvince() );
                    txtCusContact.setText( String.valueOf( customerDTO.getCusContact() ) );
                }else {
                    new Alert( Alert.AlertType.ERROR,"Empty Customer !" ).show();
                }
            }
            else {
                new Alert( Alert.AlertType.ERROR,"Empty Customer !" ).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchCustomerTpOnAction(ActionEvent actionEvent) {
        try {
            if (customerBO.ifCustomerTpExist(Integer.parseInt( txtCusContact.getText( )))){
                CustomerDTO customerDTO = customerBO.searchCustomerTp(Integer.parseInt( txtCusContact.getText( )));

                if (customerDTO!=null){
                    txtCusId.setText( customerDTO.getCusId() );
                    txtCusType.setText( customerDTO.getCusType() );
                    txtCusName.setText( customerDTO.getCusName() );
                    txtCusAddress.setText( customerDTO.getCusAddress() );
                    txtCusCity.setText( customerDTO.getCusCity() );
                    txtCusProvince.setText( customerDTO.getCusProvince() );
                }else {
                    new Alert( Alert.AlertType.ERROR,"Empty Customer !" ).show();
                }
            }
            else {
                new Alert( Alert.AlertType.ERROR,"Empty Customer !" ).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cmbSelectProductName(ActionEvent actionEvent) {
        try {
            List<ProductDTO> allActiveStateItems = productBO.getAllActiveStateItems();

            for ( ProductDTO product:allActiveStateItems) {
                if (cmbSelectProductName.getValue().equals( product.getDisplayName() )){
                    txtProductId.setText( product.getProductId() );
                    txtUnitPrice.setText( String.valueOf( product.getPrice() ) );
                    txtQty.setText( String.valueOf( product.getQty() ) );
                }else {

                }
            }
        } catch (SQLException | ClassNotFoundException throwables ) {
            throwables.printStackTrace( );
        }catch (NullPointerException e){

        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        if (Pattern.compile( "^(C)[0-9]{1,5}" ).matcher( txtCusId.getText( ) ).matches( )) {
            if (Pattern.compile( "^[A-z .]{1,35}" ).matcher( txtCusType.getText( ) ).matches( )) {
                if (Pattern.compile( "^[A-z .]{1,35}" ).matcher( txtCusName.getText( ) ).matches( )) {
                    if (Pattern.compile( "^[A-z0-9!@#&–?/*$^+., ]{1,50}" ).matcher( txtCusAddress.getText( ) ).matches( )) {
                        if (Pattern.compile( "^[A-z0-9 ]{1,35}" ).matcher( txtCusCity.getText( ) ).matches( )) {
                            if (Pattern.compile( "^[A-z ]{1,40}" ).matcher( txtCusProvince.getText( ) ).matches( )) {
                                if (Pattern.compile( "^[0-9]{9,10}" ).matcher( txtCusContact.getText( ) ).matches( )) {
                                    if (Pattern.compile( "^[0-9]{1,10}" ).matcher( txtOrderQty.getText( ) ).matches( )) {
                                        if (cmbRef.getValue() != null){
                                            if (cmbOrderType.getValue() != null){
                                                String date = lblDate.getText( );
                                                String cashierId = CashierLoginFormController.userId;

                                                double dic = 0;
                                                if ( txtDiscount.getText( ).isEmpty() ){
                                                    dic = 0;
                                                }else {
                                                    dic = Double.parseDouble( txtDiscount.getText( ) );
                                                }

                                                int qty = Integer.parseInt( txtOrderQty.getText( ) );
                                                double uniPrice = Double.parseDouble( txtUnitPrice.getText( ) );

                                                double dicTot = (dic * qty);
                                                double subTot = (uniPrice * qty) - (dic * qty);

                                                TempDataDTO tempData = new TempDataDTO(
                                                        txtOrderId.getText( ) ,
                                                        date ,
                                                        txtCusId.getText( ) ,
                                                        txtCusType.getText( ) ,
                                                        txtCusName.getText( ) ,
                                                        txtCusAddress.getText( ) ,
                                                        txtCusCity.getText( ) ,
                                                        txtCusProvince.getText( ) ,
                                                        Integer.parseInt( txtCusContact.getText( ) ) ,
                                                        cashierId ,
                                                        subTot,
                                                        String.valueOf(cmbRef.getValue()),
                                                        null,
                                                        cmbOrderType.getValue()
                                                );

                                                int rowNumber1 = isExistsTempData( tempData );

                                                if (rowNumber1==-1) {
                                                    temps.add( tempData );
                                                    tblTempOrder.refresh( );
                                                }
                                                else {
                                                    tblTempOrder.refresh( );
                                                }

                                                TempTableDTO tempTable = new TempTableDTO(
                                                        txtOrderId.getText( ) ,
                                                        txtProductId.getText( ) ,
                                                        String.valueOf( cmbSelectProductName.getValue( ) ) ,
                                                        Double.parseDouble( txtUnitPrice.getText( ) ) ,
                                                        Integer.parseInt( txtOrderQty.getText( ) ) ,
                                                        Double.parseDouble( String.valueOf( dicTot ) ) ,
                                                        Double.parseDouble( String.valueOf( subTot ) ),
                                                        String.valueOf(cmbRef.getValue())
                                                );

                                                int rowNumber = isExists( tempTable );
                                                if (rowNumber==-1) {
                                                    if (Integer.parseInt( txtQty.getText( ) ) >= Integer.parseInt( txtOrderQty.getText( ) )) {
                                                        tempTableArray.add( tempTable );
                                                        getAllProcessingOrder();
                                                    }
                                                    else {
                                                        new Alert( Alert.AlertType.WARNING , "Out of Bounds" ).show( );
                                                    }
                                                }
                                                else {
                                                    if (Integer.parseInt( txtQty.getText( ) ) >= tempTableArray.get( rowNumber ).getQty( ) + Integer.parseInt( txtOrderQty.getText( ) )) {
                                                        tempTableArray.get( rowNumber ).setQty( tempTableArray.get( rowNumber ).getQty( ) + Integer.parseInt( txtOrderQty.getText( ) ) );
                                                        tempTableArray.get( rowNumber ).setSubTotal( tempTableArray.get( rowNumber ).getSubTotal( ) + subTot );
                                                        tempTableArray.get( rowNumber ).setDiscount( tempTableArray.get( rowNumber ).getDiscount( ) + dicTot );
                                                        tblTempOrder.refresh( );
                                                    }else {
                                                        new Alert( Alert.AlertType.WARNING , "Out of Bounds" ).show( );
                                                    }
                                                }

                                                getAllProcessingOrder( );
                                                generateTotal( );
                                                clearProductDetails();
                                            }else {
                                                cmbOrderType.setFocusColor( Paint.valueOf( "red" ) );
                                                cmbOrderType.requestFocus( );
                                            }
                                        }else {
                                            cmbRef.setFocusColor( Paint.valueOf( "red" ) );
                                            cmbRef.requestFocus( );
                                        }
                                    }else {
                                        txtOrderQty.setFocusColor( Paint.valueOf( "red" ) );
                                        txtOrderQty.requestFocus( );
                                    }
                                }else {
                                    txtCusContact.setFocusColor( Paint.valueOf( "red" ) );
                                    txtCusContact.requestFocus( );
                                }
                            }else {
                                txtCusProvince.setFocusColor( Paint.valueOf( "red" ) );
                                txtCusProvince.requestFocus( );
                            }
                        }else {
                            txtCusCity.setFocusColor( Paint.valueOf( "red" ) );
                            txtCusCity.requestFocus( );
                        }
                    }else {
                        txtCusAddress.setFocusColor( Paint.valueOf( "red" ) );
                        txtCusAddress.requestFocus( );
                    }
                }else {
                    txtCusName.setFocusColor( Paint.valueOf( "red" ) );
                    txtCusName.requestFocus( );
                }
            }else {
                txtCusType.setFocusColor( Paint.valueOf( "red" ) );
                txtCusType.requestFocus( );
            }
        }else {
            txtCusId.setFocusColor( Paint.valueOf( "red" ) );
            txtCusId.requestFocus( );
        }
    }

    private int isExistsTempData ( TempDataDTO tempData ) {
        for ( int i = 0; i < temps.size( ); i++ ) {
            if (temps.get( i ).getOrderId().equals( tempData.getOrderId() )){
                return i;
            }
        }
        return -1;
    }

    private int isExists ( TempTableDTO tempTable ) {
        for ( int i = 0; i < tempTableArray.size( ); i++ ) {
            if (tempTableArray.get( i ).getOrderId().equals( txtOrderId.getText() ) && tempTableArray.get( i ).getPropertyId().equals( tempTable.getPropertyId() )){
                return i;
            }
        }
        return -1;
    }

    public void getAllProcessingOrder(){
        tblTempOrder.setItems( null );
        ObservableList<TempOrderTM> list = FXCollections.observableArrayList( );

        for ( TempTableDTO data:tempTableArray) {
            JFXButton btn = new JFXButton( "Delete" );
            if (txtOrderId.getText().equals( data.getOrderId() )){
                list.add( new TempOrderTM( data.getPropertyId(),data.getProductName(),
                        data.getUnitPrice(),data.getQty(),
                        data.getDiscount(),data.getSubTotal(),btn
                ) );
            }
            btn.setStyle ( "-fx-background-color: #ff7675;-fx-cursor: hand" );
            btn.setOnAction( (e)->{
                for ( int i = 0; i < tempTableArray.size( ); i++ ) {
                    TempTableDTO table = tempTableArray.get( i );
                    if (txtOrderId.getText( ).equals( table.getOrderId( ) )){
                        if (data.getPropertyId( ).equals( table.getPropertyId( ) )){
                            boolean remove = tempTableArray.remove( table );
                            if (remove){
                                getAllProcessingOrder();
                                generateTotal( );
                            }
                        }
                    }
                }
            } );
        }
        tblTempOrder.setItems( list );
    }

    double totalCost = 0.0;
    int qty = 0;
    public void generateTotal(){

        double dic = 0;
        if ( txtDiscount.getText( ).isEmpty() ){
            dic = 0;
        }else {
            dic = Double.parseDouble( txtDiscount.getText( ) );
        }

        qty = Integer.parseInt( txtOrderQty.getText( ) );
        double uniPrice = Double.parseDouble(txtUnitPrice.getText( ));

        double temp = ((uniPrice * qty) - (dic * qty));
        totalCost+=temp;

        tot = String.valueOf(totalCost);

        lblTotal.setText( String.valueOf( totalCost ) );
    }

    private void clearProductDetails() {
        cmbSelectProductName.setValue( null );
        txtProductId.setText( "" );
        txtUnitPrice.setText( "" );
        txtQty.setText( "" );
        txtDiscount.setText( "" );
        txtOrderQty.setText( "" );
    }

    public void calculateBalance(ActionEvent actionEvent) {
        if (!lblTotal.getText().isEmpty()){
            if (!txtPaid.getText().isEmpty()){
                double total = Double.parseDouble( lblTotal.getText( ) );
                double paidAmount = Double.parseDouble( txtPaid.getText( ) );;
                double balance = paidAmount - total;
                lblBalance.setText( String.valueOf( balance ) );
            }
        }
    }

    public void calculateBalanceKeyPressedOnAction(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) {
            btnPlaceOrder.requestFocus();
        }
    }

    public void calculateBalanceOnKeyReleased(KeyEvent keyEvent) {
        if (!lblTotal.getText().isEmpty()){
            if (!txtPaid.getText().isEmpty()){
                double total = Double.parseDouble( lblTotal.getText( ) );
                double paidAmount = Double.parseDouble( txtPaid.getText( ) );;
                double balance = paidAmount - total;
                lblBalance.setText( String.valueOf( balance ) );
            }
        }
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        if (!txtPaid.getText().isEmpty()){
            CustomerDTO customer = new CustomerDTO(
                    txtCusId.getText( ) ,
                    txtCusType.getText( ) ,
                    txtCusName.getText( ) ,
                    txtCusAddress.getText( ) ,
                    txtCusCity.getText( ) ,
                    txtCusProvince.getText( ) ,
                    Integer.parseInt( txtCusContact.getText( ) )
            );

            String paymentMethod = getPaymentMethod();
            String orderTypes = cmbOrderType.getValue();

            ArrayList<OrderDTO> orders = new ArrayList<>( );
            OrderDTO order = new OrderDTO( txtOrderId.getText( ) , lblDate.getText(), Double.parseDouble( lblTotal.getText( ) ) , txtCusId.getText( ) , lblCashierId.getText( ), paymentMethod, orderTypes );
            orders.add(order);
            customer.setOrders(orders);

            ArrayList< OrderDetailDTO > details = new ArrayList<>( );
            for ( TempTableDTO tm : tempTableArray) {
                if (txtOrderId.getText().equals( tm.getOrderId() )){
                    details.add( new OrderDetailDTO( tm.getQty( ) , tm.getUnitPrice( ),tm.getDiscount() , txtOrderId.getText() , tm.getPropertyId( ),tm.getProductName(), tm.getRefId(), tm.getSubTotal() ) );
                }
            }
            customer.setDetails(details);

            try {
                if (customerBO.addCustomer(customer)){
                    deleteTempData();
                    new Alert( Alert.AlertType.CONFIRMATION,"Success ! " ).show();
                    getAllOrderIdFromArray();
                    getAllProcessingOrder();;
                    showReport();
                    setOrderId();
                    clear();
                    tblTempOrder.setItems(null);
                }else {
                    new Alert( Alert.AlertType.WARNING,"Fail !" ).show();
                }
            } catch ( SQLException throwables ) {
                throwables.printStackTrace( );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            new Alert( Alert.AlertType.WARNING,"Please Enter Customer Paid Amount !" ).show();
        }
    }

    private String getPaymentMethod() {
        String paymentMethod = null;
        if(rbtCash.isSelected()){
            paymentMethod = "Cash";
        } else if (rbtCard.isSelected()) {
            paymentMethod = "Card";
        } else if (rbtCredit.isSelected()) {
            paymentMethod = "Credit";
        }else {
            new Alert( Alert.AlertType.WARNING,"Select Payment Method !" ).show();
        }
        return paymentMethod;
    }

    private void deleteTempData(){
        for ( int i = 0; i < temps.size( ); i++ ) {
            TempDataDTO data = temps.get( i );
            if (txtOrderId.getText().equals( data.getOrderId() )){
                boolean remove = temps.remove( data );
                if (remove){
                    for ( int j = 0; j < tempTableArray.size( ); j++ ) {
                        TempTableDTO table = tempTableArray.get( j );
                        if (txtOrderId.getText().equals( table.getOrderId() )){
                            tempTableArray.remove( table );
                        }
                    }
                    list.refresh();
                    tblTempOrder.refresh();
                }
            }
        }
    }

    public void getAllOrderIdFromArray(){
        ObservableList< String > obs = FXCollections.observableArrayList( );
        for ( TempDataDTO data:temps ) {
            obs.add( data.getOrderId() );
        }
        list.setItems( obs );
    }

    private void clear(){
        setCustomerId();
        txtCusType.setText( "" );
        txtCusName.setText( "" );
        txtCusAddress.setText( "" );
        txtCusCity.setText( "" );
        txtCusProvince.setText( "" );
        txtCusContact.setText( "" );
        txtProductId.setText( "" );
        txtUnitPrice.setText( "" );
        txtQty.setText( "" );
        txtDiscount.setText( "" );
        txtOrderQty.setText( "" );
        txtPaid.setText( "" );
        lblTotal.setText( "" );
        lblBalance.setText("0.0");
        cmbSelectProductName.setValue( null );
        cmbRef.setValue( null );
        cmbOrderType.setValue( null );
        rbtCredit.setSelected(false);
        rbtCard.setSelected(false);
        rbtCash.setSelected(false);
    }

    public void click(MouseEvent mouseEvent) {
        for ( TempDataDTO data:temps ) {
            if (this.list.getSelectionModel().selectedItemProperty().getValue().equals( data.getOrderId() )){
                lblCashierId.setText(data.getCashierId() );
                txtOrderId.setText( data.getOrderId() );
                txtCusId.setText( data.getCusId() );
                txtCusType.setText( data.getCusType() );
                txtCusName.setText(data.getCusName());
                txtCusAddress.setText(data.getCusAddress());
                txtCusCity.setText(data.getCusCity());
                txtCusProvince.setText(data.getCusProvince());
                txtCusContact.setText( String.valueOf( data.getCusContact() ) );
                cmbRef.setValue(String.valueOf(data.getRefId()));
                cmbOrderType.setValue(String.valueOf(data.getOrderType()));
            }
        }
        ObservableList<TempOrderTM> list = FXCollections.observableArrayList( );

        for ( TempTableDTO data:tempTableArray) {
            JFXButton btn = new JFXButton( "Delete" );
            if (this.list.getSelectionModel().selectedItemProperty().getValue().equals( data.getOrderId() )){
                list.add( new TempOrderTM( data.getPropertyId(),data.getProductName(),data.getUnitPrice(),data.getQty(),data.getDiscount(),data.getSubTotal(),btn ) );
            }
            btn.setStyle ( "-fx-background-color: #ff7675;-fx-cursor: hand" );
            btn.setOnAction( (e)->{
                for ( int i = 0; i < tempTableArray.size( ); i++ ) {
                    TempTableDTO table = tempTableArray.get( i );
                    if (txtOrderId.getText( ).equals( table.getOrderId( ) )){
                        if (data.getPropertyId( ).equals( table.getPropertyId( ) )){
                            boolean remove = tempTableArray.remove( table );
                            if (remove){
                                getAllProcessingOrder();
                            }
                        }
                    }
                }
            } );
            lblTotal.setText( String.valueOf( data.getSubTotal() ) );
        }
        tblTempOrder.setItems( list );
    }

    public void btnConfirmOnAction(ActionEvent actionEvent) {
        try{
            int tempId = Integer.parseInt( txtOrderId.getText().split( "O" )[ 1 ] );
            tempId+=1;
            if (tempId < 10){
                txtOrderId.setText("O00"+tempId);
            }else if (tempId<100){
                txtOrderId.setText("O0"+tempId);
            }

            getAllOrderIdFromArray();
            getAllPropertyName();
            clear();
            tblTempOrder.setItems( null );
            //setBtnValid(true);
        }catch ( Exception e ){

        }
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        deleteTempData();
        getAllOrderIdFromArray();
        getAllProcessingOrder();
        setOrderId();
        clear();
    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        clear();
        setOrderId();
        //setBtnValid(true);
        tblTempOrder.setItems(null);
        txtCusType.requestFocus();
    }

    public void btnBackOnAction(MouseEvent mouseEvent) {
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

    public void showReport(){
//        try {
//            // Load the JRXML file
//            String jrxmlFile = "src/main/resources/report/invoice.jrxml";
//            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile);
//
//            // Create a data source
//            Connection connection = DBConnection.getInstance().getConnection();
//
//            // add data for parameters
//            BigDecimal ab = BigDecimal.valueOf(100.00);
//            String paymentMethod = getPaymentMethod();
//            BigDecimal netAmount = BigDecimal.valueOf(Double.parseDouble(lblTotal.getText( )));
//            BigDecimal paidAmount = BigDecimal.valueOf(Double.parseDouble(txtPaid.getText( )));
//            BigDecimal balance = BigDecimal.valueOf(Double.parseDouble(lblBalance.getText( )));
//
//            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("orderId", txtOrderId.getText());
//            parameters.put("orderType", cmbOrderType.getValue());
//            parameters.put("paymentType", paymentMethod );
//            parameters.put("netAmount", netAmount);
//            parameters.put("paidAmount", paidAmount);
//            parameters.put("balance", balance);
//
//            // Fill the report with data
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
//
//            // Export the report
//            JasperViewer viewer = new JasperViewer(jasperPrint,false);
//            viewer.setTitle("Super-Market");
//            viewer.setVisible(true);
//
////            JasperExportManager.exportReportToPdfFile(jasperPrint, "E:\\\\new pos intelleon\\\\new maven pos system\\\\project 1\\\\report9.pdf");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
