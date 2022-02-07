package com.capgemini.iCalConvert;

//import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class CalendarConverterApplication {

	public static void main(String[] args) throws Exception {

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
		ical4j.deleteFile(oldPath);
		ical4j.renameFile(newtPath, oldPath);

		System.out.println("=================== DONE ===================");
		System.out.println("========= PLEASE KEEP THE OLD FILE =========");
	}

}
