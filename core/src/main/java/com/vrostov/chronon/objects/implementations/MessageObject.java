package com.vrostov.chronon.objects.implementations;

import com.vrostov.chronon.objects.ChNObject;
import com.vrostov.chronon.objects.maintenance.behavior.impl.NotMoveableMoveBehavior;
import playn.core.Tile;

/**
 * Created by vrostov on 24.10.2017.
 */
public class MessageObject extends ChNObject{

    public MessageObject(Tile tile) {
        super(tile);
        moveBehavior=new NotMoveableMoveBehavior();

    }


}
