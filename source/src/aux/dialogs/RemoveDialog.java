package aux.dialogs;

import javafx.beans.NamedArg;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Created by danilopinotti on 30/11/15.
 */
public class RemoveDialog extends Alert {
    public RemoveDialog(@NamedArg("NameToExclude") String contentText) {
        super(AlertType.WARNING, "Tem certeza que deseja remover este " + contentText, ButtonType.YES, ButtonType.NO);
    }
}
