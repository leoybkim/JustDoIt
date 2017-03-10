package com.leoybkim.justdoit.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by leo on 03/03/17.
 */

public final class TaskContract {
    public static final String CONTENT_AUTHORITY = "com.leoybkim.justdoit";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TASK = "tasks";

    private TaskContract() {}

    public static final class TaskEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TASK);
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TASK;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TASK;

        public static final String TABLE_NAME ="tasks";
        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_TASK_DESCRIPTION = "description";
        public static final String COLUMN_TASK_DUE_DATE = "dueDate";
        public static final String COLUMN_TASK_PRIORITY = "priority";
    }
}