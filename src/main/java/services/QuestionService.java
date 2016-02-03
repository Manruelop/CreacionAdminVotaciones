package services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.QuestionRepository;
import domain.DomainEntity;
import domain.Question;

@Service
public class QuestionService extends DomainEntity{

	//Repository
	@Autowired
	private QuestionRepository questionRepository;

	//Methods
		public Question create(Integer surveyId){
		Question o = new Question();
		o.setSurveyId(surveyId);
		questionRepository.saveAndFlush(o);
		return o;
	}

	public Question create(String question) {
		Question o = new Question();
		o.setText(question);
		
		questionRepository.saveAndFlush(o);
		return o;
	}
}
