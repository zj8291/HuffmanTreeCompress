package com.z;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 根据字符串生成相应的哈夫曼树。
 * 生成哈夫曼树需要的信息：元素个数、元素权值
 * 依靠InputProcess类提供信息(元素个数和元素权值)创建哈夫曼树
 *
 * @author z
 */
public class HuffmanTree {

    //String test;
    private int leafNumber;   //叶子节点个数
    private int nodeNumber;   //总结点个数
    List<Code> codes;
    List<Node> leafNodes, handledNode;

    public HuffmanTree(String input) {
        // TODO Auto-generated constructor stub
        codes = InputProcess.getEachWeight(input);
        leafNumber = codes.size();
        nodeNumber = 2 * leafNumber - 1; //n个叶子节点的Huffman树的节点个数为2n-1(huffman树没有度数为1个节点)
    }

    //根据codes创建一个哈夫曼树，返回其根节点
    public Node createHuffmanTree() {
        leafNodes = new ArrayList<Node>();
        //优先构造叶子节点
        for (int i = 0; i < getLeafNumber(); i++) {
            leafNodes.add(new Node(codes.get(i).code, codes.get(i).time));
            leafNodes.get(i).setID();
        }
        System.out.println(leafNodes.size());
        List<Node> unhandlingNode = leafNodes;
        //leafNodes.clear();
        handledNode = new ArrayList<Node>();
        Node[] min = new Node[2];

        //min[0]为最小，min[1]为次小
        for (int i = unhandlingNode.size(); unhandlingNode.size() > 1; i--) {  //当未处理的节点只剩下根节点时，树创建完成
//			min[0]=findMinWeight(unhandlingNode).get(0);
//			min[1]=findMinWeight(unhandlingNode).get(1);

            min = findMinWeight(unhandlingNode).toArray(new Node[0]);//获取权值最小的两个节点
            if (min[0].isLeaf()) {
                handledNode.add(min[0]);
            }
            if (min[1].isLeaf()) {
                handledNode.add(min[1]);
            }
            System.out.println(unhandlingNode.size());

        }

        return unhandlingNode.get(0);   //返回unhandling的最后一个节点，及根节点

//		for(int i=0;i<unhandlingNode.size();i++) {
//			System.out.println(unhandlingNode.get(i).getMassage()+"  "+unhandlingNode.get(i).getWeight());
//		}
    }

    public List<Node> findMinWeight(List<Node> unhandlingNode) {
        List<Node> min = new ArrayList<Node>(2);
        Node min1, min2; //min1为未处理的node中权值最小的，min2为未处理的node中权值次小的
        Node sum;
        min1 = unhandlingNode.get(0);
        min2 = unhandlingNode.get(unhandlingNode.size() - 1);
//		System.out.println("min1="+min1.massage+" 出现次数"+min1.getWeight());
//		System.out.println("min2="+min2.massage+" 出现次数"+min2.getWeight());
        System.out.println();
        for (int i = 0; i < unhandlingNode.size(); i++) {
            //min1
            if (min1.getWeight() > unhandlingNode.get(i).getWeight()) {
                //若min1的权值大于于当前所遍历的node的权值
                min1 = unhandlingNode.get(i);
            }
        }
        String temp1 = min1.getID();
        for (int i = 0; i < unhandlingNode.size(); i++) {
            //min2
            if (min2.getWeight() > unhandlingNode.get(i).getWeight() && min2.getWeight() >= min1.getWeight()) {
                if (unhandlingNode.get(i).getID() != min1.getID()) {  //通过ID辨认min1和min2
                    min2 = unhandlingNode.get(i);
                }
            }
        }
        String temp2 = min2.getID();
        System.out.println("ID " + temp1 + " min1=" + min1.getMassage() + " 出现次数" + min1.getWeight());
        System.out.println("ID " + temp2 + " min2=" + min2.getMassage() + " 出现次数" + min2.getWeight());

        //在unhandingNode中添加一个新的根节点,并确定关系
        sum = new Node(min1, min2);
        min1.setParent(sum);
        min2.setParent(sum);
        sum.setID();
        sum.setWeight(min1.getWeight() + min2.getWeight());   //将新节点的权值设定为两个权值最小节点的和

        unhandlingNode.add(sum);

        for (int i = 0; i < unhandlingNode.size(); i++) {
            System.out.println(unhandlingNode.get(i).getMassage() + "->" + unhandlingNode.get(i).getWeight());
        }

        //删除unhandlingNode中的min1和min2
        unhandlingNode.remove(min1);
        unhandlingNode.remove(min2);
        System.out.println();
        for (int i = 0; i < unhandlingNode.size(); i++) {
            System.out.println(unhandlingNode.get(i).getMassage() + "->" + unhandlingNode.get(i).getWeight());
        }

        min.add(min1);
        min.add(min2);

        return min;
    }

    //从哈夫曼树中获取哈夫曼编码
    public void getHuffmanCode() {  //传入整棵树的根节点
        //List huffmanCode = new  ArrayList<String>();
        //StringBuilder string=new StringBuilder();
        Node root = createHuffmanTree();
        //List<Node> nodeList=leafNodes;
        System.out.println("****************");
        if (root.isLeaf()) {  //若输入的根节点就为叶子节点
            System.out.println("输入字符太短！");
        } else {
            for (int i = 0; i < leafNumber; i++) {  //遍历每一个叶子节点
                //System.out.println(i);
                Node current = handledNode.get(i);
                StringBuilder huffmanCode = new StringBuilder();
                while (current != root) {
                    /**
                     * 若该节点的夫节点的左节点时其本身时,判断其为其父节点的左孩子，否则为右孩子
                     */
                    if (current.getParent().getLnode().getID().equals(current.getID())) {
                        //左孩子
                        huffmanCode.append('0');  //左孩子为0
                        current = current.getParent();  //继续向上层推进，直到根节点
                    } else {
                        //右孩子
                        huffmanCode.append('1');
                        current = current.getParent();
                    }
                }
                //此处的HuffmanCode为该叶子节点Huffman编码的倒序;
                huffmanCode = huffmanCode.reverse();
                System.out.println(handledNode.get(i).getMassage() + "  " + current.getWeight() + " " + huffmanCode.toString());
                handledNode.get(i).setHuffmanCode(huffmanCode.toString());
            }
        }
    }

    public int getLeafNumber() {
        return leafNumber;
    }

    public void printInputMassage() {
        for (int i = 0; i < codes.size(); i++) {
            System.out.println(codes.get(i).code + "出现了" + codes.get(i).time + "次");
        }
    }

//	public static void main(String[] args) {
//		String string="dshjfasdfasdfasfdadsasdfads";
//		List<Code> list = InputProcess.getEachWeight(string);
////		for(int i=0;i<list.size();i++) {
////			System.out.println(list.get(i).code+"出现了"+list.get(i).time+"次");
////		}
//	}

}
