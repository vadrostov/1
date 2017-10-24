package com.vrostov.chronon.objects.implementations;

import com.vrostov.chronon.objects.ChNObject;
import com.vrostov.chronon.objects.ObsForObjectObserver;
import com.vrostov.chronon.objects.ObsForObjectSubject;
import com.vrostov.chronon.objects.maintenance.behavior.impl.ControlMoveBehavior;
import com.vrostov.chronon.objects.maintenance.behavior.impl.PersUseBehavior;
import playn.core.Tile;

import java.util.ArrayList;

/**
 * Created by vrostov on 18.10.2017.
 */
public class PlayableObject extends ChNObject implements ObsForObjectSubject{

    ArrayList<ObsForObjectObserver> observers;

    public PlayableObject(Tile tile) {
        super(tile);
        moveBehavior=new ControlMoveBehavior();
        useBehavior=new PersUseBehavior();
        observers=new ArrayList<ObsForObjectObserver>();
    }

    public void registerObserver(ObsForObjectObserver o) {
        observers.add(o);
    }

    public void deleteObserver(ObsForObjectObserver o) {

        int i=observers.indexOf(o);
        if (i>=0) observers.remove(i);
    }

    public void notifyObs() {
        for (ObsForObjectObserver obs: observers){
            obs.update(this.getX(), this.getY());
        }



    }

    @Override
    public void setX(double x) {
        super.setX(x);
        notifyObs();
    }

    @Override
    public void setY(double y) {
        super.setY(y);
        notifyObs();
    }
}
