package com.pc.util.hierarchy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public interface HierarchyGeneratorListener<T> {

    /**
     * @Description	新增子节点
     * @author pc
     * @date 2019年5月30日上午11:00:00
     *
     * @param t
     */
    @JsonIgnore
    public boolean addChild(T t);

    /**
     * @Description	一次新增所有子节点
     * @author pc
     * @date 2019年5月30日上午11:00:00
     *
     * @param t
     */
    @JsonIgnore
    public boolean addChilds(List<T> t);

    /**
     * @Description	判断自己是否为根节点
     * @author pc
     * @date 2019年5月30日上午11:04:16
     *
     * @return
     */
    @JsonIgnore
    public boolean isRoot();

    /**
     * 
     * @Description	判断传入的节点是否为子节点
     * @author pc
     * @date 2019年5月30日上午11:05:32
     *
     * @param t 传入的节点
     * @return
     */
    @JsonIgnore
    public boolean isMyChild(T t);
}
