/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.report.common;

import java.util.Calendar;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.ImageBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import qbits.configuration.Configuration;
import qbits.configuration.Utilities;

/**
 *
 * @author Pipilika
 */
public class ReportBanner extends HorizontalListBuilder {

    private StyleBuilder titleStyle;
    private StyleBuilder subTitleStyle;
    private ImageBuilder logo;
    private String strTitle;
    private String strSubTitle;

    public ReportBanner() {

        super();

        logo = cmp.image(getClass().getResource("/qbits/resources/image/qbits.png")).setHorizontalAlignment(HorizontalAlignment.LEFT).setFixedDimension(100, 50);

        titleStyle = stl.style();
        titleStyle.setFontName("Times New Roman");
        titleStyle.setFontSize(16);
        titleStyle.bold();
        titleStyle.setVerticalAlignment(VerticalAlignment.MIDDLE);
        titleStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);

        subTitleStyle = stl.style();
        subTitleStyle.setFontName("Times New Roman");
        subTitleStyle.setFontSize(12);
        subTitleStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
    }

    public void setTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    public void setSubTitle(String strSubTitle) {
        this.strSubTitle = strSubTitle;
    }

    public HorizontalListBuilder get() {

        TextFieldBuilder<String> title = cmp.text(Configuration.APP_TITLE);
        title.setStyle(titleStyle);

        TextFieldBuilder<String> subTitle = cmp.text(strSubTitle);
        subTitle.setStyle(subTitleStyle);
        
        TextFieldBuilder<String> reportDateTitle = cmp.text("Reporting Date: "+ Utilities.getFormattedDate(Calendar.getInstance().getTime()));
        reportDateTitle.setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.RIGHT));
        
        HorizontalListBuilder horizontalListBuilder = cmp.horizontalList();
        horizontalListBuilder.add(subTitle, reportDateTitle);

//        add(logo);
        add(title);
        newRow();
        add(cmp.verticalList().add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point()))).add(3, cmp.filler().setStyle(stl.style().setTopBorder(stl.pen1Point()))));
        newRow();
        add(horizontalListBuilder);

        return this;
    }
}
