package com.vrostov.chronon.envirmoment.beans;

import com.vrostov.chronon.envirmoment.ChNMainCity;
import com.vrostov.chronon.envirmoment.ValuesBean;
import playn.core.Tile;
import pythagoras.f.IDimension;

/**
 * Created by vrostov on 18.10.2017.
 */
public class MainCityValuesBean implements ValuesBean{

    private final IDimension viewSize;

    private static final int TILE_HEIGHT=100;
    private static final int TILE_WIDTH=80;
    private static final int TILE_DEPTH=40;
    private static final int TILE_BASE = 90;
    private static final int TILE_IMAGE_HEIGHT = 170;

    private static final int MAX_STACK_HEIGHT = 35;

    private static final int OBJECT_BASE=30;

    private static final double GRAVITY = -10.0;
    private static final double RESTITUTION = 0.4;
    private static final double FRICTION = 10.0;

    public  final String[] tilesNames=new String[]{"block_wood", "block_wood"};
    private final Tile[] tiles=new Tile[tilesNames.length];

    public MainCityValuesBean(IDimension viewSize) {
        this.viewSize = viewSize;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public String[] getTilesNames() {
        return tilesNames;
    }
}
