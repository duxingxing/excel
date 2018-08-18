package parseexcel;
/**
 *
 * @author duxing
 *
 */
public class CustomCell {
    private int rowIndex;

    private int columnIndex;

    private int rowSpan;

    private int columnSpan;

    private String value;
    public CustomCell(int rowIndex,int columnIndex,int rowSpan,int columnSpan){
        this.rowIndex=rowIndex;
        this.columnIndex=columnIndex;
        this.rowSpan=rowSpan;
        this.columnSpan=columnSpan;
    }
    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    public int getColumnSpan() {
        return columnSpan;
    }

    public void setColumnSpan(int columnSpan) {
        this.columnSpan = columnSpan;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
