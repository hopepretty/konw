package com.pc.util.hierarchy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 层次关系编排构造器
 * @Description	
 * @author pc
 * @date 2019年5月30日上午10:54:46
 *
 * @param <T>
 */
public class HierarchyGenerator<T extends HierarchyGeneratorListener<T>> {

    private List<T> objects = new ArrayList<>();

    private HierarchyGenerator() {
        super();
    }

    public static <T extends HierarchyGeneratorListener<T>> HierarchyGenerator<T> getInstance() {
        return new HierarchyGenerator<>();
    }

    /**
     * 增加对象
     * @param object
     */
    public void addObject(T object) {
        objects.add(object);
    }

    /**
     * 添加数据
     * @param data
     */
    public void addObjects(List<T> data) {
        objects.addAll(data);
    };

    /**
     * 匹配子节点
     * @param r
     * @param data
     * @return
     */
    private T covert(T r, List<T> data) {
        List<T> children = data.stream()
                .filter(r::isMyChild)
                .map(subR -> covert(subR, data))
                .collect(Collectors.toList());
        r.addChilds(children);
        return r;
    }

    /**
     * 获得整理好层次关系的对象
     * @return
     */
    public List<T> getHierarchy() {
        //1、先将所有根节点过滤出来，然后对这些对象进行匹配子节点，通过map函数，扔进去一个根节点，让它
        //去匹配自己的子节点，返回自己就行了
        return objects.stream()
                .filter(T::isRoot)
                .map(r -> covert(r, objects))
                .collect(Collectors.toList());
    }

}
