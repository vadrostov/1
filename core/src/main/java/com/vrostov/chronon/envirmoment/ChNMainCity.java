package com.vrostov.chronon.envirmoment;

import com.vrostov.chronon.objects.ChNObject;
import com.vrostov.chronon.envirmoment.beans.MainCityValuesBean;
import com.vrostov.chronon.envirmoment.maintenance.CityPainter;
import com.vrostov.chronon.envirmoment.maintenance.CityPhysics;
import playn.core.*;
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

        public List<ChNObject> getObjects() {
            return objects;
        }

        public int[] getTiles() {
            return tiles;
        }

        public void setTiles(int[] tiles) {
            this.tiles = tiles;
        }

        public int height() {
            return tiles.length;
        }

    }


    private static final String[] tilesNames=new String[]{"block_wood", "block_wood"};
    private final Tile[] tiles=new Tile[tilesNames.length];

    private ValuesBean values;

    private CityPainter painter;
    private CityPhysics physics;


    private static final int TILE_HEIGHT=100;
    private static final int TILE_WIDTH=80;
    private static final int TILE_DEPTH=40;
    private static final int TILE_BASE = 90;
    private static final int TILE_IMAGE_HEIGHT = 170;
    private static final Stack EMPTY_STACK;
    private static final int MAX_STACK_HEIGHT = 35;

    private static final int OBJECT_BASE=30;

    private static final double GRAVITY = -10.0;
    private static final double RESTITUTION = 0.4;
    private static final double FRICTION = 10.0;

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
        values=new MainCityValuesBean();

        this.city=new Stack[cityHeight*cityWidth];
        int i=0;
        for (int ty=0;ty<cityHeight;++ty){
            for(int tx=0; tx<cityWidth;++tx){
                this.city[i]=new Stack();
                city[i].tiles=new int[0];
                ++i;
            }
        }

        this.painter=new CityPainter(values, city,platform);
        this.physics=new CityPhysics(values, city, platform);



    }


    //метод отрисовывает мир
    public void paint(Surface surface, float alpha){
      painter.paint(surface,alpha);
       /* if (!loaded) return;

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

                        int px= worldToPixelX(surface, x);
                        int py=worldToPixelY(surface, y,z)-TILE_BASE;
                        if ((px > viewSize.width()) || (py > viewSize.height())
                                || (px + TILE_WIDTH < 0) || (py + TILE_IMAGE_HEIGHT < 0)) {
                            continue;
                        }



                        try{
                        Tile tile=tiles[stack.tiles[z]];

                        surface.draw(tile, px, py);}
                        catch (ArrayIndexOutOfBoundsException e){
                            e.printStackTrace();
                            System.out.println(stack.tiles[z]);
                        }

                    }
                    else if (z>stack.height()){
                        paintObjects(surface, stack, z, alpha);
                    }
                }
            }


        }*/

    }

    //отрисовывает объекты на поверхности.
    /*private void paintObjects(Surface surface, Stack stack, int tz, float alpha){
        for(ChNObject o: stack.objects){
            if ((int)o.z== tz){
                //int px= worldToPixelX(surface, o.x(alpha));
                double center=viewSize.width()*0.5;
                double oxal=o.x(alpha);
                int px= (int) (center-(viewOriginX*TILE_WIDTH)+o.x(alpha)*TILE_WIDTH);
                int py=worldToPixelY(surface,o.x(alpha), o.z(alpha));
                float baseX=o.tile.width()/2;
                float baseY=o.tile.height()-OBJECT_BASE;
                surface.draw(o.tile, px-baseX, py-baseY);

            }
        }

    }*/
/*

    //вычисляет высоту для плитки по координатам
    private int height(int tx, int ty) {
        return stack(tx, ty).height();
    }
*/

    //добавляет плитку в
  /*  public void addTile(int tx, int ty, int type){
        Stack stack=createStack(tx, ty);
        int lenght=stack.tiles.length;
        if (lenght==MAX_STACK_HEIGHT){
            return;
        }
        int [] newTiles=new int[lenght+1];
        System.arraycopy(stack.tiles, 0 , newTiles,0,lenght);
        stack.tiles=newTiles;
        stack.tiles[lenght]=type;


    }*/

  /*  private Stack createStack(int tx, int ty) {
        if ((tx < 0) || (tx >= cityWidth) || (ty < 0) || (ty >= cityHeight)) {
            return EMPTY_STACK;
        }

        return city[ty * cityWidth + tx];
    }*/

    /*public void updatePhysics(double delta){
        for (int ty=0; ty<cityHeight; ++ty){
            for(int tx=0;tx<cityWidth; ++tx){
                updatePhysics(stack(tx, ty), delta);
            }
        }
    }*/
