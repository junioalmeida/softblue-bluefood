package almeida.ferreira.junio.bluefood.domain.restaurant;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import almeida.ferreira.junio.bluefood.domain.user.User;
import almeida.ferreira.junio.bluefood.infrastructure.web.validator.UploadConstraint;
import almeida.ferreira.junio.bluefood.utils.FileType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

	@Size(max = 80)
	private String logotipo;

	@UploadConstraint(acceptedFileTypes = {FileType.PNG}, message = "A imagem não está em um formato válido.")
	private transient MultipartFile logotipoFile;

	@Column(length = 80, nullable = false)
	@NotNull(message = "A taxa básica de entrega deve ser informado.")
	@Min(value = 0, message = "A taxa básica de entrega não pode ser negativa.")
	@Max(value = 99, message = "A taxa básica de entrega deve ser menor que R$ 99,00.")
	private BigDecimal baseDeliveryRate;

	@Column(length = 80, nullable = false)
	@NotNull(message = "O tempo de entrega deve ser informado.")
	@Min(value = 0, message = "O tempo de entrega não pode ser negativa.")
	@Max(value = 120, message = "O tempo de entrega deve ser menor que 120 minutos.")
	private Integer baseDeliveryTime;

	@ManyToMany
	@JoinTable(
			name = "restaurant_has_category", 
			joinColumns = @JoinColumn(name = "restaurant_id"), 
			inverseJoinColumns = @JoinColumn(name = "category_id")
	)
	@Size(min = 1, message = "Ao menos uma categoria deve ser selecionada.")
	@ToString.Exclude
	private Set<RestaurantCategory> categories = new HashSet<>(0);

	@OneToMany(mappedBy = "restaurant")
	private Set<MenuItem> menuItens = new HashSet<>(0);
	
	public void setLogotipoFileName() {
		if (getId() == null) {
			throw new IllegalStateException("O restaurante deve estar salvo no banco de dados.");
		}

		this.logotipo = String.format("%04d-logo.%s", 
									getId(), 
									FileType.of(logotipoFile.getContentType()));
	}
}
