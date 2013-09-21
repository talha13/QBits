/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.report.product;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import static net.sf.dynamicreports.report.builder.DynamicReports.bcode;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.template;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.SplitType;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

/**
 *
 * @author Pipilika
 */
public class ReportBarcode {

    private JasperReportBuilder reportBuilder;

    public ReportBarcode(String barcode, String label, int quantity, String genre) {
        try {
            reportBuilder = new JasperReportBuilder();
            reportBuilder.setTemplate(template().setBarcodeHeight(50));
            reportBuilder.title(getBarcode(barcode, label, quantity, genre));
            reportBuilder.setPageFormat(PageType.A4);
            reportBuilder.setTitleSplitType(SplitType.IMMEDIATE);
            reportBuilder.show();
        } catch (DRException ex) {
            Logger.getLogger(ReportBarcode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ComponentBuilder<?, ?> getBarcode(String barcode, String label, int quantity, String genre) {

        HorizontalListBuilder builder = cmp.horizontalFlowList();

        for (int i = 0; i < quantity; i++) {
            builder.add(cmp.hListCell(barcode(label, bcode.code128(barcode), genre)));
        }

        return builder;
    }

    private ComponentBuilder<?, ?> barcode(String label, ComponentBuilder<?, ?> barcode, String genre) {

        VerticalListBuilder verticalListBuilder = cmp.verticalList();

        StyleBuilder labelStyle = stl.style();
        labelStyle.setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.TOP);
        verticalListBuilder.add(cmp.vListCell(cmp.text("Taradin Super Shop").setStyle(labelStyle)));
        verticalListBuilder.add(cmp.verticalGap(1));
        verticalListBuilder.add(cmp.vListCell(cmp.text(label + "-" + genre).setStyle(labelStyle)));
        verticalListBuilder.add(cmp.verticalGap(1));
        verticalListBuilder.add(barcode.setStyle(labelStyle));
        verticalListBuilder.add(cmp.verticalGap(5));

        return verticalListBuilder;
    }

    public static void main(String[] args) {
        new ReportBarcode("123456789012345", "Windows Seven", 100, "test");
    }
}
