package lt.vu.decorators;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Customer;
import lt.vu.services.CustomerService;

@RequestScoped
public class CustomerDeleter implements IDeleter, Serializable {

    @Inject
    @Setter
    @Getter
    private CustomerService customerService;

    @Override
    public Customer deleteById(int id) {
        return customerService.delete(id);
    }
}
