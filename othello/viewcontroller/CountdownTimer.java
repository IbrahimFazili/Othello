package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.util.Observable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer Object to create a timer. This object provides some basic controls of the timer (start, pause, reset, stop)
 * and takes care of the underlying control of the java.util.Timer and java.util.TimerTask
 * */
public class CountdownTimer extends Observable {

    private int[] totalTime = { 5, 0};
    private int[] timeRemaining;
    private Timer timer;
    private TimerTask updateTask;
    private char label;
    private boolean isRunning;

    /**
     * Initialize this CountdownTimer Object
     *
     * @param min Total minutes for the countdown (must be greater than 0). In case of invalid value,
     *            defaults to 0
     * @param sec Total Seconds for the countdown (must be less than 60). In case of invalid range,
     *            defaults to 59 sec
     * @param label Label for this timer to identify this timer
     * */
    public CountdownTimer(int min, int sec, char label) {
        this.totalTime[0] = min < 0 ? 0 : min;
        this.totalTime[1] = sec < 0 || sec > 59 ? 59 : sec;
        this.timeRemaining = new int[2];
        this.timeRemaining[0] = this.totalTime[0];
        this.timeRemaining[1] = this.totalTime[1];
        this.label = label;

        // create a timer task to update GUI every second
        this.reinitializeTimerTask();

        this.timer = new Timer();
        this.isRunning = false;
    }

    /**
     * @return the total time this timer is initialized with
     * */
    public int[] getTotalTime() {
        int[] ret = { this.totalTime[0], this.totalTime[1] };
        return ret;
    }

    /**
     * @return the remaining time
     * */
    public int[] getRemainingTime() {
        int[] ret = { this.timeRemaining[0], this.timeRemaining[1] };
        return ret;
    }

    /**
     * @return the label for this timer. Can be used to identify who this timer belongs to
     * */
    public char getLabel() { return this.label; }

    public boolean isRunning() { return this.isRunning; }

    /**
     * Cancel the current updateTask and reset remaining time to total time
     * */
    public void resetTimer() {
        if(this.isRunning) { this.updateTask.cancel(); }
        this.isRunning = false;
        this.timeRemaining[0] = this.totalTime[0];
        this.timeRemaining[1] = this.totalTime[1];
    }

    public void pauseTimer() {
        this.isRunning = false;
        this.updateTask.cancel();
    }

    /**
     * Stop this timer and kill the timer thread to prevent the application
     * from being suspended in the background
     * */
    public void killTimer() { this.timer.cancel(); this.isRunning = false; this.timer.purge(); }

    /**
     * Update the observers of the new remaining time. This method is used
     * to update the GUI of the updated remaining time
     * */
    private void updateTime() {
        if(this.timeRemaining[1] <= 0) {
            this.timeRemaining[0] = this.timeRemaining[0] == 0 ? 0 : this.timeRemaining[0] - 1;
        }

        this.timeRemaining[1] = this.timeRemaining[1] <= 0 ? 59 : this.timeRemaining[1] - 1;
        this.notifyObservers();
    }

    /**
     * Initialize the this.updateTask with a new TimerTask to start the countdown
     * */
    private void reinitializeTimerTask() {
        this.updateTask = new TimerTask() {
            @Override
            public void run() {
                if(timeRemaining[0] <= 0 && timeRemaining[1] <= 0) {
                    this.cancel();
                }
                else {
                    updateTime();
                }
            }
        };
    }

    /**
     * Start the timer that updates the remaining time every second
     * */
    public void startTimer() {
        this.reinitializeTimerTask();
        this.timer.scheduleAtFixedRate(this.updateTask, 0, 1000);
        this.isRunning = true;
    }

}
