package orderProcessingSystem.repository;

import orderProcessingSystem.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GoodRepository extends JpaRepository<Good, Long> {

    Good findByNazva(String nazva);

    Good findByArticle(String article);

    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN TRUE ELSE FALSE END FROM Good g WHERE g.nazva = :nazva AND g.id <> :id")
    boolean existsGoodByNazvaAndNotId(@Param("nazva") String nazva, @Param("id") long id);


    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN TRUE ELSE FALSE END FROM Good g WHERE g.article = :article AND g.id <> :id")
    boolean existsGoodByArticleAndNotId(@Param("article") String article, @Param("id") long id);

}