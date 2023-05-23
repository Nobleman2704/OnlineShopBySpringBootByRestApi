package uz.jk.onlineshopbyspringboot.domain.entity.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import uz.jk.onlineshopbyspringboot.domain.entity.BaseEntity;
import uz.jk.onlineshopbyspringboot.domain.entity.product.ProductEntity;
import uz.jk.onlineshopbyspringboot.domain.entity.user.UserEntity;

@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class OrderEntity extends BaseEntity {

    @Column(name = "product_amount")
    private int amount;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "product_id")
    private ProductEntity products;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity users;
}
