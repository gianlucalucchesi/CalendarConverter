package com.capgemini.iCalConvert;

//import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ICalConvertApplication {

	public static void main(String[] args) throws Exception {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter input calendar file path: ");
		String path = scanner.nextLine();

		if (!path.contains(".ics")) {
			throw new Exception("Invalid path");
		}

		System.out.println("Enter UID of last imported event: ");
		String uid = scanner.nextLine();

		if (uid == "" || uid == null) {
			throw new Exception("UID cannot be null");
		}

		iCal4j ical4j = new iCal4j(path, uid);
		ical4j.setOldCalendar();
		ical4j.parseCalendar();
		ical4j.export();

		System.out.println("======== DONE ========");
	}

}
