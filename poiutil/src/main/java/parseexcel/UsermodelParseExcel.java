package parseexcel;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 解析Excel 工具类
 *
 * @author duxing
 *
 */
public class UsermodelParseExcel implements ParseExcel {
    private int columns = 0;
    private int totalRows=0;
    private int[] rows;
    private Map<String,CellRangeAddress> cellRangeAddressMap=new HashMap<String,CellRangeAddress>();
    private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public List<List<CustomCell>> parseWorkbook(InputStream inputStream) throws Exception {
        // TODO 待完成 后续规划实现
        return null;
    }
    public List<List<CustomCell>> parseFirstSheet(InputStream inputStream) throws Exception {
        Workbook wb = WorkbookFactory.create(inputStream);
        Sheet sheet = wb.getSheetAt(0);
        return parseSheet(sheet);
    }
    /**
     * 解析sheet
     *
     * @param sheet s
     *
     * @return r
     */
    private List<List<CustomCell>> parseSheet(Sheet sheet) {
        List<List<CustomCell>> list = new ArrayList<List<CustomCell>>();
        init(sheet);
        for (int r = 0; r < totalRows + 1; r++) {
            Row row = sheet.getRow(r);
            if (validateRow(row)) {
                list.add(parseRow(row, r));
            }else{
                for (int i = 0; i < columns; i++) {
                    rows[i]+=1;
                }
            }
        }
        return list;
    }
    public int getColumns() {
        return columns;
    }

    public int getTotalRows() {
        return totalRows;
    }
    private void init(Sheet sheet) {
        totalRows = sheet.getLastRowNum();
        int count=sheet.getNumMergedRegions();
        cellRangeAddressMap.clear();
        for (int j = 0; j < count; j++) {
            CellRangeAddress cellRangeAddress=sheet.getMergedRegion(j);
            int x=cellRangeAddress.getFirstRow();
            int y=cellRangeAddress.getFirstColumn();
            cellRangeAddressMap.put(x+":"+y, cellRangeAddress);
        }
        //前30行找最大列数
        for (int i = 0; i < 30&&i<totalRows; i++) {
            int maxColumns=0;
            int y=0;
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            Cell cell=row.getCell(y);
            while (cell!=null&&cell.getCellType()!= Cell.CELL_TYPE_BLANK) {
                CellRangeAddress cellRangeAddress=cellRangeAddressMap.get(i+":"+y);
                int colSpan=0;
                if(cellRangeAddress!=null){
                    colSpan=cellRangeAddress.getLastColumn()-cellRangeAddress.getFirstColumn();
                }
                maxColumns+=colSpan+1;
                cell=row.getCell(++y+colSpan);
            }
            if(columns<maxColumns){
                columns=maxColumns;
            }
        }
        rows=new int[columns];
        for (int j = 0; j < rows.length; j++) {
            rows[j]=0;
        }
    }

    /**
     * 解析row
     *
     * @param row r
     * @param index i
     * @return r
     */
    private List<CustomCell> parseRow(Row row, int index) {
        List<CustomCell> customRow = new ArrayList<CustomCell>();
        int length=columns;
        for (int c = 0; c < length; c++) {
            if(index!=rows[c]){
                continue;
            }
            Cell cell = row.getCell(c);

            CellRangeAddress cellRangeAddress=cellRangeAddressMap.get(index+":"+c);
            int rowSpan=0,colSpan=0;
            if(cellRangeAddress!=null){
                rowSpan=cellRangeAddress.getLastRow()-cellRangeAddress.getFirstRow();
                colSpan=cellRangeAddress.getLastColumn()-cellRangeAddress.getFirstColumn();
            }
            CustomCell customCell = new CustomCell(index, c, rowSpan, colSpan);
            customRow.add(customCell);
            for (int i = c; i < c+colSpan+1; i++) {
                rows[i]+=rowSpan+1;
            }
            c+=colSpan;
            if (null == cell || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                customCell.setValue("");
                continue;
            }
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                if (HSSFDateUtil.isCellDateFormatted(cell)) { // Excel Date类型处理
                    Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                    customCell.setValue(fmt.format(date));
                } else {// 数值类型数据处理
                    DecimalFormat df = new DecimalFormat();
                    String value = df.format(cell.getNumericCellValue());
                    customCell.setValue(value.replace(",", ""));
                }
                continue;
            }
            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                customCell.setValue(cell.getStringCellValue());
                continue;
            }
            if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                customCell.setValue(String.valueOf(cell.getBooleanCellValue()).trim());
                continue;
            }
            //公式解析
            if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                try {
                    customCell.setValue(String.valueOf(cell.getNumericCellValue()));
                } catch (IllegalStateException e) {
                    customCell.setValue(String.valueOf(cell.getRichStringCellValue()));
                }
            }
        }
        return customRow;
    }

    /**
     * 校验行有效性
     *
     * @return r
     */
    private boolean validateRow(Row row) {
        if (row == null) {
            return false;
        }
        for (int i = 0; i < columns; i++) {
            Cell cell=row.getCell(i);
            if (null != cell&& cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                return true;
            }
        }
        return false;
    }

}
