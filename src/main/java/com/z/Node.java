package com.z;

/**
 * 节点类
 *
 * @author z
 * 包含：数据域(英语(char类型))、左子树根节点(可以为空)、右子树根节点(可以为空)、父节点(可以为空)、权值
 */
public class Node {
    char massage; //数据域，存储单个字符
    private Node Parent, Lnode, Rnode;
    private int weight;
    private String ID;  //给每个节点设置id,作为辨认每个节点的唯一标志
    private String huffmanCode;
    private static int i = 1;

    //constructor
    public Node(char massage) {
        this.massage = massage;
    }

    public Node(char massage, int weight) {
        this.massage = massage;
        this.weight = weight;
    }

    public Node(char massage, Node Parent) {
        this.massage = massage;
        this.Parent = Parent;
    }

    public Node(Node Lnode, Node Rnode) {
        //this.Parent = Parent;
        this.Lnode = Lnode;
        this.Rnode = Rnode;
    }

    public void setID() {
        ID = String.valueOf(10000 + (i++));
    }

    public String getID() {
        return ID;
    }

    public void setMassage(char massage) {
        this.massage = massage;
    }

    public char getMassage() {
        return massage;
    }

    public Node getParent() {
        return Parent;
    }

    public void setParent(Node parent) {
        Parent = parent;
    }

    public Node getLnode() {
        return Lnode;
    }

    public void setLnode(Node lnode) {
        Lnode = lnode;
    }

    public Node getRnode() {
        return Rnode;
    }

    public void setRnode(Node rnode) {
        Rnode = rnode;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getHuffmanCode() {
        return huffmanCode;
    }

    public void setHuffmanCode(String huffmanCode) {
        this.huffmanCode = huffmanCode;
    }

    //判断是否为叶子节点
    public boolean isLeaf() {
        if (this.getRnode() == null && this.getLnode() == null) {
            return true;
        } else {
            return false;
        }
    }


}
