/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.beepsoft.ptools;

/**
 *
 * @author heri
 */
public abstract class DelayedTask extends java.util.TimerTask {

    DelayedTask me = this;

    public DelayedTask(long delay) {
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(this, delay);
    }

}
