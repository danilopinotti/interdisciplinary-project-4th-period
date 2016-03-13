package aux.dialogs;

import javafx.beans.NamedArg;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Created by danilopinotti on 30/11/15.
 */
public class WarningDialog extends Alert {
    public WarningDialog(@NamedArg("WarningMessage") String contentText) {
        super(AlertType.WARNING, contentText, ButtonType.OK);
    }
}
