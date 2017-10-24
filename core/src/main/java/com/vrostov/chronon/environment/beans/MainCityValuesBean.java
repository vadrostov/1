package com.vrostov.chronon.environment.beans;

import com.vrostov.chronon.environment.ValuesBean;
import playn.core.Tile;

/**
 * Created by vrostov on 18.10.2017.
 */
public class MainCityValuesBean implements ValuesBean{


    private  final int TILE_HEIGHT=100;
    private  final int TILE_WIDTH=80;
    private  final int TILE_DEPTH=40;
    private  final int TILE_BASE = 90;
    private  final int TILE_IMAGE_HEIGHT = 170;

    private   final int MAX_STACK_HEIGHT = 35;

    private  final int OBJECT_BASE=30;

    private  final double GRAVITY = -10.0;
    private  final double RESTITUTION = 0.4;
    private  final double FRICTION = 10.0;

    private final int width=16;
    private final int height=16;

    private static final String[] tilesNames = new String[] { "block_brown",
            "block_dirt", "block_grass", "block_plain", "block_stone", "block_wall",
            "block_water", "block_wood",

            "ramp_north", "ramp_east", "ramp_south", "ramp_west",

            "roof_north", "roof_northeast", "roof_east", "roof_southeast",
            "roof_south", "roof_southwest", "roof_west", "roof_northwest", };

    private final Tile[] tiles=new Tile[tilesNames.length];

    public MainCityValuesBean() {

    }

    public Tile[] getTiles() {
        return tiles;
    }

    public String[] getTilesNames() {
        return tilesNames;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMAX_STACK_HEIGHT() {
        return MAX_STACK_HEIGHT;
    }

    public  int getTileHeight() {
        return TILE_HEIGHT;
    }

    public  int getTileWidth() {
        return TILE_WIDTH;
    }

    public  int getTileDepth() {
        return TILE_DEPTH;
    }

    public  int getTileBase() {
        return TILE_BASE;
    }

    public  int getTileImageHeight() {
        return TILE_IMAGE_HEIGHT;
    }

    public  int getObjectBase() {
        return OBJECT_BASE;
    }

    public  double getGRAVITY() {
        return GRAVITY;
    }

    public  double getRESTITUTION() {
        return RESTITUTION;
    }

    public  double getFRICTION() {
        return FRICTION;
    }
}
