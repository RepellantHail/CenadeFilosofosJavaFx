package com.example.filosofos;

public class Philosopher implements Runnable {
    private int philosopherIndex;
    private Object leftFork;
    private Object rightFork;
    private int foodLeft = 10;
    private CenaController cenaController;

    public Philosopher(Object leftFork, Object rightFork, CenaController cenaController, int philosopherIndex) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.cenaController = cenaController;
        this.philosopherIndex = philosopherIndex;
    }

    private void doAction(int philosopherIndex, String action) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " " + action);
        Thread.sleep((int) (Math.random() * 3000));

        cenaController.updatePhilosopherStatus(philosopherIndex, action);
    }

    @Override
    public void run() {
        try {
            while (true) {
                doAction(philosopherIndex, "Thinking");
                synchronized (leftFork) {
                    doAction(philosopherIndex, "Picked up left fork");
                    synchronized (rightFork) {
                        doAction(philosopherIndex, "Picked up right fork and started eating");
                        doAction(philosopherIndex, "Put down right fork");
                    }
                    doAction(philosopherIndex, "Put down left fork and start thinking");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
    }
}
