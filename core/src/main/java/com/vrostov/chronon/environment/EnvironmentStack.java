package com.vrostov.chronon.environment;

import com.vrostov.chronon.objects.ChNObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vrostov on 24.10.2017.
 */
public class EnvironmentStack {

    private int[] tiles;

    private List<ChNObject> objects=new ArrayList<ChNObject>();

    public int[] getTiles() {
        return tiles;
    }

    public void setTiles(int[] tiles) {
        this.tiles = tiles;
    }

    public List<ChNObject> getObjects() {
        return objects;
    }


    public int stackHeight(){
        return tiles.length;
    }

}
