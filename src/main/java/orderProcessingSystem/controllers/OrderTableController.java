package orderProcessingSystem.controllers;

import orderProcessingSystem.entity.Good;
import orderProcessingSystem.entity.Order;
import orderProcessingSystem.enums.Manager;
import orderProcessingSystem.services.GoodService;
import orderProcessingSystem.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Controller
public class OrderTableController {
    private final OrderService orderService;
    private final GoodService goodService;

    public OrderTableController(OrderService orderService, GoodService goodService) {
        super();
        this.orderService = orderService;
        this.goodService = goodService;
    }

    @GetMapping("/orders")
    public String showOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "OrderTable";
    }

    @GetMapping("/orders/new")
    public String newOrderForm(Model model) {
        List<Good> goods = goodService.getAllGoods();
        goods.sort(Comparator.comparing(Good::getNazva));
        model.addAttribute("goods", goods);
        model.addAttribute("order", new Order());
        return "CreateOrder";
    }

    @PostMapping("/orders")
    public String saveOrder(@ModelAttribute("order") Order order) {
        order.setCreateTime(LocalDateTime.now());

        Manager[] managers = Manager.values();
        Manager randomManager = managers[new Random().nextInt(managers.length)];
        order.setManager(randomManager);

        order.setSendTime(null);
        order.setTakeTime(null);

        double price = order.getCount() >= 10 ? (order.getGood().getPriceOpt()) : order.getGood().getPrice();
        order.setPriceOrder(price * order.getCount());

        orderService.addOrder(order);
        return "redirect:/orders";
    }

    @PatchMapping("/orders/edit/{id}")
    public String editOrderForm(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        return "UpdateOrder";
    }

    @PatchMapping("/orders/{id}")
    public String updateOrder(@PathVariable Long id,
                             @ModelAttribute("order") Order order,
                             Model model) {
        Order existingOrder = orderService.getOrderById(id);

        if(order.getSendTime() != null && order.getSendTime().isBefore(existingOrder.getCreateTime())) {
            model.addAttribute("order", existingOrder);
            model.addAttribute("errorMessage", "Send date cannot be before order date");
            return "UpdateOrder";
        }

        if(order.getTakeTime() != null && order.getTakeTime().isBefore(existingOrder.getSendTime())) {
            model.addAttribute("order", existingOrder);
            model.addAttribute("errorMessage", "Take date cannot be before send date");
            return "UpdateOrder";
        }

        existingOrder.setId(id);
        if(order.getReceiver() != null) {
            existingOrder.setReceiver(order.getReceiver());
        }

        if(order.getCount() != null) {
            existingOrder.setCount(order.getCount());
            double price = order.getCount() >= 10 ? (existingOrder.getGood().getPriceOpt()) : existingOrder.getGood().getPrice();
            existingOrder.setPriceOrder(price * order.getCount());
        }

        if(order.getSendTime() != null)
            existingOrder.setSendTime(order.getSendTime());
        existingOrder.setTakeTime(order.getTakeTime());

        orderService.updateOrder(existingOrder);
        return "redirect:/orders";
    }

    @DeleteMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable Long id, Model model) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }

    @PostMapping("/goods/{id}/orders")
    public String saveOrderFrom(@PathVariable Long id, @ModelAttribute("order") Order order) {
        order.setCreateTime(LocalDateTime.now());
        Good good = goodService.getGoodById(id);

        order.setGood(good);
        Manager[] managers = Manager.values();
        Manager randomManager = managers[new Random().nextInt(managers.length)];
        order.setManager(randomManager);

        order.setSendTime(null);
        order.setTakeTime(null);

        double price = order.getCount() >= 10 ? (order.getGood().getPriceOpt()) : order.getGood().getPrice();
        order.setPriceOrder(price * order.getCount());

        orderService.addOrder(order);
        return "redirect:/orders";
    }
}
