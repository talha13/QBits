/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.account;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import net.sf.dynamicreports.examples.DataSource;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import qbits.configuration.Utilities;
import qbits.entity.Transaction;
import qbits.gui.common.UIParentFrame;
import qbits.gui.common.daterangepicker.DateRangeListener;
import qbits.gui.common.daterangepicker.UIDateRangePicker;
import qbits.report.account.ReportReceivePayments;
import qbits.report.common.Report;
import qbits.report.common.ReportListener;

/**
 *
 * @author Pipilika
 */
public class UIReceivePayment extends UIDateRangePicker implements DateRangeListener, ReportListener {

    private UIParentFrame parentFrame;
    private ArrayList<Transaction> transactions;
    private Report report;

    public UIReceivePayment(UIParentFrame frame) {
        super();

        setTitle("Receive/Payment");
        parentFrame = frame;

        transactions = new ArrayList<>();
        addDateRangeListener(this);
    }

    @Override
    public void processDateRange(Calendar fromDate, Calendar toDate) {

        transactions.clear();
        ReportReceivePayments receivePayments = new ReportReceivePayments();
        transactions.addAll(receivePayments.getAccountTransaction(fromDate.getTime(), toDate.getTime()));
        report = new Report();
        report.addReportListener(this);
        report.getReportBanner().setSubTitle("Receive Payment From Date: " + Utilities.getFormattedDate(fromDate.getTime())
                + " To Date: " + Utilities.getFormattedDate(toDate.getTime()));
        report.showReport();
    }

    @Override
    public void loadDataSource(JasperReportBuilder reportBuilder) {

        Comparator<Transaction> comparator = new Comparator<Transaction>() {
            @Override
            public int compare(Transaction txn1, Transaction txn2) {
                return txn1.getDate().compareTo(txn2.getDate());
            }
        };

        Collections.sort(transactions, comparator);

        DataSource dataSource = new DataSource("sl", "date", "particular", "account_info", "debit", "credit", "balance");
        int count = 1;
        double balance = 0.00;

        for (Transaction transaction : transactions) {

            balance += transaction.isDebit() ? transaction.getAmount() : transaction.getAmount() * -1;

            dataSource.add(count++, transaction.getDate(), transaction.getParticular(), transaction.getPaymentMode() + "-" + transaction.getAccountTitle(),
                    transaction.isDebit() ? transaction.getAmount() : 0.00, transaction.isDebit() ? 0.00 : transaction.getAmount(),
                    balance);
        }

        reportBuilder.setDataSource(dataSource);
    }

    @Override
    public void addColumns(JasperReportBuilder reportBuilder) {

        StyleBuilder coloumnData = stl.style().setFontSize(8).setVerticalAlignment(VerticalAlignment.MIDDLE).setHorizontalAlignment(HorizontalAlignment.CENTER);
        TextColumnBuilder<Integer> serial = col.column("Sl #", "sl", type.integerType());
        TextColumnBuilder<Date> date = col.column("Date", "date", type.dateType());
        TextColumnBuilder<String> particular = col.column("Particular", "particular", type.stringType());
        TextColumnBuilder<String> accountInfo = col.column("Account Info", "account_info", type.stringType());
        TextColumnBuilder<Double> debit = col.column("Debit", "debit", type.doubleType());
        TextColumnBuilder<Double> credit = col.column("Credit", "credit", type.doubleType());
        TextColumnBuilder<Double> balance = col.column("Balance", "balance", type.doubleType());

        reportBuilder.addColumn(serial, date, particular, accountInfo, debit, credit, balance);
    }

    @Override
    public void pageFormat(JasperReportBuilder reportBuilder) {
        reportBuilder.setPageFormat(PageType.A4, PageOrientation.PORTRAIT);
    }
}
