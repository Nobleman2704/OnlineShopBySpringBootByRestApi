package uz.jk.onlineshopbyspringboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jk.onlineshopbyspringboot.domain.dto.product.ProductCreateDto;
import uz.jk.onlineshopbyspringboot.domain.entity.product.ProductCategory;
import uz.jk.onlineshopbyspringboot.domain.entity.product.ProductEntity;
import uz.jk.onlineshopbyspringboot.domain.response.BaseResponse;
import uz.jk.onlineshopbyspringboot.service.product.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add_product")
    public ResponseEntity<BaseResponse<ProductEntity>> addProduct(
            @RequestBody ProductCreateDto productCreateDto,
            @RequestParam(name = "userId") UUID userId) {
        productCreateDto.setUserId(userId);
        return ResponseEntity.ok(productService.create(productCreateDto));
    }

    @GetMapping("/search_by_name")
    public ResponseEntity<BaseResponse<List<ProductEntity>>> searchByName(
            @RequestParam(name = "word") String word,
            @RequestParam(name = "page",
                    defaultValue = "0") int page) {
        return ResponseEntity.ok(productService.findByProductName(word, page));
    }


    @GetMapping("/search_by_category")
    public ResponseEntity<BaseResponse<List<ProductEntity>>> searchByCategory(
            @RequestParam(name = "word") ProductCategory category,
            @RequestParam(name = "page",
                    defaultValue = "0") int page) {
        return ResponseEntity.ok(productService.findByCategory(category, page));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<BaseResponse<ProductEntity>> update(
            @RequestBody ProductCreateDto productCreateDto,
            @PathVariable("id") UUID id) {
        productCreateDto.setId(id);
        return ResponseEntity.ok(productService.update(productCreateDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<ProductEntity>> deleteProduct(
            @PathVariable UUID id) {
        return ResponseEntity.ok(productService.delete(id));
    }

    @GetMapping("/get_seller_products")
    public ResponseEntity<BaseResponse<List<ProductEntity>>> getProducts(
            @RequestParam(name = "userId") UUID userId,
            @RequestParam(name = "page",
                    defaultValue = "0") int page) {

        return ResponseEntity.ok(productService.findSellerProductsById(userId, page));
    }
}
