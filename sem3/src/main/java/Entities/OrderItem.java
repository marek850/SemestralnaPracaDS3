package Entities;

import Entities.States.FurnitureType;
import Entities.States.OrderItemState;
import OSPAnimator.AnimImageItem;
import UserInterface.AnimatorConfig;

public class OrderItem extends AnimImageItem{
    private int id;
    private double orderArrivalTime;
    private FurnitureType itemType;
    private Order order;
    private OrderItemState state;
    private AssemblyStation assemblyStation;
    private boolean stain;
    public boolean isStain() {
        return stain;
    }
    public void setStain(boolean stain) {
        this.stain = stain;
    }
    public AssemblyStation getAssemblyStation() {
        return assemblyStation;
    }
    public void setAssemblyStation(AssemblyStation assemblyStation) {
        this.assemblyStation = assemblyStation;
    }
    public OrderItem(int id, double orderArrivalTime, FurnitureType itemType) {
        super(AnimatorConfig.CHAIR, 36, 36);
        this.id = id;
        this.orderArrivalTime = orderArrivalTime;
        this.itemType = itemType;
        setZIndex(1);
        switch (itemType) {
            case CHAIR:
                setImage(AnimatorConfig.CHAIR);
                break;
            case TABLE:
                setImage(AnimatorConfig.TABLE);
                break;
            case WARDROBE:
                setImage(AnimatorConfig.WARDROBE);
                break;
            default:
        }
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
