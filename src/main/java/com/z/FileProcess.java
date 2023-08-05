package com.z;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * 文件处理类
 *
 * @author z
 * 包含文本文件的读取，压缩，解压缩，文件大小的输出
 */
public class FileProcess {
    private static String newPath;
    private int zeroNumber;
    HuffmanTree huffmanTree;

    //文件压缩
    public HuffmanTree compress(String path, String newPath) throws IOException {
        this.newPath = newPath;
        File file = new File(path);
        InputStream fis = new FileInputStream(file);
        byte[] input = new byte[(int) file.length()];
        fis.read(input);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length; i++) {  //从头开始取
            //System.out.println(byteToBit(input[i]));
            builder.append((char) Integer.parseInt(byteToBit(input[i]).substring(0, 8), 2));
            //System.out.println(builder.toString());
            //System.out.println(Integer.toBinaryString(input[i]));
            //builder.append(Integer.toBinaryString(input[i]));
        }
        String sourse = builder.toString();

        File newFile = new File(newPath);
        OutputStream fos = new FileOutputStream(newFile);
        huffmanTree = new HuffmanTree(sourse);
        huffmanTree.createHuffmanTree();
        huffmanTree.getHuffmanCode();
//		for(int i=0;i<huffmanTree.getLeafNumber();i++) {
//			System.out.println(huffmanTree.handledNode.get(i).getMassage()+"的huffmanCode是"+huffmanTree.handledNode.get(i).getHuffmanCode());
//		}
        StringBuilder compressCode = new StringBuilder();  //压缩编码
        //System.out.println("文件大小"+file.length());
        //System.out.println("节点个数"+huffmanTree.handledNode.size());
        fis = new FileInputStream(file);
        for (int i = 0; i < file.length(); i++) {
            for (int j = 0; j < huffmanTree.handledNode.size(); j++) {
                //System.out.println((char)input[i]+"?"+huffmanTree.handledNode.get(j).getMassage());
                if (input[i] == huffmanTree.handledNode.get(j).getMassage()) {
                    //System.out.println("一次配对");
                    compressCode.append(huffmanTree.handledNode.get(j).getHuffmanCode());
                    break;
                }
            }
        }
        //System.out.println("编码长度"+compressCode.toString().length());
//		for(int i=0;i<compressCode.toString().length();i++) {
//			System.out.println("编码"+compressCode.toString());
//		}
        byte[] b;
        if (compressCode.toString().length() % 8 == 0) {
            b = new byte[compressCode.toString().length() / 8];
        } else {
            b = new byte[compressCode.toString().length() / 8 + 1];
        }
        int index;
        for (index = 0; compressCode.toString().length() >= 8; compressCode.replace(0, 8, "")) {
            //b[i]= Byte.valueOf(compressCode.toString().substring(0, 8));
            //System.out.println(Integer.valueOf(compressCode.toString().substring(0, 8),2));
            int temp = Integer.valueOf(compressCode.toString().substring(0, 8), 2);
            //b[i]=(int)Integer.valueOf(compressCode.toString().substring(0, 8),2);
            b[index] = (byte) temp;
            //System.out.println("byte"+b[index]);
            index++;
            //System.out.println("int"+Integer.toBinaryString(temp));
        }
        zeroNumber = 8 - compressCode.length();
        if (zeroNumber != 8 && zeroNumber != 0) {
            for (int i = zeroNumber; i > 0; i--) {
                compressCode.insert(0, '0');
            }
        }
        System.out.println(compressCode.toString());
        b[index] = (byte) (int) Integer.valueOf(compressCode.toString().substring(0, 8), 2);
        System.out.println(zeroNumber);

        fos.write(b);
        System.out.println(newFile.getParent().replace("\\\\", "\\\\\\\\") + "\\" + "configuration.ini");

        fis.close();
        fos.close();
        return huffmanTree;
//		for(int i=0;i<b.length;i++) {
//			System.out.println("+++++++++++++++++");
//			System.out.println(b[i]);
//		}
    }

    /**
     * 解码
     *
     * @param newPath
     * @throws IOException
     */
    public String decode(String newPath, HuffmanTree huffmanTree) throws IOException {
        //this.huffmanTree=huffmanTree;
        File file = new File(newPath);
        InputStream fis = new FileInputStream(file);
        byte[] b = new byte[(int) (file.length())];
        int size = fis.read(b);
        StringBuilder builder = new StringBuilder();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        for (int i = 0; i < b.length; i++) {
            builder.append(FileProcess.byteToBit(b[i]));
            //System.out.println(String.valueOf(b[i]));
        }
        builder.replace(builder.length() - 8, builder.length() - 8 + zeroNumber, "");
        //System.out.println(builder);
        char[] c = new char[builder.length()];
        c = builder.toString().toCharArray();
        StringBuilder buffer = new StringBuilder();
        /**
         * 基本思路
         * 将c中的元素一个一个的读取，读取同时与叶子节点的huffmancode进行对比进行解码
         */
        StringBuilder decodeMassage = new StringBuilder();
        for (int i = 0; i < c.length; i++) {
            buffer.append(c[i]);
            for (int j = 0; j < huffmanTree.getLeafNumber(); j++) {
                //配对成功
                if (buffer.toString().equals(huffmanTree.handledNode.get(j).getHuffmanCode())) {
                    decodeMassage.append(huffmanTree.handledNode.get(j).getMassage());
                    //配对成功后清空buffer
                    buffer.delete(0, buffer.length());
                    //System.out.println("**"+huffmanTree.handledNode.get(j).getMassage());
                    continue;
                }

            }
        }
        //System.out.println(decodeMassage.toString());
        return decodeMassage.toString();
    }

    //获取每个byte的八位二进制编码
    public static String byteToBit(byte b) {
        String string = new String("" +
                (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1) +
                (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1) +
                (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1) +
                (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1)
        );
        return string;
    }

    public void setNewPath(String newPath) {
        this.newPath = newPath;
    }

    public String getNewPath() {
        return newPath;
    }

//	public String getSourseCode(String path,String newPath) throws IOException {
//		FileProcess fileProcess=new FileProcess();
//		HuffmanTree huffmanTree=fileProcess.compress("C:\\Users\\z\\Desktop\\Test.txt", "C:\\Users\\z\\Pictures\\UbisoftConnect\\Test.huf");
//		String string = fileProcess.decode("C:\\Users\\z\\Pictures\\UbisoftConnect\\Test.huf",huffmanTree);
//		return string;
//	}
//	public static void main(String[] args) throws IOException {
//		FileProcess fileProcess=new FileProcess();
//		HuffmanTree huffmanTree=fileProcess.compress("C:\\Users\\z\\Desktop\\Test.txt", "C:\\Users\\z\\Pictures\\UbisoftConnect\\Test.huf");
//		System.out.println(fileProcess.decode("C:\\Users\\z\\Pictures\\UbisoftConnect\\Test.huf",huffmanTree));
//	}
}
