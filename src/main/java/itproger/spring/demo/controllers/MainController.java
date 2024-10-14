package itproger.spring.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller // This annotation indicates that this class is a controller.
public class MainController {
    @GetMapping("/") // This annotation indicates that the method will handle the GET request for the root URL.
    public String home(Model model) { // При переходе на главную страницу будет вызван метод home.
        // параметр model - это объект, который передает данные в представление. Он принимается всегда.
        model.addAttribute("title", "Main page"); // Указываем какие данные передаем в представление.
        //  передаем в представление данные с ключом title и значением Main page.
        // Т.е. передаем определенные данные внутрь шаблона.
        // А дальше возвращаем шаблон по имени home.html со значением title.
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About Us");
        return "about";
    }

}