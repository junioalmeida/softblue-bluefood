package almeida.ferreira.junio.bluefood.domain.order;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ItemOrderPK implements Serializable{
	
	@NotNull
	@ManyToOne
	private Order order;
	
	@NotNull
	private Integer sequence;
}
