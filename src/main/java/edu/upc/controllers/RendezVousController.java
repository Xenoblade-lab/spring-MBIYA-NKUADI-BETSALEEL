package edu.upc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.upc.models.dtos.RendezVousDto;
import edu.upc.services.PatientService;
import edu.upc.services.MedecinService;
import edu.upc.services.RendezVousService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/rendezvous")
public class RendezVousController {

	@Autowired
	private RendezVousService service;

	@Autowired
	private MedecinService medecinService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping
	public String list(Model model) {
		model.addAttribute("rows", service.get());
		return "rendezvous-view";
	}

	@GetMapping("/create")
	public String createForm(Model model) {
		prepareForm(model, new RendezVousDto());
		return "rendezvous-form";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("form") RendezVousDto form, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			prepareForm(model, form);
			return "rendezvous-form";
		}
		service.create(form, result);
		redirect.addFlashAttribute("message",
				messageSource.getMessage("message.rendezvous", null, LocaleContextHolder.getLocale()));
		return "redirect:/rendezvous";
	}

	@GetMapping("/{id}/delete")
	public String delete(@PathVariable("id") long id, RedirectAttributes redirect) {
		service.delete(id);
		redirect.addFlashAttribute("message",
				messageSource.getMessage("message.deleted", null, LocaleContextHolder.getLocale()));
		return "redirect:/rendezvous";
	}

	private void prepareForm(Model model, RendezVousDto form) {
		model.addAttribute("form", form);
		model.addAttribute("medecins", medecinService.get());
		model.addAttribute("patients", patientService.get());
	}

}
