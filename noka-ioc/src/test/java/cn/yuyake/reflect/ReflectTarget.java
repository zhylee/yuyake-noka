package cn.yuyake.reflect;

import cn.yuyake.core.annotation.Repository;

/**
 * create by yeah on 2021/4/3 10:21
 */
@Repository
public class ReflectTarget extends ReflectTargetOrigin {

    //------构造函数------

    //（默认的带参数构造函数）
    ReflectTarget(String str) {
        System.out.println("（默认）的构造方法 s = " + str);
    }

    // 无参构造函数
    public ReflectTarget() {
        System.out.println("调用了公有的无参构造方法。。。");
    }

    // 有一个参数的构造函数
    public ReflectTarget(char name) {
        System.out.println("调用了带有一个参数的构造方法，参数值为 " + name);
    }

    // 有多个参数的构造函数
    public ReflectTarget(String name, int index) {
        System.out.println("调用了带有多个参数的构造方法，参数名为：s = " + name + " i = " + index);
    }

    // 受保护的构造函数
    protected ReflectTarget(boolean n) {
        System.out.println("受保护的构造方法 n = " + n);
    }

    // 私有的构造函数
    private ReflectTarget(int index) {
        System.out.println("私有的构造方法 i = " + index);
    }

    // --------字段--------

    public String name;
    protected int index;
    char type;
    private String targetInfo;

    //------成员方法------

    public void show1(String s) {
        System.out.println("调用了公有方法 show1()：s = " + s);
    }

    protected void show2() {
        System.out.println("调用了受保护的无参方法 show2()");
    }

    void show3() {
        System.out.println("调用了默认的无参方法 show3()");
    }

    private String show4(int index) {
        System.out.println("调用了私有且有返回值的方法 show4()：index = " + index);
        return "show4result";
    }

    @Override
    public String toString() {
        return "ReflectTarget{" +
            "name='" + name + '\'' +
            ", index=" + index +
            ", type=" + type +
            ", targetInfo='" + targetInfo + '\'' +
            ", defaultMember='" + defaultMember + '\'' +
            ", publicMember='" + publicMember + '\'' +
            ", protectedMember='" + protectedMember + '\'' +
            '}';
    }
}
