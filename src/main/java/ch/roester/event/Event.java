package ch.roester.event;

import ch.roester.app_user.AppUser;
import ch.roester.event_product_amount.EventProductAmount;
import ch.roester.location.Location;
import ch.roester.product.Product;
import ch.roester.variant.Variant;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "days_before_subscription_closes", nullable = false)
    private Integer daysBeforeSubscriptionCloses;

    @OneToMany(mappedBy = "event")
    List<EventProductAmount> eventProductAmounts;

    @ManyToMany
    @JoinTable(
            name = "event_app_user",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "app_user_id"))
    Set<AppUser> appUsers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
