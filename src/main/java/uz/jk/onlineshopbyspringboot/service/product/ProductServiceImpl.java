package uz.jk.onlineshopbyspringboot.service.product;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jk.onlineshopbyspringboot.dao.product.ProductDao;
import uz.jk.onlineshopbyspringboot.dao.user.UserDao;
import uz.jk.onlineshopbyspringboot.domain.dto.product.ProductCreateDto;
import uz.jk.onlineshopbyspringboot.domain.entity.product.ProductCategory;
import uz.jk.onlineshopbyspringboot.domain.entity.product.ProductEntity;
import uz.jk.onlineshopbyspringboot.domain.entity.user.UserEntity;
import uz.jk.onlineshopbyspringboot.domain.exception.DataNotFoundException;
import uz.jk.onlineshopbyspringboot.domain.exception.InvalidInputException;
import uz.jk.onlineshopbyspringboot.domain.response.BaseResponse;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    private final ModelMapper modelMapper;

    private final UserDao userDao;

    @Override
    public BaseResponse<ProductEntity> create(ProductCreateDto productCreateDto) {
        isParamValid(productCreateDto.getName(), productCreateDto.getDescription(), productCreateDto.getPrice());

        ProductEntity product = modelMapper.map(productCreateDto, ProductEntity.class);
        UserEntity user = userDao.findById(productCreateDto.getUserId()).orElseThrow(
                () -> new DataNotFoundException("there is no user by this id:" + productCreateDto.getUserId()));
        product.setUsers(user);
        productDao.save(product);
        return BaseResponse.<ProductEntity>builder()
                .status(200)
                .data(product)
                .message("Successfully added")
                .build();
    }

    @Override
    public ProductEntity findById(UUID id) {
        return productDao.findById(id).orElseThrow(() ->
                new DataNotFoundException("there is no product by this id:" + id)
        );
    }

    @Override
    public BaseResponse<ProductEntity> delete(UUID id) {
        try {
            productDao.deleteProductEntityById(id);
            return BaseResponse.<ProductEntity>builder()
                    .status(200)
                    .message("deleted")
                    .build();
        } catch (Exception e) {
            throw new DataNotFoundException("there is no product by this id: " + id);
        }
    }

    @Override
    public BaseResponse<ProductEntity> update(ProductCreateDto productCreateDto) {
        isParamValid(productCreateDto.getName(), productCreateDto.getDescription(), productCreateDto.getPrice());

        ProductEntity product1 = productDao.findById(productCreateDto.getId()).orElseThrow(() ->
                new DataNotFoundException("there is no product by this id:" + productCreateDto.getId())
        );
        modelMapper.map(productCreateDto, product1);
        productDao.save(product1);
        return BaseResponse.<ProductEntity>builder()
                .status(200)
                .message("updated")
                .data(product1)
                .build();

    }

    @Override
    public BaseResponse<List<ProductEntity>> findSellerProductsById(UUID id, int page) {

        Pageable pageRequest = PageRequest.of(page, 5);
//        PageRequest.of(7, 5);

        try {
            Page<ProductEntity> productEntitiesPage = productDao.findProductEntitiesByUsersId(id, pageRequest);
            return getProducts(productEntitiesPage);
        } catch (Exception e) {
            throw new InvalidInputException("Out of page");
        }
    }

    @Override
    public BaseResponse<List<ProductEntity>> findByCategory(ProductCategory category, int page) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<ProductEntity> productEntitiesPage = productDao.findProductEntitiesByCategory(category, pageRequest);
        return getProducts(productEntitiesPage);
    }

    @Override
    public BaseResponse<List<ProductEntity>> findByProductName(String word, int page) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<ProductEntity> productEntitiesPage = productDao
                .findProductEntitiesByNameContainingIgnoreCase(word, pageRequest);
        return getProducts(productEntitiesPage);
    }


    private BaseResponse<List<ProductEntity>> getProducts(Page<ProductEntity> productEntitiesPage) {
        if (productEntitiesPage.getContent().isEmpty()) {
            throw new DataNotFoundException("No products found");
        }
        int totalPages = productEntitiesPage.getTotalPages();
        return BaseResponse.<List<ProductEntity>>builder()
                .status(200)
                .message(productEntitiesPage.getTotalElements() + " result(s) found")
                .totalPages((totalPages == 0) ? 0 : totalPages - 1)
                .data(productEntitiesPage.getContent())
                .build();
    }

    public void isParamValid(String name, String description, Double price) {
        if (price == null || price <= 0) {
            throw new InvalidInputException("price should be positive");
        } else if (name.isBlank()) {
            throw new InvalidInputException("name should not be blank or empty");
        } else if (description.isBlank()) {
            throw new InvalidInputException("description should not be blank or empty");
        }
    }
}
