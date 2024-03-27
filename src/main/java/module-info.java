module org.group.project {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.group.project to javafx.fxml;
    exports org.group.project;
    exports org.group.project.classes;
    exports org.group.project.test.generators;
    exports org.group.project.stackscenes.view;
    opens org.group.project.controller to javafx.fxml;
    opens org.group.project.scenes to javafx.fxml;
    opens org.group.project.stackscenes.view to javafx.fxml;
}