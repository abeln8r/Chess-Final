 	package util;  
	public class SparseTester
   {
      public static void main(String[]arg)
      {
         SparseMatrix<String> sm = new SparseMatrix(2, 3);
         System.out.println(sm);		
      	/*
      	- - -
      	- - -
      	*/
      	
         if(sm.isEmpty())
            System.out.println("empty");
      	//empty	
      	
         sm.add(0,1,"B");
         sm.add(1,0,"C");
         sm.add(1,1,"D");
         System.out.println(sm);		
      	/*
      	- B -
      	C D -
      	*/
      	
         sm.add(0,0,"E");
         sm.add(0,2,"F");
         sm.add(1,2,"G");
         System.out.println(sm);		
      	/*
      	E B F
      	C D G
      	*/
      	
         System.out.println(sm.get(0,0));
      	//E
         System.out.println(sm.get(0,2));
      	//F
         System.out.println(sm.get(1,2));
      	//G
      	
         String changed = sm.set(0,2,"H");
         System.out.println(changed + " has been changed");
      	//F has been changed
         System.out.println(sm);		
      	/*
      	E B H
      	C D G
      	*/
      	
         if(sm.contains("H"))
            System.out.println(" H has been found");
         else
            System.out.println(" H has NOT been found");
      
         if(sm.contains("X"))
            System.out.println(" X has been found");
         else
            System.out.println(" X has NOT been found");
         //H has been found
      	//X has NOT been found
      	
         changed = sm.remove(0,0);
         System.out.println(changed + " has been removed");
      	//E has been removed
         System.out.println(sm);		
      	/*
      	- B H
      	C D G
      	*/
      	
         sm.remove(1,0);
         sm.remove(1,2);
         System.out.println(sm);		
      	/*
      	- B H
      	- D -
      	*/
         System.out.println(sm.get(0,0));
      	//null
      
      }
   }