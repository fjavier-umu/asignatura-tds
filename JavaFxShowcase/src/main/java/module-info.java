module es.um.tds.JavaFsShowcase {
    requires javafx.controls;
    requires javafx.fxml;

    opens es.um.tds.JavaFsShowcase to javafx.fxml;
    exports es.um.tds.JavaFsShowcase;
}
