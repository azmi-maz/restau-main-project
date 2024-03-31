module org.group.project {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.group.project to javafx.fxml;
    exports org.group.project;
    exports org.group.project.classes;
    exports org.group.project.test.generators;
    exports org.group.project.stackscenes.view;
    exports org.group.project.multiwindows;
    opens org.group.project.classes to javafx.fxml;
    opens org.group.project.controllers to javafx.fxml;
    opens org.group.project.scenes to javafx.fxml;
    opens org.group.project.stackscenes.view to javafx.fxml;
    opens org.group.project.multiwindows to javafx.fxml;
    opens org.group.project.mapscenes.controller to javafx.fxml;
    opens org.group.project.controllers.customer to javafx.fxml;
}