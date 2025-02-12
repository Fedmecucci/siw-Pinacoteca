package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Artista;

import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.model.User;


public interface OperaRepository extends  CrudRepository<Opera,Long> {
	
	public boolean existsByTitoloAndAnno(String titolo,Integer anno);
	
    public List<Opera> findAll();
	
    public Opera findByUser(User utente);
}
