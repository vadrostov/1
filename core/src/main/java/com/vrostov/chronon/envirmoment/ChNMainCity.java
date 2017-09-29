package com.vrostov.chronon.envirmoment;

import com.vrostov.chronon.ChNObject;
import playn.core.Platform;
import pythagoras.f.IDimension;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vrostov on 29.09.2017.
 */
public class ChNMainCity {

    static class Stack {
        private int[] tiles;
        List<ChNObject> objects = new ArrayList<ChNObject>();



        int height() {
            return tiles.length;
        }
    }


    private static final String[] tilesNames=new String[]{"block", "asphalt", "yelllowgrass", "greengrass", "water"};

    private static final int TILE_HEIGHT=100;
    private static final int TILE_WIDTH=100;
    private static final Stack EMPTY_STACK;
    private static final int MAX_STACK_HEIGHT = 8;

    static {
        EMPTY_STACK = new Stack();
        EMPTY_STACK.tiles = new int[0];
    }


    Platform platform;
    private final IDimension viewSize;
    int cityWidth, cityHeight;

    private Stack[] world;

    public ChNMainCity(Platform platform, int width, int height) {
        this.platform = platform;
        this.viewSize=platform.graphics().viewSize;
        this.cityWidth=width;
        this.cityHeight=height;


    }

    public void addTile(int tx, int ty, int type){
        Stack stack=createStack(tx, ty);
        int lenght=stack.tiles.length;
        if (lenght==MAX_STACK_HEIGHT){
            return;
        }
        int [] newTiles=new int[lenght+1];
        System.arraycopy(stack.tiles, 0 , newTiles,0,lenght);
        stack.tiles=newTiles;
        stack.tiles[lenght]=type;


    }

    private Stack createStack(int tx, int ty) {
        if ((tx < 0) || (tx >= cityWidth) || (ty < 0) || (ty >= cityHeight)) {
            return EMPTY_STACK;
        }

        return world[ty * cityWidth + tx];
    }

}
