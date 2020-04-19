package pl.betweenthelines.szopp.security.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleName {
    ADMIN,
    STAFF,
    REGISTERED_USER
}