/*

    public void setViewOrigin(int x, int y, int z){
        painter.setViewOrigin();
    }
*/

   /* private Stack stackForObject(ChNObject o) {
        if ((o.x < 0) || (o.y < 0) || (o.x >= cityWidth) || (o.y >= cityHeight)) {
            return EMPTY_STACK;
        }

        return stack((int) o.x, (int) o.y);
    }
*/
    public void addObject(ChNObject o) {
        physics.addObject(o);
    }


  /*  private Stack stack(int tx, int ty) {
        if ((tx < 0) || (tx >= cityWidth) || (ty < 0) || (ty >= cityHeight)) {
            return EMPTY_STACK;
        }

        return city[ty * cityWidth + tx];
    }*/

  /*  private void updatePhysics(Stack stack, double delta){

        for(int i=0;i<stack.objects.size();++i){
            ChNObject chNObject=stack.objects.get(i);
            updatePhysics(chNObject, delta);


        }
    }*/

    /*private double pixelToWorldX(Surface surf, float x) {
        double center = viewSize.width() * 0.5;
        return (int) (((viewOriginX * TILE_WIDTH) + x - center) / TILE_WIDTH);
    }

    private int worldToPixelX(Surface surface, double x){
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
    }*/

/*    private void updatePhysics(ChNObject chNObject, double delta){


        chNObject.vx+=chNObject.getAx()*delta;
        chNObject.vy+=chNObject.getAy()*delta;
      //  chNObject.setVz(chNObject.getAz()*delta);
        moveBy(chNObject, chNObject.getVx(),chNObject.getVy()*//**, chNObject.getVz()*//*);

    }*/

  /*  private void loadImg(){

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
*/

 /*   private void moveBy(ChNObject chNObject, double dx, double dy*//*, double dz*//*){
        int tx=(int) chNObject.x, ty=(int) chNObject.y;
        int hc=(int) chNObject.z;
        int heightnorth=height(tx, ty-1);
        int heightsouth=height(tx, ty+1);
        int heightwest=height(tx-1, ty);
        int heighteast=height(tx+1,ty);
        int heightsoutheast=height(tx+1, ty+1);
        int heightnortheast=height(tx+1, ty-1);
        int heightnorthwest=height(tx-1, ty-1);
        int heightsouthwest=height(tx-1, ty+1);
        double left=chNObject.x+dx-chNObject.getR(), right=chNObject.x+dx+chNObject.getR();
        double top=chNObject.y+dy-chNObject.getR(), bottom=chNObject.y+dy+chNObject.getR();
        boolean pastLeft=left<tx, pastTop=top<ty;
        boolean pastRight=right>tx+1, pastBottom=bottom>ty+1;

        //Коллизии - проверяем возможность перемещения

        //Юг, север, запад, восток
    *//*    if (pastLeft){
            if (heightwest>hc){
                dx=tx+chNObject.getR()-chNObject.getPosition().getX();
                chNObject.setVx(-chNObject.getPosition().getX()*RESTITUTION);
            }
        } else if (pastRight){
            if (heighteast>hc){
                dx=tx+1-chNObject.getR()-chNObject.getPosition().getX();
                chNObject.setVx(-chNObject.getPosition().getX()*RESTITUTION);
            }
        }

        if (pastTop){
            if(heightnorth>hc){
                dy=ty+chNObject.getR()-chNObject.getPosition().getY();
                chNObject.setVy(-chNObject.getPosition().getY()*RESTITUTION);
            }
        } else if (pastBottom){
            if (heightsouth>hc){
                dy=ty+1-chNObject.getR()-chNObject.getPosition().getY();
                chNObject.setVy(-chNObject.getPosition().getY()*RESTITUTION);
            }
        }*//*

        //северо-восток, северо-запад, юго-восток, юго-запад
        // ~TODO
        if (pastTop&&pastRight){

        }

        if (pastTop&&pastLeft){

        }

        if (pastBottom&&pastRight){

        }

        if (pastBottom&&pastLeft){

        }

        //обновляем позицию объекта
        chNObject.x=chNObject.x+dx;
        chNObject.y=chNObject.y+dy;

        //края мира


        // TODO коллизии для высоты (как минимум крыши, возможно прыжки/полет)

    }
*/

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
