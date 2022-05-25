package lt.vu.persistence;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import lt.vu.entities.Customer;

@ApplicationScoped
public class CustomerDAO implements ICustomerDAO {

    @Inject
    private EntityManager em;

    public List<Customer> loadAll() {
        return em.createNamedQuery("Customer.findAll", Customer.class).getResultList();
    }

    public void persist(Customer customer){
        this.em.persist(customer);
    }

    public Customer findOne(Integer id){
        return em.find(Customer.class, id);
    }

    public Customer update(Customer customer){
        return em.merge(customer);
    }

    public void delete(Customer customer){
        em.remove(customer);
    }
}
