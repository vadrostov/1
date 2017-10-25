package com.vrostov.chronon.objects.implementations;

import com.vrostov.chronon.objects.ChNObject;
import com.vrostov.chronon.objects.ObsForObjectObserver;
import com.vrostov.chronon.objects.maintenance.behavior.impl.PeriodicMoveBehavior;
import com.vrostov.chronon.objects.maintenance.behavior.impl.SpeakUseBehavior;
import playn.core.Tile;

/**
 * Created by vrostov on 18.10.2017.
 */
public class NPCObject extends ChNObject implements ObsForObjectObserver{

    double viewRad=5;
    boolean messaging=false;
    Tile secondtile;
    Tile firsttile;




    public NPCObject(Tile tile) {
        super(tile);
        moveBehavior=new PeriodicMoveBehavior();
        useBehavior=new SpeakUseBehavior();
    }

    public void update(double x, double y) {
        double myx = this.x, myy=this.y;
        double katX=Math.abs(myx-x), katY=Math.abs(myy-y);
        double hip=Math.sqrt(Math.pow(katX,2)+ Math.pow(katY,2));
        if (hip<=viewRad){
            this.setTile(secondtile);
        }
        else this.setTile(firsttile);

    }

    public double getViewRad() {
        return viewRad;
    }

    public void setViewRad(double viewRad) {
        this.viewRad = viewRad;
    }

    private void setTile(Tile tile){
        this.tile=tile;
    }

    public Tile getSecondtile() {
        return secondtile;
    }

    public void setSecondtile(Tile secondtile) {
        this.secondtile = secondtile;
    }

    public Tile getFirsttile() {
        return firsttile;
    }

    public void setFirsttile(Tile firsttile) {
        this.firsttile = firsttile;
    }
}
