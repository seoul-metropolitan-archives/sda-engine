package ${domainPackageName}.${targetPackageName};

import io.onsemiro.core.annotations.ColumnPosition;
import io.onsemiro.core.domain.SimpleJpaModel;
import io.onsemiro.core.annotations.Comment;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.io.Serializable;
${importPackages}

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "${tableName}")
@Comment(value = "${tableComment}")
@IdClass(${keyClassRefName}.class)
@Alias("${entityClassFieldName}")
public class ${entityClassName} extends SimpleJpaModel<${keyClassRefName}> {
${entityFields}

@Override
public ${keyClassName} getId() {
return ${keyClassName}.of(${returnKeyName});
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class ${keyClassName} implements Serializable {
${keyFields}
}
}