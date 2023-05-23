package uz.jk.onlineshopbyspringboot.domain.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.jk.onlineshopbyspringboot.domain.entity.product.ProductCategory;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCreateDto {
    private UUID id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @Positive
    @NotNull
    private Double price;

    @NotNull
    private ProductCategory category;

    private UUID userId;
}
