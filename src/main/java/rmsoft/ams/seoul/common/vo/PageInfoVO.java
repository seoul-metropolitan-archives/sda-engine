package rmsoft.ams.seoul.common.vo;

import lombok.Data;

@Data
public class PageInfoVO
{
    private long limit = 20;
    private int pageSize = 20;
    private int pageNumber = 0;
    private int totalPages = 0;
    private long start;
    private long end;

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;

    }

    public void setLimit(long limit) {
        this.limit = limit;

        this.totalPages = (int)this.limit/this.pageSize;
        if(this.limit%this.pageSize != 0)
        {
            this.totalPages++;
            if(this.totalPages == 1)
                this.totalPages++;
        }
        this.start = this.pageSize * this.pageNumber;
        this.end = this.pageSize * (this.pageNumber+1);
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {

        return end;
    }
}
