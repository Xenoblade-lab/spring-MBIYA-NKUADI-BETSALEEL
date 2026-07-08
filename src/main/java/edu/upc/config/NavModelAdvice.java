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

	@ModelAttribute("navCategories")
	public boolean navCategories(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/categories");
	}

	@ModelAttribute("navClients")
	public boolean navClients(HttpServletRequest request) {
		String path = request.getRequestURI();
		return path.startsWith("/clients") && !path.startsWith("/clients-");
	}

	@ModelAttribute("navPlats")
	public boolean navPlats(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/plats");
	}

	@ModelAttribute("navCommandes")
	public boolean navCommandes(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/commandes");
	}

}
