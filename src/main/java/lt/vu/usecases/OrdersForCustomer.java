package lt.vu.usecases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Customer;
import lt.vu.entities.Dish;
import lt.vu.entities.Order;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.CustomerDAO;
import lt.vu.persistence.DishDAO;
import lt.vu.persistence.OrderDAO;

@Model
public class OrdersForCustomer implements Serializable {

    @Inject
    private CustomerDAO customersDAO;

    @Inject
    private OrderDAO ordersDAO;

    @Inject
    private DishDAO dishesDAO;

    @Getter @Setter
    private List<Dish> allDishes;

    @Getter @Setter
    private Customer customer;

    @Getter @Setter
    private Order orderToCreate = new Order();

    @Getter @Setter
    private Dish dishToCreate = new Dish();

    @Getter @Setter
    private String dishIdsStr;

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer customerId = Integer.parseInt(requestParameters.get("customerId"));
        this.customer = customersDAO.findOne(customerId);
        loadAllDishes();
    }

    @Transactional
    @LoggedInvocation
    public void createOrder() {
        orderToCreate.setCustomer(this.customer);
        String[] strIds = dishIdsStr.replaceAll("\\s+","").split(",");
        List<Dish> dishes = new ArrayList<>();
        for (String strId: strIds) {
            dishes.add(dishesDAO.findOne(Integer.parseInt(strId)));
        }
        orderToCreate.setDate(new Date());
        orderToCreate.setDishes(dishes);
        ordersDAO.persist(orderToCreate);
    }

    private void loadAllDishes(){
        this.allDishes = dishesDAO.loadAll();
    }

    @Transactional
    public void createDish() {
        if (dishToCreate.getDescription().isEmpty()) {
            dishToCreate.setDescription("-");
        }
        this.dishesDAO.persist(dishToCreate);
    }
}
