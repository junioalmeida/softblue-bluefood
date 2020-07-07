package almeida.ferreira.junio.bluefood.domain.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import almeida.ferreira.junio.bluefood.utils.StringUtils;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 80, nullable = false)
	@NotBlank(message = "O nome deve ser preenchido")
	@Size(max = 80, message = "O nome é muito grande")
	private String name;
	
	@Column(length = 60, nullable = false)
	@NotBlank(message = "O email deve ser preenchido")
	@Email(message = "O email não é válido")
	@Size(max = 60, message = "O email é muito grande")
	private String email;
	
	@Column(length = 11, nullable = false)
	@NotBlank(message = "O telefone deve ser preenchido")
	@Digits(integer = 11, fraction = 0, message = "O telefone não é válido")
	private String phone;
	
	@Column(length = 8, nullable = false)
	@NotBlank(message = "O CEP deve ser preenchido")
	@Digits(integer = 8, fraction = 0, message = "O telefone não é válido")
	private String cep;
	
	@Column(length = 80, nullable = false)
	@NotBlank(message = "A Senha deve ser preenchida")
	@Size(max = 80, message = "A senha é muito grande")
	private String password;
	
	public void encryptPassword() {
		this.password = StringUtils.encypt(this.password);
	}
}
