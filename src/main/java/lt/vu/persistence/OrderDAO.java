package lt.vu.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import lt.vu.entities.Order;

@ApplicationScoped
public class OrderDAO {

    @Inject
    private EntityManager em;

    public void persist(Order order){
        this.em.persist(order);
    }

    public Order findOne(Integer id){
        return em.find(Order.class, id);
    }

    public Order update(Order order){
        return em.merge(order);
    }
}
