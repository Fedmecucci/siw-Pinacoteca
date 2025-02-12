package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.service.OperaService;



@Component
public class OperaValidator implements Validator {
	
	@Autowired
	private OperaService operaService;
	
	@Override
	public void validate(Object o, Errors errors) {
	    Opera opera  = (Opera)o;
	    if (opera.getTitolo() != null && opera.getAnno() != null
	        && operaService.existsByTitoloAndAnno(opera.getTitolo(), opera.getAnno())) {
	        errors.reject("opera.duplicate");
	    }
	}

	@Override
	public boolean supports(Class<?> aClass) {
	    return Opera.class.equals(aClass);
	}
}
