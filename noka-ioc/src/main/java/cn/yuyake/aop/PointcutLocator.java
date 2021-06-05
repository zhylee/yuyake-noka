package cn.yuyake.aop;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;

import java.lang.reflect.Method;

/**
 * 解析Aspect表达式并且定位被织入的目标
 */
public class PointcutLocator {
    // Pointcut解析器，直接给它赋值上 AspectJ 的所有表达式，以便支持对众多表达式的解析
    private final PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingContextClassloaderForResolution(
            PointcutParser.getAllSupportedPointcutPrimitives()
    );
    // 表达式解析器
    private final PointcutExpression pointcutExpression;

    public PointcutLocator(String expression) {
        this.pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    /**
     * 判断传入的 Class 对象是否是 Aspect 的目标代理类，即匹配 Pointcut 表达式（初筛）
     *
     * @param targetClass 目标类
     * @return 是否匹配
     */
    public boolean roughMatches(Class<?> targetClass) {
        // couldMatchJoinPointsInType 比较坑，只能校验 within
        // 不能校验 (execution, call, get, set)，面对无法校验的表达式，直接返回 true
        return pointcutExpression.couldMatchJoinPointsInType(targetClass);
    }

    /**
     * 判断传入的 Method 对象是否是 Aspect 的目标代理方法，即匹配 Pointcut 表达式（精筛）
     *
     * @param method 目标方法
     * @return 是否完全匹配
     */
    public boolean accurateMatches(Method method) {
        var shadowMatch = pointcutExpression.matchesMethodExecution(method);
        return shadowMatch.alwaysMatches();
    }
}
