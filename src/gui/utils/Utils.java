package gui.utils;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

	public static Stage currentStage(Event event) {
		return (Stage)((Node)event.getSource()).getScene().getWindow();
	}
}
