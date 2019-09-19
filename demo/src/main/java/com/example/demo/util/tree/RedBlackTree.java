package com.example.demo.util.tree;

public class RedBlackTree<T extends Comparable> {

    private static final boolean RED   = false;
    private static final boolean BLACK = true;

    private RBTNode<T> mRoot;

    private void insert(RBTNode<T> node){

    }

    private void leftRotate(RBTNode<T> x) {
        // 左旋示意图
        //       x        -->>  y
        //      /\             /\
        //     a  y           x  c
        //       /\          /\
        //      b  c        a b

        RBTNode<T> y = x.right; // 设置x的右孩子为y

        // 将 “y的左孩子” 设为 “x的右孩子”；
        // 如果y的左孩子非空，将 “x” 设为 “y的左孩子的父亲”
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }

        // 将 “x的父亲” 设为 “y的父亲”
        y.parent = x.parent;

        if (x.parent == null) {
            this.mRoot = y;            // 如果 “x的父亲” 是空节点，则将y设为根节点
        } else {
            if (x.parent.left == x) {
                x.parent.left = y;    // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
            } else {
                x.parent.right = y;    // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
            }
        }

        // 将 “x” 设为 “y的左孩子”
        y.left = x;
        // 将 “x的父节点” 设为 “y”
        x.parent = y;
    }

    private void rightRotate(RBTNode<T> y) {
        // 设置x是当前节点的左孩子。
        RBTNode<T> x = y.left;

        // 将 “x的右孩子” 设为 “y的左孩子”；
        // 如果"x的右孩子"不为空的话，将 “y” 设为 “x的右孩子的父亲”
        y.left = x.right;
        if (x.right != null) {
            x.right.parent = y;
        }

        // 将 “y的父亲” 设为 “x的父亲”
        x.parent = y.parent;

        if (y.parent == null) {
            this.mRoot = x;            // 如果 “y的父亲” 是空节点，则将x设为根节点
        } else {
            if (y == y.parent.right) {
                y.parent.right = x;    // 如果 y是它父节点的右孩子，则将x设为“y的父节点的右孩子”
            } else {
                y.parent.left = x;    // (y是它父节点的左孩子) 将x设为“x的父节点的左孩子”
            }
        }

        // 将 “y” 设为 “x的右孩子”
        x.right = y;

        // 将 “y的父节点” 设为 “x”
        y.parent = x;
    }
}


class RBTNode<T extends Comparable> {
    boolean    color;        // 颜色
    T          key;                // 关键字(键值)
    RBTNode<T> left;    // 左孩子
    RBTNode<T> right;    // 右孩子
    RBTNode<T> parent;    // 父结点

    public RBTNode(T key, boolean color, RBTNode<T> parent, RBTNode<T> left, RBTNode<T> right) {
        this.key = key;
        this.color = color;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }
}

