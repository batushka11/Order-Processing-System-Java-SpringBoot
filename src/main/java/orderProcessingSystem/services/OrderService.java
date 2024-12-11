package orderProcessingSystem.services;

import orderProcessingSystem.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    void addOrder(Order order);

    void updateOrder(Order order);

    void deleteOrder(Long id);

    Order getOrderById(Long id);
}
