package itproger.spring.demo.repo;

import itproger.spring.demo.models.Post;
import org.springframework.data.repository.CrudRepository;

//@Repository // Аннотация не нужна, т.к. в данном случае она автоматически добавляется спригом.
public interface PostRepository extends CrudRepository<Post, Long> {
    //CrudRepository - интерфейс, который содержит все методы для работы с сущностью.
    //<Post, Long> Post - модель (entity), с которой мы работаем. Long - тип данных первичного ключа.
}
