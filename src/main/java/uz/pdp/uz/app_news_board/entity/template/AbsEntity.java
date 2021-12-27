package uz.pdp.uz.app_news_board.entity.template;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@MappedSuperclass
@EntityListeners(value =AuditingEntityListener.class )
public class AbsEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false,updatable = false)//==> admin update qila olmaydi
    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updateAt;

    @Column(nullable = false,updatable = false)
    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;



}
