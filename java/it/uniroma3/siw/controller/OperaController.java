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
import it.uniroma3.siw.model.Curatore;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.OperaService;
import it.uniroma3.siw.validator.OperaValidator;
import jakarta.validation.Valid;

@Controller
public class OperaController {
	
	@Autowired
    private OperaService operaService;
	@Autowired
	private CredentialsService credentialsService;
	@Autowired
	private OperaValidator operaValidator;
	@Autowired
	private GlobalController globalController;
	

	 @GetMapping("/paginaOpere")
	    public String paginaOpere(Model model) {
	        model.addAttribute("opere", this.operaService.findAll());
	        return "paginaOpere.html";
	    }
	 
	  @GetMapping("/opera2")
	    public String pagina(Model model) {
	        model.addAttribute("opera", new Opera());
	        return "opera2.html";
	    }
	  @GetMapping("/admin/modificaOpera")
	    public String aggiornaOpera(Model model) {
	        Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
	        User utente = credenziali.getUser();
	        model.addAttribute("opera", this.operaService.findByUser(utente));

	        return "admin/formModificaOpera.html";
	    }
	  
	  @PostMapping("/admin/formModificaOpera")
	    public String modificaopera(@RequestParam("id") Long id,
				@RequestParam("nuovoTitolo") String nuovoTitolo,
				@RequestParam("nuovoAnno") Integer nuovoAnno,
				@RequestParam("nuovaTecnica")  String nuovaTecnica, 
				@RequestParam("nuovaCollocazione") String nuovaCollocazione,
			
			 Model model) {
	        this.operaService.modifica(id,nuovoTitolo,nuovoAnno,nuovaTecnica,nuovaCollocazione);
	        return "/admin/indexAdmin";
	    }

	
	@GetMapping("/opera{id}")
	public String getSingolaOpera(@PathVariable("id")Long id,Model model) {
		model.addAttribute("opera", this.operaService.findById(id));
		return "opera.html";
	}
	  @GetMapping("/formCreaOpera")
	    public String formCreaOpera(Model model) {
	        model.addAttribute("opera", new Opera());
	        return "admin/formCreaOpera.html";
	    }

	    @PostMapping("/admin/formCreaOpera")
	    public String creaAzienda(@Valid @ModelAttribute("opera") Opera opera, 
	                              BindingResult bindingResult,
	                              @RequestParam("imageFile") MultipartFile imageFile, 
	                              Model model) throws IOException {
	        this.operaValidator.validate(opera, bindingResult);
	        if (!bindingResult.hasErrors()) {
	            this.operaService.creaOpera(opera, bindingResult, imageFile);
	            return "/admin/indexAdmin";
	        } else {
	            return "admin/formCreaOpera";
	        }
	    }
	    
	    @GetMapping("/admin/profiloOpera")
	    public String profiloOpera(Model model) {
	        Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
	        User utente = credenziali.getUser();
	        Opera opera1 = this.operaService.findByUser(utente);
	        if (opera1 != null) {
	            model.addAttribute("opera", this.operaService.findByUser(utente));
	            return "admin/profiloOpera.html";
	        } else {
	            model.addAttribute("opera", new Opera());
	            return "admin/formCreaOpera.html";
	        }
	    }
	    
//	    @GetMapping("/admin/paginaOpere/profiloOpera")
//	    public String profiloOpera2(Model model) {
//	        Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
//	        User utente = credenziali.getUser();
//	        Opera opera1 = this.operaService.findByUser(utente);
//	        if (opera1 != null) {
//	            model.addAttribute("opera", this.operaService.findByUser(utente));
//	            return "admin/profiloOpera.html";
//	      
//	    }
//	        return "admin/profiloOpera.html";
//
//	    }

}
