package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Curatore;

import it.uniroma3.siw.service.CuratoreService;


@Component
public class CuratoreValidator implements Validator{

	
	@Autowired
	private CuratoreService curatoreService;
	
	@Override
	public void validate(Object o, Errors errors) {
	    Curatore curatore = (Curatore)o;
	    if (curatore.getNome() != null && curatore.getCognome() != null
	        && curatoreService.existsByNomeAndCognome(curatore.getNome(), curatore.getCognome())) {
	        errors.reject("opera.duplicate");
	    }
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
	    return Curatore.class.equals(aClass);
	}
}
