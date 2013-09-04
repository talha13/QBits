/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.purchase.product;

import com.lowagie.text.pdf.hyphenation.TernaryTree;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import qbits.entity.ProductStock;
import qbits.gui.common.UIParentFrame;
import qbits.gui.common.crud.CRUDDataLoaderListener;
import qbits.gui.common.crud.CRUDListener;
import qbits.gui.common.crud.UICRUD;
import qbits.report.product.ProductReport;
import qbitserp.common.Message;

/**
 *
 * @author Pipilika
 */
public class UIProductStock extends UICRUD implements CRUDDataLoaderListener, CRUDListener {

    private UIParentFrame parentFrame;
    private HashMap<Integer, ProductStock> productStocks;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateRecord(int recordID) {
        Message.warning("Action not supported");
    }

    @Override
    public void addRecord() {
        Message.warning("Action not supported");
    }
}
