package org.marketplace.springwebapp.controllers;

import jakarta.validation.Valid;
import org.marketplace.springwebapp.dao.ProductsDao;
import org.marketplace.springwebapp.models.Product;
import org.marketplace.springwebapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("marketplace-spring-webapp/products")
public class ProductController {
    private final ProductsDao productsDao;

    @Autowired
    public ProductController(ProductsDao productsDao) {
        this.productsDao = productsDao;
    }

    @GetMapping()
    public String index (Model model) {
        model.addAttribute("products", productsDao.index());
        return "products/index";
    }
    @GetMapping("/{id}")
    public static String showProduct(@PathVariable("id") int id, Model model) {
        return "products/{id}";
    }
    @GetMapping("/new")
    public String newProduct(@ModelAttribute("product") Product product) {
        return "product/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product/new";
        }
        productsDao.save(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productsDao.showProduct(id));
        return "products/edit";
    }

    @PatchMapping("/{id}/update")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("product") @Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "products/edit";
        }
        productsDao.update(id, product);
        return "redirect:/products";
    }

    @DeleteMapping("/{id}/destroy")
    public String destroy(@PathVariable("id") int id) {
        productsDao.destroy(id);
        return "redirect:/products";
    }
}
