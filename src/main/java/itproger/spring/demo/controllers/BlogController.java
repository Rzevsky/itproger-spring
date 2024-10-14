package itproger.spring.demo.controllers;


import itproger.spring.demo.models.Post;
import itproger.spring.demo.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller // This annotation indicates that this class is a controller.
public class BlogController {

    // Чтобы передавать все записи в шаблон blog-main.html создадим переменную для ссылки на PostRepository - postRepository.
    // @Autowired - аннотация, которая позволяет автоматически связать переменную postRepository с нужным компонентом.
    @Autowired
    private PostRepository postRepository; // Переменная для ссылки на PostRepository.

    @GetMapping("/blog") // This annotation indicates that the method will handle the GET request for the /blog URL.
    public String blogMain(Model model) { // model - an object that passes data to the view.

        // Iterable - это интерфейс для коллекций, который позволяет перебирать элементы.
        // <Post> - указываем, что коллекция будет содержать объекты класса Post.
        // posts - переменная потребуется для передачи всех записей в шаблон blog-main.html.
        // postRepository.findAll() - получаем все записи из БД.
        // .findAll() - метод заимствован из CrudRepository.
        Iterable<Post> posts = postRepository.findAll(); // Получаем все записи из БД.

        // model - объект, который передает данные в представление. Он передан в метод blogMain в качестве параметра.
        //  в момент передачи в метод blogMain - model пустой. ???
        // addAttribute - метод, который передает данные в представление.
        // model и blog-main.html связаны путем ??????
        model.addAttribute("posts", posts); // Указываем какие данные передаем в представление (в шаблон blog-main.html в итоге).
        // Это происходит за счет метода addAttribute, который передает данные в представление.
        // "posts" - это имя переменной, а posts - значение переменной.
        // а model в свою очередь передает данные в шаблон blog-main.html потому что он связан с ним через
        return "blog-main"; // Return the blog-main.html template.
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    //    @RequestParam String title - аннотация для получения параметров из запроса. В данном случае получаем параметр title.
    //    title - не случайное имя. Оно совпадает с атрибутом в файле blog-add.html - name="title" в поле input.
    //    аналогично с остальными параметрами.
    @PostMapping("/blog/add")
    public String blogPostAdd(
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String full_text,
            Model model) {
        Post post = new Post(title, anons, full_text); // Создаем новый объект класса Post.
        postRepository.save(post); // Сохраняем объект в БД.
        return "redirect:/blog"; // Redirect to the /blog URL.
    }

    // {id} - это переменная, которая будет передаваться в метод blogDetails. Имя может быть любым.
    // В {} - потому что передается динамический параметр.
    @GetMapping("/blog/{id}")
//    (@PathVariable(value = "id") - аннотация для получения параметров из URL. В данном случае получаем параметр id.
//    в () - указывается, какой именно параметр мы хотим получить. Их может быть несколько. Берем из @GetMapping("/blog/{id}")
//    long id - указываем тип данных параметра и имя переменной которой будет передан параметр. Имя произвольное (можно не id из GetMapping).
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        //        т.е. при переходе на URL /blog/{id} будет вызван метод blogDetails, который получит параметр id.
        //        и теперь нам надо получить запись из БД по этому id.
        if (!postRepository.existsById(id)) { // Проверяем, существует ли запись с таким id.
            return "redirect:/blog"; // Если запись не существует, то перенаправляем на главную страницу.
        }

//        PostRepository.findById(id) - метод, который получает запись из БД по id.
//        Optional<Post> - Optional - это класс, который позволяет избежать NullPointerException.
//        в случае, если запись не будет найдена, то вернется пустой объект с именем post. post = Optional.empty().
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>(); // Извлекаем запись из Optional и добавляем в ArrayList.
        post.ifPresent(res::add); // Извлекаем запись из Optional и добавляем в ArrayList.
//        res::add - это ссылка на метод add класса ArrayList.
//        :: работает как указатель на метод. Т.е. указывает на метод add класса ArrayList.
//        Альтернативой была бы запись: post.ifPresent(p -> res.add(p));
//        Лямбда-выражение p -> res.add(p) - это аналогично res::add. Где p - замещает объект post.
        model.addAttribute("post", res); // Передаем запись в шаблон blog-details.html.
        return "blog-details"; // Return the blog-details.html template.


//        Это то что я сделал:
//        Post post = postRepository.findById(id).orElse(new Post()); // Получаем запись из БД по id.
//
//        model.addAttribute("post", post); // Передаем запись в шаблон blog-details.html.
//        return "blog-details"; // Return the blog-details.html template.
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) { // Проверяем, существует ли запись с таким id.
            return "redirect:/blog"; // Если запись не существует, то перенаправляем на главную страницу.
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>(); // Извлекаем запись из Optional и добавляем в ArrayList.
        post.ifPresent(res::add); // Извлекаем запись из Optional и добавляем в ArrayList.
        model.addAttribute("post", res); // Передаем запись в шаблон blog-details.html.
        return "blog-edit"; // Return the blog-edit.html template.
    }


    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(
            @PathVariable(value = "id") long id,
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String full_text,
            Model model) {

        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return "redirect:/blog";
        }

        Post post = optionalPost.get();

        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);

        postRepository.save(post);
        return "redirect:/blog/{id}";
    }

    @GetMapping("/blog/{id}/remove")
    public String blogRemove(@PathVariable(value = "id") long id, Model model) {

        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return "redirect:/blog";
        }

        Post post = optionalPost.get();

        postRepository.delete(post);

        return "redirect:/blog"; // Redirect to the /blog URL.
    }

}
