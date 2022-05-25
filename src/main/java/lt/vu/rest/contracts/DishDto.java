package lt.vu.rest.contracts;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.vu.entities.Dish;

@NoArgsConstructor
@Getter @Setter
public class DishDto {

    private Integer id;

    private String name;

    private String description;

    private Double price;

    private List<OrderDto> orders;

    public DishDto(Dish dish) {
        this.id = dish.getId();
        this.name = dish.getName();
        this.description = dish.getDescription();
        this.price = dish.getPrice();
        // this.orders = DtoUtil.convertOrderList(dish.getOrders());
    }
}
