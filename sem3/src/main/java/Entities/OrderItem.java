package Entities;

import Entities.States.FurnitureType;
import com.sem3.Entities.States.OrderItemState;

public class OrderItem {
    private int id;
    private double orderArrivalTime;
    private FurnitureType itemType;
    private Order order;
    private OrderItemState state;

    public OrderItem(int id, double orderArrivalTime, FurnitureType itemType) {
        this.id = id;
        this.orderArrivalTime = orderArrivalTime;
        this.itemType = itemType;
    }
    public void setState(OrderItemState state) {
        this.state = state;
    }
    public OrderItemState getState() {
        return state;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getOrderArrivalTime() {
        return orderArrivalTime;
    }
    public FurnitureType getItemType() {
        return itemType;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
}
