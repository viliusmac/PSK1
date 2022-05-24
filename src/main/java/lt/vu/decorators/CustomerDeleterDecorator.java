package lt.vu.decorators;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import lt.vu.entities.Customer;

@Decorator
public class CustomerDeleterDecorator implements IDeleter {

    @Inject
    @Delegate
    @Any
    IDeleter customerDeleter;

    @Override
    public Customer deleteById(int id) {
        Customer customer = customerDeleter.deleteById(id);

        if (customer != null) {
            System.out.println("Deleted item from DB: \n ID: " + customer.getId()
            + "\n FIRST_NAME: " + customer.getFirstName()
            + "\n LAST_NAME: " + customer.getLastName());
        }

        return customer;
    }
}
