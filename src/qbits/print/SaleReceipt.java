/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.print;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import qbits.configuration.Utilities;
import qbits.entity.Product;

/**
 *
 * @author Pipilika
 */
public class SaleReceipt implements Printable {

    private int pageWidth = 288;
    private int margin = 15;
    private double total;
    private double vat;
    private double discount;
    private double payable;
    private double paid;
    private double change;
    private ArrayList<Product> selectedProducts;

    public void setSelectedProducts(ArrayList<Product> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setPayable(double payable) {
        this.payable = payable;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public int print(Graphics g, PageFormat pf, int page)
            throws PrinterException {

        if (page > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        g.drawRect(0, 0, pageWidth, 1000);

        Font font = new Font("Serif", Font.BOLD, 30);
        int y = drawCenter("Taradin", font, g, 10);

        font = new Font("Serif", Font.ITALIC, 15);
        y = drawCenter("First Super Shop of Sylhet", font, g, y + 5);

        font = new Font("Serif", Font.ITALIC, 13);
        drawLeft(Utilities.nowDate(), font, g, y + 10);

        font = new Font("Serif", Font.ITALIC, 13);
        y = drawRight(Utilities.nowTime(), font, g, y + 10);

        y = drawLine(g, y + 5);
        font = new Font("Serif", Font.ITALIC, 13);
        y = drawCenter("Invoice No: 123456789", font, g, y + 5);
        y = drawLine(g, y + 5);

        font = new Font("Serif", Font.PLAIN, 10);
//        y = drawCenter("1234 ABCDEFGHKIKLMNO 12345 123456789 1234567890", font, g, y + 5);
//        y = drawLeft("SL#  ITEMS           QTY   PRICE     Total     ", font, g, y + 5);
        y = drawItem("SL#", "ITEMS", "QTY", "PRICE", "TOTAL", g, y + 15);
        y = drawLine(g, y + 5);
//        y = drawItem("1", "Bangladesh ItemASSSSSSSSSSS", "25.00", "1250.00", "125987.00", g, y + 15);
//        y = drawItem("1", "Bangladesh Item", "25.00", "1250.00", "125987", g, y);

        for (int count = 0; count < selectedProducts.size(); count++) {

            if (count == 0) {
                y = drawItem("" + (count + 1), selectedProducts.get(count).getName() + "-" + selectedProducts.get(count).getCategory(), "" + selectedProducts.get(count).getQuantity(), "" + selectedProducts.get(count).getRpu(), "" + (selectedProducts.get(count).getQuantity() * selectedProducts.get(count).getRpu()), g, y + 15);
            } else {
                y = drawItem("" + (count + 1), selectedProducts.get(count).getName() + "-" + selectedProducts.get(count).getCategory(), "" + selectedProducts.get(count).getQuantity(), "" + selectedProducts.get(count).getRpu(), "" + (selectedProducts.get(count).getQuantity() * selectedProducts.get(count).getRpu()), g, y);
            }
        }

        y = drawLine(g, y);
        y = drawItem("", "", "", "Total", "" + total, g, y + 10);
        y = drawItem("", "", "", "Vat", "" + vat, g, y);
        y = drawItem("", "", "", "Discount", "" + discount, g, y);
        y = drawLine(g, y);
        y = drawItem("", "", "", "Payable", "" + payable, g, y + 10);
        y = drawLine(g, y);
        y = drawItem("", "", "", "Paid", "" + paid, g, y + 10);
        y = drawItem("", "", "", (change > 0 ? "Due" : "Return"), "" + change, g, y);
        
        return PAGE_EXISTS;
    }

    private int drawItem(String sl, String description, String quantity, String price, String total, Graphics graphics, int y) {

        int x;
        Font font = new Font("Serif", Font.PLAIN, 10);
        graphics.setFont(font);

        if (sl.length() > 4) {
            sl = sl.substring(0, 3);
        }

        x = margin + (20 - (sl.length() * 5));
        graphics.drawString(sl, x, y);

        if (description.length() > 20) {
            description = description.substring(0, 19);
        }

        x = margin + 25;
        graphics.drawString(description, x, y);

        if (quantity.length() > 5) {
            quantity = quantity.substring(0, 5);
        }

        x = margin + 25 + 101;
        graphics.drawString(quantity, x, y);

        if (price.length() > 9) {
            price = price.substring(0, 8);
        }

        x = margin + 25 + 101 + 30;
        graphics.drawString(price, x, y);

        if (total.length() > 10) {
            total = total.substring(0, 9);
        }

        x = margin + 25 + 101 + 30 + 50;
        graphics.drawString(total, x, y);

        return y + 10;
    }

    private int drawLine(Graphics graphics, int y) {
        graphics.drawLine(margin, y, pageWidth - margin, y);
        return y;
    }

    private int drawCenter(String thisString, Font font, Graphics graphics, int y) {


        graphics.setFont(font);
        FontMetrics fontMetrics = graphics.getFontMetrics(font);
        int lineHeight = fontMetrics.getHeight();
        int lineWidth = fontMetrics.stringWidth(thisString);
        graphics.drawString(thisString, (int) (pageWidth - lineWidth) / 2, (int) (lineHeight) / 2 + y);

        System.out.println(thisString);
        System.out.println("LineHeight: " + lineHeight);
        System.out.println("LineWidth: " + lineWidth);
        System.out.println("Per Char: " + lineWidth / thisString.length());

        return (int) (lineHeight) / 2 + y;
    }

    private int drawLeft(String thisString, Font font, Graphics graphics, int y) {

        graphics.setFont(font);
        FontMetrics fontMetrics = graphics.getFontMetrics(font);
        int lineHeight = fontMetrics.getHeight();
        int lineWidth = fontMetrics.stringWidth(thisString);
        graphics.drawString(thisString, margin, (int) (lineHeight) / 2 + y);

        return (int) (lineHeight) / 2 + y;
    }

    private int drawRight(String thisString, Font font, Graphics graphics, int y) {

        graphics.setFont(font);
        FontMetrics fontMetrics = graphics.getFontMetrics(font);
        int lineHeight = fontMetrics.getHeight();
        int lineWidth = fontMetrics.stringWidth(thisString);
        graphics.drawString(thisString, (int) (pageWidth - lineWidth) - margin, (int) (lineHeight) / 2 + y);

        return (int) (lineHeight) / 2 + y;
    }

    public void printReport() {
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pageFormat = new PageFormat();
        pageFormat.setOrientation(PageFormat.PORTRAIT);
        Paper paper = new Paper();
        paper.setSize(300, 500);
        pageFormat.setPaper(paper);
        job.defaultPage(pageFormat);

        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                /* The job did not successfully complete */
            }
        }
    }

    public static void main(String args[]) {

        SaleReceipt receipt = new SaleReceipt();
        receipt.printReport();
    }
}
