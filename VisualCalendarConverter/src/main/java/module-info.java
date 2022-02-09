module com.capgemini.visualcalendarconverter {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mnode.ical4j.core;
    requires org.apache.commons.lang3;


    opens com.capgemini.visualcalendarconverter to javafx.fxml;
    exports com.capgemini.visualcalendarconverter;
}