package orderProcessingSystem.controllers;

import orderProcessingSystem.entity.Good;
import orderProcessingSystem.entity.Order;
import orderProcessingSystem.services.GoodService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GoodTableController {

    private final GoodService serviceGood;

    public GoodTableController(GoodService serviceGood) {
        super();
        this.serviceGood = serviceGood;
    }
    
    @GetMapping("/goods")
    public String showGoods(Model model) {
        model.addAttribute("goods", serviceGood.getAllGoods());
        return "GoodTable";
    }

    @GetMapping("/goods/new")
    public String newGoodForm(Model model) {
        model.addAttribute("good", new Good());
        return "CreateGood";
    }

    @PostMapping("/goods")
    public String saveGood(@ModelAttribute("good") Good good, Model model) {
        try {
            serviceGood.addGood(good);
        }
        catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("good", good);
            return "CreateGood";
        }
        return "redirect:/goods";
    }

    @PatchMapping("/goods/edit/{id}")
    public String editGoodForm(@PathVariable Long id, Model model) {
        model.addAttribute("good", serviceGood.getGoodById(id));
        return "UpdateGood";
    }

    @PatchMapping("/goods/{id}")
    public String updateGood(@PathVariable Long id,
                             @ModelAttribute("good") Good good,
                             Model model) {

        Good existingGood = serviceGood.getGoodById(id);
        existingGood.setId(id);
        existingGood.setArticle(good.getArticle());
        existingGood.setCategory(good.getCategory());
        existingGood.setNazva(good.getNazva());
        existingGood.setPrice(good.getPrice());
        existingGood.setPriceOpt(good.getPriceOpt());
        try {
            serviceGood.updateGood(id, existingGood);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("good", existingGood);
            return "UpdateGood";
        }
        return "redirect:/goods";
    }


    @DeleteMapping("/goods/delete/{id}")
    public String deleteGood(@PathVariable Long id, Model model) {
        serviceGood.deleteGood(id);
        return "redirect:/goods";
    }

    @GetMapping("/goods/{id}/orders/new")
    public String createOrderFromGood(@PathVariable Long id, Model model) {
        Good good = serviceGood.getGoodById(id);
        model.addAttribute("good", good);
        model.addAttribute("order", new Order());
        return "CreateOrderFromGoodTable";
    }
}
