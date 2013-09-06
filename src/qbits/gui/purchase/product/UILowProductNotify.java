/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.purchase.product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.dynamicreports.examples.DataSource;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import qbits.db.QueryBuilder;
import qbits.entity.ProductStock;
import qbits.gui.common.UIParentFrame;
import qbits.gui.common.crud.CRUDDataLoaderListener;
import qbits.gui.common.crud.CRUDListener;
import qbits.gui.common.crud.UICRUD;
import qbits.report.common.Report;
import qbits.report.common.ReportListener;
import qbits.report.product.ProductReport;
import qbits.common.Message;

/**
 *
 * @author Pipilika
 */
public class UILowProductNotify extends UICRUD implements CRUDListener, CRUDDataLoaderListener, ReportListener {

    private UIParentFrame parentFrame;
    private HashMap<Integer, ProductStock> productStocks;
    private Report report;

    public UILowProductNotify(UIParentFrame frame) {

        super();
        setTitle("Low Quantity Product");
        setShowPopup(false);

        parentFrame = frame;

        Vector<String> columns = new Vector<>();

        columns.add("All");
        columns.add("Name");
        columns.add("Category");
        columns.add("Brand");
        columns.add("Unit");
        columns.add("Quantity");

        addCRUDDataLoaderListener(this);
        addCRUDListener(this);

        ProductReport productReport = new ProductReport();
        productStocks = productReport.getCurrentStocks();

        setColumns(columns);
        populateRecords();

        report = new Report();
        report.getReportBanner().setSubTitle("Low Quantity Product");
        report.addReportListener(this);
    }

    @Override
    public void removeRecord(int recordID) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Message.warning("Action not supported");
    }

    @Override
    public void printRecords() {
        report.showReport();
    }

    @Override
    public void updateRecord(int recordID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addRecord() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Message.warning("Action not supported");
    }

    @Override
    public void load(JTable recordTable) {

        Vector rowData;
        ProductStock productStock;
        int count = 0;
        DefaultTableModel tableModel = (DefaultTableModel) recordTable.getModel();

        for (Integer productID : productStocks.keySet()) {

            productStock = productStocks.get(productID);
            System.out.println(productStock.getProduct().getNotifyQuantity());
            System.out.println(productStock.getQuantityLeft());

            if (productStock.getProduct().getNotifyQuantity() >= productStock.getQuantityLeft()) {
                count++;
                rowData = new Vector();

                rowData.add(count);
                rowData.add(productStock.getProduct().getName());
                rowData.add(productStock.getProduct().getCategory());
                rowData.add(productStock.getProduct().getBrand());
                rowData.add(productStock.getProduct().getUnit());
                rowData.add(productStock.getQuantityLeft());

                tableModel.addRow(rowData);
            }
        }

        changeStatusMessage(count);
    }

    @Override
    public void loadDataSource(JasperReportBuilder reportBuilder) {
        DataSource dataSource = new DataSource("sl", "name", "category", "brand", "unit", "qty");
        int count = 0;

        ArrayList<ProductStock> sortedStocks = sortProductStock(productStocks.values());

        for (ProductStock productStock : sortedStocks) {

            count++;
            dataSource.add(count, productStock.getProduct().getName(), productStock.getProduct().getCategory(),
                    productStock.getProduct().getBrand(), productStock.getProduct().getUnit(), productStock.getQuantityLeft());

        }
        reportBuilder.setDataSource(dataSource);
    }

    private ArrayList<ProductStock> sortProductStock(Collection<ProductStock> stocks) {

        ArrayList<ProductStock> sortedStocks = new ArrayList<>(stocks);

        Comparator<ProductStock> comparator = new Comparator<ProductStock>() {
            @Override
            public int compare(ProductStock p1, ProductStock p2) {
                return p1.getProduct().getCategory().compareTo(p2.getProduct().getCategory());
            }
        };

        Collections.sort(sortedStocks, comparator);

        return sortedStocks;

    }

    @Override
    public void addColumns(JasperReportBuilder reportBuilder) {

        TextColumnBuilder<Integer> slCol = col.column("SL#", "sl", type.integerType());
        TextColumnBuilder<String> nameCol = col.column("Name", "name", type.stringType());
        TextColumnBuilder<String> categoryCol = col.column("Category", "category", type.stringType());
        TextColumnBuilder<String> unitCol = col.column("Unit", "unit", type.stringType());
        TextColumnBuilder<String> brandCol = col.column("Brand", "brand", type.stringType());
        TextColumnBuilder<Double> quantityCol = col.column("Quantity", "qty", type.doubleType());

        reportBuilder.columns(slCol, nameCol, categoryCol, brandCol, unitCol, quantityCol);
        reportBuilder.groupBy(categoryCol);
    }

    @Override
    public void pageFormat(JasperReportBuilder reportBuilder) {
    }
}
