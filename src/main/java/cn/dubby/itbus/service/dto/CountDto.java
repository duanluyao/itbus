package cn.dubby.itbus.service.dto;

import java.util.List;

/**
 * Created by yangzheng03 on 2017/4/25.
 */
public class CountDto {

    public CountDto() {
    }

    public CountDto(int currentPageId, List<Integer> pageIdList) {
        this.currentPageId = currentPageId;
        this.pageIdList = pageIdList;
    }

    private int currentPageId;

    private List<Integer> pageIdList;

    public int getCurrentPageId() {
        return currentPageId;
    }

    public void setCurrentPageId(int currentPageId) {
        this.currentPageId = currentPageId;
    }

    public List<Integer> getPageIdList() {
        return pageIdList;
    }

    public void setPageIdList(List<Integer> pageIdList) {
        this.pageIdList = pageIdList;
    }
}
