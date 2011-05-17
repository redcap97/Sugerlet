package com.akr97.sugerlet.util;

import java.util.Iterator;
import android.database.Cursor;

public final class CursorJoinerWithIntKey implements Iterator<CursorJoinerWithIntKey.Result>, Iterable<CursorJoinerWithIntKey.Result> {

    private Cursor mCursorLeft;

    private Cursor mCursorRight;

    private boolean mCompareResultIsValid;

    private Result mCompareResult;

    private int[] mColumnsLeft;

    private int[] mColumnsRight;

    private int[] mValues;

    public enum Result {
        RIGHT,
        LEFT,
        BOTH
    }

    public CursorJoinerWithIntKey(Cursor cursorLeft, String[] columnNamesLeft, Cursor cursorRight, String[] columnNamesRight) {
        if (columnNamesLeft.length != columnNamesRight.length) {
            throw new IllegalArgumentException("you must have the same number of columns on the left and right, " + columnNamesLeft.length + " != " + columnNamesRight.length);
        }

        mCursorLeft = cursorLeft;
        mCursorRight = cursorRight;

        mCursorLeft.moveToFirst();
        mCursorRight.moveToFirst();

        mCompareResultIsValid = false;

        mColumnsLeft = buildColumnIndiciesArray(cursorLeft, columnNamesLeft);
        mColumnsRight = buildColumnIndiciesArray(cursorRight, columnNamesRight);

        mValues = new int[mColumnsLeft.length * 2];
    }

    public Iterator<Result> iterator() {
        return this;
    }

    private int[] buildColumnIndiciesArray(Cursor cursor, String[] columnNames) {
        int[] columns = new int[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            columns[i] = cursor.getColumnIndexOrThrow(columnNames[i]);
        }
        return columns;
    }

    public boolean hasNext() {
        if (mCompareResultIsValid) {
            switch (mCompareResult) {
            case BOTH:
                return !mCursorLeft.isLast() || !mCursorRight.isLast();

            case LEFT:
                return !mCursorLeft.isLast() || !mCursorRight.isAfterLast();

            case RIGHT:
                return !mCursorLeft.isAfterLast() || !mCursorRight.isLast();

            default:
                throw new IllegalStateException("bad value for mCompareResult, " + mCompareResult);
            }
        } else {
            return !mCursorLeft.isAfterLast() || !mCursorRight.isAfterLast();
        }
    }

    public Result next() {
        if (!hasNext()) {
            throw new IllegalStateException("you must only call next() when hasNext() is true");
        }
        incrementCursors();
        assert hasNext();

        boolean hasLeft = !mCursorLeft.isAfterLast();
        boolean hasRight = !mCursorRight.isAfterLast();

        if (hasLeft && hasRight) {
            populateValues(mValues, mCursorLeft, mColumnsLeft, 0 /* start filling at index 0 */);
            populateValues(mValues, mCursorRight, mColumnsRight, 1 /* start filling at index 1 */);
            switch (compareInts(mValues)) {
            case -1:
                mCompareResult = Result.LEFT;
                break;
            case 0:
                mCompareResult = Result.BOTH;
                break;
            case 1:
                mCompareResult = Result.RIGHT;
                break;
            }
        } else if (hasLeft) {
            mCompareResult = Result.LEFT;
        } else {
            assert hasRight;
            mCompareResult = Result.RIGHT;
        }
        mCompareResultIsValid = true;
        return mCompareResult;
    }

    public void remove() {
        throw new UnsupportedOperationException("not implemented");
    }

    private static void populateValues(int[] values, Cursor cursor, int[] columnIndicies, int startingIndex) {
        assert startingIndex == 0 || startingIndex == 1;
        for (int i = 0; i < columnIndicies.length; i++) {
            values[startingIndex + i * 2] = cursor.getInt(columnIndicies[i]);
        }
    }

    private void incrementCursors() {
        if (mCompareResultIsValid) {
            switch (mCompareResult) {
            case LEFT:
                mCursorLeft.moveToNext();
                break;
            case RIGHT:
                mCursorRight.moveToNext();
                break;
            case BOTH:
                mCursorLeft.moveToNext();
                mCursorRight.moveToNext();
                break;
            }
            mCompareResultIsValid = false;
        }
    }

    private static int compareInts(int... values) {
        if ((values.length % 2) != 0) {
            throw new IllegalArgumentException("you must specify an even number of values");
        }

        for (int index = 0; index < values.length; index += 2) {
            int comp = values[index] - values[index + 1];
            if (comp != 0) {
                return comp < 0 ? -1 : 1;
            }
        }

        return 0;
    }
}