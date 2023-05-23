package uz.jk.onlineshopbyspringboot.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.jk.onlineshopbyspringboot.domain.entity.user.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserDao extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findUserEntityByEmail(String email);
}
