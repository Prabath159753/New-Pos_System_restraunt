package lk.intelleon.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.intelleon.bo.BOFactory;
import lk.intelleon.bo.custom.CustomerBO;
import lk.intelleon.bo.custom.SystemReportBO;
import lk.intelleon.dto.CustomerDTO;
import lk.intelleon.dto.OrderDTO;
import lk.intelleon.dto.OrderDetailDTO;
import lk.intelleon.dto.tm.OrderAnnualTM;
import lk.intelleon.dto.tm.OrderDetailTM;
import lk.intelleon.dto.tm.OrderTM;
import lk.intelleon.dto.tm.OrdersWiseCusTM;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Kavishka Prabath
 * @created : 10/6/2023 - 8:07 AM
 **/

public class SystemReportFormController {
    public AnchorPane root;
    public JFXButton btnOrder;
    public JFXButton btnOrderDetails;
    public JFXButton btnCusWiseIncome;
    public JFXButton btnAnnualIncome;
    public JFXComboBox cmbSelect;
    public JFXTextField txtOrderId;
    public JFXTextField txtCusId;
    public JFXComboBox cmbPaymentMethod;
    public TableView tblOrder;
    public TableColumn colOrderTblOrderId;
    public TableColumn colOrderTblDate;
    public TableColumn colOrderTblTotal;
    public TableColumn colOrderTblCusId;
    public TableColumn colOrderTblUserId;
    public TableColumn colOrderTblPaymentMethod;
    public TableColumn colOrderTblOption;
    public JFXTextField txtOrderDetails;
    public TableView tblOrderDetails;
    public TableColumn colDetailQty;
    public TableColumn colDetailUnitPrice;
    public TableColumn colDiscount;
    public TableColumn colDetailOrderId;
    public TableColumn colDetailPropId;
    public TableColumn colReId;
    public JFXComboBox cmbCusId;
    public Label lblTotal;
    public TableView tblCusWiseIncome;
    public TableColumn colCusId;
    public TableColumn colOrderId;
    public TableColumn colTotalCost;
    public TableColumn colPaymentMethod;
    public TableView tblAnnualIncome;
    public TableColumn colIncomeOrderId;
    public TableColumn colIncomeDate;
    public TableColumn colIncomeTot;
    public TableColumn colIncomeCusId;
    public TableColumn colIncomeUserId;
    public JFXComboBox cmbAnnual;
    public Label lblAnnualTot;
    public JFXDatePicker datePicker;
    public JFXDatePicker pickerToDate;
    public TableColumn colOrderTblOrderType;

