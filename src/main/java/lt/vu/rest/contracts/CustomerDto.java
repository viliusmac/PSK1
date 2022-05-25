package lt.vu.rest.contracts;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.vu.DtoUtil;
import lt.vu.entities.Customer;

@Getter @Setter
@NoArgsConstructor
public class CustomerDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private List<OrderDto> orders;

    public CustomerDto(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.orders = DtoUtil.convertOrderList(customer.getOrders());
    }
}
