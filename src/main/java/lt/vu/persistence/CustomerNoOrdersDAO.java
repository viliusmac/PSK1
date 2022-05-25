package lt.vu.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

import lt.vu.entities.Customer;

@Alternative
@ApplicationScoped
public class CustomerNoOrdersDAO extends CustomerDAO implements ICustomerDAO {

    @Override
    public List<Customer> loadAll() {
        System.out.println("Alternative was CALLED.");
        List<Customer> customersWithNoOrders = new ArrayList<>();
        for (Customer customer: super.loadAll()) {
            if (customer.getOrders().isEmpty()) {
                customersWithNoOrders.add(customer);
            }
        }
        return customersWithNoOrders;
    }
}
