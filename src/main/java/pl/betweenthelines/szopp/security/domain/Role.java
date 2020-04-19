package pl.betweenthelines.szopp.security.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "app_role")
public class Role {

    @Id
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private RoleName name;

}
