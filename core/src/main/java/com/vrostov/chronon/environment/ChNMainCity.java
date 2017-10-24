package com.vrostov.chronon.environment;

import com.vrostov.chronon.objects.ChNObject;
import com.vrostov.chronon.environment.beans.MainCityValuesBean;
import com.vrostov.chronon.environment.maintenance.CityPainter;
import com.vrostov.chronon.environment.maintenance.CityPhysics;
import com.vrostov.chronon.objects.implementations.NPCObject;
import playn.core.*;
import pythagoras.f.IDimension;
import react.Slot;

/**
 * Created by vrostov on 29.09.2017.
 */
public class ChNMainCity {





    private ValuesBean values;

    private CityPainter painter;
    private CityPhysics physics;



    private static final EnvironmentStack EMPTY_STACK;

    static {
        EMPTY_STACK = new EnvironmentStack();
        EMPTY_STACK.setTiles(new int[0]);
    }


    Platform platform;
    private final IDimension viewSize;
    int cityWidth, cityHeight;
    boolean loaded;

    private EnvironmentStack[] city;

    public ChNMainCity(Platform platform, int width, int height) {
        this.platform = platform;
        this.viewSize=platform.graphics().viewSize;
        this.cityWidth=width;
        this.cityHeight=height;
        values=new MainCityValuesBean();

        this.city=new EnvironmentStack[cityHeight*cityWidth];
        int i=0;
        for (int ty=0;ty<cityHeight;++ty){
            for(int tx=0; tx<cityWidth;++tx){
                this.city[i]=new EnvironmentStack();
                city[i].setTiles(new int[0]);
                ++i;
            }
        }

        this.painter=new CityPainter(values, city,platform);
        this.physics=new CityPhysics(values, city, platform);

        platform.assets().getImage("/images/gem_green.png").state.onSuccess(new Slot<Image>() {
            public void onEmit(Image event) {
                ChNObject object=new NPCObject(event.texture());
                object.setPos(4,4,1);
                addObject(object);

            }
        });


    }


    //метод отрисовывает мир
    public void paint(Surface surface, float alpha){
      painter.paint(surface,alpha);

    }


    public void addObject(ChNObject o) {
        physics.addObject(o);
    }


    //возвращает строковый путь к ресурсу-изображению
    private String imageRes(String name) {
        return "/images/" + name + ".png";
    }

    public CityPainter getPainter() {
        return painter;
    }

    public CityPhysics getPhysics() {
        return physics;
    }
}
