package lt.vu.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;

import lt.vu.entities.Customer;

@Specializes
@ApplicationScoped
public class LastOrderDAO extends CustomerDAO {

    private void logLastOrder(Customer customer) {
        System.out.println("Customer's " + customer.getFirstName() + " " + customer.getLastName()
                + "Last order is:  Id:" + customer.getOrders().get(customer.getOrders().size() - 1).getId()
                + ", Date:" + customer.getOrders().get(customer.getOrders().size() - 1).getDate());
    }

    @Override
    public Customer findOne(Integer id) {
        Customer customer = super.findOne(id);
        logLastOrder(customer);
        return customer;
    }
}
