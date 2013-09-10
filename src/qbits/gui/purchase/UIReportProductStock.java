/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.purchase;

import java.util.Calendar;
import net.sf.dynamicreports.examples.DataSource;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
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

/**
 *
 * @author Pipilika
 */
public class UIReportProductStock extends UIDateRangePicker implements DateRangeListener, ReportListener {

    private UIParentFrame parentFrame;
    private Report report;

    public UIReportProductStock(UIParentFrame frame) {

        super();
        this.parentFrame = frame;
        setTitle("Product Stock");
        addDateRangeListener(this);

    }

    @Override
    public void processDateRange(Calendar fromDate, Calendar toDate) {
//        System.out.println("FROM: " + fromDate.getTime());
//        System.out.println("TO: " + toDate.getTime());
//        ProductReport productReport = new ProductReport();
        report = new Report();
        report.addReportListener(this);
        report.getReportBanner().setSubTitle("Product Stock From Date: " + Utilities.getFormattedDate(fromDate.getTime())
                + " To Date: " + Utilities.getFormattedDate(toDate.getTime()));
        report.showReport();
    }

    @Override
    public void loadDataSource(JasperReportBuilder reportBuilder) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        DataSource dataSource = new DataSource("sl", "desp", "punit", "opcpu", "opqty", "optotal", "incpu", "inqty", "intotal", "outcpu", "outqty", "outtotal", "wstgcpu", "wstgqty", "wstgtotal", "closecpu", "closeqty", "closetotal");

        dataSource.add(1, "Product Description", "unit", 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5);
        dataSource.add(1, "Product Description", "unit", 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5);
        dataSource.add(1, "Product Description", "unit", 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5);
        dataSource.add(1, "Product Description", "unit", 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5);
        dataSource.add(1, "Product Description", "unit", 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5);
        dataSource.add(1, "Product Description", "unit", 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5);
        dataSource.add(1, "Product Description", "unit", 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5);
        dataSource.add(1, "Product Description", "unit", 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5);
        dataSource.add(1, "Product Description", "unit", 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5, 12145897.5);

        reportBuilder.setDataSource(dataSource);
    }

    @Override
    public void addColumns(JasperReportBuilder reportBuilder) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        StyleBuilder coloumnData = stl.style().setFontSize(8).setVerticalAlignment(VerticalAlignment.MIDDLE).setHorizontalAlignment(HorizontalAlignment.CENTER);

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

        reportBuilder.columnGrid(sl, productDesp, productUnit, openGroup, inGroup, outGroup, wstgGroup, closeGroup);
        reportBuilder.columns(sl, productDesp, productUnit, opCPU, opQty, opTotal, inCPU, inQty, inTotal,
                outCPU, outQty, outTotal, wstgCPU, wstgQty, wstgTotal, closeCPU, closeQty, closeTotal);

    }

    @Override
    public void pageFormat(JasperReportBuilder reportBuilder) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        reportBuilder.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
    }
}
