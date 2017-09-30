package ua.hlibbabii.seabattle.ui.field.adaptors;

import android.content.Context;
import android.widget.BaseAdapter;

import ua.hlibbabii.seabattle.game.domain.FieldSnapshot;
import ua.hlibbabii.seabattle.game.domain.Point;

/**
 * Created by hlib on 14.09.16.
 */
public abstract class CellsAdapter extends BaseAdapter {

    protected final Context context;
    protected final FieldSnapshot fieldSnapshot;

    public CellsAdapter(Context context, FieldSnapshot fieldSnapshot) {
        super();
        this.context = context;
        this.fieldSnapshot = fieldSnapshot;
    }

    @Override
    public int getCount() {
        int dimensions = fieldSnapshot.getRows();
        return dimensions * dimensions;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected Point getPointFromPosition(int position) {
        return Point.create(position / 10, position % 10);
    }
}
