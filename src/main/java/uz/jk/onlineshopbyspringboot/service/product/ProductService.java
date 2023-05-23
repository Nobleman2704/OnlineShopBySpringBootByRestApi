package uz.jk.onlineshopbyspringboot.service.product;

import org.springframework.stereotype.Service;
import uz.jk.onlineshopbyspringboot.domain.dto.product.ProductCreateDto;
import uz.jk.onlineshopbyspringboot.domain.entity.product.ProductCategory;
import uz.jk.onlineshopbyspringboot.domain.entity.product.ProductEntity;
import uz.jk.onlineshopbyspringboot.domain.response.BaseResponse;
import uz.jk.onlineshopbyspringboot.service.BaseService;

import java.util.List;
import java.util.UUID;

@Service
public interface ProductService extends BaseService<ProductCreateDto, ProductEntity> {
    BaseResponse<ProductEntity> update(ProductCreateDto productCreateDto);

    BaseResponse<List<ProductEntity>> findSellerProductsById(UUID id, int page);

    BaseResponse<List<ProductEntity>> findByCategory(ProductCategory category, int page);

    BaseResponse<List<ProductEntity>> findByProductName(String word, int page);
}
