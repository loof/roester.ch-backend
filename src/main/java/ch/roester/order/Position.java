package ch.roester.order;

import ch.roester.event.Event;
import ch.roester.variant.Variant;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "amount", nullable = false)
    @Min(0)
    private Integer amount;

    @OneToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "variant_id", referencedColumnName = "id", nullable = false)
    private Variant variant;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(id, position.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
