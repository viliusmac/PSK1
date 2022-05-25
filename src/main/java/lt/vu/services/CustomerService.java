package lt.vu.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;

import lt.vu.DtoUtil;
import lt.vu.entities.Customer;
import lt.vu.persistence.CustomerDAO;
import lt.vu.rest.contracts.CustomerDto;
import lt.vu.rest.contracts.OrderDto;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerDAO customerDAO;

    @Inject
    private EntityManager em;


    public List<Customer> getAll() {
        return customerDAO.loadAll();
    }

    public Customer getById(int id) throws NotFoundException {
        Customer customer = customerDAO.findOne(id);
        if (customer == null) {
            throw new NotFoundException("Customer with Id " + id + " was not found");
        }
        return customer;
    }

    public Customer createCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setOrders(DtoUtil.dtoToOrder(customerDto.getOrders()));

        customerDAO.persist(customer);
        return customer;
    }

    public Customer updateCustomer(CustomerDto customerDto) {
        Customer customer = getById(customerDto.getId());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setOrders(DtoUtil.dtoToOrder(customerDto.getOrders()));

        customerDAO.update(customer);
        return customer;
    }

    public Customer delete(int id) {
        Customer item = getById(id);
        customerDAO.delete(item);
        return item;
    }

    public Customer updateCustomerOptimistic(CustomerDto customerDto) throws InterruptedException {
        try {
            Customer customer = getById(customerDto.getId());
            Thread.sleep(7000);
            // em.refresh(customer);
            customer.setFirstName(customerDto.getFirstName());
            customer.setLastName(customerDto.getLastName());
            customer.setOrders(DtoUtil.dtoToOrder(customerDto.getOrders()));
            customerDAO.update(customer);
            em.flush();
            return customer;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<OrderDto> getOrdersByCustomerId(int id) {
        Customer customer = getById(id);
        return DtoUtil.convertOrderList(customer.getOrders());
    }
}
