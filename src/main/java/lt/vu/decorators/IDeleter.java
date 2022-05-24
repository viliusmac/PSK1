package lt.vu.decorators;

import lt.vu.entities.Customer;

public interface IDeleter {
    Customer deleteById(int id);
}
