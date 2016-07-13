package cn.lts.common.wx;

import java.time.LocalDateTime;

/**
 * uparty订单分组时用到的过渡对象
 * @project uparty-common
 * @author czz
 * @date 2015年5月15日下午4:36:52
 */
public class UpartyOrderGroupVo {

    private long roomId;
    
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public UpartyOrderGroupVo(long roomId, LocalDateTime startTime, LocalDateTime endTime) {
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "{roomId:"+roomId+",startTime:"+startTime.toString()+",endTime:"+endTime.toString()+"}";
    }
}
