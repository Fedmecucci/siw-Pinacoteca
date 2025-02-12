package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.model.User;

public interface ArtistaRepository extends CrudRepository<Artista,Long>{
	
	public boolean existsByNomeAndCognome(String nome, String cognome);

	public List<Artista> findAll();
	
	public Artista findByUser(User utente);
}
