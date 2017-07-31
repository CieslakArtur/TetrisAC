import java.awt.EventQueue;

/**
 * The class is responsible for running the application
 * @author Artur Cieslak
 * @version 1.0
 * @since 26.04.2017
 */
public class RunTetris {
	/**
	 * Entry-point of the game. Responsible for creating and starting a new
	 * game instance.
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(()->{
			new GUI();
		});
	}
}
