package com.vrostov.chronon.objects.implementations;

import com.vrostov.chronon.objects.ChNObject;
import com.vrostov.chronon.objects.maintenance.behavior.impl.NotMoveableMoveBehavior;
import com.vrostov.chronon.objects.maintenance.behavior.impl.UseableUseBehavior;
import playn.core.Tile;

/**
 * Created by vrostov on 18.10.2017.
 */
public class UseableObject extends ChNObject{

    public UseableObject(Tile tile) {
        super(tile);
        moveBehavior=new NotMoveableMoveBehavior();
        useBehavior=new UseableUseBehavior();
    }
}
