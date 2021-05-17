package cn.yuyake.aop.aspect;

public class AspectInfo {
    private final int orderIndex;
    private final DefaultAspect aspectObject;

    public AspectInfo(int orderIndex, DefaultAspect aspectObject) {
        this.orderIndex = orderIndex;
        this.aspectObject = aspectObject;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public DefaultAspect getAspectObject() {
        return aspectObject;
    }
}
