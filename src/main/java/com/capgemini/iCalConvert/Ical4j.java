package com.capgemini.iCalConvert;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Ical4j {
    final String oldPath, newPath;

    private Calendar oldCalendar;
    private Calendar newCalendar;
    private Calendar calendarToImport;

    Ical4j(String oldPath, String newPath) {
        this.oldPath = oldPath;
        this.newPath = newPath;
    }

    private String getParentPath(String path) {
        Path p = Paths.get(path);
        return p.getParent().toString();
    }

    public void setCalendars() {
        try {
            FileInputStream oldFis = new FileInputStream(this.oldPath);
            FileInputStream newFis = new FileInputStream(this.newPath);
            CalendarBuilder builder = new CalendarBuilder();
            this.oldCalendar = builder.build(oldFis); // This writes DEBUG info in console
            this.newCalendar = builder.build(newFis); // This writes DEBUG info in console
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createNewCalendar() {
        this.calendarToImport = new Calendar();
        this.calendarToImport.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        this.calendarToImport.getProperties().add(Version.VERSION_2_0);
        this.calendarToImport.getProperties().add(CalScale.GREGORIAN);
    }

    public void compareCalendars() {
        boolean toAdd = true;
        createNewCalendar();

        for (CalendarComponent newEvent : this.newCalendar.getComponents()) {
            for (CalendarComponent oldEvent : this.oldCalendar.getComponents()) {
                final Property newProperty = newEvent.getProperties().getProperty(Property.UID);
                final Property oldProperty = oldEvent.getProperties().getProperty(Property.UID);
                String newUID = null;
                String oldUID = null;

                if (newProperty != null)
                    newUID = newProperty.getValue();

                if (oldProperty != null)
                    oldUID = oldProperty.getValue();

                if (oldUID == null && newUID == null){
                    toAdd = false;
                    break;
                }

                if (oldUID == null || newUID == null)
                    toAdd = false;

                if ((oldUID != null && newUID != null) && oldUID.equals(newUID)){
                    toAdd = false;
                    break;
                }

                toAdd = true;
            }

            if (toAdd) {
                this.calendarToImport.getComponents().add(newEvent);
            }

            toAdd = true;
        }
    }

    public void export() {
        String basePath = getParentPath(this.newPath);
        String separator;

        if (SystemUtils.IS_OS_WINDOWS) {
            separator = "\\";
        } else {
            separator = "/";
        }

        try {
            FileOutputStream fos = new FileOutputStream(basePath + separator + "importThenDelete.ics");
            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(this.calendarToImport, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Calendar getCalendarToImport() {
        return this.calendarToImport;
    }

    public void printCalendarEvents(Calendar calendar) {
        for (CalendarComponent event : calendar.getComponents()) {
            System.out.println(event);
        }
    }

    public void deleteFile(String path) {
        File file = new File(path);
        if (file.delete()) {
            System.out.println("Deleted the file: " + file.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }

    public void renameFile(String newFilePath, String oldFilePath) throws IOException {
        File oldFile = new File(newFilePath);
        File newFile = new File(oldFilePath);

        if (newFile.exists())
            throw new java.io.IOException("New file already exists");

        boolean success = oldFile.renameTo(newFile);

        if (!success)
            throw new IOException("File could not be renamed");
    }

}
