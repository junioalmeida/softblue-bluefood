package almeida.ferreira.junio.bluefood.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import almeida.ferreira.junio.bluefood.domain.custumer.Custumer;
import almeida.ferreira.junio.bluefood.domain.custumer.CustumerRepository;

@Service
public class CustumerService{
	
	@Autowired
	private CustumerRepository custumerRepository;
	
	public void save(Custumer custumer) throws ValidationException{
		
		if(!validateEmail(custumer.getEmail(), custumer.getId())) {
			throw new ValidationException("O email informado já está sendo utilizado.");
		}
		
		if(custumer.getId() != null) {
			Custumer custumerDB = custumerRepository.findById(custumer.getId()).orElseThrow();
			custumer.setPassword(custumerDB.getPassword());
		} else {
			custumer.encryptPassword();
		}
		
		custumerRepository.save(custumer);
	}
	
	private boolean validateEmail(String email, Integer id) {
		
		Custumer custumer = custumerRepository.findByEmail(email);
		
		if(custumer != null) {
			if(id == null) {
				return false;
			}
			
			if(!custumer.getId().equals(id)) {
				return false;
			}
		}
		
		return true;
	}
	
}
