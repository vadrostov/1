package com.vrostov.chronon;

import com.vrostov.chronon.envirmoment.ChNMainCity;
import playn.core.*;
import playn.scene.Layer;
import playn.scene.SceneGame;
import react.Slot;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vrostov on 29.09.2017.
 */
public class ChNGame extends SceneGame{

    ChNMainCity city;
    Layer gameLayer;
    private ChNObject mainPers;
    boolean controlLeft, controlRight, controlUp, controlDown;
    private float frameAlpha;

    private static Map<Key, Integer> ADD_TILE_KEYS = new HashMap<Key, Integer>();
    static {
        int idx = 0;
        for (Key key : new Key[] {
                Key.K1, Key.K2, Key.K3, Key.K4, Key.K5, Key.K6, Key.K7, Key.K8,
                Key.W, Key.D, Key.S, Key.A, Key.T, Key.Y, Key.H, Key.N, Key.B, Key.V, Key.F, Key.R }) {
            ADD_TILE_KEYS.put(key, idx++);
        }
    }

    public ChNGame(Platform plat) {
        super(plat, 33);

        plat.input().keyboardEvents.connect(new Keyboard.KeySlot() {
            @Override
            public void onEmit(Keyboard.KeyEvent keyEvent) {
                if (keyEvent.down){
                    Integer tileIdX=ADD_TILE_KEYS.get(keyEvent.key);
/*                    if (tileIdX!=null){
                        addTile(mainPers.getPosition().getX(), mainPers.getPosition().getY(), tileIdX);
                        return;
                    }*/

                    switch (keyEvent.key){
                        case W:
                            controlUp=keyEvent.down;
                            return;
                        case A:
                            controlLeft=keyEvent.down;
                            return;
                        case S:
                            controlDown=keyEvent.down;
                            return;
                        case D:
                            controlRight=keyEvent.down;
                            return;
                        default:
                            break;
                    }
                }
            }
        });

        int cx=5, cy=5;
        city=new ChNMainCity(plat, cy, cx);
        for (int x=0; x<cx;++x){
            for (int y=0;y<cy;++y){
                city.addTile(x,y,1);
            }
        }

        rootLayer.add(gameLayer=new Layer() {
            @Override
            protected void paintImpl(Surface surface) {
                if (mainPers!= null) city.setViewOrigin(mainPers.getPosition().getX(),mainPers.getPosition().getY(), mainPers.getPosition().getZ());
                surface.clear();
                city.paint(surface, alpha);

            }
        });

        plat.assets().getImage("/images/chn.png").state.onSuccess(new Slot<Image>() {
            public void onEmit(Image image) {
                mainPers=new ChNObject(image.texture());
                mainPers.setR(0.3);
                mainPers.updatePosition(2,2,2);
                city.addObject(mainPers);
                update.connect(new Slot<Clock>() {
                    public void onEmit(Clock clock) {
                        mainPers.setAxeleration(0.0, 0.0, 0.0);
                        if(controlLeft) mainPers.setAx(-1.0);
                        if(controlRight) mainPers.setAx(1.0);
                        if (controlDown) mainPers.setAy(1.0);
                        if (controlUp) {
                            mainPers.setAy(-1.0);}

                    }
                });
            }
        });

    }


    @Override
    public void update(Clock clock) {
        super.update(clock);
        city.updatePhysics(clock.dt/1000f);
    }

    @Override
    public void paint(Clock clock) {
        frameAlpha=clock.alpha;
        super.paint(clock);

    }

    private void addTile(int x, int y, int type){

        city.addTile(x,y,type);
    }
}
