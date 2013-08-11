/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.purchase;

import java.util.Calendar;
import qbits.gui.common.UIParentFrame;
import qbits.gui.common.daterangepicker.DateRangeListener;
import qbits.gui.common.daterangepicker.UIDateRangePicker;

/**
 *
 * @author Pipilika
 */
public class UIReportProductStock extends UIDateRangePicker implements DateRangeListener {

    private UIParentFrame parentFrame;

    public UIReportProductStock(UIParentFrame frame) {

        super();
        this.parentFrame = frame;
        addDateRangeListener(this);
    }

    @Override
    public void processDateRange(Calendar fromDate, Calendar toDate) {
        System.out.println("FROM: " + fromDate.getTime());
        System.out.println("TO: " + toDate.getTime());
    }
}
