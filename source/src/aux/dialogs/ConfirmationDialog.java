package aux.dialogs;

import javafx.beans.NamedArg;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Created by danilopinotti on 30/11/15.
 */
public class ConfirmationDialog extends Alert {
    public ConfirmationDialog(@NamedArg("ConfirmationMessage") String contentText) {
        super(AlertType.CONFIRMATION, contentText, ButtonType.YES, ButtonType.NO);
    }
}