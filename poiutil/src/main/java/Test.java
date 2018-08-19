import parseexcel.EventModelParseExcel;
import parseexcel.ExcelReader;
import parseexcel.ParseExcel;

import java.util.List;

/**
 * Created by Administrator on 2018/8/18.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        String file = "C:\\Users\\Administrator\\Desktop\\test.xlsx";
        ExcelReader reader = new ExcelReader() {
            public void getRows(int sheetIndex, int curRow, List<String> rowList) {

                System.out.println("Sheet:" + sheetIndex + ", Row:" + curRow + ", Data:" + rowList);

            }
        };
        reader.process(file, 1);
    }
}
