import java.awt.*;
import acm.graphics.*;
import acm.program.*;

public class BouncingBall extends GraphicsProgram {

  private static final int DIAM_BALL = 20;
  private static final double GRAVITY = 3;
  private static final int DELAY = 50;
  private static final double X_START = DIAM_BALL / 2;
  private static final double Y_START = DIAM_BALL /2;
  private static final double X_VEL = 10;
  private static final double BOUNCE_REDUCE = 0;
  private double xVel = X_VEL;
  private double yVel = 0.0;
  private GOval ball;

  public void run() {
    resize(800, 600);
    setup();
    waitForClick();

    // basic algorithm for the game
    while (ball.getX() < getWidth()) {
      moveBall();
      checkForCollision();
      checkRightCollision();
      checkLeftCollision();
      checkTopCollision();
      pause(DELAY);
    }
  }

  // setting up the ball in the canvas
  private void setup() {
    ball = new GOval(X_START, Y_START, DIAM_BALL, DIAM_BALL);
    ball.setFilled(true);
    ball.setFillColor(Color.magenta);
    setBackground(Color.ORANGE);
    add(ball);
  }

  // y velocity keeps on increasing, x velocity remains constant
  private void moveBall() {
    yVel += GRAVITY;
    ball.move(xVel, yVel);
  }

  // raising the ball by the length as it would have gone down the screen
  private void checkForCollision() {
    if(ball.getY() > getHeight() - DIAM_BALL) {
      yVel = - yVel + BOUNCE_REDUCE * yVel;

      double diff = ball.getY() - (getHeight() - DIAM_BALL);
      ball.move(0, -2 * diff);
    }
  }

  // checking for right screen limit
  private void checkRightCollision() {
    if(ball.getX() > getWidth() - DIAM_BALL) {
      xVel = -xVel;

      double diff_2 = ball.getX() -(getWidth() - DIAM_BALL);
      ball.move(-2 * diff_2, 0);
    }
  }

  // checking for left screen limit
  private void checkLeftCollision() {
    if (ball.getX() <= 0) {
      xVel = -xVel;

      double diff_3 = - ball.getX();
      ball.move(2 * diff_3, 0);
    }
  }

  private void checkTopCollision() {
    if(ball.getY() <= 0) {
      yVel = - yVel;

      double diff_4 = - ball.getY();
      ball.move(0, 2 * diff_4);
    }
  }
}
