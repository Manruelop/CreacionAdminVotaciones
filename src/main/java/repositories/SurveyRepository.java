package repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Survey;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer> {

	//Consulta a la base de datos que nos devuelve una lista de Survey
	//cuya fecha de finalización sea la que se pasa por parámetro.
	@Query("select s from Survey s where ?1 = s.endDate")
	public List<Survey> allFinishedSurveys(Date now);

	//Cosulta a la base de datos que nos devuelve una lista de Survey
	//que han sido creadas por un username que se pasa por parámetro.
	@Query("select s from Survey s where ?1 = s.usernameCreator")
	public List<Survey> allCreatedSurveys(String username);
}
