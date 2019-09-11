import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * lixiang
 * 2019-9-11
 */

/**
 * 解析在文本文档中类似于excel格式的表格数据
 * 由于在个别文本中的特殊性，需要将除标题外的行中的数据的前两列合并成一列，合并使用的changeList()方法，可以在40行左右修改
 */
public class ParsedTextTable {

    private BufferedReader bufferedReader = null;

    private List<List<String>> strList = null;

    public ParsedTextTable(String file) {
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
            strList = new ArrayList();
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {

                char[] chars = line.toCharArray();
                ArrayList<String> tempList = new ArrayList<>();
                String tempStr = "";

                for (int i = 0; i < chars.length; i++) {
                    if (("" + chars[i]).indexOf(" ") == -1 && ("" + chars[i]).indexOf("\t") == -1) {
                        tempStr += String.valueOf(chars[i]);
                    } else {
                        tempList.add(tempStr);
                        tempStr = "";
                    }
                }

                tempList.add(tempStr);

                //从第二行开始的数据，前两列合并起来
                if (strList.size() != 0) {
                    strList.add(changeList(tempList));
                } else {
                    //第一行是名称，不能合并前两列
                    strList.add(tempList);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文本总行数
     *
     * @return
     */
    public int getRows() {
        return strList.size();
    }

    /**
     * 获取文本总列数
     *
     * @return
     */
    public int getColumns() {
        return strList.get(0).size();
    }


    /**
     * @param column 0-size
     * @param row    0-size
     * @return 传入行数列数，返回对应的值
     */
    public String getCell(int column, int row) {
        return strList.get(row - 1).get(column - 1);
    }

    /**
     * 返回整个文件的解析数据，存在list中
     * @return
     */
    public List<List<String>> getStrList() {
        return strList;
    }

    /**
     * 由于本文本的特殊性，需要将第一列20130101和第二列00：00：00合并成一列处理
     * 将文件在Excel中打开即可看到区别
     *
     * @param list
     * @return
     */
    public List<String> changeList(List<String> list) {
        list.set(0, list.get(0) + list.get(1));
        list.remove(1);
        return list;
    }

}
