package com.capgemini.visualcalendarconverter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextField oldPathTextField;
    @FXML
    private TextField newPathTextField;

    String oldPath;
    String newPath;

    @FXML
    public void onSubmit(ActionEvent actionEvent) {
        oldPath = this.oldPathTextField.getText();
        newPath = this.newPathTextField.getText();

        runConversion(oldPath, newPath);
    }

    private void runConversion(String oldPath, String newPath) {
        Ical4j ical4j = new Ical4j(oldPath.trim(), newPath.trim());
        ical4j.setCalendars();
        ical4j.compareCalendars();
        ical4j.printCalendarEvents(ical4j.getCalendarToImport());
        ical4j.export();
        ical4j.deleteFile(oldPath);
        ical4j.renameFile(newPath, oldPath);
    }
}