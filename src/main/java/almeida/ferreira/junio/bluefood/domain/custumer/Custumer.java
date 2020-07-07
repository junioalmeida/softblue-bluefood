package almeida.ferreira.junio.bluefood.domain.custumer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

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
	@NotBlank(message = "O CPF deve ser informado.")
	@Digits(integer = 11, fraction = 0, message = "O formato do CPF não é válido.")
	private String cpf;

}
