package cn.yuyake.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * create by yeah on 2021/4/3 16:54
 */
public class ClassUtilTest {

    @DisplayName("提取目标类方法：extractPackageClass")
    @Test
    public void extractPackageClassTest() {
        var classSet = ClassUtil.extractPackageClass("cn.yuyake.util");
        System.out.println(classSet);
        assert classSet != null;
        Assertions.assertEquals(1, classSet.size());
    }
}
