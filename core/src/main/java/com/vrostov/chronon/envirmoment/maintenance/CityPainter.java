package com.vrostov.chronon.envirmoment.maintenance;

import com.vrostov.chronon.envirmoment.ChNMainCity;
import com.vrostov.chronon.envirmoment.beans.MainCityValuesBean;
import playn.core.Image;
import playn.core.Platform;
import playn.core.Surface;
import react.RFuture;
import react.Slot;
import react.UnitSlot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vrostov on 18.10.2017.
 */
public class CityPainter {

    MainCityValuesBean valuesBean;
    Platform platform;
    boolean loaded;

    public CityPainter(MainCityValuesBean valuesBean, Platform platform) {
        this.valuesBean = valuesBean;
        this.platform = platform;
    }

    public void paint(Surface surface, float alpha){

    }

    private double pixelToWorldX(Surface surf, float x) {
        return 0;
    }

    private double pixelToWorldY(Surface surf, float y, double z) {
        return 0;
    }

    private int worldToPixelX(Surface surface, double x){
        return 0;
    }

    private int worldToPixelY(Surface surface, double y, double z){
        return 0;
    }

    private void paintObjects(Surface surface, ChNMainCity.Stack stack, int tz, float alpha){

    }

    private void loadImg(){
        List<RFuture<Image>> wait=new ArrayList<RFuture<Image>>();

        for(int i=0;i<valuesBean.getTiles().length; ++i){
            final int idx=1;
            Image tile=platform.assets().getImage(imageRes(valuesBean.getTilesNames()[i]));
            tile.state.onSuccess(new Slot<Image>() {
                public void onEmit(Image image) {
                    valuesBean.getTiles()[idx]=image.texture();
                }
            });
            wait.add(tile.state);
        }

        RFuture.sequence(wait).onSuccess(new UnitSlot() {
            @Override
            public void onEmit() {
                loaded=true;
            }
        });

    }
    private String imageRes(String name) {
        return "/images/" + name + ".png";
    }

}
