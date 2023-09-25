package auxiliary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFormattedTextField.AbstractFormatter;

public class DateLabelFormatter extends AbstractFormatter {

    //Formato da data que aparece na caixa de texto, yyyy-MM-dd. Se queremos noutro formato, é necessário alterar esta variável, como por exemplo, a data começar com o dia e terminar com o ano dd-MM-yyyy
    private String datePattern = "yyyy-MM-dd"; 
    
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }
}