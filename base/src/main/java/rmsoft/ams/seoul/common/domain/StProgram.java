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

    /*@Column(name = "NO", length = 8, nullable = false)
    private int no;*/

    @Column(name = "MACHINE_TYPE_UUID", length = 36, nullable = false)
    private String machineTypeUuid;

    @Column(name = "MACHINE_NAME", length = 50, nullable = false)
    private String machineName;

    @Column(name = "PROGRAM_NAME", length = 50, nullable = false)
    private String programName;

    @Column(name = "VERSION", length = 36, nullable = false)
    private String version;

    @Column(name = "FILE_PATH", length = 50, nullable = false)
    private String filePath;

    @Column(name = "FILE_NAME", length = 50, nullable = false)
    private String fileName;

    @Column(name = "DESCRIPTION", length = 4000, nullable = false)
    private String description;



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
