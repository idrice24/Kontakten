package com.auel.kontakten.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.auel.kontakten.dao.KontaktDao;
import com.auel.kontakten.model.Kontakt;



@Controller
public class KontaktPageController {
	private static final Logger logger = LoggerFactory.getLogger(KontaktPageController.class);

	@Autowired
	private KontaktDao kontaktDao;

	@RequestMapping(value = {"/", "/list", "/index", "/prev", "/next"}, 
			method = RequestMethod.GET) 
	public String getHomePage(@RequestParam(required=false, defaultValue = "0") int page,
				@RequestParam(required=false, defaultValue = "0") int currentPage,
				@RequestParam(required=false, defaultValue = "4") int size,
				Model model) {   
		
			Pageable firstPageWithTwoElements = PageRequest.of(page ,size);   		     	    
			Page<Kontakt> kontakten = kontaktDao.findAll(firstPageWithTwoElements);
			Map<Kontakt, String> mapKontakten = new HashMap<Kontakt, String>();
			kontakten.stream()
		    //.filter(k -> 
			.forEach( k -> { 
			     if(k.getPhoto() != null ) {
			        mapKontakten.put(k, Base64.getEncoder().encodeToString(k.getPhoto()) );
			     } else {
			    	mapKontakten.put(k,"");
			     }
			});
			model.addAttribute("mapKontakten", mapKontakten);
			model.addAttribute("pages", new int[kontakten.getTotalPages()]);
			logger.info(new int[kontakten.getTotalPages()].toString() + "print of pages");
			model.addAttribute("size", size);
			model.addAttribute("currentPage", page);
			return "list";
    }		

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteKontakt(@RequestParam(name = "id") int id) {
			kontaktDao.deleteById(id);
			logger.info(id + "Geloescht");
			return "redirect:/list";
	}
		
	@RequestMapping(value = {"/form", "/form/{yes}"}, method = RequestMethod.GET)
	public String showKontaktForm(@PathVariable("yes") Optional<Integer> yes, Model model) {
		if (yes.isPresent()) {
			model.addAttribute("successMessage", "Kontakt wurde erfolgreich gespeichen");	
		  }
			model.addAttribute("kontaktForm", new Kontakt());
			return "form";
	}
	
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String submitKontakt(@Valid @ModelAttribute("kontaktForm") Kontakt kontaktForm, BindingResult result,
			@ModelAttribute("file") MultipartFile file,			
			Model model) throws IOException 
	{	
		if ( result.hasErrors() ) {
			logger.error("Validation errors in the submitting form.");
			//model.addAttribute("errorMessage", "error in: " + result.toString());
		     model.addAttribute("errorMessage", "error in: "+ kontaktForm.toString());
			//model.addAttribute("errorMessage", "Ungueldtigen Eingaben. Geben Sie kontakt Daten erneuert");
			return "form";
		} else {
			if( file.getBytes() != null) {
				     kontaktForm.setPhoto(file.getBytes());
			} else {	
				     logger.error("Upload-ERROR");
			    model.addAttribute("errorMessage", "Uplaod des Photos unerfolgreich");				
			    return "form";
		    }		    
			try {
				kontaktDao.save(kontaktForm);		
				logger.info(kontaktForm.toString() + "has been successfully saved.");
			} catch (Exception e) {
				logger.error("saving" + kontaktForm.toString() + "has been unsuccessfully: ", e);
			}			
		    return "redirect:/form/1";
		}
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editKontakt(@RequestParam(name = "id") int id, Model model) {
		Kontakt kontakt = kontaktDao.findById(id).get();
		logger.info("findbyId ok in editKontakt " + kontakt.toString());
		model.addAttribute("kontaktForm", kontakt);
		return "form";
	}
	
	@RequestMapping(value = {"/find", "/deleteresult"}, method = {RequestMethod.GET} )
	public String findKontakt(Model model,
			@RequestParam(name="id", required=false, defaultValue = "0") int id,
			@RequestParam(required=false, defaultValue = "0") int currentPage,
			@RequestParam(required=false, defaultValue = "0") int page,
			@RequestParam(required=false, defaultValue = "4") int size,
			@RequestParam(required=false, defaultValue = "") String searchValue) {
		 logger.info("keyword has been submitted for search.");
		 if(searchValue.isEmpty()) {
			logger.error("keyword is empty");
			model.addAttribute("errorMessageFind", "Geben Sie ein Suchwort");
			return "result";
		} else {			
			logger.info("keyword value for search submitted.");
			//List<Kontakt> kontaktFound = kontaktDao.suchen("%"+searchValue()+"%");		
			//Page<Kontakt> kontaktFound = kontaktDao.suchenPageIgnoreCase("%"+searchValue()+"%",PageRequest.of(page,size));
			if(id != 0) {
				kontaktDao.deleteById(id);
			}
			Page<Kontakt> kontaktFound = kontaktDao.suchenPageIgnoreCase("%"+searchValue+"%",PageRequest.of(page,size));  
			logger.info("Search has been successfully submitted.");
			if (kontaktFound.isEmpty()) {
				model.addAttribute("message", "Kein Passenden Kontakt Gefunden");				
			} else {
				model.addAttribute("kontakten", kontaktFound);
			    model.addAttribute("pages", new int[kontaktFound.getTotalPages()]);
		     	model.addAttribute("size", size);
		    	model.addAttribute("currentPage", page);
		    	model.addAttribute("searchValue", searchValue);
			}
			return "result";
		}
	}

}
