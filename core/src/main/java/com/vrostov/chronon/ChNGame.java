package com.vrostov.chronon;

import com.vrostov.chronon.envirmoment.ChNMainCity;
import playn.core.Platform;
import playn.scene.SceneGame;

/**
 * Created by vrostov on 29.09.2017.
 */
public class ChNGame extends SceneGame{

    ChNMainCity city;

    public ChNGame(Platform plat) {
        super(plat, 33);

        int cx=15, cy=35;
        city=new ChNMainCity(plat, cy, cx);
        for (int x=0; x<cx;++x){
            for (int y=0;y<cy;++y){
                city.addTile(x,y,1);
            }
        }
    }
}
