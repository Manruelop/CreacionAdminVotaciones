package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Census;

public interface CensusRepository extends JpaRepository<Census, Integer>{

}
