package almeida.ferreira.junio.bluefood.infrastructure.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import almeida.ferreira.junio.bluefood.domain.custumer.CustumerRepository;
import almeida.ferreira.junio.bluefood.domain.restaurant.RestaurantRepository;
import almeida.ferreira.junio.bluefood.domain.user.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private CustumerRepository custumerRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = custumerRepository.findByEmail(username);
		
		if(user == null) {
			
			user = restaurantRepository.findByEmail(username);
			
			if(user == null) {
				throw new UsernameNotFoundException(username);
			}
		}
		
		return new LoggedUser(user);
		
	}

}
