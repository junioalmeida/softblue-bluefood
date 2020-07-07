package almeida.ferreira.junio.bluefood.domain.custumer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustumerRepository extends JpaRepository<Custumer, Integer> {
	
	public Custumer findByEmail(String email);

}
