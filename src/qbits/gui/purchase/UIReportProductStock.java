/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.purchase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import net.sf.dynamicreports.examples.DataSource;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import static net.sf.dynamicreports.report.builder.DynamicReports.grid;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.grid.ColumnTitleGroupBuilder;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import qbits.gui.common.UIParentFrame;
import qbits.gui.common.daterangepicker.DateRangeListener;
import qbits.gui.common.daterangepicker.UIDateRangePicker;
import qbits.report.common.Report;
import qbits.report.common.ReportListener;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import qbits.configuration.Utilities;
import qbits.entity.ProductStock;
import qbits.report.product.ProductReport;

/**
 *
 * @author Pipilika
 */
public class UIReportProductStock extends UIDateRangePicker implements DateRangeListener, ReportListener {

    private UIParentFrame parentFrame;
    private Report report;
    private HashMap<Integer, ProductStock> openingStocks;
    private HashMap<Integer, ProductStock> currentStock;

    public UIReportProductStock(UIParentFrame frame) {

        super();
        this.parentFrame = frame;
        setTitle("Product Stock");
        addDateRangeListener(this);
    }

    @Override
    public void processDateRange(Calendar fromDate, Calendar toDate) {
        ProductReport productReport = new ProductReport();

        openingStocks = productReport.getStocks(null, fromDate.getTime());
        currentStock = productReport.getStocks(fromDate.getTime(), toDate.getTime());

        report = new Report();
        report.addReportListener(this);
        report.getReportBanner().setSubTitle("Product Stock From Date: " + Utilities.getFormattedDate(fromDate.getTime())
                + " To Date: " + Utilities.getFormattedDate(toDate.getTime()));
        report.showReport();
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
    public void loadDataSource(JasperReportBuilder reportBuilder) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        DataSource dataSource = new DataSource("category", "sl", "desp", "punit", "opcpu", "opqty", "optotal", "incpu", "inqty", "intotal", "outcpu", "outqty", "outtotal", "wstgcpu", "wstgqty", "wstgtotal", "closecpu", "closeqty", "closetotal");

        ArrayList<ProductStock> productStocks = sortProductStock(currentStock.values());
        ArrayList<Object> row = new ArrayList<>();
        int count = 1;
        double closingQty = 0;
        ProductStock openingStock = null;
        double productPrice = 0.0;

        for (ProductStock productStock : productStocks) {

            row.clear();
            productPrice = 0.0;
            row.add(productStock.getProduct().getCategory());
            row.add(count);
            row.add(productStock.getProduct().getName() + "-" + productStock.getProduct().getBrand());
            row.add(productStock.getProduct().getUnit());

            if (openingStocks.containsKey(productStock.getProduct().getId())) {
                openingStock = openingStocks.get(productStock.getProduct().getId());
                row.add(openingStock.getProduct().getRpu());
                row.add(openingStock.getQuantityLeft());
                row.add(openingStock.getProduct().getRpu() * openingStock.getQuantityLeft());
                productPrice = openingStock.getProduct().getRpu();
            } else {
                openingStock = null;
                row.add(0.0);
                row.add(0);
                row.add(0.0);
            }

            row.add(productStock.getPurchaseProduct().getRpu());
            row.add(productStock.getPurchaseProduct().getQuantity());
            row.add(productStock.getPurchaseProduct().getTotal());

            if (productStock.getPurchaseProduct().getQuantity() != 0) {
                productPrice = productStock.getPurchaseProduct().getRpu();
            }

            row.add(productStock.getSaleProduct().getRpu());
            row.add(productStock.getSaleProduct().getQuantity());
            row.add(productStock.getSaleProduct().getTotal());

            row.add(productStock.getWastageProduct().getRpu());
            row.add(productStock.getWastageProduct().getQuantity());
            row.add(productStock.getWastageProduct().getTotal());

            closingQty = ((openingStock == null) ? 0 : openingStock.getQuantityLeft()) + productStock.getPurchaseProduct().getQuantity() - productStock.getWastageProduct().getQuantity() - productStock.getSaleProduct().getQuantity() - productStock.getReturnProduct().getQuantity();
            row.add(productPrice);
            row.add(closingQty);
            row.add(productPrice * closingQty);

            dataSource.add(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4), row.get(5), row.get(6), row.get(7), row.get(8), row.get(9), row.get(10), row.get(11), row.get(12), row.get(13), row.get(14), row.get(15), row.get(16), row.get(17), row.get(18));
            count++;
        }

//        dataSource.add("Cat", 1, "Product Description", "unit", 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5);
//        dataSource.add(1, "Product Description", "unit", 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5);
//        dataSource.add(1, "Product Description", "unit", 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5);
//        dataSource.add(1, "Product Description", "unit", 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5);
//        dataSource.add(1, "Product Description", "unit", 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5);
//        dataSource.add(1, "Product Description", "unit", 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5);
//        dataSource.add(1, "Product Description", "unit", 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5);
//        dataSource.add(1, "Product Description", "unit", 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5);
//        dataSource.add(1, "Product Description", "unit", 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5);

