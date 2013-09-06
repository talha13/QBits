/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.report.common;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.dynamicreports.examples.Templates;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import qbits.configuration.Configuration;

/**
 *
 * @author Pipilika
 */
public class Report extends JasperReportBuilder {

    private ReportBanner reportBanner;
    private ReportListener reportListener;
    private StyleBuilder copyrightStyle;
    private StyleBuilder columnTitleStyle;

    public Report() {
        super();

        reportBanner = new ReportBanner();

        copyrightStyle = stl.style();
        copyrightStyle.italic();
        copyrightStyle.setForegroundColor(Color.lightGray);

        StyleBuilder boldStyle = stl.style().bold();
        StyleBuilder boldCenteredStyle = stl.style(boldStyle).setHorizontalAlignment(HorizontalAlignment.CENTER);
        columnTitleStyle = stl.style(boldCenteredStyle).setBorder(stl.penThin()).setBackgroundColor(Color.WHITE);
        StyleBuilder boldRightStyle = stl.style(boldStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT);
    }

    public void addReportListener(ReportListener listener) {
        reportListener = listener;
    }

    public ReportBanner getReportBanner() {
        return reportBanner;
    }

    public void setReportBanner(ReportBanner reportBanner) {
        this.reportBanner = reportBanner;
    }

    public void showReport() {

        buildReport();

        try {
            show(true);
        } catch (DRException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buildReport() {

        setTemplate(Templates.reportTemplate);
        
       
        reportListener.pageFormat(this);
        title(reportBanner.get());
        reportListener.addColumns(this);
        reportListener.loadDataSource(this);
        pageFooter(cmp.pageXofY());
        pageFooter(cmp.text(Configuration.COPYRIGHT_TEXT).setStyle(copyrightStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT));
    }
}
