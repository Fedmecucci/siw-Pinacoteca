package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Curatore;
import it.uniroma3.siw.model.Opera;

public interface CuratoreRepository extends CrudRepository<Curatore, Long>{
	
	 public List<Curatore> findAll();
	 
	public Boolean existsByNomeAndCognome(String nome, String cognome);

}
