package com.vrostov.chronon;

import com.vrostov.chronon.objects.maintnanace.ChNObjectPosition;
import playn.core.Tile;

import javax.swing.text.Position;
import com.vrostov.chronon.envirmoment.ChNMainCity.Stack;


/**
 * Created by vrostov on 29.09.2017.
 */
public class ChNObject {

    public Tile tile;


    public double vx, vy, vz;
    public double ax, ay, az;
    public double ox, oy, oz;
    public double x,y,z;
    private double r;
    private Stack stack;
    int lastUpdated;
    boolean resting;

    public ChNObject(Tile tile) {
        this.tile = tile;

    }

    public Stack getStack() {
        return stack;
    }

    public void setStack(Stack stack) {
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



    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getVz() {
        return vz;
    }

    public void setVz(double vz) {
        this.vz = vz;
    }

    public double getAx() {
        return ax;
    }

    public void setAx(double ax) {
        this.ax = ax;
    }

    public double getAy() {
        return ay;
    }

    public void setAy(double ay) {
        this.ay = ay;
    }

    public double getAz() {
        return az;
    }

    public void setAz(double az) {
        this.az = az;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setAxeleration(double ax, double ay, double az){
        this.ax=ax;
        this.ay=ay;
        this.az=az;
    }
}
