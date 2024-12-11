package orderProcessingSystem.controllers;

import orderProcessingSystem.entity.User;
import orderProcessingSystem.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        super();
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "Register";
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        model.addAttribute("user", new User());
        return "Login";
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute("user") User user) {
        try{
            userService.saveUser(user);
        }
        catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Register";
        }
        return "redirect:/auth/login";
    }
}