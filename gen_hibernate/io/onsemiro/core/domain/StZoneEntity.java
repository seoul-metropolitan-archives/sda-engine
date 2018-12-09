package io.onsemiro.core.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ST_ZONE", schema = "AMS", catalog = "")
public class StZoneEntity {
    private String zoneUuid;
    private long no;
    private String zoneId;
    private String zoneName;
    private String insertUuid;
    private Timestamp insertDate;
    private String updateUuid;
    private Timestamp updateDate;

    @Id
    @Column(name = "ZONE_UUID", nullable = false, length = 36)
    public String getZoneUuid() {
        return zoneUuid;
    }

    public void setZoneUuid(String zoneUuid) {
        this.zoneUuid = zoneUuid;
    }

    @Basic
    @Column(name = "NO", nullable = false, precision = 0)
    public long getNo() {
        return no;
    }

    public void setNo(long no) {
        this.no = no;
    }

    @Basic
    @Column(name = "ZONE_ID", nullable = false, length = 36)
    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    @Basic
    @Column(name = "ZONE_NAME", nullable = false, length = 50)
    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    @Basic
    @Column(name = "INSERT_UUID", nullable = false, length = 36)
    public String getInsertUuid() {
        return insertUuid;
    }

    public void setInsertUuid(String insertUuid) {
        this.insertUuid = insertUuid;
    }

    @Basic
    @Column(name = "INSERT_DATE", nullable = false)
    public Timestamp getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Timestamp insertDate) {
        this.insertDate = insertDate;
    }

    @Basic
    @Column(name = "UPDATE_UUID", nullable = false, length = 36)
    public String getUpdateUuid() {
        return updateUuid;
    }

    public void setUpdateUuid(String updateUuid) {
        this.updateUuid = updateUuid;
    }

    @Basic
    @Column(name = "UPDATE_DATE", nullable = false)
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StZoneEntity that = (StZoneEntity) o;
        return no == that.no &&
                Objects.equals(zoneUuid, that.zoneUuid) &&
                Objects.equals(zoneId, that.zoneId) &&
                Objects.equals(zoneName, that.zoneName) &&
                Objects.equals(insertUuid, that.insertUuid) &&
                Objects.equals(insertDate, that.insertDate) &&
                Objects.equals(updateUuid, that.updateUuid) &&
                Objects.equals(updateDate, that.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneUuid, no, zoneId, zoneName, insertUuid, insertDate, updateUuid, updateDate);
    }
}
