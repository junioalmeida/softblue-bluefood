package almeida.ferreira.junio.bluefood.domain.custumer;

import javax.persistence.Entity;

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

	private String cpf;

}
