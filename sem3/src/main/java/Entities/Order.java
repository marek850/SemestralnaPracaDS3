package Entities;

import java.util.ArrayList;
import java.util.List;

import com.sem3.Entities.States.OrderState;

public class Order {
    private final int id;
    private final List<OrderItem> items;
    private OrderState state;
    private double orderArrivalTime;

   
    public double getOrderArrivalTime() {
        return orderArrivalTime;
    }
    public void setOrderArrivalTime(double orderArrivalTime) {
        this.orderArrivalTime = orderArrivalTime;
    }
    public Order(int id) {
        this.id = id;
        this.items = new ArrayList<>();
    }
    public int getId() {
        return id;
    }
    public OrderState getState() {
        return state;
    }
    public void setState(OrderState state) {
        this.state = state;
    }
    public List<OrderItem> getItems() {
        return items;
    }
    public void addItem(OrderItem item) {
        if (item.getOrder() != null) {
            throw new IllegalArgumentException("Item už patrí inej objednávke!");
        }
        items.add(item);
        item.setOrder(this); 
    }

}
