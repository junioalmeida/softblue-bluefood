package almeida.ferreira.junio.bluefood.infrastructure.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		LoggedUser loggedUser = (LoggedUser) authentication.getPrincipal();

		if (loggedUser.getRole() == Role.CUSTUMER) {
			response.sendRedirect("custumer/home");
		} else if (loggedUser.getRole() == Role.RESTAURANT) {
			response.sendRedirect("restaurant/home");
		} else {
			throw new IllegalStateException("O tipo de usuário não é válido.");
		}

	}

}
