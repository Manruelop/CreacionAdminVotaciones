package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Question;
@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{

	//No se aplican consultas específicas en esta clase, únicamente se
	//crea para poder disponer en los servicios de los métodos internos del
	//repositorio.

}
