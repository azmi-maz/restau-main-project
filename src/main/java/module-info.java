module org.group.project {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.group.project to javafx.fxml;
    exports org.group.project;
    exports org.group.project.controller;
    opens org.group.project.controller to javafx.fxml;
}