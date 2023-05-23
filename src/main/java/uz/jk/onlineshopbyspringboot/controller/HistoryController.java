package uz.jk.onlineshopbyspringboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.jk.onlineshopbyspringboot.domain.entity.history.HistoryEntity;
import uz.jk.onlineshopbyspringboot.domain.response.BaseResponse;
import uz.jk.onlineshopbyspringboot.service.history.HistoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping("/get_my_order_history")
    public ResponseEntity<BaseResponse<List<HistoryEntity>>> getMyOrderHistory(
            @RequestParam(value = "userId") UUID userId,
            @RequestParam(value = "page"
                    , defaultValue = "0") int page) {

        return ResponseEntity.ok(historyService.getMyOrderHistory(userId, page));
    }
}
