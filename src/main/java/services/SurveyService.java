
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SurveyRepository;
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
	
	
}