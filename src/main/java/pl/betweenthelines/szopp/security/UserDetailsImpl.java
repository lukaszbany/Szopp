package pl.betweenthelines.szopp.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.betweenthelines.szopp.security.domain.Role;
import pl.betweenthelines.szopp.security.domain.RoleName;
import pl.betweenthelines.szopp.security.domain.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;
    public static final String ROLE_ = "ROLE_";

    private String username;
    private String password;
    private boolean isActive;
    private List<GrantedAuthority> authorities;

    public UserDetailsImpl(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.isActive = true;
        this.authorities = mapRolesToAuthorities(user);
    }

    private List<GrantedAuthority> mapRolesToAuthorities(User user) {
        return user.getRoles()
                .stream()
                .map(this::toSimpleGrantedAuthority)
                .collect(Collectors.toList());
    }

    private SimpleGrantedAuthority toSimpleGrantedAuthority(Role role) {
        RoleName roleName = role.getName();
        return new SimpleGrantedAuthority(ROLE_ + roleName.name());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
