/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.report.product;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.dynamicreports.examples.Templates;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import static net.sf.dynamicreports.report.builder.DynamicReports.bcode;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.template;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.MultiPageListBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.grid.VerticalColumnGridListBuilder;
import net.sf.dynamicreports.report.builder.style.ReportStyleBuilder;
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

    private float barcodeWidth;
    private float barcodeHeight;
    private JasperReportBuilder reportBuilder;

    public ReportBarcode(String barcode, String label, int quantity) {
        try {
            reportBuilder = new JasperReportBuilder();
            reportBuilder.setTemplate(template().setBarcodeHeight(50));
            reportBuilder.title(getBarcode(barcode, label, quantity));
            reportBuilder.setPageFormat(PageType.A4);
            reportBuilder.setTitleSplitType(SplitType.IMMEDIATE);
            reportBuilder.show();
        } catch (DRException ex) {
            Logger.getLogger(ReportBarcode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ComponentBuilder<?, ?> getBarcode(String barcode, String label, int quantity) {

        HorizontalListBuilder builder = cmp.horizontalFlowList();

        for (int i = 0; i < quantity; i++) {
            builder.add(cmp.hListCell(barcode(label, bcode.code128(barcode))));
        }

        return builder;
    }

    private ComponentBuilder<?, ?> barcode(String label, ComponentBuilder<?, ?> barcode) {

        VerticalListBuilder verticalListBuilder = cmp.verticalList();

        StyleBuilder labelStyle = stl.style();
        labelStyle.setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.TOP);
        verticalListBuilder.add(cmp.vListCell(cmp.text(label).setStyle(labelStyle)));
        verticalListBuilder.add(cmp.verticalGap(1));
        verticalListBuilder.add(barcode.setStyle(labelStyle));
        verticalListBuilder.add(cmp.verticalGap(5));

        return verticalListBuilder;
    }

    public static void main(String[] args) {
        new ReportBarcode("123456789012345", "12121212221", 148);
    }
}
