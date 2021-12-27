package uz.pdp.uz.app_news_board.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.pdp.uz.app_news_board.entity.enums.StatusName;
import uz.pdp.uz.app_news_board.entity.template.AbsEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = AuditingEntityListener.class )
@EqualsAndHashCode(callSuper = true)
@Entity
public class News extends AbsEntity {


    private String newsHeader;

    @Length(max = 500)
    @Column(nullable = false)
    private String text;


    private Boolean checkedByAdmin=false;

    private Boolean cancel=true;

    @ManyToOne(optional = false)
    private User newsOwner;

    private String newsOwnerLogin;

    @Email
    private String newsOwnerEmail;

    @ManyToOne
    private Status statusName;

    private Timestamp timeCheckedByAdmin;

}
