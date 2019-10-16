package com.sasms.ui.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sasms.security.SecurityService;
import com.sasms.service.UserService;
import com.sasms.shared.dto.UserDetailDto;
import com.sasms.validator.UserValidator;

//@Slf4j
@Controller
@RequestMapping("/userController")
public class UserControllerWeb {

	@Autowired
	private UserService userService;
	@Autowired
	private SecurityService SecurityService;
	@Autowired
	private UserValidator userValidator;
	
	private static final org.slf4j.Logger logg = org.slf4j.LoggerFactory.getLogger(UserControllerWeb.class);
	
	@GetMapping(value = "/registration")
	public String registration(Model model) {
		model.addAttribute("userForm", new UserDetailDto());	
		return "registration";
	}	

	@PostMapping(value = "registration")
	public String registration(@ModelAttribute("userForm") UserDetailDto userForm, BindingResult bindingResult, Model model) {
		userValidator.validate(userForm, bindingResult);
		if(bindingResult.hasErrors()) {
			return "registration";
		}
		userService.save(userForm);
		SecurityService.autoLogin(userForm.getEmail(), userForm.getPassword());		
		return "redirect:/welcome";
	}
	
	@GetMapping(value = "/login")
	public String login(Model model, String error, String logout) {		
		if(error!=null) {
			model.addAttribute("error", "Your email and password is invalid");
		}
		if(logout!=null) {
			model.addAttribute("message", "You have been logout successfully");
		}
		logg.info("login -> {}");
		return "login";
	}
	
	@GetMapping(value = {"/", "welcome"})
	public String welcome(Model model) {
		return "welcome";
	}
	
}
