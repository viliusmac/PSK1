package lt.vu;

import java.util.ArrayList;
import java.util.List;

import lt.vu.entities.Customer;
import lt.vu.entities.Dish;
import lt.vu.entities.Order;
import lt.vu.rest.contracts.CustomerDto;
import lt.vu.rest.contracts.DishDto;
import lt.vu.rest.contracts.OrderDto;

public class DtoUtil {

    public static List<DishDto> convertDishList(List<Dish> dishes) {
        List<DishDto> result = new ArrayList<>();
        for (Dish dish : dishes) {
            result.add(new DishDto(dish));
        }
        return result;
    }

    public static List<OrderDto> convertOrderList(List<Order> orders) {
        List<OrderDto> result = new ArrayList<>();
        for (Order order : orders) {
            result.add(new OrderDto(order));
        }
        return result;
    }

    public static List<CustomerDto> convertCustomerList(List<Customer> customers) {
        List<CustomerDto> result = new ArrayList<>();
        for (Customer customer : customers) {
            result.add(new CustomerDto(customer));
        }
        return result;
    }

    public static Order dtoToOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setDate(orderDto.getDate());
        order.setCustomer(dtoToCustomer(orderDto.getCustomer()));
        order.setDishes(dtoToDish(orderDto.getDishes()));
        return order;
    }

    public static List<Order> dtoToOrder(List<OrderDto> orderDto) {
        List<Order> result = new ArrayList<>();
        for (OrderDto dto : orderDto) {
            result.add(dtoToOrder(dto));
        }
        return result;
    }

    public static Customer dtoToCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setOrders(dtoToOrder(customerDto.getOrders()));
        return customer;
    }

    public static List<Customer> dtoToCustomer(List<CustomerDto> customerDto) {
        List<Customer> result = new ArrayList<>();
        for (CustomerDto dto : customerDto) {
            result.add(dtoToCustomer(dto));
        }
        return result;
    }

    public static Dish dtoToDish(DishDto dishDto) {
        Dish dish = new Dish();
        dish.setId(dishDto.getId());
        dish.setName(dishDto.getName());
        dish.setDescription(dishDto.getDescription());
        dish.setPrice(dishDto.getPrice());
        return dish;
    }

    public static List<Dish> dtoToDish(List<DishDto> dishDto) {
        List<Dish> result = new ArrayList<>();
        for (DishDto dto : dishDto) {
            result.add(dtoToDish(dto));
        }
        return result;
    }
}
