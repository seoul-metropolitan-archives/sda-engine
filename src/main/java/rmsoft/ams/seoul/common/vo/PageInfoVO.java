package rmsoft.ams.seoul.common.vo;

import lombok.Data;

/**
 * The type Page info vo.
 */
@Data
public class PageInfoVO
{
    private long limit = 20;
    private int pageSize = 20;
    private int pageNumber = 0;
    private int totalPages = 0;
    private long start;
    private long end;

    /**
     * Sets page number.
     *
     * @param pageNumber the page number
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;

    }

    /**
     * Sets limit.
     *
     * @param limit the limit
     */
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

    /**
     * Sets page size.
     *
     * @param pageSize the page size
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Gets start.
     *
     * @return the start
     */
    public long getStart() {
        return start;
    }

    /**
     * Gets end.
     *
     * @return the end
     */
    public long getEnd() {

        return end;
    }
}
