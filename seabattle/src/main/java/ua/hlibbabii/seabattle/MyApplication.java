package ua.hlibbabii.seabattle;

import android.app.Application;

import ua.hlibbabii.seabattle.game.domain.Game;

/**
 * Created by hlib on 09.11.16.
 */
public class MyApplication extends Application {
    private static MyApplication myApp;
    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
    }

    public static MyApplication getMyApp() {
        return myApp;
    }
}
