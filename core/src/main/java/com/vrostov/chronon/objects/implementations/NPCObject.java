package com.vrostov.chronon.objects.implementations;

import com.vrostov.chronon.objects.ChNObject;
import com.vrostov.chronon.objects.maintenance.behavior.impl.PeriodicMoveBehavior;
import com.vrostov.chronon.objects.maintenance.behavior.impl.SpeakUseBehavior;
import playn.core.Tile;

/**
 * Created by vrostov on 18.10.2017.
 */
public class NPCObject extends ChNObject{
    public NPCObject(Tile tile) {
        super(tile);
        moveBehavior=new PeriodicMoveBehavior();
        useBehavior=new SpeakUseBehavior();
    }
}
