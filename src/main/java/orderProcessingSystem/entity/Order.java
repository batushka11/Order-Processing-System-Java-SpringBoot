package orderProcessingSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import orderProcessingSystem.enums.Manager;
import org.hibernate.annotations.Check;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "receiver")
    @Check(constraints = "receiver REGEXP '^[A-Z][a-z]+\s[A-Z][a-z]+$'")
    private String receiver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "manager")
    @Check(constraints = "manager IN ('IVANYK', 'PETRUK', 'JANIUK')")
    private Manager manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "good")
    private Good good;

    @Column(nullable = false, name = "count")
    @Check(constraints = "count >= 1")
    private Integer count;

    @Column(nullable = false, name = "price_order")
    @Check(constraints = "price_order >= 0")
    private double priceOrder;

    @Column(nullable = false, name = "create_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createTime;

    @Column(name = "send_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime sendTime;

    @Column(name = "take_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime takeTime;
}
