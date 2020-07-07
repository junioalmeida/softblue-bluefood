package almeida.ferreira.junio.bluefood.domain.restaurant;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import almeida.ferreira.junio.bluefood.domain.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
public class Restaurant extends User {

	@Column(length = 14, nullable = false)
	@NotBlank(message = "O CNPJ deve ser informado.")
	@Digits(integer = 14, fraction = 0, message = "O formato do CNPJ não é válido.")
	private String cnpj;
	
	@Column(length = 80, nullable = false)
	@NotBlank(message = "A logotipo deve ser informado.")
	@Size(max = 80)
	private String logotipo;

	@Column(length = 80, nullable = false)
	@NotNull(message = "A taxa básica de entrega deve ser informado.")
	@Min(value = 0, message = "A taxa básica de entrega não pode ser negativa.")
	@Max(value = 99, message = "A taxa básica de entrega deve ser menor que R$ 99,00.")
	private BigDecimal baseDeliveryRate;

	@Column(length = 80, nullable = false)
	@NotNull(message = "O tempo de entrega deve ser informado.")
	@Min(value = 0, message = "O tempo de entrega não pode ser negativa.")
	@Max(value = 120, message = "O tempo de entrega deve ser menor que 120 minutos.")
	private Integer deliveryTime;

	@ManyToMany
	@JoinTable(
			name = "restaurant_has_category", 
			joinColumns = @JoinColumn(name = "restaurant_id"), 
			inverseJoinColumns = @JoinColumn(name = "category_id")
	)
	@Size(min = 1, message = "Ao menos uma categoria deve ser selecionada.")
	private Set<RestaurantCategory> categories = new HashSet<>(0);
}
