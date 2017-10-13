package com.vrostov.chronon;

import com.vrostov.chronon.envirmoment.ChNMainCity;
import playn.core.*;
import playn.scene.Layer;
import playn.scene.SceneGame;
import react.Slot;

/**
 * Created by vrostov on 29.09.2017.
 */
public class ChNGame extends SceneGame{

    ChNMainCity city;
    Layer gameLayer;
    private ChNObject mainPers;
    boolean controlLeft, controlRight, controlUp, controlDown;
    private float frameAlpha;

    public ChNGame(Platform plat) {
        super(plat, 33);

        plat.input().keyboardEvents.connect(new Keyboard.KeySlot() {
            @Override
            public void onEmit(Keyboard.KeyEvent keyEvent) {

            }
        });

        int cx=15, cy=35;
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

        plat.assets().getImage("C:/Users/vrostov/Documents/ChN_The_Game/assets/src/main/resources/images/chn.png").state.onSuccess(new Slot<Image>() {
            public void onEmit(Image image) {
                mainPers=new ChNObject(image.texture());
                mainPers.setR(0.3);
                city.addObject(mainPers);
                update.connect(new Slot<Clock>() {
                    public void onEmit(Clock clock) {
                        mainPers.setAxeleration(0.0, 0.0, 0.0);
                        if(controlLeft) mainPers.setAx(-1.0);
                        if(controlRight) mainPers.setAx(1.0);
                        if (controlDown) mainPers.setAy(1.0);
                        if (controlUp) mainPers.setAy(-1.0);
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
}
