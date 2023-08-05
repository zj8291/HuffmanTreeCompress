package com.z;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 对输入内容进行处理，获取相应内容：元素个数、元素内容、元素权值
 *
 * @author z
 * 结果返回一个List类型为Code，包含字符内容和出现次数
 */
public class InputProcess {
    //获取输入信息中每一种字符的权值
    public static List<Code> getEachWeight(String input) {

        System.out.println("输入总字符个数：" + input.length());
        List<Code> inputWeight = new ArrayList<Code>();  //创建一个表用于存储字符内容及权值
        Map<Character, Integer> map = Count.getCharMaps(input);
        Object[] allInput = map.keySet().toArray();
        System.out.println("共有" + map.size() + "种字符");

        System.out.println(map);
        for (int i = 0; i < map.size(); i++) {
            inputWeight.add(new Code((Character) allInput[i], map.get(allInput[i])));
        }
        if (map.size() > 256) {
            System.out.println("文件中字符种类过多！！！");

        }
        return inputWeight;
    }

//	public static void main(String[] args) {
////		String s = "abcdad"; //待测试的字符串
////		Map<Character, Integer> result = Count.getCharMaps(s);
////		System.out.println(result);
//		String input = "AAAABBBCCCCCCABCBbbb中的中国";
//		List<Code> list=getEachWeight(input);
//		for(int i=0;i<list.size();i++) {
//			System.out.println(list.get(i).code+"出现了"+list.get(i).time+"次");
//		}
//	}
}

//创建一个Count类，对输入内容进行计数
class Count {
    public static Map<Character, Integer> getCharMaps(String s) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        //对输入内容进行计数
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            Integer count = map.get(c);
            map.put(c, count == null ? 1 : count + 1);
        }
        return map;
    }

}

//创建一个Code类
class Code {
    public char code;
    public int time = 0;  //出现次数默认为0

    public Code(char code, int time) {
        // TODO Auto-generated constructor stub
        this.code = code;
        this.time = time;
    }

    public char getCode() {
        return code;
    }

    public void setCode(char code) {
        this.code = code;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
