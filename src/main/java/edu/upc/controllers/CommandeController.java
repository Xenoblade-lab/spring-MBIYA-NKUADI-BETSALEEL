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

import edu.upc.models.dtos.CommandeDto;
import edu.upc.services.PlatService;
import edu.upc.services.ClientService;
import edu.upc.services.CommandeService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/commandes")
public class CommandeController {

	@Autowired
	private CommandeService service;

	@Autowired
	private ClientService clientService;

	@Autowired
	private PlatService platService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping
	public String list(Model model) {
		model.addAttribute("rows", service.get());
		return "commandes-view";
	}

	@GetMapping("/create")
	public String createForm(Model model) {
		prepareForm(model, new CommandeDto());
		return "commandes-form";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("form") CommandeDto form, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			prepareForm(model, form);
			return "commandes-form";
		}
		service.create(form, result);
		redirect.addFlashAttribute("message",
				messageSource.getMessage("message.commande", null, LocaleContextHolder.getLocale()));
		return "redirect:/commandes";
	}

	@GetMapping("/{id}/delete")
	public String delete(@PathVariable("id") long id, RedirectAttributes redirect) {
		service.delete(id);
		redirect.addFlashAttribute("message",
				messageSource.getMessage("message.deleted", null, LocaleContextHolder.getLocale()));
		return "redirect:/commandes";
	}

	private void prepareForm(Model model, CommandeDto form) {
		model.addAttribute("form", form);
		model.addAttribute("clients", clientService.get());
		model.addAttribute("plats", platService.get());
	}

}
