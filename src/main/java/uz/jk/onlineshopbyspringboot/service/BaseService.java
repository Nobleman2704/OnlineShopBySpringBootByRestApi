package uz.jk.onlineshopbyspringboot.service;

import org.springframework.stereotype.Service;
import uz.jk.onlineshopbyspringboot.domain.response.BaseResponse;

import java.util.UUID;

/**
 * @param <CD> This is Creation DTO
 * @param <RD> This is Read DTO
 * @author Asadbek
 */
@Service
public interface BaseService<CD, RD> {
    BaseResponse<RD> create(CD cd);

    RD findById(UUID id);

    BaseResponse<RD> delete(UUID id);
}
