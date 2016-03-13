package aux.dialogs;

import javafx.beans.NamedArg;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Created by danilopinotti on 30/11/15.
 */
public class ErrorDialog extends Alert{
    public ErrorDialog(@NamedArg("ErrorMessage") String contentText) {
        super(AlertType.ERROR, contentText, ButtonType.OK);
    }
}