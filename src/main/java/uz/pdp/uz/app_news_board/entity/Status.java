package uz.pdp.uz.app_news_board.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.uz.app_news_board.entity.enums.StatusName;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Enumerated(EnumType.STRING)
    private StatusName statusName;

}
