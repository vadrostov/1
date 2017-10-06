package com.vrostov.chronon.envirmoment;

import com.vrostov.chronon.ChNObject;
import playn.core.Platform;
import playn.core.Surface;
import pythagoras.f.IDimension;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vrostov on 29.09.2017.
 */
public class ChNMainCity {

    public static class Stack {
        private int[] tiles;
        List<ChNObject> objects = new ArrayList<ChNObject>();



        int height() {
            return tiles.length;
        }

    }


    private static final String[] tilesNames=new String[]{"block", "asphalt", "yelllowgrass", "greengrass", "water"};

    private static final int TILE_HEIGHT=100;
    private static final int TILE_WIDTH=100;
    private static final int TILE_DEPTH=50;
    private static final Stack EMPTY_STACK;
    private static final int MAX_STACK_HEIGHT = 8;

    private double viewOriginX, viewOriginY, viewOriginZ;

    static {
        EMPTY_STACK = new Stack();
        EMPTY_STACK.tiles = new int[0];
    }


    Platform platform;
    private final IDimension viewSize;
    int cityWidth, cityHeight;
    boolean loaded;

    private Stack[] city;

    public ChNMainCity(Platform platform, int width, int height) {
        this.platform = platform;
        this.viewSize=platform.graphics().viewSize;
        this.cityWidth=width;
        this.cityHeight=height;

        this.city=new Stack[cityHeight*cityWidth];
        int i=0;
        for (int ty=0;ty<cityHeight;++ty){
            for(int tx=0; tx<cityWidth;++tx){

            }
        }

    }


    public void paint(Surface surface, float alpha){
        if (!loaded) return;

        int startX = (int) pixelToWorldX(surface, 0);
        int endX = (int) pixelToWorldX(surface, viewSize.width());
        if (startX < 0)
            startX = 0;
        if (endX < 0)
            endX = 0;
        if (startX >= cityWidth)
            startX = cityWidth - 1;
        if (endX >= cityWidth)
            endX = cityWidth - 1;

        int startY = (int) pixelToWorldY(surface, 0, 0);
        int endY = (int) pixelToWorldY(surface, viewSize.height(), MAX_STACK_HEIGHT);
        if (startY < 0)
            startY = 0;
        if (endY < 0)
            endY = 0;
        if (startY >= cityHeight)
            startY = cityHeight - 1;
        if (endY >= cityHeight)
            endY = cityHeight - 1;

        for (int z=0; z<MAX_STACK_HEIGHT; ++z){
            for (int y=startY; y<endY;++y){
                for (int x=startX; x<endX; ++x){


                }
            }


        }

    }
    public void addTile(int tx, int ty, int type){
        Stack stack=createStack(tx, ty);
        int lenght=stack.tiles.length;
        if (lenght==MAX_STACK_HEIGHT){
            return;
        }
        int [] newTiles=new int[lenght+1];
        System.arraycopy(stack.tiles, 0 , newTiles,0,lenght);
        stack.tiles=newTiles;
        stack.tiles[lenght]=type;


    }

    private Stack createStack(int tx, int ty) {
        if ((tx < 0) || (tx >= cityWidth) || (ty < 0) || (ty >= cityHeight)) {
            return EMPTY_STACK;
        }

        return city[ty * cityWidth + tx];
    }

    public void updatePhysics(double delta){
        for (int ty=0; ty<cityHeight; ++ty){
            for(int tx=0;tx<cityWidth; ++tx){
                updatePhysics(stack(tx, ty), delta);
            }
        }
    }

    public void setViewOrigin(int x, int y, int z){
        viewOriginX=x;
        viewOriginY=y;
        viewOriginZ=z;
    }

    private Stack stackForObject(ChNObject o) {
        if ((o.getPosition().getX() < 0) || (o.getPosition().getY() < 0) || (o.getPosition().getX() >= cityWidth) || (o.getPosition().getY() >= cityHeight)) {
            return EMPTY_STACK;
        }

        return stack((int) o.getPosition().getX(), (int) o.getPosition().getY());
    }

    public void addObject(ChNObject o) {
        Stack stack = stackForObject(o);
        stack.objects.add(o);
        o.setStack(stack);
    }


    private Stack stack(int tx, int ty) {
        if ((tx < 0) || (tx >= cityWidth) || (ty < 0) || (ty >= cityHeight)) {
            return EMPTY_STACK;
        }

        return city[ty * cityWidth + tx];
    }

    private void updatePhysics(Stack stack, double delta){

        for(int i=0;i<stack.objects.size();++i){
            ChNObject chNObject=stack.objects.get(i);
            updatePhysics(chNObject, delta);


        }
    }

    private double pixelToWorldX(Surface surf, float x) {
        double center = viewSize.width() * 0.5;
        return (int) (((viewOriginX * TILE_WIDTH) + x - center) / TILE_WIDTH);
    }

    private double pixelToWorldY(Surface surf, float y, double z) {
        double center = viewSize.height() * 0.5;
        return (y + (viewOriginY * TILE_HEIGHT - viewOriginZ * TILE_DEPTH)
                + (z * TILE_DEPTH) - center)
                / TILE_HEIGHT;
    }

    private void updatePhysics(ChNObject chNObject, double delta){
        chNObject.setVx(chNObject.getAx()*delta);
        chNObject.setVy(chNObject.getAy()*delta);
        chNObject.setVz(chNObject.getAz()*delta);
        moveBy(chNObject, chNObject.getVx(),chNObject.getVy(), chNObject.getVz());

    }


    private void moveBy(ChNObject chNObject, double dx, double dy, double dz){

    }

}
