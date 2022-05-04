package lt.vu.usecases;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mybatis.dao.CustomerMapper;
import lt.vu.mybatis.model.Customer;

@Model
public class CustomersMyBatis {
    @Inject
    private CustomerMapper customerMapper;

    @Getter
    private List<Customer> allCustomers;

    @Getter @Setter
    private Customer customerToCreate = new Customer();

    @PostConstruct
    public void init() {
        this.loadAllCustomers();
    }

    private void loadAllCustomers() {
        this.allCustomers = customerMapper.selectAll();
    }

    @Transactional
    public String createCustomer() {
        customerMapper.insert(customerToCreate);
        return "/myBatis/customers?faces-redirect=true";
    }
}
