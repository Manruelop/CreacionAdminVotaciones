package domain;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Question extends DomainEntity {

	// Attributes
	private String text;
	private Integer surveyId;

	public Question() {
		super();
<<<<<<< HEAD
		text = "";
	}

	// Methods
	public Question(Survey s) {
		text = "";
	}

	@NotBlank
	public String getText() {
=======
		text="";
	}	

	public Question(Survey s){
		text="";
	}

	//Methods

	//Este atributo indica el texto que forma la question.

	public String getText(){
>>>>>>> comments
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer id) {
		surveyId = id;
	}

	//Método toString para la representación como cadena de la clase entidad.

	@Override
	public String toString() {
		return "Question [text=" + text + "]";
	}

}
