package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;


@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ST_PROGRAM")
@IdClass(StProgram.StProgramId.class)
@Alias("stProgram")
public class StProgram extends SimpleJpaModel<StProgram.StProgramId> {

    @Id
    @Column(name = "PROGRAM_UUID", length = 36, nullable = false)
    private String programUuid;







    @Override
    public StProgramId getId() { return StProgramId.of(programUuid); }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class StProgramId implements Serializable {
        @NonNull
        private String programUuid;

    }

}
