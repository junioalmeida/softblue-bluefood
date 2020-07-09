package almeida.ferreira.junio.bluefood.domain.payment;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import almeida.ferreira.junio.bluefood.domain.order.Order;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Payment implements Serializable{
	
	@Id
	private Integer id;
	
	@NotNull
	@OneToOne
	@MapsId
	private Order order;
	
	@NotNull
	private LocalDateTime date;
	
	@NotNull
	@Size(min = 4, max = 4)
	private String endCreditCard;
	
	@Column(length = 10, nullable = false)
	@Enumerated(EnumType.STRING)
	private BrandCreditCard brand;
	
	public void setEndAndBrand(String numCard) {
		brand = getBrandFromNumber(numCard);
		endCreditCard = numCard.substring(12);
	}
	
	private BrandCreditCard getBrandFromNumber(String numCard) {
		if(numCard.startsWith("1111")) {
			return BrandCreditCard.AMEX;
		}
		
		if(numCard.startsWith("2222")) {
			return BrandCreditCard.MASTER;
		}
		
		if(numCard.startsWith("3333")) {
			return BrandCreditCard.ELO;
		}
		
		return BrandCreditCard.VISA;
	}
}
