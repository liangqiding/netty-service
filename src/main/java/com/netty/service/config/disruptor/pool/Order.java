package com.netty.service.config.disruptor.pool;

/**
 * description : TODO
 *
 * @author : qiDing
 * date: 2020-12-02 16:54
 * @version v1.0.0
 */
public class Order {

    private String id;
    private String name;
    private double price;

    public Order() {}

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

}
