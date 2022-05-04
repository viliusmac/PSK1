package lt.vu.usecases;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Dish;
import lt.vu.entities.Order;
import lt.vu.persistence.DishDAO;
import lt.vu.persistence.OrderDAO;

@Model
public class DishesForOrder implements Serializable {

    @Inject
    private OrderDAO ordersDAO;

    @Inject
    private DishDAO dishesDAO;

    @Getter @Setter
    private List<Dish> orderDishes;

    @Getter @Setter
    private Order order;

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer orderId = Integer.parseInt(requestParameters.get("orderId"));
        this.order = ordersDAO.findOne(orderId);
    }
}
