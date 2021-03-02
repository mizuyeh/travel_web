package com.cyx.entity;

import java.util.List;

/**
 * @Description
 * @date 2021/3/1
 */
public class OrderAdd {
    private Product product;
    private Order order;
    private List<Traveler> travelers;
    private Member member;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Traveler> getTravelers() {
        return travelers;
    }

    public void setTravelers(List<Traveler> travelers) {
        this.travelers = travelers;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "OrderAdd{" +
                "product=" + product +
                ", order=" + order +
                ", travelers=" + travelers +
                ", member=" + member +
                '}';
    }
}
