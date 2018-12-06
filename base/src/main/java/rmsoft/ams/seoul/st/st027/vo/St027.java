package rmsoft.ams.seoul.st.st027.vo;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ST_ZONE")
@Comment("ST_ZONE")
@Alias("st027")
public class St027 extends SimpleJpaModel<String> {

    @Id
    @Column(name = "ZONE_UUID", length = 36, nullable = false)
    private String zoneUuid;

    @Column(name = "NO", length = 4, nullable = false)
    private int no;

    @Column(name = "ZONE_ID", length = 36, nullable = false)
    private String zoneId;

    @Column(name = "ZONE_NAME", length = 50, nullable = false)
    private String zoneName;

    @Column(name = "INSERT_UUID", length = 36, nullable = false)
    private String insertUuid;

    @Column(name = "INSERT_DATE", nullable = false)
    private Timestamp insertDate;

    @Column(name = "UPDATE_UUID", length = 36, nullable = false)
    private String updateUuid;

    @Column(name = "UPDATE_DATE", nullable = false)
    private Timestamp updateDate;

    @Override
    public String getId() {
        return zoneUuid;
    }

}
