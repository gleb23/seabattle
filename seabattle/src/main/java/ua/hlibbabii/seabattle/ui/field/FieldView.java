package ua.hlibbabii.seabattle.ui.field;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import ua.hlibbabii.seabattle.game.domain.FieldSnapshot;
import ua.hlibbabii.seabattle.game.domain.Point;
import ua.hlibbabii.seabattle.ui.field.adaptors.MyButtonAdapter;
import ua.hlibbabii.seabattle.ui.field.adaptors.OpponentButtonAdapter;

/**
 * Created by hlib on 05.11.16.
 */
public class FieldView {

    private GridView cellGrid;
    private OnCellClickListener onCellClickListener = point -> {/* do nothing by default */};
    private int dimensions;

    private FieldView(View parentView, int gridViewId, Context context, FieldSnapshot fieldSnapshot, boolean myField) {
        dimensions = fieldSnapshot.getRows();
        cellGrid = (GridView) parentView.findViewById(gridViewId);
        cellGrid.setAdapter(myField ?
                new MyButtonAdapter(context, fieldSnapshot) :
                new OpponentButtonAdapter(context, fieldSnapshot));
        cellGrid.setOnItemClickListener((parent, v, position, id) -> {
            onCellClickListener.fire(Point.create(position / dimensions, position % dimensions));
        });
    }

    public static FieldView createMyFieldView(View parentView, int gridViewId, Context context,
                                    FieldSnapshot fieldSnapshot) {
        return new FieldView(parentView, gridViewId, context, fieldSnapshot, true);
    }

    public static FieldView createOpponentFieldView(View parentView, int gridViewId, Context context,
                                              FieldSnapshot fieldSnapshot) {
        return new FieldView(parentView, gridViewId, context, fieldSnapshot, false);
    }

    public boolean isKnownCellView(Point point) {
        CellView cellView = (CellView)getCellViewAt(point);
        return cellView.isKnownCellView();
    }

    public void invalidate(Point point) {
        CellView cellView = (CellView)getCellViewAt(point);
        cellView.invalidate();
    }


    @FunctionalInterface
    public interface OnCellClickListener {
        void fire(Point point);
    }

    public void setOnCellClickListener(OnCellClickListener onCellClickListener) {
        this.onCellClickListener = onCellClickListener;
    }

    public void open(Point point) {
        CellView cellView = (CellView)getCellViewAt(point);
        cellView.open();
    }

    public void open(Point point, boolean isShipPresent) {
        CellView cellView = (CellView)getCellViewAt(point);
        cellView.openUnknown(isShipPresent);
    }

    private View getCellViewAt(Point point) {
        View childAt = cellGrid.getChildAt(point.getHor() * dimensions + point.getVer());
        return childAt;
    }
}
