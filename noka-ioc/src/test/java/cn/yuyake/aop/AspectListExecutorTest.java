package cn.yuyake.aop;

import cn.yuyake.aop.aspect.AspectInfo;
import cn.yuyake.aop.mock.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AspectListExecutorTest {

    @DisplayName("Aspect 排序：sortAspectList")
    @Test
    public void sortTest() {
        List<AspectInfo> aspectInfoList = new ArrayList<>();
        aspectInfoList.add(new AspectInfo(3, new Mock1()));
        aspectInfoList.add(new AspectInfo(5, new Mock2()));
        aspectInfoList.add(new AspectInfo(2, new Mock3()));
        aspectInfoList.add(new AspectInfo(4, new Mock4()));
        aspectInfoList.add(new AspectInfo(1, new Mock5()));
        var aspectListExecutor = new AspectListExecutor(AspectListExecutorTest.class, aspectInfoList);
        List<AspectInfo> sortedAspectInfoList = aspectListExecutor.getSortedAspectInfoList();
        // expected: Mock5 -> Mock3 -> Mock1 -> Mock4 -> Mock2
        for (AspectInfo aspectInfo : sortedAspectInfoList) {
            System.out.println(aspectInfo.getAspectObject().getClass().getName());
        }
    }
}
