package lt.vu.persistence;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;

import lt.vu.entities.Customer;
import lt.vu.entities.Order;

@Specializes
@ApplicationScoped
public class LastOrderDAO extends CustomerDAO {

    private void logLastOrder(Customer customer) {
        List<Order> orders = customer.getOrders();
        if (!orders.isEmpty()) {
            Order order = orders.get(customer.getOrders().size() - 1);

            System.out.println("Customer's " + customer.getFirstName() + " " + customer.getLastName()
                    + "Last order is:  Id:" + order.getId()
                    + ", Date:" + order.getDate());
        } else {
            System.out.println("Customer has no orders.");
        }
    }

    @Override
    public Customer findOne(Integer id) {
        Customer customer = super.findOne(id);
        logLastOrder(customer);
        return customer;
    }
}