        reportBuilder.setDataSource(dataSource);
    }

    @Override
    public void addColumns(JasperReportBuilder reportBuilder) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        StyleBuilder coloumnData = stl.style().setFontSize(8).setVerticalAlignment(VerticalAlignment.MIDDLE).setHorizontalAlignment(HorizontalAlignment.CENTER);

        TextColumnBuilder<String> category = col.column("Category", "category", type.stringType());
        TextColumnBuilder<Integer> sl = col.column("SL#", "sl", type.integerType());
        TextColumnBuilder<String> productDesp = col.column("Product Description", "desp", type.stringType()).setFixedWidth(100);
        TextColumnBuilder<String> productUnit = col.column("Unit", "punit", type.stringType());

        TextColumnBuilder<Double> opCPU = col.column("CPU", "opcpu", type.doubleType());
        TextColumnBuilder<Double> opQty = col.column("QTY", "opqty", type.doubleType());
        TextColumnBuilder<Double> opTotal = col.column("Total", "optotal", type.doubleType());

        TextColumnBuilder<Double> inCPU = col.column("CPU", "incpu", type.doubleType());
        TextColumnBuilder<Double> inQty = col.column("QTY", "inqty", type.doubleType());
        TextColumnBuilder<Double> inTotal = col.column("Total", "intotal", type.doubleType());

        TextColumnBuilder<Double> outCPU = col.column("CPU", "outcpu", type.doubleType());
        TextColumnBuilder<Double> outQty = col.column("QTY", "outqty", type.doubleType());
        TextColumnBuilder<Double> outTotal = col.column("Total", "outtotal", type.doubleType());

        TextColumnBuilder<Double> wstgCPU = col.column("CPU", "wstgcpu", type.doubleType());
        TextColumnBuilder<Double> wstgQty = col.column("QTY", "wstgqty", type.doubleType());
        TextColumnBuilder<Double> wstgTotal = col.column("Total", "wstgtotal", type.doubleType());

        TextColumnBuilder<Double> closeCPU = col.column("CPU", "closecpu", type.doubleType());
        TextColumnBuilder<Double> closeQty = col.column("QTY", "closeqty", type.doubleType());
        TextColumnBuilder<Double> closeTotal = col.column("Total", "closetotal", type.doubleType());

        ColumnTitleGroupBuilder openGroup = grid.titleGroup("Opening", opCPU, opQty, opTotal);
        ColumnTitleGroupBuilder inGroup = grid.titleGroup("Inward", inCPU, inQty, inTotal);
        ColumnTitleGroupBuilder outGroup = grid.titleGroup("Outward", outCPU, outQty, outTotal);
        ColumnTitleGroupBuilder wstgGroup = grid.titleGroup("Wastage", wstgCPU, wstgQty, wstgTotal);
        ColumnTitleGroupBuilder closeGroup = grid.titleGroup("Closing", closeCPU, closeQty, closeTotal);

        category.setStyle(coloumnData);
        sl.setStyle(coloumnData);
        productDesp.setStyle(coloumnData);
        productUnit.setStyle(coloumnData);
        opCPU.setStyle(coloumnData);
        opQty.setStyle(coloumnData);
        opTotal.setStyle(coloumnData);
        inCPU.setStyle(coloumnData);
        inQty.setStyle(coloumnData);
        inTotal.setStyle(coloumnData);
        outCPU.setStyle(coloumnData);
        outQty.setStyle(coloumnData);
        outTotal.setStyle(coloumnData);
        closeCPU.setStyle(coloumnData);
        closeQty.setStyle(coloumnData);
        closeTotal.setStyle(coloumnData);
        wstgCPU.setStyle(coloumnData);
        wstgQty.setStyle(coloumnData);
        wstgTotal.setStyle(coloumnData);

        reportBuilder.columnGrid(category, sl, productDesp, productUnit, openGroup, inGroup, outGroup, wstgGroup, closeGroup);
        reportBuilder.columns(category, sl, productDesp, productUnit, opCPU, opQty, opTotal, inCPU, inQty, inTotal,
                outCPU, outQty, outTotal, wstgCPU, wstgQty, wstgTotal, closeCPU, closeQty, closeTotal);

        reportBuilder.groupBy(category);
        reportBuilder.subtotalsAtSummary(sbt.sum(closeTotal));
        reportBuilder.subtotalsAtFirstGroupFooter(sbt.sum(closeQty), sbt.sum(closeTotal));

    }

    @Override
    public void pageFormat(JasperReportBuilder reportBuilder) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        reportBuilder.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
    }
}
