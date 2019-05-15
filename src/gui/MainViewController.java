package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import utils.Utils;

public class MainViewController implements Initializable {
	
	@FXML private Label labelWrittenDate;
	@FXML private Label labelTime, labelAlarmTime;
	@FXML private Label labelTimeAMorPM, labelAlarmTimeAMorPM;
	@FXML private Label labelSEG;	
	@FXML private Label labelTER;
	@FXML private Label labelQUA;
	@FXML private Label labelQUI;
	@FXML private Label labelSEX;
	@FXML private Label labelSAB;
	@FXML private Label labelDOM;
	@FXML private Button btSetAlarm;
	@FXML private CheckBox checkBoxAMorPM;
	@FXML private CheckBox checkBoxAlarmActivated;
	
	private Calendar calendar = Calendar.getInstance();	
	private Calendar alarmCalendar = Calendar.getInstance();
	private int todayDayOfWeek;
	private final Paint defaultDayOfWeekColor = Color.web("#c1b8c9");
	private final Paint todayDayOfWeekColor = Color.web("#4d4257");
	private File alarmSound = new File("D:/workspace/digital-clock/bin/sounds/Alarm01.wav");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//calendar.set(2019, Calendar.DECEMBER, 31, 23, 59, 57);
		alarmCalendar.set(0, 0, 0, 6, 0, 0);
		checkBoxAMorPM.setSelected(false);
		labelTimeAMorPM.setVisible(false);
		labelAlarmTimeAMorPM.setVisible(false);
		
		todayDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);		
		setDayOfWeekColor();		
		labelWrittenDate.setText(Utils.convertDateToWritten(calendar));
		
		labelTime.setText(Utils.getHourFormat().format(calendar.getTime()));
		labelAlarmTime.setText(Utils.getHourFormat().format(alarmCalendar.getTime()));
			
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
			calendar = Calendar.getInstance();
			//calendar.add(Calendar.SECOND, 1);
			
			if (!checkBoxAMorPM.isSelected())
				labelTime.setText(Utils.getHourFormat().format(calendar.getTime()));
			else
				labelTime.setText(Utils.getHourFormatAMPM().format(calendar.getTime()));
			
            if (calendar.get(Calendar.DAY_OF_WEEK) != todayDayOfWeek) {
            	todayDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            	labelWrittenDate.setText(Utils.convertDateToWritten(calendar));
            	setDayOfWeekColor();
            }
            
            if(checkBoxAlarmActivated.isSelected()) {
            	if (calendar.get(Calendar.HOUR) == alarmCalendar.get(Calendar.HOUR)
                		&& calendar.get(Calendar.MINUTE) == alarmCalendar.get(Calendar.MINUTE)
                		&& calendar.get(Calendar.SECOND) == alarmCalendar.get(Calendar.SECOND))
                	ringAlarm();	
            }
	    }));
	    timeline.setCycleCount(Animation.INDEFINITE);
	    timeline.play();
	}
	
	public void setAlarmSound(File alarm) {
		alarmSound = alarm;
	}
	
	public File getAlarmSound() {
		return alarmSound;
	}
	
	private void ringAlarm() {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(alarmSound));
	        clip.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML public void onCheckBoxAlarmActivatedAction() {
		if (checkBoxAlarmActivated.isSelected())
			labelAlarmTime.setTextFill(Color.web("#6c5875"));
		else
			labelAlarmTime.setTextFill(Color.web("#cccccc"));
	}
	
	@FXML public void onBtSetAlarmAction(ActionEvent event) {
		try {			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AlarmSetupView.fxml"));
			VBox mainHBox = loader.load();
			AlarmSetupViewController alarmViewController = loader.getController();
			alarmViewController.setMainViewController(this);
			Stage stage = new Stage();
			stage.setScene(new Scene(mainHBox));
			stage.setTitle("Alarm setup");
			stage.setResizable(false);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.initOwner(gui.utils.Utils.currentStage(event));
			stage.setX(gui.utils.Utils.currentStage(event).getX() + 310);
			stage.setY(gui.utils.Utils.currentStage(event).getY() + 50);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.showAndWait();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void onCheckBoxAMorPMAction() {
		if (!checkBoxAMorPM.isSelected()) {
			labelTime.setText(Utils.getHourFormat().format(calendar.getTime()));
			labelTimeAMorPM.setVisible(false);			
			labelAlarmTime.setText(Utils.getHourFormat().format(alarmCalendar.getTime()));
			labelAlarmTimeAMorPM.setVisible(false);			
		} else {
			labelTime.setText(Utils.getHourFormatAMPM().format(calendar.getTime()));
			labelTimeAMorPM.setText(Utils.getAMPM().format(calendar.getTime()));
			labelTimeAMorPM.setVisible(true);			
			labelAlarmTime.setText(Utils.getHourFormatAMPM().format(alarmCalendar.getTime()));
			labelAlarmTimeAMorPM.setText(Utils.getAMPM().format(alarmCalendar.getTime()));
			labelAlarmTimeAMorPM.setVisible(true);
		}
	}

	public Calendar getAlarmCalendar() {
		return alarmCalendar;
	}
		
	private void setDayOfWeekColor() {		
		int today = calendar.get(Calendar.DAY_OF_WEEK);    	
    	if (today == Calendar.MONDAY) {  
    		labelDOM.setTextFill(defaultDayOfWeekColor);
    		labelSEG.setTextFill(todayDayOfWeekColor);
    	} else if (today == Calendar.TUESDAY) {
    		labelSEG.setTextFill(defaultDayOfWeekColor);
    		labelTER.setTextFill(todayDayOfWeekColor);
    	} else if (today == Calendar.WEDNESDAY) {
    		labelTER.setTextFill(defaultDayOfWeekColor);
    		labelQUA.setTextFill(todayDayOfWeekColor);
    	} else if (today == Calendar.THURSDAY) {
    		labelQUA.setTextFill(defaultDayOfWeekColor);
    		labelQUI.setTextFill(todayDayOfWeekColor);
    	} else if (today == Calendar.FRIDAY) {
    		labelQUI.setTextFill(defaultDayOfWeekColor);
    		labelSEX.setTextFill(todayDayOfWeekColor);
    	} else if (today == Calendar.SATURDAY) {
    		labelSEX.setTextFill(defaultDayOfWeekColor);
    		labelSAB.setTextFill(todayDayOfWeekColor);
    	} else if (today == Calendar.SUNDAY) {
    		labelSAB.setTextFill(defaultDayOfWeekColor);
    		labelDOM.setTextFill(todayDayOfWeekColor);
    	}
	}

}
