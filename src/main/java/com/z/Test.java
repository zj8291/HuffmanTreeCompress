package com.z;

public class Test {

    public static void main(String[] args) {
        String string = "efsdfsdfasfaerfsdfearefeargsdfggaerfeargasdgaerhyertye4";
        HuffmanTree huffmanTree = new HuffmanTree(string);
//		System.out.println(string.length());
//		System.out.println("有"+huffmanTree.getLeafNumber()+"个叶子节点");
//		huffmanTree.printInputMassage();
        huffmanTree.createHuffmanTree();
        huffmanTree.getHuffmanCode();
        for (int i = 0; i < huffmanTree.getLeafNumber(); i++) {
            System.out.println(huffmanTree.handledNode.get(i).getMassage() + "的huffmanCode是" + huffmanTree.handledNode.get(i).getHuffmanCode());
        }
    }
}
