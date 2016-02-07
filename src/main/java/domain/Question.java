package domain;

import javax.persistence.Entity;

@Entity
public class Question extends DomainEntity {

	// Attributes
	private String text;
	private Integer surveyId;

	public Question() {
		super();
		text = "";
	}

	// Methods

	// Este atributo indica el texto que forma la question.
	public String getText() {
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

	// Método toString para la representación como cadena de la clase entidad.

	@Override
	public String toString() {
		return "Question [text=" + text + "]";
	}

}
