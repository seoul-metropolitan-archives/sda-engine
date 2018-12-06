package rmsoft.ams.seoul.st.st012.vo;

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

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ST_INOUT_EXCEPT")
@Comment("")
@Alias("st012")
public class St012 extends SimpleJpaModel<String> {

	@Id
	@Column(name = "test", length = 36, nullable = false)
	private String test;

	@Column(name = "test123", length = 36, nullable = false)
	private String test123;

	@Column(name = "test321", length = 36, nullable = false)
	private String test321;


    @Override
    public String getId() {
        return test;
    }
}
