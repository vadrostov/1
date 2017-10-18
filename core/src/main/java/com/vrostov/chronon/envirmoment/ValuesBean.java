package com.vrostov.chronon.envirmoment;

import playn.core.Tile;

/**
 * Created by vrostov on 18.10.2017.
 */
public interface ValuesBean {


    public Tile[] getTiles();
    public String[] getTilesNames();

    public int getWidth();
    public int getHeight();

    public int getMAX_STACK_HEIGHT();

    public  int getTileHeight();

    public  int getTileWidth() ;

    public  int getTileDepth() ;

    public  int getTileBase() ;

    public  int getTileImageHeight() ;

    public  int getObjectBase() ;

    public  double getGRAVITY() ;

    public  double getRESTITUTION() ;

    public  double getFRICTION() ;
}
