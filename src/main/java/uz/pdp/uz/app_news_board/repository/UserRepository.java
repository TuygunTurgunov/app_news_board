package uz.pdp.uz.app_news_board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.uz.app_news_board.entity.User;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);

    boolean existsByEmailAndLogin(String email, String login);
    boolean existsByEmailAndLoginAndPassword(String email, String login, String password);
    boolean existsByLoginAndPassword(String login, String password);
    Optional<User> findByLogin(String login);
    Optional<User> findByEmailAndLoginAndPassword(String email, String login, String password);
    Optional<User> findByEmail(String email);
    Optional<User>findByLoginAndPassword(String login, String password);


//    Optional<User> findByLoginAndPassword(String login, String password);



}
