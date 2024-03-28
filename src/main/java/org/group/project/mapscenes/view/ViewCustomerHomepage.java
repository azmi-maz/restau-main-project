package org.group.project.mapscenes.view;

import javafx.stage.Stage;
import org.group.project.mapscenes.controller.ViewOneController;

public class ViewCustomerHomepage extends ViewBase {

    public ViewCustomerHomepage(Stage stage) {
        super(stage, "This is customer homepage",
                e -> new ViewOneController(stage).handleMousePress(e));
    }

}
