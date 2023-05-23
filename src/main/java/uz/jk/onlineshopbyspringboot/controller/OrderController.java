package uz.jk.onlineshopbyspringboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jk.onlineshopbyspringboot.domain.dto.order.OrderCreateDto;
import uz.jk.onlineshopbyspringboot.domain.entity.order.OrderEntity;
import uz.jk.onlineshopbyspringboot.domain.entity.order.OrderStatus;
import uz.jk.onlineshopbyspringboot.domain.response.BaseResponse;
import uz.jk.onlineshopbyspringboot.service.order.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/add_order")
    ResponseEntity<BaseResponse<OrderEntity>> addOrder(
            @RequestBody OrderCreateDto orderCreateDto,
            @RequestParam(name = "userId") UUID userId
    ) {
        orderCreateDto.setOwnerId(userId);
        return ResponseEntity.ok(orderService.create(orderCreateDto));
    }


    @GetMapping("/delete/{id}")
    ResponseEntity<BaseResponse<OrderEntity>> delete(
            @PathVariable("id") UUID uuid) {
        return ResponseEntity.ok(orderService.delete(uuid));
    }

    @PatchMapping("/edit_order_amount")
    ResponseEntity<BaseResponse<OrderEntity>> editOrderAmount(
            @RequestParam("id") UUID orderId,
            @RequestParam("amount") int amount) {

        return ResponseEntity.ok(orderService.changeOrderAmount(orderId, amount));
    }

    @PatchMapping("/edit_order_status")
    ResponseEntity<BaseResponse<OrderEntity>> editOrderStatus(
            @RequestParam("status") OrderStatus status,
            @RequestParam("id") UUID orderId) {

        return ResponseEntity.ok(orderService.changeOrderStatus(orderId, status));
    }

    @GetMapping("/get_seller_orders")
    ResponseEntity<BaseResponse<List<OrderEntity>>> getSellerOrder(
            @RequestParam(name = "userId") UUID userId,
            @RequestParam(name = "page",
                    defaultValue = "0") int page) {

        return ResponseEntity.ok(orderService.getSellerOrders(userId, page));
    }

    @GetMapping("/get_my_orders")
    ResponseEntity<BaseResponse<List<OrderEntity>>> getMyOrders(
            @RequestParam(name = "userId") UUID userId,
            @RequestParam(name = "page",
                    defaultValue = "0") int page) {

        return ResponseEntity.ok(orderService.findMyOrdersById(userId, page));
    }
}
