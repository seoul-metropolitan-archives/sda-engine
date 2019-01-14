package rmsoft.ams.seoul.ad.ad010.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=true)
public class Ad01001VO extends BaseVO {

	private String classificationScheme;
	private String class01;
	private String generalRecordsSchedule;
	private String recordSchedule;
	private String disposalFreezeEvent;
	private String disposalFreezeDegree;
	private String normalRecords;
	private String publishedRecords;
	private String temporaryAggregation;
	private String temporaryItem;
	private String normalRecordGroup;
	private String normalSeries;
	private String normalFile;
	private String normalNone;
	private String normalItem;
	private String publishedRecordGroup;
	private String publishedSeries;
	private String publishedFile;
	private String publishedItem;
	private String classificationDraft;
	private String classificationConfirm;
	private String classDraft;
	private String classConfirm;
	private String classifyRecord;
	private String normalRecord;
	private String grsDraft;
	private String grsConfirm;
	private String rsDraft;
	private String rsConfirm;
	private String rsSchedulingRecord;
	private String normalRecordItem;
	private String dfEventDraft;
	private String dfEventConfirm;
	private String dfDegreeDraft;
	private String dfDegreeConfirm;
	private String dfFreezeRecord;
	private String dfRecordDraft;
	private String dfRecordConfirm;
	private String dfRecordComplete;
}