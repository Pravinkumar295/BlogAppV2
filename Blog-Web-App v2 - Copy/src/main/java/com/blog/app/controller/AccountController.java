package com.blog.app.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blog.app.models.Account;
import com.blog.app.service.AccountService;
import com.blog.app.service.EmailService;

import jakarta.validation.Valid;
import utilConstants.email.EmailDetails;

@Controller
public class AccountController {
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private AccountService accountService;
	
	@Value("${password.token.reset.timeout.minutes}")
	private int password_token_timeout;
	
	@Value("${site.domain}")
	private String site_domain;

	@GetMapping("/register")
	public String register(Model model) {
		Account account = new Account();
		model.addAttribute("account", account);
		return "account_views/register";
	}

	@PostMapping("/register")
	public String register_user(@Valid @ModelAttribute Account account, BindingResult result) {
		if (result.hasErrors()) {
			return "account_views/register";
		}

		accountService.save(account);
		return "redirect:/";
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "account_views/login";
	}

	@GetMapping("/profile")
	@PreAuthorize("isAuthenticated()")
	public String profile(Model model, Principal principal) {
		String authUser = "email";
		if (principal != null) {
			authUser = principal.getName();
		}
		Optional<Account> optionalAccount = accountService.findOneByEmail(authUser);
		if (optionalAccount.isPresent()) {
			Account account = optionalAccount.get();
			model.addAttribute("account", account);
			model.addAttribute("photo", account.getPhoto());
			return "account_views/profile";
		} else {
			return "redirect:/error";
		}

	}

	@PostMapping("/profile")
	@PreAuthorize("isAuthenticated()")
	public String post_profile(@Valid @ModelAttribute Account account, BindingResult bindingResult,
			Principal principal) {
		if (bindingResult.hasErrors()) {
			return "account_views/profile";
		}
		String authUser = "email";
		if(principal!=null) {
			authUser = principal.getName();
		}
		Optional<Account> optionalAccount = accountService.findOneByEmail(authUser);
		if(optionalAccount.isPresent()) {
			Account account_by_id =accountService.findById(account.getId()).get();
			account_by_id.setAge(account.getAge());
			account_by_id.setDate_of_birth(account.getDate_of_birth());
			account_by_id.setFirstName(account.getFirstName());
			account_by_id.setLastName(account.getLastName());
			account_by_id.setGender(account.getGender());
			account_by_id.setPassword(account.getPassword());
			
			accountService.save(account_by_id);
			SecurityContextHolder.clearContext();
			return "redirect:/";
		}
		else {
			return "redirect:?error";
		}
	}
	
	@GetMapping("/forgot-password")
	public String forgotPassword(Model model){
		return "account_views/forgot-Password";
	}
	
	@PostMapping("/reset-password")
	public String reset_password(@RequestParam("email") String _email, RedirectAttributes attributes,Model model ) {
		Optional<Account> optionalAccount =accountService.findOneByEmail(_email);
		if(optionalAccount.isPresent()) {
			Account account = optionalAccount.get();
			String reset_token = UUID.randomUUID().toString();
			account.setPassword_reset_token(reset_token);
			account.setPassword_reset_token_expiry(LocalDateTime.now().plusMinutes(password_token_timeout));
			accountService.save(account);
			String reset_message = "This is the reset password link:"+site_domain+"change-password?token="+reset_token;
			EmailDetails emailDetails = new EmailDetails(account.getEmail(),reset_message,"Reset password demo");
			if(emailService.sendSimpleEmail(emailDetails)==false) {
				attributes.addFlashAttribute("error" ,"error while sending email contact admin");
				return "redirect:/forgot-password";
			}
			
			attributes.addFlashAttribute("message", "Password reset email sent");
			return "redirect:/login";
		}
		else {
			attributes.addFlashAttribute("error", "No user found with the email supplied");
			return "redirect:/forgot-password";
		}
	}
	
	@GetMapping("/emailSender")
	public String emailsender() {
		return "emailSender";
	}
	
	@PostMapping("/sendEmail")
	public String send_email(@RequestParam("email") String _email, RedirectAttributes attributes,Model model ) {
		String email = _email;
		String message = "Hello World";
		String demo = "email demo";
		
			EmailDetails emailDetails = new EmailDetails(email,message,demo);
			if(emailService.sendSimpleEmail(emailDetails)==true) {
				attributes.addFlashAttribute("message", "Password reset email sent");
				return "redirect:/login";
			}
			else {
				attributes.addFlashAttribute("error" ,"error while sending email contact admin");
				return "redirect:/forgot-password";
			}
		}
	}
	
	/*
	 * @GetMapping("/change-password") public String changePassword(Model
	 * model,@RequestParam("token") String token ,RedirectAttributes attributes) {
	 * Optional<Account> optionalAccount = accountService.findByToken(token);
	 * if(optionalAccount.isPresent()) { long account_id =
	 * optionalAccount.get().getId(); LocalDateTime now = LocalDateTime.now();
	 * if(now.isAfter(optionalAccount.get().getPassword_reset_token_expiry())) {
	 * attributes.addFlashAttribute("error", "Token expired"); return
	 * "redirect:/forgot-password"; } model.addAttribute("accoun_id", account_id);
	 * return "account_views/change-password"; }
	 * attributes.addFlashAttribute("error", "Invalid token"); return
	 * "redirect:/forgot-password"; }
	 */










