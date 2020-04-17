package fr.gendarmerienationale.reseauprevention31.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.LOG;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.writeTraceException;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "reseauprevention31.mDb";
    private SQLiteDatabase mDb;

    public DatabaseHelper(Context _context) {
        super(_context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase _database) {
        try {
        } catch (SQLException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Supprime les anciennes tables
        try {

            onCreate(db);
        } catch (SQLException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void open() {
        mDb = this.getWritableDatabase();
    }

    public void beginTransaction() {
        if (mDb == null) open();
        mDb.beginTransaction();
    }

    public void cancelTransaction() {
        if (mDb == null) return;

        if (mDb.inTransaction()) mDb.endTransaction();
    }

    public void endTransaction() {
        if (mDb.inTransaction()) {
            mDb.setTransactionSuccessful();
            mDb.endTransaction();
        }
    }

    /**
     * Purge toutes les données
     */
    public boolean purgeAll() {
        if (mDb == null) open();

        try {
            return true;
        } catch (SQLException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);

            return false;
        }
    }
}