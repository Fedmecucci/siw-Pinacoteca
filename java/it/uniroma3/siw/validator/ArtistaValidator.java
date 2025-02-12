package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Artista;

import it.uniroma3.siw.service.ArtistaService;


@Component
public class ArtistaValidator implements Validator{

	@Autowired
	private ArtistaService artistaService;
	
	@Override
	public void validate(Object o, Errors errors) {
	    Artista artista = (Artista)o;
	    if (artista.getNome() != null && artista.getCognome() != null
	        && artistaService.existsByNomeAndCognome(artista.getNome(), artista.getCognome())) {
	        errors.reject("artista.duplicate");
	    }
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
	    return Artista.class.equals(aClass);
	}

}
