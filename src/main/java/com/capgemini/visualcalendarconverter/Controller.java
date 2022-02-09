package com.capgemini.visualcalendarconverter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;

public class Controller {
    @FXML
    private ImageView validOldFile;
    @FXML
    private ImageView validNewFile;
    @FXML
    private ImageView conversionDone;

    String oldPath;
    String newPath;

    @FXML
    public void onConvert(ActionEvent actionEvent) {
        this.oldPath = this.oldPath.replace('"', ' ');
        this.oldPath = this.oldPath.trim();
        this.newPath = this.newPath.replace('"', ' ');
        this.newPath = this.newPath.trim();

        Ical4j ical4j = new Ical4j(this.oldPath, this.newPath);
        ical4j.setCalendars();
        ical4j.compareCalendars();
        ical4j.printCalendarEvents(ical4j.getCalendarToImport());
        ical4j.export();
        this.conversionDone.setVisible(true);
        ical4j.deleteFile(this.oldPath);
        ical4j.renameFile(this.newPath, this.oldPath);
    }

    @FXML
    private void onClickBtnOld(ActionEvent actionEvent) {
        String username = System.getProperty("user.name");

        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("C:\\Users\\" + username +"\\Downloads"));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("iCal Files", "*.ics"));
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            this.oldPath = selectedFile.getAbsolutePath();
            this.validOldFile.setVisible(true);
        }
    }

    @FXML
    private void onClickBtnNew(ActionEvent actionEvent) {
        String username = System.getProperty("user.name");

        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("C:\\Users\\" + username +"\\Downloads"));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("iCal Files", "*.ics"));
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            this.newPath = selectedFile.getAbsolutePath();
            this.validNewFile.setVisible(true);
        }
    }

}