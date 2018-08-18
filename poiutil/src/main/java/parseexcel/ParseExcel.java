package parseexcel;

/**
 * Created by Administrator on 2018/8/18.
 *//**
 * Created by Administrator on 2018/8/18.
 */

import java.io.InputStream;
import java.util.List;

/**
 * 解析excel 接口 usermodel
 *
 * @author duxing
 *
 */
public interface ParseExcel {
    /**
     * 解析 excel
     * @param inputStream
     * @return
     */
    public List<List<CustomCell>> parseWorkbook(InputStream inputStream)throws Exception ;
    /**
     * 解析sheet
     *
     * @param inputStream
     *
     * @return
     */
    public List<List<CustomCell>> parseFirstSheet(InputStream inputStream)throws Exception ;

    /**
     * 获取列数
     *
     * @return
     */
    public int getColumns();

    /**
     * 获取行数
     *
     * @return
     */
    public int getTotalRows();
}

