package com.bhl.store.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bhl.store.dao.IProdDao;
import com.bhl.store.entities.Product;


@Controller
public class ShowController {

	@Autowired
	private IProdDao prodDao;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/showProd")
	public String showAllProd(Model model) {
		List<Product> prods = prodDao.getAllProducts();
		model.addAttribute("listProds", prods);
		model.addAttribute("motCle", "");
		return "products";
	}
	
	
	@PostMapping("/searchProd")
	public String showAllProdByMc(Model model,@RequestParam(name = "motCle") String mc) {
		List<Product> prods = prodDao.getAllProductsByMc("%"+mc+"%");
		model.addAttribute("motCle", mc);
		model.addAttribute("listProds", prods);
		return "products";
	}
	
	@GetMapping("/addFormProd")
	public String addFormPage(Model model) {
		model.addAttribute("product", new Product());
		return "addFormPage";
	}
	
	@PostMapping("/addProduct")
	public String addProduct(Model model,@ModelAttribute("prod") @Valid Product p, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("product", p);
			return "addFormPage";
		}
		model.addAttribute("product", p);
		prodDao.saveProd(p);
		return "confirmation";
	}
	
	@GetMapping("/updateFormProd")
	public String updateFormPage(Model model,@RequestParam(name = "id") Long id) {
		model.addAttribute("product", prodDao.getProductById(id));
		return "updateFormPage";
	}
	
	@PostMapping("/updateProduct")
	public String updateProduct(Model model,@ModelAttribute("prod") @Valid Product p, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("product", p);
			return "updateFormPage";
		}
		model.addAttribute("product", p);
		prodDao.updateProd(p);
		return "confirmation";
	}
	
	@GetMapping("/deleteProd")
	public String deletProduct(@RequestParam(name = "id")Long id) {
		prodDao.deleteProd(id);
		return "redirect:/showProd";
	}
		
	
}
