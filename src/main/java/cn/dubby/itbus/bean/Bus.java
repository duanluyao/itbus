package cn.dubby.itbus.bean;

import java.util.Date;

public class Bus {
    private Integer id;

    private Integer busLineId;

    private String busName;

    private Date createTime;

    private Date updateTime;

    private String busTicket;

    private Long upCount;

    private Long downCount;

    private Integer status;

    private String busContent;

    private Integer authorId;

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusLineId() {
        return busLineId;
    }

    public void setBusLineId(Integer busLineId) {
        this.busLineId = busLineId;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName == null ? null : busName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBusTicket() {
        return busTicket;
    }

    public void setBusTicket(String busTicket) {
        this.busTicket = busTicket == null ? null : busTicket.trim();
    }

    public Long getUpCount() {
        return upCount;
    }

    public void setUpCount(Long upCount) {
        this.upCount = upCount;
    }

    public Long getDownCount() {
        return downCount;
    }

    public void setDownCount(Long downCount) {
        this.downCount = downCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBusContent() {
        return busContent;
    }

    public void setBusContent(String busContent) {
        this.busContent = busContent == null ? null : busContent.trim();
    }
}