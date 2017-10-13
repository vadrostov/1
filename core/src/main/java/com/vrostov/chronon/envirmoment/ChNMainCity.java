package com.vrostov.chronon.envirmoment;

import com.vrostov.chronon.ChNObject;
import playn.core.Image;
import playn.core.Platform;
import playn.core.Surface;
import playn.core.Tile;
import pythagoras.f.IDimension;
import react.RFuture;
import react.Slot;
import react.UnitSlot;

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
    private final Tile[] tiles=new Tile[tilesNames.length];

    private static final int TILE_HEIGHT=100;
    private static final int TILE_WIDTH=100;
    private static final int TILE_DEPTH=50;
    private static final int TILE_BASE = 90;
    private static final int TILE_IMAGE_HEIGHT = 190;
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

        loadImg();

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

                    Stack stack=city[y*cityWidth+x];

                    if(z<stack.height()){
                        if((z<stack.height()-1)&&(height(x, y+1)>z)){
                            continue;
                        }

                        int px=worldYoPixelX(surface, x);
                        int py=worldToPixelY(surface, y,z)-TILE_BASE;
                        if ((px > viewSize.width()) || (py > viewSize.height())
                                || (px + TILE_WIDTH < 0) || (py + TILE_IMAGE_HEIGHT < 0)) {
                            continue;
                        }

                        surface.draw(tiles[stack.tiles[z]], px, py);


                    }
                    else if (z>stack.height()){
                        paintObjects(surface, stack, z, alpha);
                    }
                }
            }


        }

    }

    private void paintObjects(Surface surface, Stack stack, int tz, float alpha){
        for(ChNObject o: stack.objects){
            if ((int)o.getPosition().getZ()== tz){
                int px=worldYoPixelX(surface, o.x(alpha));
                int py=worldToPixelY(surface,o.x(alpha), o.z(alpha));
                float baseX=o.tile.width()/2;
                float baseY=o.tile.height()-TILE_BASE;
                surface.draw(o.tile, px-baseX, py-baseY);

            }
        }

    }

    private int height(int tx, int ty) {
        return stack(tx, ty).height();
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

    private int worldYoPixelX(Surface surface, double x){
        double center=viewSize.width()*0.5;
        return (int) (center-(viewOriginX*TILE_WIDTH)+x*TILE_WIDTH);
    }

    private int worldToPixelY(Surface surface, double y, double z){
        double center=viewSize.height()*0.5;
        return (int) (center-(viewOriginY*TILE_HEIGHT-viewOriginZ*TILE_DEPTH)+y*TILE_HEIGHT-z*TILE_DEPTH);
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

    private void loadImg(){

        List<RFuture<Image>> wait=new ArrayList<RFuture<Image>>();

        for(int i=0;i<tiles.length; ++i){
            final int idx=1;
            Image tile=platform.assets().getImage(imageRes(tilesNames[i]));
            tile.state.onSuccess(new Slot<Image>() {
                public void onEmit(Image image) {
                    tiles[idx]=image.texture();
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


    private void moveBy(ChNObject chNObject, double dx, double dy, double dz){
        int tx=(int) chNObject.getPosition().getX(), ty=(int) chNObject.getPosition().getY();
        int hc=(int) chNObject.getPosition().getZ();
        int heightnorth=height(tx, ty-1);
        int heightsouth=height(tx, ty+1);
        int heightwest=height(tx-1, ty);
        int heighteast=height(tx+1,ty);
        int heightsoutheast=height(tx+1, ty+1);
        int heightnortheast=height(tx+1, ty-1);
        int heightnorthwest=height(tx-1, ty-1);
        int heightsouthwest=height(tx-1, ty+1);
        double left=chNObject.getPosition().getX()+dx-chNObject.getR(), right=chNObject.getPosition().getX()+dx+chNObject.getR();
        double top=chNObject.getPosition().getY()+dy-chNObject.getR(), bottom=chNObject.getPosition().getY()+dy+chNObject.getR();
        boolean pastLeft=left<tx, pastTop=top<ty;
        boolean pastRight=right>tx+1, pastBottom=bottom>ty+1;



    }


    private String imageRes(String name) {
        return "images/" + name + ".png";
    }

}
