package com.example.demo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class SylvesterVsTweety extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PLAYER_SIZE = 50;
    private static final int TWEETY_SIZE = 30;
    private static final int CAT_SIZE = 70;

    private Image sylvesterImage;
    private Image tweetyImage;
    private Image background;

    private double sylvesterX = WIDTH / 2;
    private double sylvesterY = HEIGHT / 2;
    private double tweetyX;
    private double tweetyY;
    private double catX;
    private double catY;

    private boolean gameRunning = false;
    private Random random = new Random();

    @Override
    public void start(Stage primaryStage) {
        loadImages();

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Scene scene = new Scene(new StackPane(canvas), WIDTH, HEIGHT);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                resetGame();
            }
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameRunning) {
                    update();
                    draw(gc);
                }
            }
        }.start();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Sylvester vs Tweety");
        primaryStage.show();

        resetGame();
    }

    private void loadImages() {
        sylvesterImage = new Image("cat.png");
        tweetyImage = new Image("bird.png");
        background = new Image("garden.jpg");
    }

    private void resetGame() {
        sylvesterX = WIDTH / 2;
        sylvesterY = HEIGHT / 2;
        tweetyX = random.nextDouble() * (WIDTH - TWEETY_SIZE);
        tweetyY = random.nextDouble() * (HEIGHT - TWEETY_SIZE);
        catX = random.nextDouble() * (WIDTH - CAT_SIZE);
        catY = random.nextDouble() * (HEIGHT - CAT_SIZE);
        gameRunning = true;
    }

    private void update() {
        // Move the cat randomly
        double catSpeed = 3;
        double dx = sylvesterX - catX;
        double dy = sylvesterY - catY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double catDX = catSpeed * dx / distance;
        double catDY = catSpeed * dy / distance;
        catX += catDX;
        catY += catDY;

        // Check collision
        double collisionDistance = PLAYER_SIZE + CAT_SIZE;
        if (Math.abs(sylvesterX - tweetyX) < collisionDistance && Math.abs(sylvesterY - tweetyY) < collisionDistance) {
            gameRunning = false;
        }
    }

    private void draw(GraphicsContext gc) {
        // Draw background
        gc.drawImage(background, 0, 0, WIDTH, HEIGHT);

        // Draw Sylvester
        gc.drawImage(sylvesterImage, sylvesterX, sylvesterY, PLAYER_SIZE, PLAYER_SIZE);

        // Draw Tweety
        gc.drawImage(tweetyImage, tweetyX, tweetyY, TWEETY_SIZE, TWEETY_SIZE);

        // Draw Cat
        gc.setFill(Color.RED);
        gc.fillRect(catX, catY, CAT_SIZE, CAT_SIZE);

        // Draw game over message
        if (!gameRunning) {
            gc.setFill(Color.BLACK);
            gc.fillText("GAME OVER! Press SPACE to play again.", WIDTH / 2 - 150, HEIGHT / 2);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
