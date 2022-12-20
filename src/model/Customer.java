package model;

public class Customer {
    private int customerID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String country;
    private String division;

    public Customer(int customerID, String name, String address, String postalCode, String phone, String country, String division) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.country = country;
        this.division = division;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
