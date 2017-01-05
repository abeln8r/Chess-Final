   package util;  
   public interface SparseAble<anyType>
   {
      public int size();		//return number of occupied elements
      public int numRows();	//return logical number of rows
      public int numCols();	//return logical number of columns
      public boolean add(int row, int col, anyType x);
   									//adds x at row, col and returns true.  If element already exists, doesn't add it and returns false.
      public anyType get(int row, int col);
   									//returns element at row, col.   returns null if no element there
      public anyType set(int row, int col, anyType x);
   									//changes element at row, col to x  and returns old value.  If element no element exists, returns null.
      public anyType remove(int row, int col);
      								//remove and return the element at row, col
      public boolean isEmpty();
      								//true if there are no occupied elements
      public boolean contains(anyType x);								
   									//returns true if sparse matrix contains x	
      //nice if SparseMatrix has a toString return elements in grid format
      //constructor:   SparseMatrix<anyType> sm = new SparseMatrix(int numRows, int NumCols);
   }