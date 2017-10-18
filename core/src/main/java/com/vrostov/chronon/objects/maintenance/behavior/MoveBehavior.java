package com.vrostov.chronon.objects.maintenance.behavior;

/**
 * Created by vrostov on 18.10.2017.
 */
public interface MoveBehavior {

    public boolean isMoveable();

    public double getVx();

    public void setVx(double vx);

    public double getVy();

    public void setVy(double vy);

    public double getVz();

    public void setVz(double vz);

    public double getAx();

    public void setAx(double ax);

    public double getAy();

    public void setAy(double ay);

    public double getAz();

    public void setAz(double az);
}
