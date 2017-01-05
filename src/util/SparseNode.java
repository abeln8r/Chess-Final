   package util;  
   public class SparseNode<anyType>
   {
      private int row,col;
      private anyType data;
      public SparseNode(int row, int col, anyType x)
      {
         this.row = row;
         this.col = col;
         data = x;
      }
      public int getRow()
      {
         return row;
      }
      public int getCol()
      {
         return col;
      }
      public anyType getData()
      {
         return data;
      }
      public int getKey(int cols)
      {
         return row*cols + col;
      }
   }