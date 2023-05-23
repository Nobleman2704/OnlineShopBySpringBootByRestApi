package uz.jk.onlineshopbyspringboot.domain.entity.product;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import uz.jk.onlineshopbyspringboot.domain.entity.BaseEntity;
import uz.jk.onlineshopbyspringboot.domain.entity.order.OrderEntity;
import uz.jk.onlineshopbyspringboot.domain.entity.user.UserEntity;

import java.util.List;
import java.util.UUID;

@Entity(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@DynamicUpdate
public class ProductEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String description;

    private Double price;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orderEntities;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private UserEntity users;
}
