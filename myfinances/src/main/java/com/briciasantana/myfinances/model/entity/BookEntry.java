package com.briciasantana.myfinances.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import com.briciasantana.myfinances.model.enums.BookEntryStatus;
import com.briciasantana.myfinances.model.enums.BookEntryType;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
@Table(name ="bookentry", schema="finances")
public class BookEntry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	private Long id;
	
	@Column(name="description")
	private String description;
	
	@Column(name="month")
	private Integer month;
	
	@Column(name="year")
	private Integer year;
	
	@ManyToOne
	@JoinColumn(name="id_user")
	private User user;
	
	@Column(name="value")
	private BigDecimal value;
	
	@Column(name="register_date")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate registerDate;
	
	@Column(name="type")
	@Enumerated(value = EnumType.STRING)
	private BookEntryType type;
	
	@Column(name="status")
	@Enumerated(value = EnumType.STRING)
	private BookEntryStatus status;

}
