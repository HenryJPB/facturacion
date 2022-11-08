/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author henrypb
 * MODELO A IMPLANTAR 
 * Ejemplo tomado del link: http://stackoverflow.com/questions/14529789/jtable-setcellrenderer-to-format-a-text-field-to-date
 * el 10/10/2013.  
 * 
 */
class DateRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;
    private Date dateValue;
    private SimpleDateFormat sdfNewValue = new SimpleDateFormat("EE MMM dd hh:mm:ss z yyyy");
    private String valueToString = "";

    @Override
    public void setValue(Object value) {
        if ((value != null)) {
            String stringFormat = value.toString();
            try {
                dateValue = (Date) new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH).parse(stringFormat);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            valueToString = sdfNewValue.format(dateValue);
            value = valueToString;
        }
        super.setValue(value);
    }
}   // DateRenderer.  
