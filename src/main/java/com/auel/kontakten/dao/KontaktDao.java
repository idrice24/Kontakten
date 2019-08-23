package com.auel.kontakten.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auel.kontakten.model.Kontakt;
import com.auel.kontakten.model.Sex;

@Repository
public interface KontaktDao extends JpaRepository<Kontakt, Integer> {

	
	//*@Query("SELECT id, email, name, sex, telefon FROM Kontakt  WHERE name = :searchValue")
	//List<Kontakt> findByName(@Param("searchValue") String name);
	List<Kontakt> findByName(String  name);

	List<Kontakt> findBySex(Sex sex);
	
}
