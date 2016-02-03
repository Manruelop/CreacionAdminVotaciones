package services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

	public int saveAndFlush(Question question) {
		Assert.notNull(question);
		Question q2 = questionRepository.saveAndFlush(question);
		int questionID = q2.getId();
		return questionID;
	}

	public Question findOne(int questionId) {
		return questionRepository.findOne(questionId);
	}
}
