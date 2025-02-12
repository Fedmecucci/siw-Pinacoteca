package it.uniroma3.siw.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.ArtistaRepository;

@Service
public class ArtistaService {

	@Autowired
	private ArtistaRepository artistaRepository;
	@Autowired
	private CredentialsService credentialsService;
	@Autowired
	GlobalController globalController;
	
	public Iterable<Artista>findAll(){
		return artistaRepository.findAll();
	}
	@Transactional
	public Artista findById(Long id) {
		return artistaRepository.findById(id).get();
	}
	public boolean existsByNomeAndCognome(String nome, String cognome) {
		return artistaRepository.existsByNomeAndCognome(nome, cognome);
	}
	
	@Transactional
	public Artista save(Artista artista) {
		return artistaRepository.save(artista);
		
	}
	
	@Transactional
	public void save2(Artista artista,MultipartFile file) throws IOException {
		artista.setImmagine(Base64.getEncoder().encodeToString(file.getBytes()));
		artistaRepository.save(artista);		
	}
	
	@Transactional
	public void modifica(@RequestParam("id") Long id,
			@RequestParam("nuovoNome") String nuovoNome, @RequestParam("nuovoCognome") String nuovoCognome,
			@RequestParam("nuovaCitta") String nuovaCitta,
			@RequestParam("nuovaDataDiNascita")  Integer nuovaDataDiNascita, @RequestParam("nuovaDataDiMorte") Integer nuovaDataDiMorte
			) {
		Artista s = this.findById(id);
		s.setNome(nuovoNome);
		s.setCognome(nuovoCognome);
		s.setCitta(nuovaCitta);
		s.setDataDiNascita(nuovaDataDiNascita);
		s.setDataDiMorte(nuovaDataDiMorte);
	
		this.save(s);	
	}
	
	@Transactional
    public Artista findByUser(User user) {
        return artistaRepository.findByUser(user);
    }
	
	 @Transactional
		public void creaArtista(@ModelAttribute("artista") Artista artista, BindingResult bindingResult,
				@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
		 this.save(artista);
			Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
			User user = credenziali.getUser();
			artista.setUser(user);
			this.save2(artista, imageFile);	
		}

}
