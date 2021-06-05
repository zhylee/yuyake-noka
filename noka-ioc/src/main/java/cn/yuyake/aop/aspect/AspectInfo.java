package cn.yuyake.aop.aspect;

import cn.yuyake.aop.PointcutLocator;

public class AspectInfo {
    private final int orderIndex;
    private final DefaultAspect aspectObject;
    private final PointcutLocator pointcutLocator;

    public AspectInfo(int orderIndex, DefaultAspect aspectObject, PointcutLocator pointcutLocator) {
        this.orderIndex = orderIndex;
        this.aspectObject = aspectObject;
        this.pointcutLocator = pointcutLocator;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public DefaultAspect getAspectObject() {
        return aspectObject;
    }

    public PointcutLocator getPointcutLocator() {
        return pointcutLocator;
    }
}
