import java.util.Random;

/**
 * The class is responsible for storing information about the location of bricks and modifying their coordinates.
 * @author Artur Cieœlak
 * @version 1.0
 * @since 26.04.2017
 */
public class Shape {
	//Random variable
	private int rand;
	//Three-dimensional array containing local coordinates of all shapes
	private int[][][] allShapes;

	private final int gridSize;
	//Coordinates of displacement vector
	private int X,Y;
	//Two-dimensional array storing local shape coordinates
	private int[][] shape=new int[4][2];
	
	/**
	 * Constructor of 'Shape' class 
	 * @param x Coordinate of the brick in the local coordinate system
	 * @param gridSize Size of grid
	 */
	public Shape(int x,int gridSize){
		this.X=x;
		this.Y=1;	
		this.gridSize=gridSize;
		
		//Create a 'Random' class object
		Random gen=new Random();
		//Generate a random integer from 0 to 7
		rand=gen.nextInt(7);
		//Start falling
		DisplayTable.setFalling(true);
		
		allShapes=new int[][][]{{{-1,0},{0,0},{1,0},{2,0}},		//IShape
							{{-1,0},{0,0},{1,0},{1,1}},			//LShape
							{{-1,0},{0,0},{1,0},{0,1}},			//TShape
							{{0,0},{0,1},{1,0},{1,1}},			//OShape
							{{-1,0},{0,0},{1,0},{-1,1}},		//L2Sape
							{{-1,0},{0,0},{0,1},{1,1}},			//ZShape
							{{-1,1},{0,1},{0,0},{1,0}}};		//Z2Shape
							//Copy shape coordinates to a new array
							for(int i=0;i<4;i++){
								for(int j=0;j<2;j++){
									shape[i][j]=allShapes[rand][i][j];
									}
								}

	}
	/**
	 * Converts local coordinates to global coordinates
	 * @param drawShape A two-dimensional array for storing global coordinates
	 * @return A two-dimensional array
	 */
	public int[][] setDrawShape(int[][] drawShape){
		for(int i=0;i<4;i++){
				drawShape[i][0]=shape[i][0]*gridSize+X*gridSize-gridSize;
				drawShape[i][1]=shape[i][1]*gridSize+Y*gridSize-gridSize;
		}
		return drawShape;
	}
	
	/**
	 * Copies the coordinates of the brick to the 'gridTab'.
	 */
	public void copyXY(){

		for(int i=0;i<4;i++){
			//first gridTab index
			int k=shape[i][0]+X-1;
			//second gidTab index
			int z=shape[i][1]+Y-1;
			try{
				//Copying the location of the brick to the 'gridTab' table. 
				DisplayTable.setGridTab(k,z);
			}catch(Exception e)
			{
				System.err.println(e);
			}
		}
	}
	/**
	 * Turns the brick 90 degrees to the right. 
	 */
	public void rotateRight(){
		//If the shape is not a square
		if(rand!=3){
			//Turn the brick 90 degrees to the right.
			for(int i=0;i<4;i++){
				int s=shape[i][0];
					shape[i][0]=-shape[i][1];
					shape[i][1]=s;
			}

			//If new location is not possible
			if(!DisplayTable.checkMove(shape, X, Y)){
				//Turn the brick 90 degrees to the left.
				for(int i=0;i<4;i++){
					int s=shape[i][0];
						shape[i][0]=shape[i][1];
						shape[i][1]=-s;
				}
			}
		}
	}
	/**
	 * Turns the brick 90 degrees to the left.
	 */
	public void rotateLeft(){
		//If the shape is not a square
		if(rand!=3){
			//Turn the brick 90 degrees to the left.
			for(int i=0;i<4;i++){
				int s=shape[i][0];
					shape[i][0]=shape[i][1];
					shape[i][1]=-s;
			}

			//If new location is not possible
			if(!DisplayTable.checkMove(shape, X, Y)){
				//Turn the brick 90 degrees to the right.
				for(int i=0;i<4;i++){
					int s=shape[i][0];
						shape[i][0]=-shape[i][1];
						shape[i][1]=s;
				}
			}
		}
	}
	/**
	 * Increase the Y coordinate.
	 */
	public void incY(){
			//Increase the Y coordinate.
			Y++;
			//If new location is not possible
			if(!DisplayTable.checkMove(shape,X,Y)){
			//Decrease the Y coordinate.
			--Y;
			//Stop falling
			DisplayTable.setFalling(false);
			//Save coordinates
			copyXY();
		}
	}
	/**
	 * Increase the X coordinate.
	 */
	public void incX(){
		//Increase the X coordinate.
		X++;
		//If new location is not possible
		if(!DisplayTable.checkMove(shape, X, Y)){
			//Decrease the X coordinate.
			--X;
		}
	}
	/**
	 * Decrease the X coordinate. 
	 */
	public void decX(){
		//Decrease the X coordinate.
		X--;
		//If new location is not possible
		if(!DisplayTable.checkMove(shape, X, Y)){
			//Increase the X coordinate.
			++X;
		}
	}
	/**
	 * Return 'rand' value
	 * @return A random integer from 0 to 7
	 */
	public int getRand(){
		return rand;
	}
	
}

	