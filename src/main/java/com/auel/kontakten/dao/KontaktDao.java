package com.auel.kontakten.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auel.kontakten.model.Kontakt;
//import com.auel.kontakten.model.Sex;

@Repository
public interface KontaktDao extends JpaRepository<Kontakt, Integer> {

	
	//*@Query("SELECT id, email, name, sex, telefon FROM Kontakt  WHERE name = :searchValue")
	//List<Kontakt> findByName(@Param("searchValue") String name);
	List<Kontakt> findByNameLikeIgnoreCase(String name);

	//List<Kontakt> findBySexContainingIgnoreCase(Sex sex);
	
	@Query("SELECT k FROM Kontakt k WHERE k.name like :x or k.sex like :x or k.email like :x")
	List<Kontakt> suchen(@Param("x") String key);
	
	@Query("SELECT k FROM Kontakt k WHERE UPPER(k.name) like UPPER(:x) or UPPER(k.sex) like UPPER(:x) or UPPER(k.email) like UPPER(:x)")
	Page<Kontakt> suchenPageIgnoreCase(@Param("x") String key, Pageable pageable);

}
