package domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Survey extends DomainEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 749544364605664829L;
	// Attributes
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private Integer census;
	private String tipo;

	public Survey() {
		super();
		questions = new LinkedList<Question>();
	}

	// Methods

	//Título de la votación
	
	@NotBlank
	@Length(min = 5, max = 100, message = "The field must be between 5 and 10 characters")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	//Descripción de la votación
	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

<<<<<<< HEAD
	@DateTimeFormat(pattern = "dd/MM/yyyy")
=======
	//Fecha de creación de la votación
	//con @DateTimeFormat especificamos el formato para la fecha que se va a
	//almacenar en la base de datos del sistema.
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
>>>>>>> comments
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

<<<<<<< HEAD
	@DateTimeFormat(pattern = "dd/MM/yyyy")
=======
	//Fecha fin de la votación 

	@DateTimeFormat(pattern = "yyyy/MM/dd")
>>>>>>> comments
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	//El atributo tipo indica de que tipo de votación se trata.

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	//Indica la id del censo relacionado a la votacion

	public Integer getCensus() {
		return census;
	}

	public void setCensus(Integer census) {
		this.census = census;
	}

	// Relationships
	private Collection<Question> questions;
	private String usernameCreator;

	//Asociación con la clase entidad userNameControl

	public String getUsernameCreator() {
		return usernameCreator;
	}

	public void setUsernameCreator(String usernameCreator) {
		this.usernameCreator = usernameCreator;
	}

	//Asociación con la clase entidad questions

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public Collection<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Collection<Question> questions) {
		this.questions = questions;
	}

	public void addQuestion(Question q) {
		questions.add(q);
	}

	public void removeQuestion(Question q) {
		questions.remove(q);
	}

	//Método toString para la representación del la entidad Survey
	
	@Override
	public String toString() {
		return "Survey [title=" + title + ", description=" + description
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", census=" + census + ", questions=" + questions + "]";
	}
	
}
