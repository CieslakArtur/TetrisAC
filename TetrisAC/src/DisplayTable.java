/**
 * The class is responsible for processing information about the current view.
 * @version 1.0
 * @since 26.04.2017
 */
public class DisplayTable {

	private static boolean[][] gridTab=new boolean[10][20];
	private static int score=0;
	private static boolean falling=true;
	private static boolean gameOver=false;

	/**
	 * The function verifies that the current position of falling bricks does not interfere 
	 * with any of the elements in the gridTab table. 
	 * @param shape An two-dimensional array containing the coordinates of the block elements.
	 * @param X	coordinate of the displacement vector
	 * @param Y	coordinate of the displacement vector
	 * @return Boolean value
	 */
	public static boolean checkMove(int shape[][],int X,int Y){
		//Auxiliary boolean variable
		boolean b=true;
		for(int i=0;i<4;i++){
			//first gridTab index
			int k=shape[i][0]+X-1;		
			//second gidTab index
			int z=shape[i][1]+Y-1;
			//If the coordinate 'k' exceeds the range of the array
			if(k<0 || k>9){
					b=false;
				//If the coordinate 'z' exceeds the range of the array
				}else if(z<0 || z>19){
					b=false;
				}else{
					try
					{	
						//If the cell of the array is not empty
						if(gridTab[k][z]==true){
							b=false;
						}
					}catch(Exception e){
						System.err.println(e);
					}
				}
		}
		return b;
	}
	/**
	 * This function removes the filled lines from the 'gridTab' table.
	 */
	public static void eraseLine(){
		//Row number
		int n=19;
		//New temporary table
		boolean[][] tab=new boolean[10][20];
		for(int i=19;i>0;i--){
			for(int j=0;j<10;j++){
				//If at least one cell in 'gridTab' table is empty
				if(!gridTab[j][i]){
					//Copying a row to a new table
					for(int k=0;k<10;k++){
						tab[k][n]=gridTab[k][i];
						}
					n--;
					break;
				}
			}
		}
		gridTab=tab;
		//Update player score
		score=score+(n)*(n)*10;
	}
	/**
	 * Returns the current result as a string
	 * @return Current String result
	 */
	public static String getScore(){
		return Integer.toString(score);
	}
	/**
	 * Informs whether the game is over.
	 * @return Boolean value
	 */
	public static boolean gameOver(){
		gameOver=false;
		for(int i=0;i<10;i++){
			if(gridTab[i][1]){
				gameOver=true;
			}
		}
		return gameOver;
	}
	/**
	 * Resets static variables of the DisplayTable class to their initial values.
	 */
	public static void resetTable(){
		gridTab=new boolean[10][20];
		score=0;
		falling=true;
	}
	/**
	 * Returns the value of a given cell from the 'gridTab' table. 
	 * @param i The column number of the 'grid Tab' table
	 * @param j The row number of the 'grid Tab' table
	 * @return Boolean value
	 */
	public static boolean checkGridTab(int i,int j){
		if(i>=0 && i<10 && j>=0 && j<20){
			return gridTab[i][j];
		}else{
			return false;
		}
	}
	/**
	 * Sets 'true' in cells with given coordinates. 
	 * @param i The column number of the 'gridTab' table
	 * @param j The row number of the 'gridTab' table
	 */
	public static void setGridTab(int i,int j){
		if(i>=0 && i<10 && j>=0 && j<20){
			gridTab[i][j]=true;
		}else{
			gridTab[i][j]=false;
		}
	}
	/**
	 * Returns the value of 'falling'.
	 * @return Boolean value
	 */
	public static boolean isFalling(){
		return falling;
	}
	/**
	 * Sets the value of 'falling' 
	 * @param b boolean parameter
	 */
	public static void setFalling(boolean b){
		falling=b;
	}
	/**
	 * Returns the value of the 'gameover' variable.
	 * @return Boolean value
	 */
	public static boolean isGameOver(){
		return gameOver;
	}
	
}







