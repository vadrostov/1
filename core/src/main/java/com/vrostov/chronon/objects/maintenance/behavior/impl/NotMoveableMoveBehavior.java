package com.vrostov.chronon.objects.maintenance.behavior.impl;

import com.vrostov.chronon.objects.maintenance.behavior.MoveBehavior;

/**
 * Created by vrostov on 18.10.2017.
 */
public class NotMoveableMoveBehavior implements MoveBehavior {


    private final boolean moveable=false;
    private double vx, vy, vz;
    private double ax, ay, az;

    public boolean isMoveable() {
        return moveable;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        throw new UnsupportedOperationException("Недвижимые объекты - недвижимы!");
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        throw new UnsupportedOperationException("Недвижимые объекты - недвижимы!");
    }

    public double getVz() {
        return vz;
    }

    public void setVz(double vz) {
        throw new UnsupportedOperationException("Недвижимые объекты - недвижимы!");
    }

    public double getAx() {
        return ax;
    }

    public void setAx(double ax) {
        throw new UnsupportedOperationException("Недвижимые объекты - недвижимы!");
    }

    public double getAy() {
        return ay;
    }

    public void setAy(double ay) {
        throw new UnsupportedOperationException("Недвижимые объекты - недвижимы!");
    }

    public double getAz() {
        return az;
    }

    public void setAz(double az) {
        throw new UnsupportedOperationException("Недвижимые объекты - недвижимы!");
    }
}
