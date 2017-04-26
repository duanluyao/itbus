package cn.dubby.itbus.test.service;

import com.alibaba.fastjson.JSON;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yangzheng03 on 2017/4/25.
 */
public class PageTest {

    public static void main(String[] args) {
        List<Integer> result = generateCountList(0, 0);

        System.out.println(JSON.toJSON(result));
    }

    private static List<Integer> generateCountList(int currentPageId, int totalCount) {
        if (currentPageId >= totalCount) {

        }
        LinkedList<Integer> pageIdList = new LinkedList<>();
        pageIdList.add(currentPageId);
        for (int i = 1; i <= 7; ++i) {
            int tempPageId = currentPageId - i;
            if (tempPageId >= 1) {
                pageIdList.offerFirst(tempPageId);
                if (pageIdList.size() >= 7) {
                    return pageIdList;
                }
            }
            tempPageId = currentPageId + i;
            if (tempPageId <= totalCount) {
                pageIdList.offerLast(tempPageId);
                if (pageIdList.size() >= 7) {
                    return pageIdList;
                }
            }
        }

        return pageIdList;
    }

}
