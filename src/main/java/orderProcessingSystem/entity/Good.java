package orderProcessingSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import orderProcessingSystem.enums.Category;
import org.hibernate.annotations.Check;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "goods")
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "category")
    @Check(constraints = "category IN ('ELECTRONICS', 'CLOTHING', 'FURNITURE')")
    private Category category;

    @Column(nullable = false, unique = true, length = 10, name = "article")
    @Check(constraints = "article REGEXP '[0-9]{10}'")
    private String article;

    @Column(nullable = false, unique = true, name = "nazva")
    @Check(constraints = "nazva REGEXP '^[A-Z][A-Za-z0-9 -]{2,49}$'")
    private String nazva;

    @Column(nullable = false, name = "price")
    @Check(constraints = "price >= 0.1")
    private Double price;

    @Column(nullable = false, name = "priceOpt")
    @Check(constraints = "price_opt >= 0.1")
    private Double priceOpt;

    @OneToMany(mappedBy = "good", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public Good(Category category, String article, String nazva, Double price, Double priceOpt) {
        this.category = category;
        this.article = article;
        this.nazva = nazva;
        this.price = price;
        this.priceOpt = priceOpt == null ? price : priceOpt;
    }

    public void setPriceOpt(Double priceOpt) {
        this.priceOpt = priceOpt == null ? price : priceOpt;
    }
}
