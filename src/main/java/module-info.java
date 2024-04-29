module org.group.project {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.group.project to javafx.fxml;
    exports org.group.project;
    exports org.group.project.classes;
    exports org.group.project.test.generators;
    exports org.group.project.scenes;
    exports org.group.project.classes.auxiliary;
    exports org.group.project.exceptions;
    opens org.group.project.classes to javafx.fxml;
    opens org.group.project.scenes to javafx.fxml;
    opens org.group.project.controllers.customer to javafx.fxml;
    opens org.group.project.controllers.chef to javafx.fxml;
    opens org.group.project.controllers.driver to javafx.fxml;
    opens org.group.project.controllers.manager to javafx.fxml;
    opens org.group.project.controllers.waiter to javafx.fxml;
    opens org.group.project.classes.auxiliary to javafx.fxml;
    opens org.group.project.controllers.main to javafx.fxml;
    opens org.group.project.scenes.chef to javafx.fxml;
    opens org.group.project.scenes.customer to javafx.fxml;
    opens org.group.project.scenes.driver to javafx.fxml;
    opens org.group.project.scenes.manager to javafx.fxml;
    opens org.group.project.scenes.waiter to javafx.fxml;

}