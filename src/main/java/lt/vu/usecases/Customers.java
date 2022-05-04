package lt.vu.usecases;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Customer;
import lt.vu.persistence.CustomerDAO;

@Model
public class Customers {

    @Inject
    private CustomerDAO customerDAO;

    @Getter @Setter
    private Customer customerToCreate = new Customer();

    @Getter
    private List<Customer> allCustomers;

    @PostConstruct
    public void init(){
        loadAllCustomers();
    }

    @Transactional
    public void createCustomer(){
        this.customerDAO.persist(customerToCreate);
    }

    private void loadAllCustomers(){
        this.allCustomers = customerDAO.loadAll();
    }
}
