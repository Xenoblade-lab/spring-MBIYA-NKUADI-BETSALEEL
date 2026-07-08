package edu.upc.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class NavModelAdvice {

	@ModelAttribute("navHome")
	public boolean navHome(HttpServletRequest request) {
		return "/".equals(request.getRequestURI());
	}

	@ModelAttribute("navSpecialites")
	public boolean navSpecialites(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/specialites");
	}

	@ModelAttribute("navMedecins")
	public boolean navMedecins(HttpServletRequest request) {
		String path = request.getRequestURI();
		return path.startsWith("/medecins") && !path.startsWith("/medecins-");
	}

	@ModelAttribute("navPatients")
	public boolean navPatients(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/patients");
	}

	@ModelAttribute("navRendezVous")
	public boolean navRendezVous(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/rendezvous");
	}

}
