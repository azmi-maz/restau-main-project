import org.group.project.classes.Customer;
import org.group.project.classes.DeliveryOrder;
import org.group.project.classes.Kitchen;
import org.group.project.test.generators.CustomerGenerator;
import org.group.project.test.generators.DeliveryOrderGenerator;
import org.junit.jupiter.api.Test;

public class KitchenTests {

    @Test
    public void testAddNewOrderTicket() {
        Customer customer1 = CustomerGenerator.createCustomer1();
        DeliveryOrder deliveryOrder1 =
                DeliveryOrderGenerator.createDeliverOrder1();
        Kitchen kitchen = new Kitchen();
        Boolean addingNewOrder = kitchen.addNewOrderTicket(deliveryOrder1);
        if (addingNewOrder) {
            System.out.println("Order added successfully!");
        }

        System.out.println(kitchen.getAllOrderTickets());
    }
}
