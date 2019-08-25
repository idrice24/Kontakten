package com.auel.kontakten.web.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.auel.kontakten.dao.KontaktDao;
import com.auel.kontakten.model.Kontakt;
import com.auel.kontakten.model.KontaktForm;
import com.auel.kontakten.model.Sex;
import com.auel.kontakten.model.SuchForm;

@Controller

public class KontaktPageController {
	private static final Logger logger = LoggerFactory.getLogger(KontaktPageController.class);
   
	@Autowired
	private KontaktDao kontaktDao;
	private int pageNo=-1; //save dynamically the number of element
	
	
	@RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET) 
	public String getHomePage(@RequestParam(required=false, defaultValue = "3") int pageSize,
			Model model){   
		long max = kontaktDao.count();
		
		if(pageSize<2) {
			model.addAttribute("errorMessage", "Page-size muss grosser als ein");
		}
		/*if(max<=0) {
			model.addAttribute("errorMessage", "Sie haben noch keine Kontakt hingefuegt");
		}*/
		if (pageSize <= max) {
			pageNo = 0;
		}
		Pageable firstPageWithTwoElements = PageRequest.of(0, pageSize);   	
     	    
		Page<Kontakt> kontakten = kontaktDao.findAll(firstPageWithTwoElements);
		model.addAttribute("kontakten", kontakten);		
		return "list";
    }
	
	@RequestMapping(value = "/next", method = RequestMethod.GET) 
	public String getNextPage(@RequestParam(required=false, defaultValue = "3") int pageSize,
			Model model){   
		long max = kontaktDao.count();
		Pageable firstPageWithTwoElements;
		
		if(pageSize<2) {
			model.addAttribute("errorMessage", "Page-size muss grosser als ein");
		}
		if(max<=0) {
			model.addAttribute("errorMessage", "Sie haben noch keine Kontakt hingefuegt");
		}
		
        if(pageSize*pageNo< max && pageNo < (max/pageSize)) 
        {	
        	++pageNo;
        	firstPageWithTwoElements = PageRequest.of(pageNo, pageSize);   	
        }
        else 
        {
        	firstPageWithTwoElements = PageRequest.of(pageNo, pageSize);
        	if(pageNo > 0) 
        	{
        	   pageNo=(int) (max/pageSize);;
        	}
        }
        
		Page<Kontakt> kontakten = kontaktDao.findAll(firstPageWithTwoElements);
		model.addAttribute("kontakten", kontakten);
		logger.info("methode getKontakten.");
		return "list";
    }
	
	@RequestMapping(value = "/prev", method = RequestMethod.GET) 
	public String getPreviousPage(@RequestParam(required=false, defaultValue = "3") int pageSize,
			Model model){   
		long max = kontaktDao.count();
		Pageable firstPageWithTwoElements;
		
		if(pageSize<2) {
			model.addAttribute("errorMessage", "Page-size muss grosser als ein");
		}
		if(max<=0) {
			model.addAttribute("errorMessage", "Noch Kein Kontakt oder Nicht Mehr Als Das ");
		}
		
        if(pageSize*pageNo <= max && pageNo > 0) 
        {	
    		--pageNo;
        	firstPageWithTwoElements = PageRequest.of(pageNo, pageSize);       
        }
        else 
        {
        	if(pageNo < 0) 
        	{
        	    pageNo=0;
        	}
        	firstPageWithTwoElements = PageRequest.of(pageNo, pageSize);		
        }
        
		Page<Kontakt> kontakten = kontaktDao.findAll(firstPageWithTwoElements);
		model.addAttribute("kontakten", kontakten);
		logger.info("methode getKontakten.");
		return "list";
    }
	
	
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String saveKontakt(@ModelAttribute("kontaktForm") KontaktForm kontaktForm,
			BindingResult result, Model model){

		if(kontaktForm.getEmail().length() < 5 || result.hasErrors()) 
		{
			logger.error("Validation errors in the submitting form.");
			model.addAttribute("errorMessage", "Ungueldtigen Eingaben. Geben Sie kontakt Daten erneuert");
			return "form";
		}
		else 
		{
		   Kontakt  kontakt = new Kontakt();
		   kontakt.setName(kontaktForm.getName());
		   kontakt.setEmail(kontaktForm.getEmail());
		   kontakt.setTelefon(kontaktForm.getTelefon());
		   kontakt.setSex(kontaktForm.getSex());
		   if(kontaktForm.getId() != 0){		
		   kontakt.setId(kontaktForm.getId());		
		   }
	       kontaktDao.save(kontakt);
	       logger.info( kontaktForm.toString() + "has been successfully submitted.");
		   return "redirect:/list";
		}
	}
	
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String showKontaktForm(Model model){	
		     model.addAttribute("kontaktForm",new KontaktForm());
			return "form";
	}
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteKontakt(@RequestParam(name ="id") int id) {
		kontaktDao.deleteById(id);
		logger.info(id + "Geloescht");
	    return "redirect:/list";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editKontakt(@RequestParam(name="id") int id, Model model) {	
		Kontakt kontakt = kontaktDao.findById(id).get();
		KontaktForm  kontaktForm = new KontaktForm();
		kontaktForm.setName(kontakt.getName());
		kontaktForm.setEmail(kontakt.getEmail());
	    kontaktForm.setTelefon(kontakt.getTelefon());
	    kontaktForm.setSex(kontakt.getSex());
	    kontaktForm.setId(kontakt.getId());
	    logger.info("findbyId ok in editKontakt " + kontaktForm.toString());
	    model.addAttribute("kontaktForm", kontaktForm);
	    return "form";
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET) //get the find url
	public String findForm(Model model){	
		    model.addAttribute("suchForm", new SuchForm());
			return "find";
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public String findKontakt(@ModelAttribute("suchForm") SuchForm suchForm, Model model){
		logger.info("You search "+suchForm.getSearchValue()+ " " + suchForm.getSearchOption());
		  logger.info("Name valus for search submitted.");
		if(suchForm.getSearchValue().isEmpty() || suchForm.getSearchValue() == null) 
		{	
			 logger.error("No search submitted.");
			model.addAttribute("errorMessage", "Geben Sie ein Such-Wert und wählen Sie ein Option");
			return "find";
		}
		else 
		{
			
		List <Kontakt> kontaktFound = null;
		   if(suchForm.getSearchOption().equals("name"))    
		   { 
			   logger.info("Name value for search submitted.");
			   kontaktFound = kontaktDao.findByName(suchForm.getSearchValue());
		   }
		   else if(suchForm.getSearchOption().equals("sex"))
		   {
			   logger.error("Sex value for search submitted.");
			   Sex sex =  Sex.valueOf(suchForm.getSearchValue());
			   kontaktFound = kontaktDao.findBySex( sex );
		   }
	       logger.info("Search has been successfully submitted.");
	       if(kontaktFound.size()==0 || kontaktFound==null) 
	       {
	           model.addAttribute("message", "Kein Passenden Kontakt Gefunden");
	       }
	       else
	       {
	    	   model.addAttribute("kontakten", kontaktFound); 
	       }    
		   return "result";
		}
	}
	
	
}
  
