package uz.pdp.uz.app_news_board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uz.app_news_board.entity.Status;
import uz.pdp.uz.app_news_board.entity.enums.StatusName;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status,Integer> {

    Optional<Status>findByStatusName(StatusName statusName);
}
