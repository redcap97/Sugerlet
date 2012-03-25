/*
Copyright (C) 2012 Akira Midorikawa

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.akr97.sugerlet.util;

import java.util.Iterator;
import android.database.Cursor;

public final class CursorJoinerWithLongKey implements Iterator<CursorJoinerWithLongKey.Result>, Iterable<CursorJoinerWithLongKey.Result> {

	private Cursor mCursorLeft;

	private Cursor mCursorRight;

	private boolean mCompareResultIsValid;

	private Result mCompareResult;

	private int[] mColumnsLeft;

	private int[] mColumnsRight;

	private long[] mValues;

	public enum Result {
		RIGHT,
		LEFT,
		BOTH
	}

	public CursorJoinerWithLongKey(Cursor cursorLeft, String[] columnNamesLeft, Cursor cursorRight, String[] columnNamesRight) {
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

		mValues = new long[mColumnsLeft.length * 2];
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
			switch (compareLongs(mValues)) {
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

	private static void populateValues(long[] values, Cursor cursor, int[] columnIndicies, int startingIndex) {
		assert startingIndex == 0 || startingIndex == 1;
		for (int i = 0; i < columnIndicies.length; i++) {
			values[startingIndex + i * 2] = cursor.getLong(columnIndicies[i]);
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

	private static int compareLongs(long... values) {
		if ((values.length % 2) != 0) {
			throw new IllegalArgumentException("you must specify an even number of values");
		}

		for (int index = 0; index < values.length; index += 2) {
			long comp = values[index] - values[index + 1];
			if (comp != 0) {
				return comp < 0 ? -1 : 1;
			}
		}

		return 0;
	}
}