    SystemReportBO systemReportBO = (SystemReportBO) BOFactory.getInstance().getBO(BOFactory.BOType.SYSTEMREPORT);
    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);

    public void initialize(){
        colOrderTblOrderId.setCellValueFactory( new PropertyValueFactory<>( "orderId" ) );
        colOrderTblDate.setCellValueFactory( new PropertyValueFactory<>( "dateTime" ) );
        colOrderTblTotal.setCellValueFactory( new PropertyValueFactory<>( "total" ) );
        colOrderTblCusId.setCellValueFactory( new PropertyValueFactory<>( "cusId" ) );
        colOrderTblUserId.setCellValueFactory( new PropertyValueFactory<>( "userId" ) );
        colOrderTblPaymentMethod.setCellValueFactory( new PropertyValueFactory<>( "paymentMethod" ) );
        colOrderTblOrderType.setCellValueFactory( new PropertyValueFactory<>( "orderType" ) );
        colOrderTblOption.setCellValueFactory( new PropertyValueFactory<>( "btn" ) );
        colOrderTblOption.setStyle ( "-fx-alignment:center" );
        getAllOrders();

        colDetailQty.setCellValueFactory( new PropertyValueFactory<>( "qty" ) );
        colDetailUnitPrice.setCellValueFactory( new PropertyValueFactory<>( "unitPrice" ) );
        colDiscount.setCellValueFactory( new PropertyValueFactory<>( "disCount" ) );
        colDetailOrderId.setCellValueFactory( new PropertyValueFactory<>( "orderId" ) );
        colDetailPropId.setCellValueFactory( new PropertyValueFactory<>( "propertyId" ) );
        colReId.setCellValueFactory( new PropertyValueFactory<>( "refId" ) );
        getAllOrderDetails();

        ObservableList<Object> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.add("By OrderId");
        observableArrayList.add("By Customer");
        observableArrayList.add("By Payment Method");
        cmbSelect.setItems(observableArrayList);

        ObservableList<Object> paymentMethodList = FXCollections.observableArrayList();
        paymentMethodList.add("All");
        paymentMethodList.add("Cash");
        paymentMethodList.add("Card");
        paymentMethodList.add("Debit");
        cmbPaymentMethod.setItems(paymentMethodList);

        colCusId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        getAllOrderWiseCusId();

        ObservableList<Object> list = FXCollections.observableArrayList();
        list.add("Daily");
        list.add("Monthly");
        cmbAnnual.setItems(list);

        colIncomeOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colIncomeDate.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        colIncomeTot.setCellValueFactory(new PropertyValueFactory<>("total"));
        colIncomeCusId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colIncomeUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    private void getAllOrders(){
        try {
            List<OrderDTO> allOrders = systemReportBO.getAllOrders();

            ObservableList<OrderTM> orderTMS = FXCollections.observableArrayList( );

            for ( OrderDTO order : allOrders) {
                JFXButton btn = new JFXButton( "DELETE" );
                orderTMS.add( new OrderTM(
                        order.getOrderId(),order.getDateTime(),
                        order.getTotal(),order.getCusId(),
                        order.getUserId(),order.getPaymentMethod(),order.getOrderType(),btn
                ) );
                btn.setStyle ( "-fx-background-color: #ff7675;-fx-cursor: hand" );
                btn.setOnAction( (e)->{
                    try {
                        boolean b = systemReportBO.deleteOrder(order.getOrderId());
                        if (b){
                            new Alert( Alert.AlertType.CONFIRMATION,"Deleted !" ).show();
                            getAllOrders();
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } );
            }
            tblOrder.setItems( orderTMS );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAllOrderDetails(){
        try {
            List<OrderDetailDTO> allOrderDetails = systemReportBO.getAllOrderDetails();

            ObservableList<OrderDetailTM> orderDetailTMS = FXCollections.observableArrayList( );
            for ( OrderDetailDTO detail : allOrderDetails) {
                orderDetailTMS.add( new OrderDetailTM(
                        detail.getQty(),detail.getUnitPrice(),
                        detail.getDisCount(),detail.getOrderId(),
                        detail.getProductId(),detail.getRefId(),detail.getAmount()
                ) );
            }
            tblOrderDetails.setItems( orderDetailTMS );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAllOrderWiseCusId(){
        try {
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomer();

            ObservableList<String > cusId = FXCollections.observableArrayList();

            for (CustomerDTO customer:allCustomers ) {
                cusId.add(customer.getCusId());
            }
            cmbCusId.setItems(cusId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnOrderOnAction(ActionEvent actionEvent) {
        tblOrder.setVisible( true );
        tblOrderDetails.setVisible( false );
        tblCusWiseIncome.setVisible(false);
        txtOrderId.setVisible( false );
        txtOrderDetails.setVisible( false );
        cmbSelect.setVisible(true);
        txtCusId.setVisible(false);
        cmbCusId.setVisible(false);
        lblTotal.setVisible(false);
        cmbAnnual.setVisible(false);
        tblAnnualIncome.setVisible(false);
        lblAnnualTot.setVisible(false);
        datePicker.setVisible(false);
        pickerToDate.setVisible(false);
        cmbPaymentMethod.setVisible(false);
        getAllOrders();
        clear();
    }

    public void btnOrderDetailsOnAction(ActionEvent actionEvent) {
        tblOrder.setVisible( false );
        tblOrderDetails.setVisible( true );
        tblCusWiseIncome.setVisible(false);
        txtOrderId.setVisible( false );
        txtOrderDetails.setVisible( true );
        cmbSelect.setVisible(false);
        txtCusId.setVisible(false);
        cmbCusId.setVisible(false);
        lblTotal.setVisible(false);
        cmbAnnual.setVisible(false);
        tblAnnualIncome.setVisible(false);
        lblAnnualTot.setVisible(false);
        datePicker.setVisible(false);
        pickerToDate.setVisible(false);
        cmbPaymentMethod.setVisible(false);
        getAllOrderDetails();
        clear();
    }

    public void btnCusWiseIncomeOnAction(ActionEvent actionEvent) {
        tblOrder.setVisible( false );
        tblOrderDetails.setVisible( false );
        tblCusWiseIncome.setVisible(true);
        txtOrderId.setVisible( false );
        txtOrderDetails.setVisible( false );
        cmbSelect.setVisible(false);
        txtCusId.setVisible(false);
        cmbCusId.setVisible(true);
        lblTotal.setVisible(true);
        cmbAnnual.setVisible(false);
        tblAnnualIncome.setVisible(false);
        lblAnnualTot.setVisible(false);
        datePicker.setVisible(false);
        pickerToDate.setVisible(false);
        cmbPaymentMethod.setVisible(false);
        clear();
    }

    public void btnAnnualIncomeOnAction(ActionEvent actionEvent) {
        tblOrder.setVisible( false );
        tblOrderDetails.setVisible( false );
        tblCusWiseIncome.setVisible(false);
        txtOrderId.setVisible( false );
        txtOrderDetails.setVisible( false );
        cmbSelect.setVisible(false);
        txtCusId.setVisible(false);
        cmbCusId.setVisible(false);
        lblTotal.setVisible(false);
        cmbAnnual.setVisible(true);
        tblAnnualIncome.setVisible(true);
        lblAnnualTot.setVisible(true);
        cmbPaymentMethod.setVisible(false);
        clear();
    }

    public void cmbSelectOnAction(ActionEvent actionEvent) {
        Object value = cmbSelect.getValue();
        if (value=="By OrderId"){
            txtCusId.setVisible(false);
            txtOrderId.setVisible(true);
            cmbPaymentMethod.setVisible(false);
        }else if (value=="By Customer"){
            txtCusId.setVisible(true);
            txtOrderId.setVisible(false);
            cmbPaymentMethod.setVisible(false);
        }else if (value=="By Payment Method"){
            txtCusId.setVisible(false);
            txtOrderId.setVisible(false);
            cmbPaymentMethod.setVisible(true);
        }
    }

    public void txtOrderIdOnAction(ActionEvent actionEvent) {
        tblOrder.setItems( null );
        try {
            OrderDTO order = systemReportBO.getOrder(txtOrderId.getText());

            ObservableList< OrderTM > orderTMS = FXCollections.observableArrayList( );
            JFXButton btn = new JFXButton( "DELETE" );
            if (order!=null){
                orderTMS.add( new OrderTM(
                        order.getOrderId(),order.getDateTime(),
                        order.getTotal(),order.getCusId(),
                        order.getUserId(),btn
                ));
                btn.setStyle ( "-fx-background-color: #ff7675;-fx-cursor: hand" );
                btn.setOnAction( (e)->{
                    try {
                        boolean b = systemReportBO.deleteOrder(order.getOrderId());

                        if (b){
                            new Alert( Alert.AlertType.CONFIRMATION,"Deleted !" ).show();
                            txtOrderId.setText( "" );
                            getAllOrders();
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } );
                txtOrderId.setText( "" );
            }else {
                new Alert( Alert.AlertType.ERROR,"Empty Order !" ).show();
            }
            tblOrder.setItems( orderTMS );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void txtCusIdOnAction(ActionEvent actionEvent) {
        try {
            List<OrderDTO> allOrdersByCusId = systemReportBO.getAllOrdersByCusId(txtCusId.getText());

            ObservableList<OrderTM> orderTMS = FXCollections.observableArrayList();

            for (OrderDTO order :allOrdersByCusId ){
                JFXButton btn = new JFXButton( "DELETE" );
                orderTMS.add(new OrderTM(
                        order.getOrderId(),
                        order.getDateTime(),
                        order.getTotal(),
                        order.getCusId(),
                        order.getUserId(),
                        btn
                ));
                btn.setStyle ( "-fx-background-color: #ff7675;-fx-cursor: hand" );
                btn.setOnAction( (e)->{
                    try {
                        boolean b = systemReportBO.deleteOrder(order.getOrderId());

                        if (b){
                            new Alert( Alert.AlertType.CONFIRMATION,"Deleted !" ).show();
                            txtCusId.setText( "" );
                            getAllOrders();
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } );
            }
            tblOrder.setItems(orderTMS);
            txtCusId.setText("");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void cmbPaymentMethodOnAction(ActionEvent actionEvent) {
        Object value = cmbPaymentMethod.getValue();
        if (value=="All"){
            getAllOrders();
        }
        else {
            try {
                String paymentMethod = String.valueOf(cmbPaymentMethod.getValue());
                List<OrderDTO> allOrdersByPaymentMethod = systemReportBO.getAllOrdersByPaymentMethod(paymentMethod);

                ObservableList<OrderTM> orderTMS = FXCollections.observableArrayList();

                for (OrderDTO order :allOrdersByPaymentMethod ){
                    JFXButton btn = new JFXButton( "DELETE" );
                    orderTMS.add(new OrderTM(
                            order.getOrderId(),
                            order.getDateTime(),
                            order.getTotal(),
                            order.getCusId(),
                            order.getUserId(),
                            order.getPaymentMethod(),
                            order.getOrderType(),
                            btn
                    ));
                    btn.setStyle ( "-fx-background-color: #ff7675;-fx-cursor: hand" );
                    btn.setOnAction( (e)->{
                        try {
                            boolean b = systemReportBO.deleteOrder(order.getOrderId());

                            if (b){
                                new Alert( Alert.AlertType.CONFIRMATION,"Deleted !" ).show();
                                txtCusId.setText( "" );
                                getAllOrders();
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    } );
                }
                tblOrder.setItems(orderTMS);

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void txtOrderDetailsOnAction(ActionEvent actionEvent) {
        tblOrderDetails.setItems( null );
        try {
            List<OrderDetailDTO> orderDetail = systemReportBO.getOrderDetail(txtOrderDetails.getText());
            ObservableList<OrderDetailTM> orderDetailTMS = FXCollections.observableArrayList( );
            if (orderDetail!=null){
                for ( OrderDetailDTO detail : orderDetail) {
                    orderDetailTMS.add( new OrderDetailTM(
                            detail.getQty(),detail.getUnitPrice(),
                            detail.getDisCount(),detail.getOrderId(),
                            detail.getProductId(),detail.getRefId(),detail.getAmount()
                    ) );
                }
                txtOrderDetails.setText( "" );
            }
            tblOrderDetails.setItems( orderDetailTMS );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectCusIdOnAction(ActionEvent actionEvent) {
        try {
            ArrayList<OrderDTO> allOrdersByCusId = systemReportBO.getAllOrdersByCusId(String.valueOf(cmbCusId.getValue()));

            ObservableList<OrdersWiseCusTM> wiseCusTMS = FXCollections.observableArrayList();

            double totalCost = 0.00;
            for (OrderDTO order : allOrdersByCusId) {
                wiseCusTMS.add(new OrdersWiseCusTM(
                        order.getCusId(),
                        order.getOrderId(),
                        order.getTotal(),
                        order.getPaymentMethod()
                ));
                totalCost += order.getTotal();
            }

            lblTotal.setText("Total : " + totalCost + "LKR");
            tblCusWiseIncome.setItems(wiseCusTMS);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void cmbAnnualOnAction(ActionEvent actionEvent) {
        pickerToDate.setVisible(false);

        try {
            double total = 00.00;
            Object value = cmbAnnual.getValue();
            if (value =="Daily"){
                datePicker.setVisible(true);
                datePicker.setValue(LocalDate.now());
                ArrayList<OrderDTO> allOrderByDaily = systemReportBO.getAllOrderByDaily(String.valueOf(LocalDate.now()));

                ObservableList<OrderAnnualTM> orderAnnualTMS = FXCollections.observableArrayList();

                for (OrderDTO order :allOrderByDaily) {
                    orderAnnualTMS.add(new OrderAnnualTM(
                            order.getOrderId(),
                            order.getDateTime(),
                            order.getTotal(),
                            order.getCusId(),
                            order.getUserId()
                    ));
                    total+=order.getTotal();
                }
                lblAnnualTot.setText("Total : "+total+"LKR");
                tblAnnualIncome.setItems(orderAnnualTMS);
            }else if (value == "Monthly"){
                datePicker.setVisible(true);
                pickerToDate.setVisible(true);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getMonthlyOrdersOnAction(ActionEvent actionEvent) {
        double total = 00.00;
        try {
//        LocalDate datePickerValue = datePicker.getValue();
//        String sMonth = String.valueOf(datePickerValue).split("-")[1];
//        int iMonth = Integer.parseInt(sMonth);
//        int calMonth = iMonth+1;
//
//        String afterMonth = String.valueOf(datePickerValue).split("-")[0]+"-"+"0"+calMonth+"-"+String.valueOf(datePickerValue).split("-")[2];
//
//        pickerToDate.setValue(LocalDate.parse(afterMonth));
            LocalDate value = datePicker.getValue();
            LocalDate value1 = pickerToDate.getValue();

            ArrayList<OrderDTO> allOrderByMonthly = systemReportBO.getAllOrderByMonthly(String.valueOf(value), String.valueOf(value1));

            ObservableList<OrderAnnualTM> orderAnnualTMS = FXCollections.observableArrayList();

            for (OrderDTO order :allOrderByMonthly) {
                orderAnnualTMS.add(new OrderAnnualTM(
                        order.getOrderId(),
                        order.getDateTime(),
                        order.getTotal(),
                        order.getCusId(),
                        order.getUserId()
                ));
                total+=order.getTotal();
            }
            lblAnnualTot.setText("Total : "+total+"LKR");
            tblAnnualIncome.setItems(orderAnnualTMS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clear(){
        cmbSelect.setValue(null);
        txtOrderId.setText( "" );
        txtCusId.setText( "" );
        cmbPaymentMethod.setValue(null);
        txtOrderDetails.setText( "" );
        cmbCusId.setValue(null);
        lblTotal.setText( "Total : 0.0LKR" );
        cmbAnnual.setValue(null);
        lblAnnualTot.setText( "Total : 0.0LKR" );
    }

}
