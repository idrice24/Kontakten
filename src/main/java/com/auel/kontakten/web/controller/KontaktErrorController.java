package com.auel.kontakten.web.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class KontaktErrorController implements ErrorController{

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "/error";
	}
	
	
	
	@GetMapping("/error")
	public ModelAndView handleError(HttpServletRequest request) {
		
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    	System.out.println("Error with status code " + status + " happened. Support! Do something about it!");
	    return new ModelAndView("errorPage");
	}
	
	/*
	@RequestMapping("/error")
    @ResponseBody
	public String handleError(HttpServletRequest request) {
	      Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
	      Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
	      return String.format("<html><body><h2>Error Page</h2><div>Status code: <b>%s</b></div>"
	                      + "<div>Exception Message: <b>%s</b></div><body></html>",
	              statusCode, exception==null? "N/A": exception.getMessage());
	}
  */
}
