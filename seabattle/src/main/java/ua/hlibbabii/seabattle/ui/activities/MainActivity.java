package ua.hlibbabii.seabattle.ui.activities;

import ua.hlibbabii.seabattle.MyApplication;
import ua.hlibbabii.seabattle.R;
import ua.hlibbabii.seabattle.game.computer.Computer;
import ua.hlibbabii.seabattle.game.domain.Game;
import ua.hlibbabii.seabattle.game.domain.GameOptions;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    private Game game;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        View view = findViewById(R.id.resumeButton);
        view.setVisibility(game != null ? View.VISIBLE : View.GONE);
    }

    /**
     * This method is called when "Start Game" button
     * is clicked
     * 
     * @param v "Start GameInitSnapshot" button object
     */
    public void startNewGame(View v) {
        Game game = newGame();
        MyApplication.getMyApp().setGame(game);
        goToGame();
    }

    public void resumeGame(View v) {
        goToGame();
    }

    private Game newGame() {
        game = new Game(new GameOptions());
        Computer computer = new Computer(1, game);
        game.addObserver(computer);
        game.start();
        return game;
    }

    private void goToGame() {
        Intent intent = new Intent(this, PlayActivity.class);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
