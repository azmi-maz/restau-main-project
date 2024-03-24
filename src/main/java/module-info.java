module org.group.project {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.group.project to javafx.fxml;
    exports org.group.project;
    exports org.group.project.classes;
    exports org.group.project.test.generators;
}