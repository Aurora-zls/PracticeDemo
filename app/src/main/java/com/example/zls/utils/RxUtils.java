package com.example.zls.utils;


import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Created by chaichuanfa on 2019/1/17
 */
public final class RxUtils {

    private RxUtils() {
    }

    public static <T> Consumer<T> idleConsumer() {
        return t -> {

        };
    }

    public static Action idleAction() {
        return () -> {

        };
    }


    public static final Consumer<Throwable> IgnoreErrorProcessor = throwable -> {
        Timber.e(throwable, "RxUtils.IgnoreErrorProcessor");
    };
}
