package uz.jk.onlineshopbyspringboot.dao.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.jk.onlineshopbyspringboot.domain.entity.history.HistoryEntity;


import java.util.UUID;

public interface HistoryDao extends JpaRepository<HistoryEntity, UUID> {
    Page<HistoryEntity> findHistoryEntitiesByUsersId(UUID userId, Pageable pageable);
}
