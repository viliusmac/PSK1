package lt.vu.rest.contracts;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.vu.DtoUtil;
import lt.vu.entities.Order;

@NoArgsConstructor
@Getter @Setter
public class OrderDto {

    private Integer id;

    private Date date;

    private List<DishDto> dishes;

    private CustomerDto customer;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.date = order.getDate();
        // this.customer = new CustomerDto(order.getCustomer());
        this.dishes = DtoUtil.convertDishList(order.getDishes());
    }
}
