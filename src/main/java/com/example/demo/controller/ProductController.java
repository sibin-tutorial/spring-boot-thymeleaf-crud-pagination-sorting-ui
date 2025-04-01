package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    
    private final ProductService productService;
    
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping
    public String listProducts(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "sortField", defaultValue = "name") String sortField,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(name = "keyword", required = false) String keyword) {
        
        Page<Product> pageProducts = productService.listAll(page, sortField, sortDir, keyword);
        List<Product> listProducts = pageProducts.getContent();
        
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("totalPages", pageProducts.getTotalPages());
        model.addAttribute("totalItems", pageProducts.getTotalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("keyword", keyword);
        
        return "products/list-products";
    }
    
    @GetMapping("/new")
    public String showNewProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("pageTitle", "Add New Product");
        return "products/add-product";
    }
    
    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            return "products/add-product";
        }
        
        productService.save(product);
        redirectAttributes.addFlashAttribute("message", "The product has been saved successfully.");
        return "redirect:/products";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.get(id);
        model.addAttribute("product", product);
        model.addAttribute("pageTitle", "Edit Product (ID: " + id + ")");
        return "products/edit-product";
    }
    
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                              @Valid @ModelAttribute("product") Product product,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            return "products/edit-product";
        }
        
        product.setId(id);
        productService.save(product);
        redirectAttributes.addFlashAttribute("message", "The product has been updated successfully.");
        return "redirect:/products";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        productService.delete(id);
        redirectAttributes.addFlashAttribute("message", "The product has been deleted successfully.");
        return "redirect:/products";
    }
}
