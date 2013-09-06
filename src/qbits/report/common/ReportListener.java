/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.report.common;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.jasperreports.engine.JRDataSource;

/**
 *
 * @author Pipilika
 */
public interface ReportListener {
    
    public void loadDataSource(JasperReportBuilder reportBuilder);
    public void addColumns(JasperReportBuilder reportBuilder);
}
