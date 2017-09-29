package com.vrostov.cronon;

import com.vrostov.chronon.ChNGame;
import playn.java.LWJGLPlatform;

/**
 * Created by vrostov on 29.09.2017.
 */
public class ChronoNGame {

    public static void main(String[] args) {
        LWJGLPlatform.Config config = new LWJGLPlatform.Config();
        config.width=800;
        config.height=600;
        LWJGLPlatform platform=new LWJGLPlatform(config);
        new ChNGame(platform);
        platform.start();
    }
}
