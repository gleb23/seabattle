package ua.hlibbabii.seabattle.ui.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import ua.hlibbabii.seabattle.GameObserver;
import ua.hlibbabii.seabattle.MyApplication;
import ua.hlibbabii.seabattle.R;
import ua.hlibbabii.seabattle.game.domain.FieldSnapshot;
import ua.hlibbabii.seabattle.game.domain.Game;
import ua.hlibbabii.seabattle.game.domain.Move;
import ua.hlibbabii.seabattle.game.domain.MoveResult;
import ua.hlibbabii.seabattle.game.domain.Point;
import ua.hlibbabii.seabattle.ui.field.FieldView;

/**
 * Created by hlib on 14.09.16.
 */
public class PlayActivity extends FragmentActivity implements GameObserver {

    private ShootFragment shootFragment;
    private ShotFragment shotFragment;

    private ViewSwitcher viewSwitcher;

    public static class ShootFragment extends Fragment {

        private FieldView fieldView;

        public FieldView getFieldView() {
            return fieldView;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_shoot, container, false);

            Game game = MyApplication.getMyApp().getGame();
            Activity activity = getActivity();
            FieldSnapshot fieldSnapshot = game.getComputerFieldSnapshotForPlayer();
            Log.d("", "Computer filed snapshot: " + fieldView);
            fieldView = FieldView.createOpponentFieldView(view, R.id.button_computer_grid, activity, fieldSnapshot);
            fieldView.setOnCellClickListener(point -> {
                if (!fieldView.isKnownCellView(point)) {
                    new Thread(() -> {
                        game.makeMove(Move.create(point, 0));

                    }).start();
                } else {
                    Toast.makeText(activity, "This cell is already opened!", Toast.LENGTH_SHORT).show();
                }
                    }
            );


            return view;
        }
    }

    public static class ShotFragment extends Fragment {

        private FieldView fieldView;

        public FieldView getFieldView() {
            return fieldView;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_shot, container, false);

            Game game = MyApplication.getMyApp().getGame();
            FieldSnapshot fieldSnapshot = game.getPlayerFieldSnapshotForPlayer();
            Log.d("", "Player filed snapshot: " + fieldSnapshot);
            fieldView = FieldView.createMyFieldView(view, R.id.button_player_grid,
                    getActivity(), fieldSnapshot);

            return view;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    shootFragment = new ShootFragment();
                    return shootFragment;
                case 1:
                    shotFragment = new ShotFragment();
                    return shotFragment;
                default:
                    throw new RuntimeException();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        viewSwitcher = (ViewSwitcher) findViewById(R.id.statusViewSwitcher);
        MyPagerAdapter mDemoCollectionPagerAdapter =
                new MyPagerAdapter(
                        getSupportFragmentManager());
        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);

        Game game = MyApplication.getMyApp().getGame();
        game.addObserver(this);

        initLogic();
    }

    private void initLogic() {
    }

    /* called from worker thread*/
    @Override
    public void updateLastMoveResult(MoveResult lastMoveResult) {
        latsMoveResultHandler.obtainMessage(0, lastMoveResult)
                .sendToTarget();

    }

    private Handler latsMoveResultHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            /* ui thread */
            MoveResult moveResult = (MoveResult) msg.obj;
            updateUIAfterMove(moveResult);
        }
    };

    private void updateUIAfterMove(MoveResult moveResult) {
        Toast.makeText(PlayActivity.this, moveResult.toString(), Toast.LENGTH_SHORT).show();
        if (!moveResult.isMoveSucceeded()) {
            return;
        }
        Point point = moveResult.getPoint();
        FieldView fieldView;
        if (moveResult.getTurn() == 0) {
            boolean shipPresent = moveResult.shipHit();
            fieldView = shootFragment.getFieldView();
            fieldView.open(point, shipPresent);
        } else {
            fieldView = shotFragment.getFieldView();
            fieldView.open(point);
        }
        fieldView.invalidate(point);
        viewSwitcher.showNext();
    }
}
