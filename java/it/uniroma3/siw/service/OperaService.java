package it.uniroma3.siw.service;

import java.io.IOException;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.GlobalController;
import it.uniroma3.siw.model.Artista;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Curatore;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.OperaRepository;

@Service
public class OperaService {
	
	@Autowired
	private OperaRepository operaRepository;
	@Autowired
	private CredentialsService credentialsService;
	@Autowired
	GlobalController globalController;
	
	public Iterable<Opera>findAll(){
		return operaRepository.findAll();
	}
	
	public boolean existsByTitoloAndAnno(String titolo,Integer anno) {
		return operaRepository.existsByTitoloAndAnno(titolo, anno);
	}
	
	@Transactional
	public Opera findById(Long id) {
		return operaRepository.findById(id).get();
	}
	
	@Transactional
	public Opera save(Opera opera) {
		return operaRepository.save(opera);
		
	}
	
	@Transactional
	public void save2(Opera opera,MultipartFile file) throws IOException {
		opera.setImmagine(Base64.getEncoder().encodeToString(file.getBytes()));
		operaRepository.save(opera);		
	}
	
	@Transactional
	public void modifica(@RequestParam("id") Long id,
			@RequestParam("nuovoTitolo") String nuovoTitolo, @RequestParam("nuovoAnno") Integer nuovoAnno,
			@RequestParam("nuovaTecnica")  String nuovaTecnica, @RequestParam("nuovaCollocazione") String nuovaCollocazione
			) {
		Opera s = this.findById(id);
		s.setTitolo(nuovoTitolo);
		s.setAnno(nuovoAnno);
		s.setTecnica(nuovaTecnica);
		s.setCollocazione(nuovaCollocazione);
	
		this.save(s);	
	}
	
	 @Transactional
	    public Opera findByUser(User user) {
	        return operaRepository.findByUser(user);
	    }
	 @Transactional
		public void creaOpera(@ModelAttribute("opera") Opera opera, BindingResult bindingResult,
				@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
		 this.save(opera);
			Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
			User user = credenziali.getUser();
			opera.setUser(user);
			this.save2(opera, imageFile);	
		}

}
