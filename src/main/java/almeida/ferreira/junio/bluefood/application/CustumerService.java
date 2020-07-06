package almeida.ferreira.junio.bluefood.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import almeida.ferreira.junio.bluefood.domain.custumer.Custumer;
import almeida.ferreira.junio.bluefood.domain.custumer.CustumerRepository;

@Service
public class CustumerService{
	
	@Autowired
	CustumerRepository custumerRepository;
	
	public void save(Custumer custumer) {
		custumerRepository.save(custumer);
	}
	
}
