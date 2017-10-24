package com.vrostov.chronon.environment.maintenance;

import com.vrostov.chronon.environment.EnvironmentStack;
import com.vrostov.chronon.environment.ValuesBean;
import com.vrostov.chronon.objects.ChNObject;
import playn.core.Platform;

import static java.lang.Math.max;

/**
 * Created by vrostov on 18.10.2017.
 */
public class CityPhysics {

    private ValuesBean values;
    EnvironmentStack[] world;
    Platform platform;


    private int updateCounter = -1;

    public CityPhysics(ValuesBean values, EnvironmentStack[] world, Platform platform) {
        this.values = values;
        this.world = world;
        this.platform = platform;
    }

    public void updatePhysics(double delta){
        for (int ty = 0; ty < values.getHeight(); ++ty) {
            for (int tx = 0; tx < values.getWidth(); ++tx) {
                updatePhysics(stack(tx, ty), delta);
            }
        }
        EnvironmentStack emptyStack=new EnvironmentStack();
        emptyStack.setTiles(new int[0]);
        updatePhysics(emptyStack, delta);

        ++updateCounter;
    }

    private void updatePhysics(EnvironmentStack stack, double delta){
        for (int i = 0; i < stack.getObjects().size(); ++i) {
            // Run physics.
            ChNObject o = stack.getObjects().get(i);
            updatePhysics(o, delta);

            // Re-sort.
            EnvironmentStack newStack = stackForObject(o);
            if (stack != newStack) {
                stack.getObjects().remove(i--);
                newStack.getObjects().add(o);
                o.setStack(newStack);
            }
        }
    }

    private void updatePhysics(ChNObject o, double delta){
        // Avoid double-updates.
        if (o.getLastUpdated() == updateCounter) {
            return;
        }
        o.setLastUpdated(updateCounter);
        o.saveOldPos();

        // Gravity & friction.
        if (o.z > o.getStack().stackHeight()) {
            o.setAz(o.getAz()+delta*values.getGRAVITY());
        }

        if (o.isResting()) {
            o.setVx(o.getVx()-o.getVx()*values.getFRICTION()*delta);
            o.setVy(o.getVy()-o.getVy()*values.getFRICTION()*delta);
            if (o.getVz() < 0) {
                o.setVz(0);
            }
        }

        // Update velocity
        o.setVx(o.getVx()+o.getAx()*delta);
        o.setVy(o.getVy()+o.getAy()*delta);
        o.setVz(o.getVz()+o.getAz()*delta);

        // Update position and handle collisions.
        moveBy(o, o.getVx(), o.getVy(), o.getVz());
    }

