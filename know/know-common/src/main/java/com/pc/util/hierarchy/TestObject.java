package com.pc.util.hierarchy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pc
 * @Date 2020/9/24
 **/
public class TestObject implements HierarchyGeneratorListener<TestObject> {

    private String id;

    private String pid;

    private List<TestObject> children = new ArrayList<>();

    /**
     * @param testObject
     * @Description 新增子节点
     * @author pc
     * @date 2019年5月30日上午11:00:00
     */
    @Override
    public boolean addChild(TestObject testObject) {
        children.add(testObject);
        return true;
    }

    /**
     * @param t
     * @Description 一次新增所有子节点
     * @author pc
     * @date 2019年5月30日上午11:00:00
     */
    @Override
    public boolean addChilds(List<TestObject> t) {
        children.addAll(t);
        return true;
    }

    /**
     * @return
     * @Description 判断自己是否为根节点
     * @author pc
     * @date 2019年5月30日上午11:04:16
     */
    @Override
    public boolean isRoot() {
        return "0".equals(this.pid);
    }

    /**
     * @param testObject 传入的节点
     * @return
     * @Description 判断传入的节点是否为子节点
     * @author pc
     * @date 2019年5月30日上午11:05:32
     */
    @Override
    public boolean isMyChild(TestObject testObject) {
        if (testObject.getPid().equals(this.id)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {

        HierarchyGenerator<TestObject> instance = HierarchyGenerator.getInstance();

        TestObject t6 = new TestObject();
        t6.setId("6");
        t6.setPid("2");
        instance.addObject(t6);

        TestObject t5 = new TestObject();
        t5.setId("5");
        t5.setPid("1");
        instance.addObject(t5);

        TestObject t7 = new TestObject();
        t7.setId("7");
        t7.setPid("6");
        instance.addObject(t7);

        TestObject t1 = new TestObject();
        t1.setId("1");
        t1.setPid("0");
        instance.addObject(t1);

        TestObject t2 = new TestObject();
        t2.setId("2");
        t2.setPid("0");
        instance.addObject(t2);

        TestObject t3 = new TestObject();
        t3.setId("3");
        t3.setPid("1");
        instance.addObject(t3);

        TestObject t4 = new TestObject();
        t4.setId("4");
        t4.setPid("1");
        instance.addObject(t4);

        List<TestObject> hierarchy = instance.getHierarchy();
        System.out.println(hierarchy);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<TestObject> getChildren() {
        return children;
    }

    public void setChildren(List<TestObject> children) {
        this.children = children;
    }

}
