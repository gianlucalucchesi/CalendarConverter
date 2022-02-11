package com.capgemini.visualcalendarconverter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

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
        // Should user run in again, remove visual validation first
        // FIXME: Runs so fast that you don't see it going off/on
        this.conversionDone.setVisible(false);

        this.oldPath = this.oldPath.replace('"', ' ');
        this.oldPath = this.oldPath.trim();
        this.newPath = this.newPath.replace('"', ' ');
        this.newPath = this.newPath.trim();

        Ical4j ical4j = new Ical4j(this.oldPath, this.newPath);
        ical4j.setCalendars();
        ical4j.compareCalendars();
        ical4j.printCalendarEvents(ical4j.getCalendarToImport());
        ical4j.export();

        this.validOldFile.setVisible(false);
        this.validNewFile.setVisible(false);
        this.conversionDone.setVisible(true);

        // FIXME: Not working on Capgemini PC
        // Path may need to be replaced by URL instead of String
        ical4j.deleteFile(this.oldPath);
        // As previous line is not working renaming gives an error (duplicate file)
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