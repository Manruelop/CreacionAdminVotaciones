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
		text = "";
	}

	// Methods
	public Question(Survey s) {
		text = "";
	}

	@NotBlank
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

	@Override
	public String toString() {
		return "Question [text=" + text + "]";
	}

}
