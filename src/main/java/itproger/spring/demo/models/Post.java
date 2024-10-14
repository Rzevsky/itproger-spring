package itproger.spring.demo.models;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity // This annotation indicates that this class is an entity (модель).
public class Post { // Класс (модель, entity) отвечает за хранение всех статей. За определенную таблицу внутри базы данных.
    // Таблица Post будет создана в БД автоматически при запуске приложения.
    // Тем не менее, для обработки запросов к БД, необходимо создать интерфейс: PostRepository.
    // Для каждой сущности (модели) - в БД надо создать свой репозиторий. Т.к. он включает в себя все методы для работы с сущностью.

    @Id // This annotation indicates that this field is the primary key.
    @GeneratedValue(strategy = GenerationType.AUTO)
    // This annotation indicates that the field is automatically     generated.
    // The strategy parameter indicates the generation strategy.
    private Long id; // Идентификатор статьи. Не надо менять ни Long, ни 'id' - это стандарт.

    @Lob
    private String full_text;
    private String title, anons; // Заголовок, анонс и полный текст статьи.
    private int views; // Количество просмотров статьи.

    public Post() { // Конструктор без параметров. Необходим для создания объекта класса Post.
        // itProger говорит что без его объявления не будет работать.
        // Хотя я слышал что конструктор без параметров создается автоматически.
    }

    // Конструктор с параметрами. Необходим для создания объекта класса Post.
    public Post(String title, String anons, String full_text) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
    }
}
