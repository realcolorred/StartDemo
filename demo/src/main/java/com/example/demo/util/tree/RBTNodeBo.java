package com.example.demo.util.tree;

public class RBTNodeBo<T extends Comparable> {

    boolean color;        // 颜色
    T key;                // 关键字(键值)
    RBTNodeBo<T> left;    // 左孩子
    RBTNodeBo<T> right;    // 右孩子
    RBTNodeBo<T> parent;    // 父结点

    public RBTNodeBo(T key, boolean color, RBTNodeBo<T> parent, RBTNodeBo<T> left, RBTNodeBo<T> right) {
        this.key = key;
        this.color = color;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }
}
