package ch.roester.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Status {

    public Status() {
    }

    public Status(String name) {
        this.name = name;
    }

    public static Status fromEnum(OrderStatus status) {
        return new Status(status.getDisplayName());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Integer id;

    @Column(name = "name", nullable = false, unique = true) // Ensure uniqueness for statuses
    private String name;

}
