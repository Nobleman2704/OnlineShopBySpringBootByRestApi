package uz.jk.onlineshopbyspringboot.service.history;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jk.onlineshopbyspringboot.dao.history.HistoryDao;
import uz.jk.onlineshopbyspringboot.dao.user.UserDao;
import uz.jk.onlineshopbyspringboot.domain.entity.history.HistoryEntity;
import uz.jk.onlineshopbyspringboot.domain.exception.DataNotFoundException;
import uz.jk.onlineshopbyspringboot.domain.response.BaseResponse;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    public final HistoryDao historyDao;
    public final UserDao userDao;
    @Override
    public BaseResponse<HistoryEntity> create(HistoryEntity historyEntity) {
        return null;
    }

    @Override
    public HistoryEntity findById(UUID id) {
        return null;
    }

    @Override
    public BaseResponse<HistoryEntity> delete(UUID id) {
        return null;
    }

    @Override
    public BaseResponse<List<HistoryEntity>> getMyOrderHistory(UUID userId, int page) {
        Pageable pageRequest = PageRequest.of(page, 5);
        try {
            Page<HistoryEntity> historyEntityPage = historyDao.findHistoryEntitiesByUsersId(userId, pageRequest);
            int totalPages = historyEntityPage.getTotalPages();
            return BaseResponse.<List<HistoryEntity>>builder()
                    .status(200)
                    .message(historyEntityPage.getTotalElements() + " result(s) found")
                    .totalPages((totalPages == 0) ? 0 : totalPages - 1)
                    .data(historyEntityPage.getContent())
                    .build();
        }catch (Exception e){
            throw new DataNotFoundException("there is no history by this user id: " + userId);
        }
    }
}
