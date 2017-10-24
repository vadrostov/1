package com.vrostov.chronon.environment.maintenance;

import com.vrostov.chronon.environment.EnvironmentStack;
import com.vrostov.chronon.environment.ValuesBean;
import com.vrostov.chronon.objects.ChNObject;
import playn.core.Image;
import playn.core.Platform;
import playn.core.Surface;
import pythagoras.f.IDimension;
import react.RFuture;
import react.Slot;
import react.UnitSlot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vrostov on 18.10.2017.
 */
public class CityPainter {

    ValuesBean valuesBean;

    EnvironmentStack[] world;

    private final IDimension viewSize;

    Platform platform;
    boolean loaded;
    private double viewOriginX, viewOriginY, viewOriginZ;

    public CityPainter(ValuesBean valuesBean, EnvironmentStack[] world, Platform platform) {
        this.valuesBean = valuesBean;
        this.world = world;
        this.platform = platform;
        this.viewSize=platform.graphics().viewSize;
        loadImg();
    }

    public void paint(Surface surface, float alpha){


        if (!loaded) return; // avoid rendering until images load

        int startX = (int) pixelToWorldX(surface, 0);
        int endX = (int) pixelToWorldX(surface, viewSize.width());
        if (startX < 0)
            startX = 0;
        if (endX < 0)
            endX = 0;
        if (startX >= valuesBean.getWidth())
            startX = valuesBean.getWidth() - 1;
        if (endX >= valuesBean.getWidth())
            endX = valuesBean.getWidth() - 1;

        int startY = (int) pixelToWorldY(surface, 0, 0);
        int endY = (int) pixelToWorldY(surface, viewSize.height(), valuesBean.getMAX_STACK_HEIGHT());
        if (startY < 0)
            startY = 0;
        if (endY < 0)
            endY = 0;
        if (startY >= valuesBean.getHeight())
            startY = valuesBean.getHeight() - 1;
        if (endY >= valuesBean.getHeight())
            endY = valuesBean.getHeight() - 1;

        // Paint all the tiles from back to front.
        for (int tz = 0; tz < valuesBean.getMAX_STACK_HEIGHT(); ++tz) {
            for (int ty = startY; ty <= endY; ++ty) {
                for (int tx = startX; tx <= endX; ++tx) {
                    EnvironmentStack stack = world[ty * valuesBean.getWidth() + tx];

                    if (tz < stack.stackHeight()) {
                        // Draw the tile and its shadows.

                        // Skip obviously hidden tiles.
                        if ((tz < stack.stackHeight() - 1) && (height(tx, ty + 1) > tz)) {
                            continue;
                        }

                        // Figure out where the tile goes. If it's out of screen bounds,
                        // skip it (paintShadow() is relatively expensive).
                        int px = worldToPixelX(surface, tx);
                        int py = worldToPixelY(surface, ty, tz) - valuesBean.getTileBase();
                        if ((px > viewSize.width()) || (py > viewSize.height())
                                || (px + valuesBean.getTileWidth() < 0) || (py + valuesBean.getTileImageHeight() < 0)) {
                            continue;
                        }

                        int t=stack.getTiles()[tz];
                        surface.draw(valuesBean.getTiles()[t], px, py);
                        //paintShadow(surf, tx, ty, px, py);
                    } else if (tz >= stack.stackHeight()) {
                        // Paint the objects in this stack.
                        paintObjects(surface, stack, tz, alpha);
                    }
                }
            }
        }

    }

    private double pixelToWorldX(Surface surf, float x) {
        double center = viewSize.width() * 0.5;
        return (int) (((viewOriginX * valuesBean.getTileWidth()) + x - center) / valuesBean.getTileWidth());
    }

    private double pixelToWorldY(Surface surf, float y, double z) {
        double center = viewSize.height() * 0.5;
        return (y + (viewOriginY * valuesBean.getTileHeight() - viewOriginZ * valuesBean.getTileDepth())
                + (z * valuesBean.getTileDepth()) - center)
                / valuesBean.getTileHeight();
    }

    private int worldToPixelX(Surface surface, double x){
        double center = viewSize.width() * 0.5;
        return (int) (center - (viewOriginX * valuesBean.getTileWidth()) + x * valuesBean.getTileWidth());
    }


    private int worldToPixelY(Surface surface, double y, double z){
        double center = viewSize.height() * 0.5;
        return (int) (center
                - (viewOriginY * valuesBean.getTileHeight() - viewOriginZ * valuesBean.getTileDepth()) + y
                * valuesBean.getTileHeight() - z * valuesBean.getTileDepth());
    }

    private void paintObjects(Surface surface, EnvironmentStack stack, int tz, float alpha){
        for (ChNObject o : stack.getObjects()) {
            if ((int) o.getZ() == tz) {
                int px = worldToPixelX(surface, o.x(alpha));
                int py = worldToPixelY(surface, o.y(alpha), o.z(alpha));
                float baseX = o.tile.width() / 2;
                float baseY = o.tile.height() - valuesBean.getObjectBase();
                surface.draw(o.tile, px - baseX, py - baseY);
            }
        }
    }

    private void loadImg(){
        List<RFuture<Image>> wait=new ArrayList<RFuture<Image>>();

        for(int i=0;i<valuesBean.getTiles().length; ++i){
            final int idx=i;
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

    private int height(int tx, int ty) {
        return stack(tx, ty).stackHeight();
    }


    public void setViewOrigin(double x, double y, double z) {
        viewOriginX = x;
        viewOriginY = y;
        viewOriginZ = z;
    }

    private EnvironmentStack stack(int tx, int ty) {
        if ((tx < 0) || (tx >= valuesBean.getWidth()) || (ty < 0) || (ty >= valuesBean.getHeight())) {
            EnvironmentStack emptyStack=new EnvironmentStack();
            emptyStack.setTiles(new int[0]);
        }

        return world[ty * valuesBean.getWidth() + tx];
    }

    public void addTile(int tx, int ty, int type){



        EnvironmentStack stack = stack(tx, ty);

        int len = stack.getTiles().length;
        if (len == valuesBean.getMAX_STACK_HEIGHT()) {
            return;
        }

        int[] newTiles = new int[len + 1];
        System.arraycopy(stack.getTiles(), 0, newTiles, 0, len);
        stack.setTiles(newTiles);
        stack.getTiles()[len] = type;
    }

}
