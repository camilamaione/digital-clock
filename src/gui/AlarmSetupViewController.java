package gui;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class AlarmSetupViewController implements Initializable {
	
	@FXML private ComboBox<String> smbHour = new ComboBox<>();
	@FXML private ComboBox<String> smbMinutes = new ComboBox<>();
	@FXML private ComboBox<String> checkBoxAlarmPath = new ComboBox<>();
	@FXML private Button btOK;
	@FXML private Button btCancel;
	private MainViewController mainViewController;
	
	@FXML public void onBtOKAction(ActionEvent event) {
		int hour = Integer.parseInt(smbHour.getSelectionModel().getSelectedItem());
		int minutes = Integer.parseInt(smbMinutes.getSelectionModel().getSelectedItem());
		setAlarmTime(hour, minutes);
		gui.utils.Utils.currentStage(event).close();
	}
	
	@FXML public void onBtCancelAction(ActionEvent event) {
		gui.utils.Utils.currentStage(event).close();
	}
	
	@FXML public void onCheckBoxAlarmPathAction() {
		String parentPath = mainViewController.getAlarmSound().getParent();
		String fileName = checkBoxAlarmPath.getSelectionModel().getSelectedItem();
		mainViewController.setAlarmSound(new File(parentPath + "/" + fileName));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<String> hours = IntStream.rangeClosed(0, 23).boxed().collect(Collectors.toList()).stream().map(x -> String.format("%02d", x)).collect(Collectors.toList()); 
		List<String> minutes = IntStream.rangeClosed(1, 59).boxed().collect(Collectors.toList()).stream().map(x -> String.format("%02d", x)).collect(Collectors.toList()); 
		smbHour.setItems(FXCollections.observableList(hours));
		smbMinutes.setItems(FXCollections.observableList(minutes));
		
		File dir = new File("D:/workspace/digital-clock/bin/sounds");		
		List<String> alarms = Arrays.asList(dir.listFiles()).stream().map(f -> f.getName()).collect(Collectors.toList());		
		checkBoxAlarmPath.setItems(FXCollections.observableList(alarms));
	}
	
	public void setMainViewController(MainViewController controller) {
		mainViewController = controller;
		smbHour.getSelectionModel().select(new String("" + String.format("%02d", mainViewController.getAlarmCalendar().get(Calendar.HOUR_OF_DAY))));
		smbMinutes.getSelectionModel().select(new String("" + String.format("%02d", mainViewController.getAlarmCalendar().get(Calendar.MINUTE))));
		checkBoxAlarmPath.getSelectionModel().select(mainViewController.getAlarmSound().getName());
	}
	
	public void setAlarmTime(int hour, int minute) {
		mainViewController.getAlarmCalendar().set(0, 0, 0, hour, minute, 0);
		mainViewController.onCheckBoxAMorPMAction();
	}

}
