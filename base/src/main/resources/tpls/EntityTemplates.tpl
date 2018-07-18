package ${domainPackageName}.${targetPackageName};

import io.onsemiro.core.annotations.ColumnPosition;
import io.onsemiro.core.domain.SimpleJpaModel;
import io.onsemiro.core.annotations.Comment;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
${importPackages}

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "${tableName}")
@Comment("${tableComment}")
@Alias("${entityClassFieldName}")${annotations}
public class ${entityClassName} extends SimpleJpaModel<${keyClassRefName}> {
${entityFields}

    @Override
    public ${keyClassName} getId() {
        return ${returnKeyName};
    }
}