package com.example.raansalatpak.Model;

public class Order {

    private String orderId;
    private int customerId;
    private String status;
    private String created;
    private String updated;

    public Order() {
    }

    public Order(String orderId, int customerId, String status, String created, String updated) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = status;
        this.created = created;
        this.updated = updated;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
