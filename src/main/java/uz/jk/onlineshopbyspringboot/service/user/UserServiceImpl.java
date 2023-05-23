package uz.jk.onlineshopbyspringboot.service.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.jk.onlineshopbyspringboot.dao.user.UserDao;
import uz.jk.onlineshopbyspringboot.domain.dto.user.UserCreateDto;
import uz.jk.onlineshopbyspringboot.domain.dto.user.UserReadDto;
import uz.jk.onlineshopbyspringboot.domain.entity.user.UserEntity;
import uz.jk.onlineshopbyspringboot.domain.exception.DataAlreadyException;
import uz.jk.onlineshopbyspringboot.domain.exception.DataNotFoundException;
import uz.jk.onlineshopbyspringboot.domain.exception.DataNotMatchedException;
import uz.jk.onlineshopbyspringboot.domain.exception.InvalidInputException;
import uz.jk.onlineshopbyspringboot.domain.response.BaseResponse;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    private final ModelMapper modelMapper;


    @Override
    public BaseResponse<UserReadDto> create(UserCreateDto userCreateDto) {
        String email = userCreateDto.getEmail();
        String password = userCreateDto.getPassword();
        isParamValid(email, password);
        UserEntity userEntity = modelMapper.map(userCreateDto, UserEntity.class);
        try {
            UserEntity user = userDao.save(userEntity);
            UserReadDto userReadDto = modelMapper.map(user, UserReadDto.class);
            return BaseResponse.<UserReadDto>builder()
                    .data(userReadDto)
                    .message("Success")
                    .status(200)
                    .build();
        } catch (Exception e) {
            throw new DataAlreadyException(e.getCause().getCause().getMessage());
        }
    }


    @Override
    public UserReadDto findById(UUID id) {
        UserEntity user = userDao.findById(id).orElseThrow(
                () -> new DataNotFoundException("there is no user by this id:" + id));
        return modelMapper.map(user, UserReadDto.class);
    }

    @Override
    public BaseResponse<UserReadDto> delete(UUID id) {
        return null;
    }

    @Override
    public BaseResponse<UserReadDto> signIn(UserCreateDto userCreateDto) {
        String password = userCreateDto.getPassword();
        String email = userCreateDto.getEmail();
        isParamValid(email, password);
        try {
            Optional<UserEntity> optionalUser = userDao.findUserEntityByEmail(email);
            UserEntity userEntity = optionalUser.get();
            if (Objects.equals(userEntity.getPassword(), password)) {
                UserReadDto user = modelMapper.map(userEntity, UserReadDto.class);
                return BaseResponse.<UserReadDto>builder()
                        .status(200)
                        .message("success")
                        .data(user)
                        .build();
            }
            throw new DataNotMatchedException("passwords wid not match");
        } catch (NoSuchElementException e) {
            throw new DataNotFoundException("Email not found: " + email);
        }
    }

    private void isParamValid(String email, String password){
        if (password.isBlank()){
            throw new InvalidInputException("password should not be empty");
        }else if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
            throw new InvalidInputException("valid email required");
        }
    }
}
