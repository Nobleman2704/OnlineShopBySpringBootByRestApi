package uz.jk.onlineshopbyspringboot.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jk.onlineshopbyspringboot.domain.dto.user.UserCreateDto;
import uz.jk.onlineshopbyspringboot.domain.dto.user.UserReadDto;
import uz.jk.onlineshopbyspringboot.domain.response.BaseResponse;
import uz.jk.onlineshopbyspringboot.service.user.UserService;



@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/sign_up")
    public ResponseEntity<BaseResponse<UserReadDto>> signUp(
            @RequestBody @Valid UserCreateDto createDto
    ) {
        return ResponseEntity.ok(userService.create(createDto));
    }

    @PostMapping("/sign_in")
    public ResponseEntity<BaseResponse<UserReadDto>> signIn(
            @RequestBody @Valid UserCreateDto createDto
    ) {
        return ResponseEntity.ok(userService.signIn(createDto));
    }
}
