/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.common.daterangepicker;

import java.util.Calendar;

/**
 *
 * @author Pipilika
 */
public interface DateRangeListener {

    public void processDateRange(Calendar fromDate, Calendar toDate);
}
