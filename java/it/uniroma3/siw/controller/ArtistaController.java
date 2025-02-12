package it.uniroma3.siw.controller;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.ArtistaService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.validator.ArtistaValidator;

import jakarta.validation.Valid;

@Controller
public class ArtistaController {

	@Autowired
	private ArtistaService artistaService;
	@Autowired
	private CredentialsService credentialsService;
	@Autowired
	private ArtistaValidator artistaValidator;
	@Autowired
	private GlobalController globalController;
	
	 @GetMapping("/paginaArtisti")
	    public String paginaArtisti(Model model) {
	        model.addAttribute("artisti", this.artistaService.findAll());
	        return "paginaArtisti.html";
	    }
	 
	 @GetMapping("/artista2")
	    public String pagina(Model model) {
	        model.addAttribute("artista", new Artista());
	        return "artista2.html";
	    }
	 
	 @GetMapping("/admin/modificaArtista")
	    public String aggiornaArtista(Model model) {
	        Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
	        User utente = credenziali.getUser();
	        model.addAttribute("artista", this.artistaService.findByUser(utente));

	        return "admin/formModificaArtista.html";
	    }
	 
	  @PostMapping("/admin/formModificaArtista")
	    public String modificaartista(@RequestParam("id") Long id,
				@RequestParam("nuovoNome") String nuovoNome,
				@RequestParam("nuovoCognome") String nuovoCognome,
				@RequestParam("nuovaCitta") String nuovaCitta,
				@RequestParam("nuovaDataDiNascita")  Integer nuovaDataDiNascita, 
				@RequestParam("nuovaDataDiMorte") Integer nuovaDataDiMorte,
			
			 Model model) {
	        this.artistaService.modifica(id,nuovoNome,nuovoCognome,nuovaCitta,nuovaDataDiNascita,nuovaDataDiMorte);
	        return "/admin/indexAdmin";
	    }
	  
		@GetMapping("/artista{id}")
		public String getSingolaArtista(@PathVariable("id")Long id,Model model) {
			model.addAttribute("artista", this.artistaService.findById(id));
			return "artista.html";
		}
		
		 @GetMapping("/formCreaArtista")
		    public String formCreaArtista(Model model) {
		        model.addAttribute("artista", new Artista());
		        return "admin/formCreaArtista.html";
		    }
		 
		  @PostMapping("/admin/formCreaArtista")
		    public String creaArtista(@Valid @ModelAttribute("artista") Artista artista, 
		                              BindingResult bindingResult,
		                              @RequestParam("imageFile") MultipartFile imageFile, 
		                              Model model) throws IOException {
		        this.artistaValidator.validate(artista, bindingResult);
		        if (!bindingResult.hasErrors()) {
		            this.artistaService.creaArtista(artista, bindingResult, imageFile);
		            return "/admin/indexAdmin";
		        } else {
		            return "admin/formCreaOpera";
		        }
		    }
		    
		 
		  @GetMapping("/admin/profiloArtista")
		    public String profiloArtista(Model model) {
		        Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
		        User utente = credenziali.getUser();
		        Artista artista1 = this.artistaService.findByUser(utente);
		        if (artista1 != null) {
		            model.addAttribute("artista", this.artistaService.findByUser(utente));
		            return "admin/profiloArtista.html";
		        } else {
		            model.addAttribute("artista", new Artista());
		            return "admin/formCreaArtista.html";
		        }
		    }
		    
}
