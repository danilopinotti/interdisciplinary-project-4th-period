package aux.dialogs;

import javafx.beans.NamedArg;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Created by danilopinotti on 01/12/15.
 */
public class InformationDialog extends Alert {
    public InformationDialog(@NamedArg("InformationMessage") String contentText) {
        super(AlertType.INFORMATION, contentText, ButtonType.OK);
    }
}
