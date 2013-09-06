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
import net.sf.dynamicreports.examples.Templates;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import qbits.entity.ProductStock;
import qbits.gui.common.UIParentFrame;
import qbits.gui.common.crud.CRUDDataLoaderListener;
import qbits.gui.common.crud.CRUDListener;
import qbits.gui.common.crud.UICRUD;
import qbits.report.common.Report;
import qbits.report.common.ReportListener;
import qbits.report.product.ProductReport;
import qbitserp.common.Message;

/**
 *
 * @author Pipilika
 */
public class UIProductStock extends UICRUD implements CRUDDataLoaderListener, CRUDListener, ReportListener {

    private UIParentFrame parentFrame;
    private HashMap<Integer, ProductStock> productStocks;
    private Report report;

    public UIProductStock(UIParentFrame frame) {

        super();
        setTitle("Product Stocks");
        setShowPopup(false);

        parentFrame = frame;

        Vector<String> columns = new Vector<>();

        columns.add("All");
        columns.add("Name");
        columns.add("Category");
        columns.add("Brand");
        columns.add("Quantity");
        columns.add("Cost Per Unit");
        columns.add("Rate Per Unit");

        addCRUDDataLoaderListener(this);
        addCRUDListener(this);

        ProductReport productReport = new ProductReport();
        productStocks = productReport.getCurrentStocks();

        setColumns(columns);
        populateRecords();

        report = new Report();
        report.getReportBanner().setSubTitle("Product Stock Report");
        report.addReportListener(this);
    }

    @Override
    public void load(JTable recordTable) {

        Vector rowData;
        ProductStock productStock;
        int count = 0;
        DefaultTableModel tableModel = (DefaultTableModel) recordTable.getModel();

        for (Integer productID : productStocks.keySet()) {

            count++;
            rowData = new Vector();
            productStock = productStocks.get(productID);
            rowData.add(count);
            rowData.add(productStock.getProduct().getName());
            rowData.add(productStock.getProduct().getCategory());
            rowData.add(productStock.getProduct().getBrand());
            rowData.add(productStock.getQuantityLeft());
            rowData.add(productStock.getPurchaseProduct().getRpu());
            rowData.add(productStock.getProduct().getRpu());

            tableModel.addRow(rowData);
        }

        changeStatusMessage(count);
    }

    @Override
    public void removeRecord(int recordID) {
        Message.warning("Action not supported");
    }

    @Override
    public void printRecords() {
        report.showReport();
    }

    @Override
    public void updateRecord(int recordID) {
        Message.warning("Action not supported");
    }

    @Override
    public void addRecord() {
        Message.warning("Action not supported");
    }

    @Override
    public void loadDataSource(JasperReportBuilder reportBuilder) {

        DataSource dataSource = new DataSource("sl", "name", "category", "brand", "qty", "cpu", "rpu");
        int count = 0;

        ArrayList<ProductStock> sortedStocks = sortProductStock(productStocks.values());

        for (ProductStock productStock : sortedStocks) {

            count++;
            dataSource.add(count, productStock.getProduct().getName(), productStock.getProduct().getCategory(),
                    productStock.getProduct().getBrand(), productStock.getQuantityLeft(), productStock.getPurchaseProduct().getRpu(),
                    productStock.getProduct().getRpu());

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
        TextColumnBuilder<String> brandCol = col.column("Brand", "brand", type.stringType());
        TextColumnBuilder<Double> quantityCol = col.column("Quantity", "qty", type.doubleType());
        TextColumnBuilder<Double> cpuCol = col.column("Cost Per Unit", "cpu", type.doubleType());
        TextColumnBuilder<Double> rpuCol = col.column("Rate Per Unit", "rpu", type.doubleType());

        reportBuilder.columns(slCol, nameCol, categoryCol, brandCol, quantityCol, cpuCol, rpuCol);
        reportBuilder.groupBy(categoryCol);

    }

    @Override
    public void pageFormat(JasperReportBuilder reportBuilder) {
        reportBuilder.setPageFormat(PageType.A4, PageOrientation.PORTRAIT);
    }
}
