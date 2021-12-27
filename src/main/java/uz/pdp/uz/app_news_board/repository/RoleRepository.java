package uz.pdp.uz.app_news_board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.uz.app_news_board.entity.Role;
import uz.pdp.uz.app_news_board.entity.enums.RoleName;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByRoleName(RoleName roleName);

}
