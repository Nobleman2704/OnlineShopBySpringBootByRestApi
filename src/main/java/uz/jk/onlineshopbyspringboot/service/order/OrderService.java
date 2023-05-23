package uz.jk.onlineshopbyspringboot.service.order;

import org.springframework.stereotype.Service;
import uz.jk.onlineshopbyspringboot.domain.dto.order.OrderCreateDto;
import uz.jk.onlineshopbyspringboot.domain.entity.order.OrderEntity;
import uz.jk.onlineshopbyspringboot.domain.entity.order.OrderStatus;
import uz.jk.onlineshopbyspringboot.domain.response.BaseResponse;
import uz.jk.onlineshopbyspringboot.service.BaseService;

import java.util.List;
import java.util.UUID;

@Service
public interface OrderService extends BaseService<OrderCreateDto, OrderEntity> {
    BaseResponse<List<OrderEntity>> findMyOrdersById(UUID userId, int page);

    BaseResponse<OrderEntity> changeOrderAmount(UUID orderId, int amount);

    BaseResponse<List<OrderEntity>> getSellerOrders(UUID userId, int page);

    BaseResponse<OrderEntity> changeOrderStatus(UUID orderId, OrderStatus status);
}
