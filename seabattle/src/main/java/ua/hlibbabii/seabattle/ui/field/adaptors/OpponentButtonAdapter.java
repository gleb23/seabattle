package ua.hlibbabii.seabattle.ui.field.adaptors;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import ua.hlibbabii.seabattle.game.domain.FieldSnapshot;
import ua.hlibbabii.seabattle.game.domain.Point;
import ua.hlibbabii.seabattle.ui.field.CellView;

/**
 * Created by hlib on 14.09.16.
 */
public class OpponentButtonAdapter extends CellsAdapter {

    public OpponentButtonAdapter(Context context, FieldSnapshot fieldSnapshot) {
        super(context, fieldSnapshot);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            Point point = getPointFromPosition(position);
            if (fieldSnapshot.isOpen(point)) {
                return CellView.createOpenedCellView(context, fieldSnapshot.isShipPresent(point));
            } else {
                return CellView.createCellView(context);
            }
        } else {
            return convertView;
        }
    }
}
