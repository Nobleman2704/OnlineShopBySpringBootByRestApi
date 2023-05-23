package uz.jk.onlineshopbyspringboot.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jk.onlineshopbyspringboot.dao.history.HistoryDao;
import uz.jk.onlineshopbyspringboot.dao.order.OrderDao;
import uz.jk.onlineshopbyspringboot.dao.product.ProductDao;
import uz.jk.onlineshopbyspringboot.dao.user.UserDao;
import uz.jk.onlineshopbyspringboot.domain.dto.order.OrderCreateDto;
import uz.jk.onlineshopbyspringboot.domain.entity.history.HistoryEntity;
import uz.jk.onlineshopbyspringboot.domain.entity.order.OrderEntity;
import uz.jk.onlineshopbyspringboot.domain.entity.order.OrderStatus;
import uz.jk.onlineshopbyspringboot.domain.entity.product.ProductEntity;
import uz.jk.onlineshopbyspringboot.domain.entity.user.UserEntity;
import uz.jk.onlineshopbyspringboot.domain.exception.DataAlreadyException;
import uz.jk.onlineshopbyspringboot.domain.exception.DataNotFoundException;
import uz.jk.onlineshopbyspringboot.domain.response.BaseResponse;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final UserDao userDao;
    private final HistoryDao historyDao;

    @Override
    public BaseResponse<OrderEntity> create(OrderCreateDto orderCreateDto) {
        int amount = orderCreateDto.getAmount();
        UUID productId = orderCreateDto.getId();
        UUID ownerId = orderCreateDto.getOwnerId();
        int status;
        String message;
        OrderEntity orderEntity = null;
        boolean check = orderDao.existsOrderEntitiesByUsersIdAndProductsId(ownerId, productId);
        if (check) {
            throw new DataAlreadyException("You have this product already");
        } else {
            ProductEntity product = productDao.findById(productId).orElseThrow(
                    () -> new DataNotFoundException("there is no product by this id:" + ownerId));
            UserEntity user = userDao.findById(ownerId).orElseThrow(
                    () -> new DataNotFoundException("there is no user by this id:" + ownerId));
            if (product.getUsers().getId().equals(ownerId)) {
                status = 401;
                message = "You can not order your product";
            } else {
                orderEntity = OrderEntity.builder()
                        .amount(amount)
                        .status(OrderStatus.ORDERED)
                        .users(user)
                        .products(product)
                        .build();
                orderDao.save(orderEntity);

                status = 200;
                message = "Successfully added to order list";
            }
        }
        return BaseResponse.<OrderEntity>builder()
                .status(status)
                .data(orderEntity)
                .message(message)
                .build();
    }

    @Override
    public OrderEntity findById(UUID id) {
        return null;
    }

    @Override
    public BaseResponse<OrderEntity> delete(UUID id) {
        try {
            orderDao.deleteById(id);
            return BaseResponse.<OrderEntity>builder()
                    .status(200)
                    .message("deleted")
                    .build();
        } catch (NoSuchElementException e) {
            throw new DataNotFoundException("there is no order by this id: " + id);
        }
    }

    @Override
    public BaseResponse<List<OrderEntity>> findMyOrdersById(UUID userId, int page) {
        Pageable pageRequest = PageRequest.of(page, 5);
        try {
            Page<OrderEntity> orderEntitiesPage = orderDao.findOrderEntitiesByUsersId(userId, pageRequest);
            int totalPages = orderEntitiesPage.getTotalPages();
            return BaseResponse.<List<OrderEntity>>builder()
                    .status(200)
                    .message(orderEntitiesPage.getTotalElements() + " result(s) found")
                    .totalPages((totalPages == 0) ? 0 : totalPages - 1)
                    .data(orderEntitiesPage.getContent())
                    .build();
        } catch (NoSuchElementException e) {
            throw new DataNotFoundException("there is no product by this user id: " + userId);
        }
    }

    @Override
    public BaseResponse<OrderEntity> changeOrderAmount(UUID orderId, int amount) {
        if (amount == 0) {
            orderDao.deleteById(orderId);
            return BaseResponse.<OrderEntity>builder()
                    .status(200)
                    .message("Order deleted")
                    .build();
        }
        OrderEntity orderEntity = orderDao.findById(orderId).orElseThrow(
                () -> new DataNotFoundException("there is no order by this id:" + orderId));
        orderEntity.setAmount(amount);
        orderDao.save(orderEntity);
        return BaseResponse.<OrderEntity>builder()
                .status(200)
                .message("amount changed")
                .data(orderEntity)
                .build();
    }

    @Override
    public BaseResponse<List<OrderEntity>> getSellerOrders(UUID userId, int page) {
        Pageable pageRequest = PageRequest.of(page, 5);
        try {
            Page<OrderEntity> orderEntitiesPage = orderDao.findOrderEntitiesByProductsUsersId(userId, pageRequest);
            int totalPages = orderEntitiesPage.getTotalPages();
            return BaseResponse.<List<OrderEntity>>builder()
                    .status(200)
                    .message(orderEntitiesPage.getTotalElements() + " result(s) found")
                    .totalPages((totalPages == 0) ? 0 : totalPages - 1)
                    .data(orderEntitiesPage.getContent())
                    .build();
        } catch (Exception e) {
            throw new DataNotFoundException("there is no order by this user id: " + userId);
        }
    }

    @Override
    public BaseResponse<OrderEntity> changeOrderStatus(UUID orderId, OrderStatus status) {
        OrderEntity orderEntity;
        orderEntity = orderDao.findById(orderId).orElseThrow(
                () -> new DataNotFoundException("there is no order by this id:" + orderId));

        orderEntity.setStatus(status);
        if (Objects.equals(status.toString(), "DELIVERED")) {
            ProductEntity product = orderEntity.getProducts();
            HistoryEntity history = HistoryEntity.builder()
                    .productCategory(product.getCategory())
                    .productPrice(product.getPrice())
                    .productDescription(product.getDescription())
                    .productName(product.getName())
                    .sellerName(product.getUsers().getName())
                    .totalAmount(orderEntity.getAmount())
                    .users(orderEntity.getUsers())
                    .build();
            historyDao.save(history);
            orderDao.deleteById(orderEntity.getId());
            return BaseResponse.<OrderEntity>builder()
                    .message("Order has been delivered")
                    .build();
        }
        orderDao.save(orderEntity);
        return BaseResponse.<OrderEntity>builder()
                .message("Order status has been changed to " + status)
                .data(orderEntity)
                .build();
    }
}
