package org.kaczucha;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="USERS")
public class Client {
    @Id
    @GeneratedValue
    @Column(name="USER_ID")
    private Long id;
    @Column(name="FIRST_NAME")
    private String name;
    @Column(name="MAIL")
    private String email;
    @Transient
    private double balance;

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(final double balance) {
        this.balance = balance;
    }

    public Client() {
    }

    public Client(String name, String email, double balance) {
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return Double.compare(client.balance, balance) == 0 && Objects.equals(name, client.name) && Objects.equals(email, client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, balance);
    }
}
