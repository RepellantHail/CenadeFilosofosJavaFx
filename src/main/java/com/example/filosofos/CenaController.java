package com.example.filosofos;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class CenaController {
    @FXML
    private StackPane pane;
    @FXML
    private Label[] philosopherLabels;
    @FXML
    private Circle table;
    private int philosopherIndex = 0;
    @FXML
    private Label philosopher1Label;
    @FXML
    private Label philosopher2Label;
    @FXML
    private Label philosopher3Label;
    @FXML
    private Label philosopher4Label;
    @FXML
    private Label philosopher5Label;

    private Philosopher[] philosophers;
    private Object[] forks;

    @FXML
    public void initialize() {
        philosopherLabels = new Label[5];
        philosopherLabels[0] = philosopher1Label;
        philosopherLabels[1] = philosopher2Label;
        philosopherLabels[2] = philosopher3Label;
        philosopherLabels[3] = philosopher4Label;
        philosopherLabels[4] = philosopher5Label;

        philosophers = new Philosopher[5];
        forks = new Object[5];

        for (int i = 0; i < philosophers.length; i++) {
            forks[i] = new Object();
        }

        for (int i = 0; i < philosophers.length; i++) {
            Object leftFork = forks[i];
            Object rightFork = forks[(i + 1) % 5];

            if (i == philosophers.length - 1) {
                philosophers[i] = new Philosopher(leftFork, rightFork, this, i);
            } else {
                philosophers[i] = new Philosopher(rightFork, leftFork, this, i);
            }
        }
    }

    public void updatePhilosopherStatus(int philosopherIndex, String status) {
        Platform.runLater(() -> {
            Label philosopherLabel = philosopherLabels[philosopherIndex];
            philosopherLabel.getStyleClass().clear();
            philosopherLabel.setText(status);
            philosopherLabel.setGraphic(getEmoji(status)); // Agregar emoji como gráfico al Label
            philosopherLabel.getStyleClass().add("philosopher-label");

            Node emojiNode = philosopherLabel.getGraphic();
            if (status.equals("Thinking")) {
                breatheAnimation(emojiNode);
            } else {
                emojiNode.getTransforms().clear();
                emojiNode.setOpacity(1.0);
            }

            if (status.equals("Picked up left fork") || status.equals("Picked up right fork and started eating")) {
                blinkAnimation(emojiNode);
            } else {
                emojiNode.setOpacity(1.0);
            }
        });
    }


    private Text getEmoji(String status) {
        Text emoji = new Text();
        emoji.setText(getEmojiForStatus(status));
        emoji.getStyleClass().add("emoji-text");
        return emoji;
    }

    private String getEmojiForStatus(String status) {
        switch (status) {
            case "Thinking":
                return "💭";
            case "Picked up left fork":
                return "🍴";
            case "Picked up right fork and started eating":
                return "🍽️";
            case "Put down right fork":
                return "🍴";
            case "Put down left fork and start thinking":
                return "💭";
            default:
                return status;
        }
    }


    @FXML
    protected void startDinner() {
        for (Philosopher philosopher : philosophers) {
            Thread t = new Thread(philosopher);
            t.start();
        }
    }

    private void breatheAnimation(Node node) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), node);
        scaleTransition.setByX(0.05);
        scaleTransition.setByY(0.05);
        scaleTransition.setCycleCount(Animation.INDEFINITE);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
        scaleTransition.play();
    }

    private void blinkAnimation(Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), node);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.2);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
    }




}