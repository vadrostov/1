package com.vrostov.chronon;

import com.vrostov.chronon.environment.ChNMainCity;
import com.vrostov.chronon.objects.ChNObject;
import com.vrostov.chronon.objects.implementations.PlayableObject;
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
    boolean controlLeft, controlRight, controlUp, controlDown, controlJump;
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
            @Override public void onEmit (Keyboard.KeyEvent event) {
                if (event.down) {
                    Integer tileIdx = ADD_TILE_KEYS.get(event.key);
                    if (tileIdx != null) {
                        addTile((int) mainPers.x, (int) mainPers.y, tileIdx);
                        return;
                    }
                }

                switch (event.key) {
                    case SPACE:
                        if (event.down) controlJump = true;
                        break;

                    case LEFT:
                        controlLeft = event.down;
                        break;
                    case UP:
                        controlUp = event.down;
                        break;
                    case RIGHT:
                        controlRight = event.down;
                        break;
                    case DOWN:
                        controlDown = event.down;
                        break;
                    default:
                        break; // nada
                }
            }
        });


        int cx=16, cy=16;
        city=new ChNMainCity(plat, cy, cx);
        for (int y = 0; y < 16; ++y) {
            for (int x = 0; x < 16; ++x) {
                city.getPainter().addTile(x, y, 2);
            }
        }

        rootLayer.add(gameLayer=new Layer() {
            @Override
            protected void paintImpl(Surface surface) {
                if (mainPers!= null) city.getPainter().setViewOrigin((int)mainPers.x,(int)mainPers.y, (int)mainPers.z);
                surface.clear();
                city.paint(surface, alpha);

            }
        });

        plat.assets().getImage("/images/character_cat_girl.png").state.onSuccess(new Slot<Image>() {
            public void onEmit(Image image) {
                mainPers=new PlayableObject(image.texture());
                mainPers.setR(0.3);
                mainPers.setPos(2,2,1);
                city.addObject(mainPers);
                update.connect(new Slot<Clock>() {
                    public void onEmit(Clock clock) {
                        mainPers.setAxeleration(0, 0, 0);
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
        city.getPhysics().updatePhysics(clock.dt/1000f);
    }

    @Override
    public void paint(Clock clock) {
        frameAlpha=clock.alpha;
        super.paint(clock);

    }

    private void addTile(int x, int y, int type){

        city.getPainter().addTile(x,y,type);
    }
}
