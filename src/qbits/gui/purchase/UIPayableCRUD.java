/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.purchase;

import qbits.gui.sales.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.dynamicreports.examples.DataSource;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import qbits.common.Message;
import qbits.entity.Receivable;
import qbits.gui.common.UIParentFrame;
import qbits.gui.common.crud.CRUDDataLoaderListener;
import qbits.gui.common.crud.CRUDListener;
import qbits.gui.common.crud.UICRUD;
import qbits.report.account.ReportPayable;
import qbits.report.account.ReportReceivable;
import qbits.report.common.Report;
import qbits.report.common.ReportListener;

/**
 *
 * @author Pipilika
 */
public class UIPayableCRUD extends UICRUD implements CRUDDataLoaderListener, CRUDListener, ReportListener {

    private UIParentFrame parentFrame;
    private Report report;
    private ArrayList<Receivable> payables;

    public UIPayableCRUD(UIParentFrame frame) {

        super();
        parentFrame = frame;

        setTitle("Payables");
        setShowPopup(false);

        Vector<String> columns = new Vector<>();
        columns.add("All");
        columns.add("Supplier Name");
        columns.add("Phone");
        columns.add("Amount");

        addCRUDDataLoaderListener(this);
        addCRUDListener(this);
        setColumns(columns);


        payables = (new ReportPayable()).get();
        populateRecords();

        report = new Report();
        report.getReportBanner().setSubTitle("Payables");
        report.addReportListener(this);
    }

    @Override
    public void load(JTable recordTable) {

        Vector rowData = new Vector();
        DefaultTableModel tableModel = (DefaultTableModel) recordTable.getModel();

        for (Receivable payable : payables) {

            rowData.add(rowData.size() + 1);
            rowData.add(payable.getCustomerName());
            rowData.add(payable.getPhone());
            rowData.add(payable.getPayable() - payable.getPaid());
        }

        tableModel.addRow(rowData);
        changeStatusMessage(rowData.size());
    }

    @Override
    public void removeRecord(int recordID) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Message.warning("Action not supported");
    }

    @Override
    public void printRecords() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        report.showReport();
    }

    @Override
    public void updateRecord(int recordID) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Message.warning("Action not supported");
    }

    @Override
    public void addRecord() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Message.warning("Action not supported");
    }

    @Override
    public void loadDataSource(JasperReportBuilder reportBuilder) {

        DataSource dataSource = new DataSource("sl", "name", "phone", "amount");
        int count = 1;

        for (Receivable payable : payables) {
            dataSource.add(count++, payable.getCustomerName(), payable.getPhone(), payable.getPayable()-payable.getPaid());
        }
        
        reportBuilder.setDataSource(dataSource);
    }

    @Override
    public void addColumns(JasperReportBuilder reportBuilder) {
        TextColumnBuilder<Integer> slCol = col.column("SL#", "sl", type.integerType());
        TextColumnBuilder<String> nameCol = col.column("Supplier Name", "name", type.stringType());
        TextColumnBuilder<String> phoneCol = col.column("Phone", "phone", type.stringType());
        TextColumnBuilder<Double> amountCol = col.column("Amount", "amount", type.doubleType());

        reportBuilder.columns(slCol, nameCol, phoneCol, amountCol);
        reportBuilder.subtotalsAtSummary(sbt.sum(amountCol));       
    }

    @Override
    public void pageFormat(JasperReportBuilder reportBuilder) {
        reportBuilder.setPageFormat(PageType.A4, PageOrientation.PORTRAIT);
    }
}
