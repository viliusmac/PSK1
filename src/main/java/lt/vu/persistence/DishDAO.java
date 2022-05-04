package lt.vu.persistence;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import lt.vu.entities.Dish;

@ApplicationScoped
public class DishDAO {

    @Inject
    private EntityManager em;

    public void persist(Dish dish){
        this.em.persist(dish);
    }

    public Dish findOne(Integer id){
        return em.find(Dish.class, id);
    }

    public Dish update(Dish dish){
        return em.merge(dish);
    }

    public List<Dish> loadAll() {
        return em.createNamedQuery("Dish.findAll", Dish.class).getResultList();
    }
}
