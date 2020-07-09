package almeida.ferreira.junio.bluefood.domain.order;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import almeida.ferreira.junio.bluefood.domain.restaurant.MenuItem;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "item_order")
public class ItemOrder implements Serializable{

	@EqualsAndHashCode.Include
	@EmbeddedId
	private ItemOrderPK id;
	
	@NotNull
	@ManyToOne
	private MenuItem item;
	
	@NotNull
	private Integer amount;
	
	@Size(max = 50)
	private String notes;
	
	@NotNull
	private BigDecimal price;
	
	public BigDecimal getTotalPrice() {
		return price.multiply(BigDecimal.valueOf(amount));
	}
}
