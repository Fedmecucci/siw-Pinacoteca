package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import it.uniroma3.siw.model.Curatore;
import it.uniroma3.siw.repository.CuratoreRepository;

@Service
public class CuratoreService {

	@Autowired
	private CuratoreRepository curatoreRepository;
	
	public Iterable<Curatore>findAll(){
		return curatoreRepository.findAll();
	}
	@Transactional
	public Curatore findById(Long id) {
		return curatoreRepository.findById(id).get();
	}
	public boolean existsByNomeAndCognome(String nome, String cognome) {
		return curatoreRepository.existsByNomeAndCognome(nome, cognome);
	}
}
