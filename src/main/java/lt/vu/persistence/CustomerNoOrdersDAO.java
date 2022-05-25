package lt.vu.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Alternative;

import lt.vu.entities.Customer;

@Alternative
public class CustomerNoOrdersDAO extends CustomerDAO {

    @Override
    public List<Customer> loadAll() {
        List<Customer> customersWithNoOrders = new ArrayList<>();
        for (Customer customer: super.loadAll()) {
            if (customer.getOrders().isEmpty()) {
                customersWithNoOrders.add(customer);
            }
        }
        return customersWithNoOrders;
    }
}
