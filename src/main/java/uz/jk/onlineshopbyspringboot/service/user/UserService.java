package uz.jk.onlineshopbyspringboot.service.user;

import org.springframework.stereotype.Service;
import uz.jk.onlineshopbyspringboot.domain.dto.user.UserCreateDto;
import uz.jk.onlineshopbyspringboot.domain.dto.user.UserReadDto;
import uz.jk.onlineshopbyspringboot.domain.response.BaseResponse;
import uz.jk.onlineshopbyspringboot.service.BaseService;

@Service
public interface UserService extends BaseService<UserCreateDto, UserReadDto> {
    BaseResponse<UserReadDto> signIn(UserCreateDto userCreateDto);

}
