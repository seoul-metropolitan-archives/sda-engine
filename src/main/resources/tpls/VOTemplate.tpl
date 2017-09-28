package ${domainPackageName}.${targetPackageName};

import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.ModelMapperUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import java.util.List;
${importPackages}
import static java.util.stream.Collectors.toList;


@Data
@NoArgsConstructor
public class ${voClassName} extends BaseVO {
${voFields}

    public static ${voClassName} of(${entityClassName} ${entityClassFieldName}) {
        ${voClassName} ${voClassFieldName} = ModelMapperUtils.map(${entityClassFieldName}, ${voClassName}.class);
        return ${voClassFieldName};
    }

    public static List<${voClassName}> of(List<${entityClassName}> ${entityClassFieldName}List) {
        return ${entityClassFieldName}List.stream().map(${entityClassFieldName} -> of(${entityClassFieldName})).collect(toList());
    }

    public static List<${voClassName}> of(Page<${entityClassName}> ${entityClassFieldName}Page) {
        return ${entityClassFieldName}Page.getContent().stream().map(${entityClassFieldName} -> of(${entityClassFieldName})).collect(toList());
    }
}