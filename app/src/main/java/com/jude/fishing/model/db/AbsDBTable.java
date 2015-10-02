package com.jude.fishing.model.db;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by zhuchenxi on 15/10/2.
 */
public abstract class AbsDBTable<T> {

    public abstract String create();
    public abstract ContentValues to(T object);
    public abstract T from(Cursor cursor);

}
