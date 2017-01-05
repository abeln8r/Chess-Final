package util;

import java.util.ArrayList;

public class SparseMatrix<anyType> implements SparseAble<anyType> {

    private int rows, cols;
    private ArrayList<SparseNode<anyType>> list;

    public SparseMatrix(int row, int col) {
        rows = row;
        cols = col;
        list = new ArrayList();
    }

    public int size() {
        return list.size();
    }

    public int numRows() {
        return rows;
    }

    public int numCols() {
        return cols;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
    public boolean isValid(Location loc) {
         return loc.getRow() < rows && loc.getRow() >= 0 && loc.getCol() < cols && loc.getCol() >= 0;
    }
    //returns whether there is anything between the two locations on the same Row
    public boolean isColImpeded(Location start, Location end) {
        if (start.getRow() != end.getRow())//locations must be on same row
        {
            return true;
        }
        for (int c = Math.min(start.getCol(), end.getCol()) + 1; c < Math.max(start.getCol(), end.getCol()); c++) {
            if (get(start.getRow(), c) != null)// 
            {
                return true;
            }
        }
        return false;
    }

    //adds x at row, col and returns true.  If element already exists, doesn't add it and returns false.
    public boolean add(int row, int col, anyType x) {
        if (row >= numRows() || row < 0 || col >= numCols() || col < 0) {     
				throw new IndexOutOfBoundsException();
        }
        SparseNode add = new SparseNode(row, col, x);
        int i = 0;
        while (i < list.size() && list.get(i).getKey(cols) < add.getKey(cols)) {
            i++;
        }
        list.add(i, add);
        return true;
    }

    //returns element at row, col.   returns null if no element there
    public anyType get(int row, int col) {
        for (SparseNode item : list) {
            if (item.getRow() == row && item.getCol() == col) {
                return (anyType) item.getData();
            }
        }
        return null;
    }

    public anyType get(Location loc) {
        return get(loc.getRow(), loc.getCol());
    }

    //changes element at row, col to x  and returns old value.  If element no element exists, returns null.
    public anyType set(int row, int col, anyType x) {
        anyType old = get(row, col);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRow() == row && list.get(i).getCol() == col) {
                list.set(i, new SparseNode(row, col, x));
            }
        }
        return old;
    }

    public anyType remove(int row, int col) {
        anyType old = get(row, col);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRow() == row && list.get(i).getCol() == col) {
                list.remove(i);
                break;
            }
        }
        return old;
    }

    public boolean contains(anyType x) {
        for (SparseNode item : list) {
            if (item.getData().equals(x)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        String ans = "";
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                anyType item = get(row, col);
                if (item == null) {
                    ans += "-";
                } else {
                    ans += item;
                }
            }
            ans += "\n";
        }
        return ans;
    }
}
