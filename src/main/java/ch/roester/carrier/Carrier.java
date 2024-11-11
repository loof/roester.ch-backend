package ch.roester.carrier;

import ch.roester.base_entity.BaseEntity;
import ch.roester.event.Event;
import ch.roester.shipping_method.ShippingMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Carrier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Carrier extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "contact_number", length = 15)
    private String contactNumber;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "website", length = 255)
    private String website;

    @OneToMany(mappedBy = "carrier")
    private List<ShippingMethod> shippingMethods;
}
