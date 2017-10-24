package com.vrostov.chronon.objects;

/**
 * Created by vrostov on 24.10.2017.
 */
public interface ObsForObjectSubject {


    public void registerObserver(ObsForObjectObserver o);
    public void deleteObserver(ObsForObjectObserver o);
    public void notifyObs();

}
