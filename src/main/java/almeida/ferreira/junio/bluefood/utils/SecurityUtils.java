package almeida.ferreira.junio.bluefood.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import almeida.ferreira.junio.bluefood.domain.custumer.Custumer;
import almeida.ferreira.junio.bluefood.domain.restaurant.Restaurant;
import almeida.ferreira.junio.bluefood.infrastructure.web.security.LoggedUser;

public class SecurityUtils {

	public static LoggedUser getLoggedUser() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth instanceof AnonymousAuthenticationToken) {
			return null;
		}

		return (LoggedUser) auth.getPrincipal();
	}
	
	public static Custumer getLoggedCustumer() {
		
		LoggedUser loggedUser = getLoggedUser();
		
		if(loggedUser == null) {
			throw new IllegalStateException("Não existe nenhum usuário logado.");
		}
		
		if(!(loggedUser.getUser() instanceof Custumer)) {
			throw new IllegalStateException("O usuário logado não é um cliente");
		}
		
		return (Custumer) loggedUser.getUser();
	}
	
	public static Restaurant getLoggedRestaurant() {
		
		LoggedUser loggedUser = getLoggedUser();
		
		if(loggedUser == null) {
			throw new IllegalStateException("Não existe nenhum usuário logado.");
		}
		
		if(!(loggedUser.getUser() instanceof Restaurant)) {
			throw new IllegalStateException("O usuário logado não é um cliente");
		}
		
		return (Restaurant) loggedUser.getUser();
	}
}
