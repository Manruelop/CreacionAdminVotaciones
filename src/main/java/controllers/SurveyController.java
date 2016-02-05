package controllers;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Question;
import domain.Survey;
import services.QuestionService;
import services.SurveyService;

@Controller
@RequestMapping("/vote")
public class SurveyController {

	// Services ------------------------------------------

	@Autowired
	private SurveyService surveyService;

	@Autowired
	private QuestionService questionService;

	// Constructor ---------------------------------------
	public SurveyController() {
		super();
	}

	// Listing -------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Survey> surveis;

		surveis = surveyService.allSurveys();
		Date now = new Date(System.currentTimeMillis() - 1000);
		result = new ModelAndView("vote/list");
		result.addObject("surveis", surveis);
		result.addObject("hoy", now);

		return result;
	}

	// Creation ------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Survey survey;

		survey = surveyService.create();

		result = new ModelAndView("vote/create");
		result.addObject("survey", survey);
		result.addObject("actionURL", "vote/create.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "addQuestion")
	public ModelAndView addQuestio(@Valid Survey survey, BindingResult bindingResult) {
		ModelAndView result;
		Assert.notNull(survey);
		Date now = new Date(System.currentTimeMillis() - 1000);
		System.out.println(survey.getStartDate());
		System.out.println(survey.getEndDate());
		if (bindingResult.hasErrors() || survey.getStartDate() == null || survey.getEndDate() == null
				|| survey.getTitle() == "" || survey.getTipo() == null || now.after(survey.getStartDate())
				|| now.after(survey.getEndDate()) || survey.getStartDate().after(survey.getEndDate())) {
			System.out.println(bindingResult.toString());
			result = new ModelAndView("vote/create");
			result.addObject("actionURL", "vote/create.do");
			result.addObject("survey", survey);
			if (survey.getStartDate() == null || survey.getEndDate() == null || survey.getTitle() == ""
					|| survey.getTipo() == null) {
				result.addObject("message", "survey.fields.empty");
			}
			if (now.after(survey.getStartDate()) || now.after(survey.getEndDate())) {
				result.addObject("message", "survey.dates.future");
			}
			if (survey.getStartDate().after(survey.getEndDate())) {
				result.addObject("message", "survey.start.end");
			}
		} else {
			try {
				Integer s2 = surveyService.save(survey);
				result = new ModelAndView("redirect:/vote/addQuestion.do");
				result.addObject("surveyId", s2);
			} catch (Throwable oops) {
				result = new ModelAndView("/vote/create");
				result.addObject("message", "survey.commit.error");
				result.addObject("survey", survey);
				if (survey.getStartDate() == null || survey.getEndDate() == null || survey.getTitle() == ""
						|| survey.getTipo() == null) {
					result.addObject("message", "survey.fields.empty");
				}
				if (survey.getStartDate() == null || survey.getEndDate() == null || survey.getTitle() == ""
						|| survey.getTipo() == null) {
					result.addObject("message", "survey.fields.empty");
				}
				if (now.after(survey.getStartDate()) || now.after(survey.getEndDate())) {
					result.addObject("message", "survey.dates.future");
				}
				if (survey.getStartDate().after(survey.getEndDate())) {
					result.addObject("message", "survey.start.end");
				}
			}
		}
		return result;
	}

	@RequestMapping(value = "/addQuestion", method = RequestMethod.GET)
	public ModelAndView addQuestion(Integer surveyId) {
		ModelAndView result;
		Survey survey = surveyService.findOne(surveyId);
		Question question = questionService.create(surveyId);
		question.setSurveyId(surveyId);
		result = new ModelAndView("vote/addQuestion");
		result.addObject("actionURL", "vote/addQuestion.do");
		result.addObject("survey", survey);
		result.addObject("questio", question);
		return result;
	}

	@RequestMapping(value = "/addQuestion", method = RequestMethod.POST, params = "addQuestion")
	public ModelAndView addAnotherQuestion(Question questio, BindingResult bindingResult) {
		ModelAndView result;
		Assert.notNull(questio);
		Survey survey = surveyService.findOne(questio.getSurveyId());
		if (bindingResult.hasErrors()) {
			result = new ModelAndView("vote/addQuestion");
			result.addObject("actionURL", "vote/addQuestion.do");
			result.addObject("surveyId", survey.getId());
			result.addObject("questio", questio);
		} else {
			try {
				int idQuestion = questionService.saveAndFlush(questio);
				surveyService.saveAddQuestion(survey.getId(), idQuestion, false);
				result = new ModelAndView("vote/addQuestion");
				Question question = questionService.create(survey.getId());
				question.setSurveyId(survey.getId());
				result.addObject("survey", survey);
				result.addObject("questio", question);

			} catch (Throwable oops) {
				result = new ModelAndView("vote/addQuestion");
				result.addObject("message", "survey.commit.error");
				result.addObject("actionURL", "vote/addQuestion.do");
				result.addObject("survey", survey.getId());
				result.addObject("questio", questio);
			}
		}
		return result;
	}

	@RequestMapping(value = "/addQuestion", method = RequestMethod.POST, params = "save")
	public ModelAndView saveSurvey(Question questio, BindingResult bindingResult) {
		ModelAndView result;
		Assert.notNull(questio);
		Survey survey = surveyService.findOne(questio.getSurveyId());
		if (bindingResult.hasErrors()) {
			result = new ModelAndView("vote/addQuestion");
			result.addObject("actionURL", "vote/addQuestion.do");
			result.addObject("survey", survey.getId());
			result.addObject("questio", questio);
		} else {
			try {
				int idQuestion = questionService.saveAndFlush(questio);
				surveyService.saveAddQuestion(survey.getId(), idQuestion, true);
				result = new ModelAndView("redirect:/vote/list.do");
			} catch (Throwable oops) {
				result = new ModelAndView("vote/addQuestion");
				result.addObject("message", "survey.commit.error");
				result.addObject("actionURL", "vote/addQuestion.do");
				result.addObject("survey", survey.getId());
				result.addObject("questio", questio);
			}
		}
		return result;
	}

	@RequestMapping(value = "/cancelSurvey", method = RequestMethod.GET)
	public ModelAndView cancelSurvey(@RequestParam int surveyId) {
		ModelAndView result;
		Assert.notNull(surveyId);
		try {
			surveyService.delete(surveyId);
			result = new ModelAndView("redirect:/vote/list.do");
		} catch (Throwable oops) {
			result = new ModelAndView("vote/addQuestion");
			result.addObject("message", "survey.commit.error");
			result.addObject("actionURL", "vote/addQuestion.do");
			result.addObject("survey", surveyId);
		}
		return result;
	}

	// Details ----------------------------------------------

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int surveyId) {
		ModelAndView result;
		Survey survey;
		survey = surveyService.findOne(surveyId);
		Assert.notNull(survey);
		result = new ModelAndView("vote/details");
		result.addObject("survey", survey);
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int surveyId) {
		ModelAndView result;
		try {
			surveyService.delete(surveyId);
			result = new ModelAndView("redirect:/vote/list.do");
		} catch (Throwable oops) {
			result = new ModelAndView("vote/list");
			result.addObject("message", "survey.error.dates");
		}

		return result;
	}

}