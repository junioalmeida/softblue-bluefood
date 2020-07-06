package almeida.ferreira.junio.bluefood.domain.user;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@MappedSuperclass
public class User implements Serializable {
	
	@EqualsAndHashCode.Include
	@Id
	private Integer id;
	
	private String name;
	
	private String email;
	
	private String phone;
	
	private String cep;
	
	private String password;
}
