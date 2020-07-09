package almeida.ferreira.junio.bluefood.domain.restaurant;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import almeida.ferreira.junio.bluefood.infrastructure.web.validator.UploadConstraint;
import almeida.ferreira.junio.bluefood.utils.FileType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "menu_item")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MenuItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	
	@NotBlank(message = "O nome do item deve ser informado.")
	@Size(max = 50)
	private String name;
	
	@NotBlank(message = "A descrição do item deve ser informada.")
	@Size(max = 50)
	private String description;
	
	@Size(max = 50)
	private String image;
	
	@NotBlank(message = "A categoria do item deve ser informada.")
	@Size(max = 50)
	private String category;
	
	@NotNull(message = "O preço do item deve ser informado.")
	@Min(0)
	private BigDecimal price;
	
	@NotNull
	private Boolean spotlight;
	
	@UploadConstraint(acceptedFileTypes = FileType.PNG, message = "O tipo de imagem não é válido.")
	private transient MultipartFile imageFile;
	
	@NotNull
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;
	
	public void setImageFileName() {
		
		if(getId() == null) {
			throw new IllegalStateException("O objeto deve ser salvo no banco de dados primeiramente.");
		}
		
		this.image = String.format("%04d-food.%s", 
							getId(), 
							FileType.of(imageFile.getContentType()));
	}
}
