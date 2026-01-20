module es.cifpcarlos3.jasperreportss {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;
    requires java.desktop;

    opens es.cifpcarlos3.jasperreportss to javafx.fxml;
    exports es.cifpcarlos3.jasperreportss;
}