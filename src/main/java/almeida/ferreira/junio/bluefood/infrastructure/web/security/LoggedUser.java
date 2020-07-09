package almeida.ferreira.junio.bluefood.infrastructure.web.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import almeida.ferreira.junio.bluefood.domain.custumer.Custumer;
import almeida.ferreira.junio.bluefood.domain.restaurant.Restaurant;
import almeida.ferreira.junio.bluefood.domain.user.User;

@SuppressWarnings("serial")
public class LoggedUser implements UserDetails {

	private User user;
	private Role role;
	private Collection<? extends GrantedAuthority> roles;

	public LoggedUser(User user) {
		this.user = user;

		if (this.user instanceof Custumer) {
			role = Role.CUSTUMER;
		} else if (this.user instanceof Restaurant) {
			role = Role.RESTAURANT;
		} else {
			throw new IllegalStateException("O tipo de usuário não é válido.");
		}

		roles = List.of(new SimpleGrantedAuthority("ROLE_" + role));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
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

	public User getUser() {
		return user;
	}

	public Role getRole() {
		return role;
	}

}
