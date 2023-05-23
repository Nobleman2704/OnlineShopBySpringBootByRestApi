package uz.jk.onlineshopbyspringboot.domain.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderCreateDto {
    private UUID id;
    private UUID ownerId;
    private int amount;

}
