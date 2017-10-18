package com.vrostov.chronon.objects.implementations;

import com.vrostov.chronon.ChNObject;
import com.vrostov.chronon.objects.maintenance.behavior.impl.ControlMoveBehavior;
import com.vrostov.chronon.objects.maintenance.behavior.impl.PersUseBehavior;
import playn.core.Tile;

/**
 * Created by vrostov on 18.10.2017.
 */
public class PlayableObject extends ChNObject{


    public PlayableObject(Tile tile) {
        super(tile);
        moveBehavior=new ControlMoveBehavior();
        useBehavior=new PersUseBehavior();
    }
}