    private void moveBy(ChNObject o, double dx, double dy, double dz){
        int tx = (int) o.x, ty = (int) o.y;
        int hc = (int) o.z;
        int hn = height(tx, ty - 1);
        int hs = height(tx, ty + 1);
        int hw = height(tx - 1, ty);
        int he = height(tx + 1, ty);
        int hse = height(tx + 1, ty + 1);
        int hne = height(tx + 1, ty - 1);
        int hsw = height(tx - 1, ty + 1);
        int hnw = height(tx - 1, ty - 1);

        double left = o.x + dx - o.getR(), right = o.x + dx + o.getR();
        double top = o.y + dy - o.getR(), bottom = o.y + dy + o.getR();
        boolean pastLeft = left < tx, pastTop = top < ty;
        boolean pastRight = right > tx + 1, pastBottom = bottom > ty + 1;

        // Collisions: north, east, west, south.
        if (pastLeft) {
            if (hw > hc) {
                dx = tx + o.getR() - o.x;
                o.setVx(-o.getVx()*values.getRESTITUTION());
            }
        } else if (pastRight) {
            if (he > hc) {
                dx = tx + 1 - o.getR() - o.x;
                o.setVx(-o.getVx()*values.getRESTITUTION());
            }
        }

        if (pastTop) {
            if (hn > hc) {
                dy = ty + o.getR() - o.y;
                o.setVy(-o.getVy()*values.getRESTITUTION());
            }
        } else if (pastBottom) {
            if (hs > hc) {
                dy = ty + 1 - o.getR() - o.y;
                o.setVy(-o.getVy()*values.getRESTITUTION());
            }
        }

        // Collisions: nw, ne, se, sw.
        if (pastLeft && pastTop) {
            if (hnw > hc) {
                if (tx - left > ty - top) {
                    dy = ty - (o.y - o.getR());
                    o.setVy(-o.getVy()*values.getRESTITUTION());
                } else {
                    dx = tx - (o.x - o.getR());
                    o.setVx(-o.getVx()*values.getRESTITUTION());
                }
            }
        }

        if (pastRight && pastTop) {
            if (hne > hc) {
                if (right - (tx + 1) > ty - top) {
                    dy = ty - (o.y - o.getR());
                    o.setVy(-o.getVy()*values.getRESTITUTION());
                } else {
                    dx = (tx + 1) - (o.getR() + o.x);
                    o.setVx(-o.getVx()*values.getRESTITUTION());
                }
            }
        }

        if (pastRight && pastBottom) {
            if (hse > hc) {
                if (right - (tx + 1) > bottom - (ty + 1)) {
                    dy = (ty + 1) - (o.getR() + o.y);
                    o.setVy(-o.getVy()*values.getRESTITUTION());
                } else {
                    dx = (tx + 1) - (o.getR() + o.x);
                    o.setVx(-o.getVx()*values.getRESTITUTION());
                }
            }
        }

        if (pastLeft && pastBottom) {
            if (hsw > hc) {
                if (tx - left > bottom - (ty + 1)) {
                    dy = (ty + 1) - (o.getR() + o.y);
                    o.setVy(-o.getVy()*values.getRESTITUTION());
                } else {
                    dx = tx - (o.x - o.getR());
                    o.setVx(-o.getVx()*values.getRESTITUTION());
                }
            }
        }

        // Update x/y position.
        o.x = o.x + dx;
        o.y = o.y + dy;

        // Clamp to world bounds.
        if (o.x < o.getR()) {
            o.x = o.getR();
        }
        if (o.y < o.getR()) {
            o.y = o.getR();
        }
        if (o.x > values.getWidth() - o.getR()) {
            o.x = values.getWidth() - o.getR();
        }
        if (o.y > values.getHeight() - o.getR()) {
            o.y = values.getHeight() - o.getR();
        }

        // Collisions: floors.
        left = o.x + dx - o.getR();
        right = o.x + dx + o.getR();
        top = o.y + dy - o.getR();
        bottom = o.y + dy + o.getR();
        pastLeft = left < tx - 0.01;
        pastTop = top < ty - 0.01;
        pastRight = right > tx + 1.01;
        pastBottom = bottom > ty + 1.01;

        double floor = height(tx, ty);
        if (pastLeft && hw - o.z < 0.5) {
            floor = max(floor, hw);
        }
        if (pastTop && hn - o.z < 0.5) {
            floor = max(floor, hn);
        }
        if (pastRight && he - o.z < 0.5) {
            floor = max(floor, he);
        }
        if (pastBottom && hs - o.z < 0.5) {
            floor = max(floor, hs);
        }

        if (o.z + dz < floor) {
            dz = floor - o.z;
            o.setVz(-o.getVz()*values.getRESTITUTION());

            if (o.getVz() < 0.01) {
                o.setVz(0);
            }
            o.setResting(true);
        } else {
            o.setResting(o.getVz()==0);
        }

        o.z = o.z + dz;

        // Clamp to world bounds.
        if (o.z < 0) {
            o.z = 0;
        }
        if (o.z > values.getMAX_STACK_HEIGHT() - 0.01) {
            o.z = values.getMAX_STACK_HEIGHT() - 0.01;
        }
    }

    private EnvironmentStack stack(int tx, int ty) {
        if ((tx < 0) || (tx >= values.getWidth()) || (ty < 0) || (ty >= values.getHeight())) {
            EnvironmentStack emptyStack=new EnvironmentStack();
            emptyStack.setTiles(new int[0]);
        }

        return world[ty * values.getWidth() + tx];
    }

    public void addObject(ChNObject o) {
        EnvironmentStack stack = stackForObject(o);
        stack.getObjects().add(o);
        o.setStack(stack);
    }

    private EnvironmentStack stackForObject(ChNObject o) {
        if ((o.x < 0) || (o.y < 0) || (o.x >= values.getWidth()) || (o.y >= values.getHeight())) {
            EnvironmentStack emptyStack=new EnvironmentStack();
            emptyStack.setTiles(new int[0]);
            return emptyStack;
        }

        return stack((int) o.x, (int) o.y);
    }

    private int height(int tx, int ty) {
        return stack(tx, ty).stackHeight();
    }



}
