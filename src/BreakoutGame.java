import acm.graphics.*;
import acm.program.*;
import acm.util.RandomGenerator;
import java.applet.*;
import java.awt.Color;
import java.awt.event.*;

public class BreakoutGame extends GraphicsProgram {
	
	public static final int PADDLE_WIDTH = 100;
	public static final int PADDLE_HEIGHT = 10;
	
	private static final int NTURNS = 2;  		//number of turns
	private static final int PADDLE_Y_OFFSET = 30;
	private static final int NBRICKS_PER_ROW = 10;
	private static final int NBRICKS_ROWS = 10;
	private static final int BRICK_SEP = 4;
	private static final int BRICK_WIDTH = 	(800 - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;
	private static final int BRICK_HEIGHT = 8;
	private static final int DIAM_BALL = 80;
	public static int brick_counter = 100;
	
	private int COUNT_X = 0;
	private int COUNT_Y = 0;
	
	private GRect rect;
	private GRect paddle;
	
	private static final int DELAY = 10;
	private double xVel, yVel;
	private GOval ball;
	
	public void run() {
		for (int i = 0; i < NTURNS; i++) {
			setup();
			waitForClick();
			playGame();
			if(brick_counter == 0) {
				ball.setVisible(false);
				printWinner();
				break;
			}
			if(brick_counter > 0 ) {
				removeAll();
			}
		}
		if ( brick_counter > 0) {
			endGame();
		}
	}
	
	public void init() {
		resize(800, 600);	
		addMouseListeners();
	}
	
	public void playGame() {
		waitForClick();
		getBallVelocity();
		while(true) {
			moveBall();
			if(ball.getY() >= getHeight()) {
				
				break;
			}
		}
	}
	
	public void getBallVelocity() {
		yVel = 4.0;
		xVel = rgen.nextDouble(1.0, 3.0);
		if(rgen.nextBoolean(0.5)) {
			xVel = - xVel;
		}	
	}
	 
	public void setup() {
		// adding the rectangles
		for (int i = 1; i <= NBRICKS_ROWS; i++) {
			for (int j = 1; j <= NBRICKS_PER_ROW +1; j++) {
				rect = new GRect(COUNT_X + 2, 80 + COUNT_Y, BRICK_WIDTH, BRICK_HEIGHT);
				rect.setFilled(true);
				rect.setFillColor(rgen.nextColor());
				add(rect);
				COUNT_X+= BRICK_WIDTH + BRICK_SEP;
				}
			COUNT_X = 0;
			COUNT_Y += BRICK_HEIGHT + BRICK_SEP;
		}
		COUNT_Y = 0;
			
		//adding the BouncingBall
		ball = new GOval(400, 200, DIAM_BALL, DIAM_BALL);
		ball.setFilled(true);
		add(ball);
		//adding the paddle
		double x = getWidth()/2 - PADDLE_WIDTH/2; 
		double y = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
		paddle = new GRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);		
	}
	
	//y velocity keeps on increasing, x velocity remains constant
		private void moveBall() {
			ball.move(xVel, yVel);
			if ((ball.getX() - xVel <= 0 && xVel < 0 )|| (ball.getX() + xVel >= (getWidth() - DIAM_BALL) && xVel>0)) {
				xVel = -xVel;
			}
			
			if ((ball.getY() - yVel <= 0 && yVel < 0 )) {
				yVel = -yVel;
			}
			
			if (ball.getY() > getWidth() - DIAM_BALL) {
				removeAll();
				
			}
			
			GObject collider = getCollidingObject();
			if ( collider == paddle) {
				if(ball.getY() >= getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - DIAM_BALL && ball.getY() < getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - DIAM_BALL + 4) {
					yVel = - yVel;
				}
			}
			
			else if(collider != null) {
				remove(collider);
				brick_counter -=1;
				yVel = - yVel;
			}
			pause(DELAY);
		}
		
		private void endGame() {
			GLabel gameOver = new GLabel ("Game Over", getWidth()/2, getHeight()/2);
			gameOver.setFont("Comic Sans MS-36");
			gameOver.move(-gameOver.getWidth()/2, -gameOver.getHeight());
			gameOver.setColor(Color.RED);
			add (gameOver);
		}
		
		private void printWinner() {
			GLabel Winner = new GLabel ("Winner!!", getWidth()/2, getHeight()/2);
			Winner.setFont("Comic Sans MS-36");
			Winner.move(-Winner.getWidth()/2, -Winner.getHeight());
			Winner.setColor(Color.RED);
			add (Winner);
		}
		
		private GObject getCollidingObject() {
			if((getElementAt(ball.getX(), ball.getY())) != null) {
		         return getElementAt(ball.getX(), ball.getY());
		      }
			else if (getElementAt( (ball.getX() + DIAM_BALL), ball.getY()) != null ){
		         return getElementAt(ball.getX() + DIAM_BALL, ball.getY());
		      }
			else if(getElementAt(ball.getX(), (ball.getY() + DIAM_BALL)) != null ){
		         return getElementAt(ball.getX(), ball.getY() + DIAM_BALL);
		      }
			else if(getElementAt((ball.getX() + DIAM_BALL), (ball.getY() + DIAM_BALL)) != null ){
		         return getElementAt(ball.getX() + DIAM_BALL, ball.getY() + DIAM_BALL);
		      }
			
			else{
		         return null;
		      }
		}		
		
		public void mouseMoved(MouseEvent e) {
			if ((e.getX() < getWidth() - PADDLE_WIDTH/2) && (e.getX() > PADDLE_WIDTH/2)) {
				paddle.setLocation(e.getX() - PADDLE_WIDTH/2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
			}
		}
		
		private RandomGenerator rgen = RandomGenerator.getInstance();
	
}
