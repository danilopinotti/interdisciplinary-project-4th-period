package aux;

import aux.exceptions.FormException;
import com.mysql.jdbc.StringUtils;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import jdk.nashorn.internal.codegen.types.NumericType;
import model.Product;

import java.util.List;

/**
 * Created by danilopinotti on 30/11/15.
 */
public class Validations {
    public static void isEmail(String string) throws RuntimeException{
        if(!string.contains("@"))
            throw new FormException("Email inválido.");
    }

    public static void isEmpty(String string) throws FormException {
        if(string.length() == 0)
            throw new FormException("Todos os campos devem ser preenchidos.");
    }

    public static void isEmpty(List<?> list, String name) throws FormException {
        if(list.size() == 0)
            throw new FormException("Nenhum item a ser listado: "+name+".");
    }

    public static void isSelected(TableView<?> tview) throws FormException {
        if(tview.getSelectionModel().getSelectedIndex() == -1)
            throw new FormException("Nenhum elemento selecionado na tabela.");
    }

    public static void isSelected(ComboBox<?> combo, String val) throws FormException {
        if(combo.getValue() == null)
            throw new FormException("Nenhum elemento selecionado: "+val+".");
    }

    public static void isNumeric(String string) throws FormException{
        try {
            double d = Double.parseDouble(string);
        }catch(NumberFormatException e){
            throw new FormException("Deve ser inserido um número.");
        }
    }
}
