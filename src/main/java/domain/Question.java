package domain;

import javax.persistence.Entity;
@Entity
public class Question extends DomainEntity{
	
	//Attributes
	private String text;
	
	public Question(){
		super();
		text="";
	}	

	public Question(Survey s){
		text="";
	}

	//Methods

	//Este atributo indica el texto que forma la question.

	public String getText(){
		return text;
	}

	public void setText(String text){
		this.text=text;
	}

	//Método toString para la representación como cadena de la clase entidad.

	@Override
	public String toString() {
		return "Question [text=" + text + "]";
	}
		
	
}
