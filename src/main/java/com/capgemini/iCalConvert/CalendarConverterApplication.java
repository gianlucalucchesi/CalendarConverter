package com.capgemini.iCalConvert;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.io.Console;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

@SpringBootApplication
public class CalendarConverterApplication {

    public static void main(String[] args) throws Exception {

        launch();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter OLD calendar file path: ");
        String oldPath = scanner.nextLine();

        if (!oldPath.contains(".ics")) {
            throw new Exception("Invalid path");
        }

        System.out.print("Enter NEW calendar file path: ");
        String newtPath = scanner.nextLine();

        if (!newtPath.contains(".ics")) {
            throw new Exception("Invalid path");
        }

        Ical4j ical4j = new Ical4j(oldPath.trim(), newtPath.trim());
        ical4j.setCalendars();
        ical4j.compareCalendars();
        ical4j.printCalendarEvents(ical4j.getCalendarToImport());
        ical4j.export();
//        ical4j.deleteFile(oldPath);
//        ical4j.renameFile(newtPath, oldPath);

        System.out.println("=================== DONE ===================");
        System.out.println("========= PLEASE KEEP THE OLD FILE =========");
    }

    public static void launch() {
        Console console = System.console();

        if (console != null) {
            File f = new File("launch.bat");

            if (f.exists()) {
                f.delete();
            }
        } else if (!GraphicsEnvironment.isHeadless()) {
            if (SystemUtils.IS_OS_WINDOWS) {
                try {
                    File JarFile = new File(CalendarConverterApplication.class.getProtectionDomain()
                            .getCodeSource().getLocation().toURI());

                    PrintWriter out = new PrintWriter(new File("launch.bat"));
                    out.println("@echo off");
                    out.println("title Calendar Converter");
                    out.println("java -jar " + JarFile.getPath());
                    out.close();

                    Runtime rt = Runtime.getRuntime();
                    rt.exec("cmd /c start launch.bat");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        }
    }
}
