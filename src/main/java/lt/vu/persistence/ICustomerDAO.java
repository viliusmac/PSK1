package lt.vu.persistence;

import java.util.List;

import lt.vu.entities.Customer;

public interface ICustomerDAO {
    void persist(Customer customer);
    Customer findOne(Integer id);
    Customer update(Customer customer);
    List<Customer> loadAll();
}
