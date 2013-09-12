/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.sales;

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
import qbits.report.account.ReportReceivable;
import qbits.report.common.Report;
import qbits.report.common.ReportListener;

/**
 *
 * @author Pipilika
 */
public class UIReceivableCRUD extends UICRUD implements CRUDDataLoaderListener, CRUDListener, ReportListener {

    private UIParentFrame parentFrame;
    private Report report;
    private ArrayList<Receivable> receivables;

    public UIReceivableCRUD(UIParentFrame frame) {

        super();
        parentFrame = frame;

        setTitle("Receivable");
        setShowPopup(false);

        Vector<String> columns = new Vector<>();
        columns.add("All");
        columns.add("Customer Name");
        columns.add("Phone");
        columns.add("Amount");

        addCRUDDataLoaderListener(this);
        addCRUDListener(this);
        setColumns(columns);


        receivables = (new ReportReceivable()).get();
        populateRecords();

        report = new Report();
        report.getReportBanner().setSubTitle("Receivable");
        report.addReportListener(this);
    }

    @Override
    public void load(JTable recordTable) {

        Vector rowData = new Vector();
        DefaultTableModel tableModel = (DefaultTableModel) recordTable.getModel();

        for (Receivable receivable : receivables) {

            rowData.add(rowData.size() + 1);
            rowData.add(receivable.getCustomerName());
            rowData.add(receivable.getPhone());
            rowData.add(receivable.getPayable() - receivable.getPaid());
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

        for (Receivable receivable : receivables) {
            dataSource.add(count++, receivable.getCustomerName(), receivable.getPhone(), receivable.getPayable());
        }
        
        reportBuilder.setDataSource(dataSource);
    }

    @Override
    public void addColumns(JasperReportBuilder reportBuilder) {
        TextColumnBuilder<Integer> slCol = col.column("SL#", "sl", type.integerType());
        TextColumnBuilder<String> nameCol = col.column("Name", "name", type.stringType());
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
