package uz.pdp.uz.app_news_board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uz.app_news_board.entity.News;
import uz.pdp.uz.app_news_board.entity.Status;
import uz.pdp.uz.app_news_board.entity.User;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface NewsRepository extends JpaRepository<News, UUID> {



    /**
     *For user
     */

    List<News> findAllByCheckedByAdminAndCancelAndNewsOwnerNot(Boolean checkedByAdmin, Boolean cancel, User user);
    List<News>findAllByNewsOwner(User newsOwner);





}
