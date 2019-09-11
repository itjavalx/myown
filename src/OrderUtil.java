import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by lx
 * 2019年8月7日16:46:24
 * 抽取排序工具类
 */


@SuppressWarnings("all")
public class OrderUtil
{

    /**
     *
     * @param list 需要排序的list
     * @param t 泛型，传入list中存放的对象或者支持传入Map类型
     * @param needToOrderStr 泛型对象中需要排序的字段
     * @param <T>
     * @return 排序好的list
     */

    public <T> List<T> toOrder(List list,T t ,String needToOrderStr)
    {
        try
        {
            ArrayList<T> tempList = new ArrayList();

            Map<String, T> map = new HashMap();

            int tempNum = -1;
            int tempChar = 1;

            if (!(t instanceof Map)){
                needToOrderStr = "get"+needToOrderStr.substring(0,1).toUpperCase()+needToOrderStr.substring(1);
            }

            String invokeStr ="";
            for (Object s : list)
            {

                if(t instanceof  Map){
                    invokeStr = (String) ((Map)s).get(needToOrderStr);
                }else{
                    Class clazz = ((T)s).getClass();
                    Method method = clazz.getMethod(needToOrderStr, new Class[]{});
                    invokeStr = (String) method.invoke(((T)s), new Object[]{});
                }

                int i = checkNum(invokeStr);

                if (i != -1)
                {
                    String tempInt;
                    if (i < 10)
                    {
                        tempInt = "00" + i;
                    }
                    else
                    {
                        if (i < 100) {
                            tempInt = "0" + i;
                        } else {
                            tempInt = "" + i;
                        }
                    }
                    if (map.get(tempInt) == null)
                    {
                        map.put(tempInt, ((T)s));
                    }
                    else
                    {
                        int tempChar1 = 1;
                        for (;;)
                        {
                            String tempStr2;
                            if (tempChar1 < 10) {
                                tempStr2 = "0" + tempChar1;
                            } else {
                                tempStr2 = "" + tempChar1;
                            }
                            if (map.get(tempInt + tempStr2) == null)
                            {
                                map.put(tempInt + tempStr2, ((T)s));
                                break;
                            }
                            tempChar1++;
                        }
                    }
                }
                else
                {
                    if (tempChar < 10) {
                        map.put("xxx00" + tempChar, ((T)s));
                    } else if (tempChar < 100) {
                        map.put("xxx0" + tempChar, ((T)s));
                    } else {
                        map.put("xxx" + tempChar, ((T)s));
                    }
                    tempChar++;
                }
            }
            Object[] objects = map.keySet().toArray();
            Arrays.sort(objects);
            for (int i = 0; i < objects.length; i++) {
                tempList.add((T)map.get(objects[i]));
            }
            return tempList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int checkNum(String str)
    {
        int num = -1;
        char[] chars = str.toCharArray();
        String temp = "";
        for (char aChar : chars) {
            if ((aChar >= '0') && (aChar <= '9')) {
                temp = temp + aChar;
            }
        }
        if (temp != "") {
            num = Integer.parseInt(temp);
        }
        return num;
    }
}
