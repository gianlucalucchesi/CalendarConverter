package com.capgemini.iCalConvert;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import org.apache.commons.lang3.SystemUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class iCal4j {
    final String path, uid;
    private Calendar oldCalendar;
    private Calendar newCalendar;

    iCal4j(String path, String uid) {
        this.path = path;
        this.uid = uid;
    }

    private String getFileName() {
        Path path = Paths.get(this.path);
        String fileName = path.getFileName().toString();

        return fileName.replaceFirst("[.][^.]+$", ""); // File name without extension
    }

    private String getParentPath() {
        Path path = Paths.get(this.path);
        return path.getParent().toString();
    }

    public void setOldCalendar() {
        try {
            FileInputStream fis = new FileInputStream(this.path);
            CalendarBuilder builder = new CalendarBuilder();
            this.oldCalendar = builder.build(fis); // This writes DEBUG info in console
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createNewCalendar() {
        this.newCalendar = new Calendar();
        this.newCalendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        this.newCalendar.getProperties().add(Version.VERSION_2_0);
        this.newCalendar.getProperties().add(CalScale.GREGORIAN);
    }

    public void parseCalendar() {
        boolean toAdd = false;

        createNewCalendar();

        for (CalendarComponent event : this.oldCalendar.getComponents()) {
            final PropertyList<Property> properties = event.getProperties();
            final Property property = properties.getProperty(Property.UID);

            if (toAdd) {
                this.newCalendar.getComponents().add(event);
            }

            if (property != null && !toAdd) {
                if (property.getValue().contains(this.uid)) {
                    toAdd = true;
                }
            }
        }
    }

    public void export() {
        String basePath = getParentPath();
        String separator;

        if(SystemUtils.IS_OS_WINDOWS) {
            separator = "\\";
        } else {
            separator = "/";
        }

        try {
            FileOutputStream fos = new FileOutputStream(basePath + separator + "newCalendar.ics");
            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(this.newCalendar, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printCalendarEvents(Calendar calendar) {
        for (CalendarComponent event : calendar.getComponents()) {
            System.out.println(event);
        }
    }

}
