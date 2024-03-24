package org.group.project.test.generators;

import org.group.project.classes.Chef;

public class ChefGenerator {

    public static Chef createChef1() {
        return new Chef("Chef", "Jack", "chef.jack");
    }

    public static Chef createChef2() {
        return new Chef("Chef", "Daniel", "chef.daniel");
    }
}
