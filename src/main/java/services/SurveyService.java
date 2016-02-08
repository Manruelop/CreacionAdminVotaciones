package services;
import java.util.Collection;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SurveyRepository;

import domain.Question;
import domain.Survey;


@Service
public class SurveyService {

	//Repositories
	@Autowired
	private SurveyRepository surveyRepository;
	
	//Services
	@Autowired
	private QuestionService questionService;
	
	@Transactional
	public Integer save(Survey s) {
		Assert.notNull(s);
		Date now = new Date(System.currentTimeMillis() - 1000);
		if (s.getStartDate() == null || s.getEndDate() == null || s.getTitle() == "" || s.getTipo() == null) {
			throw new IllegalArgumentException("Null");
		}
		if (now.after(s.getStartDate()) || now.after(s.getEndDate())) {
			throw new IllegalArgumentException("Dates future");
		}
		if (s.getStartDate().after(s.getEndDate())) {
			throw new IllegalArgumentException("End must be future than start");
		}
		// CAMBIAR POR EL USUARIO LOGEADO Y CENSO POR LA ID DEL CENSO
		String userName = getUserNameFromOtherSubsystem();
		s.setUsernameCreator(userName);

		// Se le pone 0 temporalmente. Cuando guardamos despues de crear
		// las preguntas entonces establecemos la conexion con ceso
		s.setCensus(0);

		Survey s1 = surveyRepository.saveAndFlush(s);
		return s1.getId();

	}

	// Metodo para obtener la id del censo. Tenemos que enviarle la survey.
	private Integer getIdCensusFromOtherSubsystem(Survey survey) {
		return 1;
	}

	// Metodo para obtener el nombre del usuario
	private String getUserNameFromOtherSubsystem() {
		return "Test1";
	}


	// Metodo para obtener un survey mediante su id que le enviamos como
	//parámetro
	public Survey findOne(int id) {
		Assert.notNull(id);
		return surveyRepository.findOne(id);
	}
	// Método de interacción con el subsistema de Visualización
	public List<Survey> allFinishedSurveys() {

		Date now = new Date(System.currentTimeMillis());
		List<Survey> res = surveyRepository.allFinishedSurveys(now);
		return res;
	}

	// Metodo que devuelve una lista de surveys que han sido creados por un usuario
	//que le pasamos como parámetro.
	public List<Survey> allCreatedSurveys(String usernameCreator) {
		List<Survey> res = surveyRepository.allCreatedSurveys(usernameCreator);
		return res;
	}
	
	// Metodo que elimina survey de la base de datos, mediante la id del survey
	//que le pasamos como parámetro
	public void delete(int id) {
		Assert.notNull(id);
		Date now = new Date(System.currentTimeMillis() - 1000);
		Survey survey = surveyRepository.findOne(id);
		if (survey.getStartDate().after(now)) {
			surveyRepository.delete(id);
		} else {
			throw new IllegalArgumentException("Survey is started");
		}
	}

	public Boolean posible(int id) {
		Assert.notNull(id);
		Survey s = findOne(id);

		if (s.getCensus() == null) {
			return true;
		} else {
			return false;
		}
	}
	
	// Metodo que devuelve una colección de todos los survey que persisten en
	//la base de datos del sistema
	public Collection<Survey> allSurveys() {
		return surveyRepository.findAll();
	}

	// Metodo que devuelve un survey una vez que ha sido creado dentro de
	//dicho metodo
	public Survey create() {
		Survey result;
		result = new Survey();
		List<Question> questions = new LinkedList<Question>();
		result.setQuestions(questions);
		return result;
	}

	// Metodo que recupera un survey y le modifica la colección de question
	// añadiendole una nueva question, que se recupera mediante la id
	//que se le pasa por parámetro.
	public Survey saveAddQuestion(int id, int questionId, boolean esFinal) {
		Survey survey = surveyRepository.findOne(id);
		Collection<Question> questions = survey.getQuestions();
		questions.add(questionService.findOne(questionId));
		survey.setQuestions(questions);
		Survey s = surveyRepository.saveAndFlush(survey);
		if (esFinal) {
			Integer idCensus = getIdCensusFromOtherSubsystem(s);
			s.setCensus(idCensus);
			Survey s2 = surveyRepository.saveAndFlush(s);
			s=s2;
		}
		return s;
	}

	// Metodo que persiste una survey en la base de datos.
	public void saveFinal(Survey survey) {
		Assert.notNull(survey);
		surveyRepository.saveAndFlush(survey);
	}

	public void addCensus(Integer censoId, Integer surveyId) {
		Survey s = surveyRepository.findOne(surveyId);
		s.setCensus(censoId);
		saveFinal(s);
	}

	
}