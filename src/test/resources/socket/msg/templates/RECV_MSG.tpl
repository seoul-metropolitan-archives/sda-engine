package @{PACKAGE_NAME};

import org.apache.commons.lang3.StringUtils;
import io.netty.util.CharsetUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by tw.jang on 17. 1. 24.
 */
@Getter
@NoArgsConstructor
public class @{CLASS_NAME} {

@{LOCAL_MEMBERS}
@{SETTER_METHODS}
    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
