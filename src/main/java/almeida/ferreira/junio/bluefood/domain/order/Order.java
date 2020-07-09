package almeida.ferreira.junio.bluefood.domain.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import almeida.ferreira.junio.bluefood.domain.custumer.Custumer;
import almeida.ferreira.junio.bluefood.domain.payment.Payment;
import almeida.ferreira.junio.bluefood.domain.restaurant.Restaurant;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "\"Order\"")
public class Order implements Serializable {
	
	@AllArgsConstructor
	@Getter
	public enum Status {
		PRODUCAO(1, "Em produção", false),
		ENTREGA(2, "Saiu para entrega", false),
		CONCLUIDO(3, "Concluído", true);
		
		private int position;
		private String description;
		private boolean last;
		
		public static Status getFromPosition(int position){
			for (Status status : values()) {
				if(status.position == position) {
					return status;
				}
			}
			
			return null;
		}
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	private LocalDateTime date;
	
	@NotNull
	@ManyToOne
	private Custumer custumer;
	
	@NotNull
	@ManyToOne
	private Restaurant restaurant;
	
	@NotNull
	private BigDecimal subtotal;

	@NotNull
	private BigDecimal deliveryRate;
	
	@NotNull
	private BigDecimal total;
	
	@NotNull
	private Status status;
	
	@OneToOne(mappedBy = "order")
	private Payment payment;
	
	@OneToMany(mappedBy = "id.order", fetch = FetchType.EAGER)
	private Set<ItemOrder> itens = new HashSet<>();
	
	@Override
	public String toString() {
		return String.format("#%04d %s", this.getId(), this.getRestaurant().getName());
	}
	
	public Status getNextStatus() {
		if(!status.isLast()) {
			return Status.getFromPosition(status.getPosition() + 1);
		}
		
		return status;
	}
	
	public void setNextStatus() {
		if(!status.isLast()) {
			status = Status.getFromPosition(status.getPosition() + 1);
		}
	}
}
