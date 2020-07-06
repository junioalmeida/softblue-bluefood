package almeida.ferreira.junio.bluefood.domain.custumer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import almeida.ferreira.junio.bluefood.domain.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
public class Custumer extends User {

	@Column(length = 11, nullable = false)
	@NotBlank(message = "O CPF deve ser informado")
	@Pattern(regexp = "[0-9]{11}", message = "O CPF não é válido")
	private String cpf;

}
