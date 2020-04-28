package pl.betweenthelines.szopp.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_shipment_address")
public class ShipmentAddress {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Setter
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Setter
    @Column(name = "first_name")
    private String firstName;

    @Setter
    @Column(name = "last_name")
    private String lastName;

    @Setter
    @Column(name = "email")
    private String email;

    @Setter
    @Column(name = "phone")
    private String phone;

    @Setter
    @Column(name = "city")
    private String city;

    @Setter
    @Column(name = "zip_code")
    private String zipCode;

    @Setter
    @Column(name = "street")
    private String street;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CustomerType type;

    @Setter
    @Column(name = "company_name")
    private String companyName;

    @Setter
    @Column(name = "nip")
    private String nip;

}
