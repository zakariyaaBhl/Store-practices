package com.bhl.store.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bhl.store.dao.RoleDao;
import com.bhl.store.entities.Product;
import com.bhl.store.entities.Role;
import com.bhl.store.entities.User;
import com.bhl.store.service.UserService;
import com.bhl.store.user.CrmUser;

@Controller
public class SecurityController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleDao roleDao;
	
	@GetMapping("/showLoginPage")
	public String showLoginPage() {
		return "loginPage";
	}
	
	@GetMapping("/accessDenied")
	public String accessDenied() {
		return "accessDenied";
	}
	
	@GetMapping("/showRegistrationForm")
	public String registerForm(Model model) {
		model.addAttribute("crmUser", new CrmUser());
		return "register-page";
	}
	
	@PostMapping("/processRegistrationForm")
	public String register(Model model,@ModelAttribute("crmUser")@Valid CrmUser crmUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("crmUser", crmUser);
			return "register-page";
		}
		
		User user = userService.findByUserName(crmUser.getUserName());
		if (user != null) {
			model.addAttribute("crmUser", crmUser);
			model.addAttribute("exist", "UserName exist already, choose an other UserName please !!!");
			return "register-page";
		}
		
		userService.save(crmUser);
		return "registration-confirmation";
	}
	
	@GetMapping("/showUsers")
	public String showAllUsers(Model model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("listUsers", users);
		model.addAttribute("motCle", "");
		return "users";
	}
	
	@GetMapping("/updateFormUser")
	public String updateFormUser(Model model,@RequestParam(name = "id") Long id) {
		User user = userService.findUserById(id);
		Collection<Role> listRoles = user.getRoles();
		
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("user", user);
		return "updateFormUser";
	}
	
	@PostMapping("/updateUser")
	public String updateUser(Model model,@ModelAttribute("userU")@Valid User user, BindingResult bindingResult, 
			@RequestParam(name = "roleUser", defaultValue = "") String role) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("user", user);
			return "updateFormUser";
		}
		
//		Role roleToAdd = roleDao.findRoleByName(role);
//		System.out.println(roleToAdd.getName());
//		
//		user.getRoles().add(roleToAdd);
		
//		Collection<Role> listRoles = user.getRoles();
//		listRoles.add(roleToAdd);
//		System.out.println(listRoles.size());
//		
//		for (Role r : listRoles) {
//			System.out.println(r.getName());
//		}
		
		userService.updateUser(user, role);
		model.addAttribute("user", user);
		return "update-confirmation";
	}
	
	@GetMapping("/deleteUser")
	public String deleteUser(Long id) {
		userService.deleteUser(id);
		return "redirect:/showUsers";
	}
	
	@PostMapping("/searchUser")
	public String showAllUsersByMc(Model model,@RequestParam(name = "motCle") String mc) {
		if (mc != null) {
			List<User> listUsers = userService.getAllUsersByMc("%"+mc+"%");
			model.addAttribute("listUsers", listUsers);
			model.addAttribute("motCle", mc);
			return "users";
		}
		
		List<User> listUsers = userService.getAllUsers();
		model.addAttribute("listUsers", listUsers);
		return "users";
	}
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
}
