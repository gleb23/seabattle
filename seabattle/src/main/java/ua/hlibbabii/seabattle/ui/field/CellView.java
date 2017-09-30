package ua.hlibbabii.seabattle.ui.field;

import android.content.Context;
import android.view.View;
import android.widget.Button;

/**
 * Created by hlib on 15.09.16.
 */
public class CellView extends Button {

    private static int UNKNOWN_COLOR = 0x8B71a769;

    private static int SHIP_COLOR = 0xFFFFFFFF;
    private static int SHIP_TEXT_COLOR = 0xFF000000;
    private static String SHIP_TEXT = "X";


    private static int NOTHING_COLOR = 0xFF000000;
    private static int NOTHING_TEXT_COLOR = 0xFFFFFFFF;
    private static String NOTHING_TEXT = "0";

    protected Boolean shipPresent = null;

    private CellView(Context context) {
        super(context);
        setFocusable(false);
        setFocusableInTouchMode(false);
        setClickable(false);
        setBackgroundColor(UNKNOWN_COLOR);
    }

    private CellView(Context context, boolean shipPresent) {
        this(context);
        this.shipPresent = shipPresent;
        if (shipPresent) {
            setBackgroundColor(SHIP_COLOR);
        } else {
            setBackgroundColor(NOTHING_COLOR);
        }
    }

    public void open() {
        if (!isKnownCellView()) {
            throw new IllegalStateException("Cell button is unknown. You need to specify if it contains "
            + "a ship when opening it!");
        }

        if (shipPresent) {
            setTextColor(SHIP_TEXT_COLOR);
            setText(SHIP_TEXT);
        } else {
            setTextColor(NOTHING_TEXT_COLOR);
            setText(NOTHING_TEXT);
        }
    }

    public void openUnknown(boolean isShipPresent) {
        if (isShipPresent) {
            setBackgroundColor(SHIP_COLOR);
            setTextColor(SHIP_TEXT_COLOR);
            setText(SHIP_TEXT);
        } else {
            setBackgroundColor(NOTHING_COLOR);
            setTextColor(NOTHING_TEXT_COLOR);
            setText(NOTHING_TEXT);
        }
    }

    public boolean isKnownCellView() {
        return shipPresent != null;
    }

    public static View createCellView(Context context) {
        return new CellView(context);
    }

    public static View createKnownCellView(Context context, boolean shipPresent) {
        return new CellView(context, shipPresent);
    }

    public static View createOpenedCellView(Context context, boolean shipPresent) {
        CellView knownCellView = new CellView(context, shipPresent);
        knownCellView.open();
        return knownCellView;
    }
}
