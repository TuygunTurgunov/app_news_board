package uz.pdp.uz.app_news_board.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uz.pdp.uz.app_news_board.entity.enums.RoleName;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @Override
    public String getAuthority() {
        return roleName.toString();
    }


}
