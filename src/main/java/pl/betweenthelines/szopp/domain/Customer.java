package pl.betweenthelines.szopp.domain;

import lombok.*;
import pl.betweenthelines.szopp.security.domain.User;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static pl.betweenthelines.szopp.domain.CustomerType.INDIVIDUAL;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_customer")
public class Customer {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Setter
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

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
    @Column(name = "type", nullable = false)
    private CustomerType type = INDIVIDUAL;

    @Setter
    @Column(name = "company_name")
    private String companyName;

    @Setter
    @Column(name = "nip")
    private String nip;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", street='" + street + '\'' +
                ", type=" + type +
                ", companyName='" + companyName + '\'' +
                ", nip='" + nip + '\'' +
                '}';
    }
}
