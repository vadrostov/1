package com.vrostov.chronon.objects;

import com.vrostov.chronon.environment.EnvironmentStack;
import com.vrostov.chronon.objects.maintenance.behavior.MessagerBehavior;
import com.vrostov.chronon.objects.maintenance.behavior.MoveBehavior;
import com.vrostov.chronon.objects.maintenance.behavior.UseBehavior;
import playn.core.Tile;



/**
 * Created by vrostov on 29.09.2017.
 */
public class ChNObject {

    public Tile tile;

    public double ox, oy, oz;
    public double x,y,z;
    private double r;
    private EnvironmentStack stack;
    private int lastUpdated;
    boolean resting;

    protected MessagerBehavior messagerBehavior;
    protected UseBehavior useBehavior;
    protected MoveBehavior moveBehavior;

    public ChNObject(Tile tile) {
        this.tile = tile;

    }

    public EnvironmentStack getStack() {
        return stack;
    }

    public void setStack(EnvironmentStack stack) {
        this.stack = stack;
    }



    public double x(double alpha) {
        return x * alpha + ox* (1.0f - alpha);
    }

    public double y(double alpha) {
        return y * alpha + oy* (1.0f - alpha);
    }

    public double z(double alpha) {
        return z * alpha + oz* (1.0f - alpha);
    }



    public void setPos(int x, int y, int z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public void saveOldPos() {
        this.ox = x;
        this.oy = y;
        this.oz = z;
    }


    public double getVx() {
        return moveBehavior.getVx();
    }

    public void setVx(double vx) {
        moveBehavior.setVx(vx);
    }

    public double getVy() {
        return moveBehavior.getVy();
    }

    public void setVy(double vy) {
        moveBehavior.setVy(vy);
    }

    public double getVz() {
        return moveBehavior.getVz();
    }

    public void setVz(double vz) {
        moveBehavior.setVz(vz);
    }

    public double getAx() {
        return moveBehavior.getAx();
    }

    public void setAx(double ax) {
        moveBehavior.setAx(ax);
    }

    public double getAy() {
        return moveBehavior.getAy();
    }

    public void setAy(double ay) {
        moveBehavior.setAy(ay);
    }

    public double getAz() {
        return moveBehavior.getAz();
    }

    public void setAz(double az) {
        moveBehavior.setAz(az);
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setAxeleration(double ax, double ay, double az){
        moveBehavior.setAx(ax);
        moveBehavior.setAy(ay);
        moveBehavior.setAz(az);
    }

    public int getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(int lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public boolean isResting() {
        return resting;
    }

    public void setResting(boolean resting) {
        this.resting = resting;
    }
}
