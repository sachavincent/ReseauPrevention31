package fr.gendarmerienationale.reseauprevention31.database;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.LOG;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.writeTraceException;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int    DATABASE_VERSION = 1;
    private static final String DATABASE_NAME    = "reseauprevention31.mDb";

    private SQLiteDatabase mDb;

    private static final String CODE_ACTIVITE_TABLE_NAME      = "CodeActivite";
    private static final String CODE_ACTIVITE_COLUMN_CODE     = "code";
    private static final String CODE_ACTIVITE_COLUMN_ACTIVITE = "activite";


    private static final String COMMUNE_TABLE_NAME         = "Commune";
    private static final String COMMUNE_COLUMN_ID          = "id_commune";
    private static final String COMMUNE_COLUMN_CODE_POSTAL = "code_postal";
    private static final String COMMUNE_COLUMN_COMMUNE     = "commune";
    private static final String COMMUNE_COLUMN_SECTEUR     = "secteur";


    private static final String GESTIONNAIRE_TABLE_NAME      = "Gestionnaire";
    private static final String GESTIONNAIRE_COLUMN_ID       = "id_gestionnaire";
    private static final String GESTIONNAIRE_COLUMN_PASSWORD = "mdp_gestionnaire";
    private static final String GESTIONNAIRE_COLUMN_NOM      = "nom_gestionnaire";
    private static final String GESTIONNAIRE_COLUMN_PRENOM   = "prenom_gestionnaire";
    private static final String GESTIONNAIRE_COLUMN_CHAMBRE  = "chambre";


    private static final String MESSAGE_TABLE_NAME       = "Message";
    private static final String MESSAGE_COLUMN_ID        = "id_message";
    private static final String MESSAGE_COLUMN_ID_AUTEUR = "id_auteur";
    private static final String MESSAGE_COLUMN_CHAMBRE   = "chambre";
    private static final String MESSAGE_COLUMN_DATE      = "date";
    private static final String MESSAGE_COLUMN_TEXTE     = "texte";


    private static final String SOCIETE_TABLE_NAME         = "Societe";
    private static final String SOCIETE_COLUMN_ID          = "id_societe";
    private static final String SOCIETE_COLUMN_ID_ACTIVITE = "id_activite";
    private static final String SOCIETE_COLUMN_SECTEUR     = "secteur";
    private static final String SOCIETE_COLUMN_CODE_POSTAL = "code_postal";
    private static final String SOCIETE_COLUMN_TELEPHONE   = "telephone";
    private static final String SOCIETE_COLUMN_MAIL        = "mail";


    private static final String UTILISATEUR_TABLE_NAME         = "Utilisateur";
    private static final String UTILISATEUR_COLUMN_ID          = "id_utilisateur";
    private static final String UTILISATEUR_COLUMN_CLE         = "cle";
    private static final String UTILISATEUR_COLUMN_CODE_ACT    = "code_act";
    private static final String UTILISATEUR_COLUMN_SECTEUR     = "secteur";
    private static final String UTILISATEUR_COLUMN_CODE_POSTAL = "code_postal";
    private static final String UTILISATEUR_COLUMN_TELEPHONE   = "telephone";
    private static final String UTILISATEUR_COLUMN_MAIL        = "mail";
    private static final String UTILISATEUR_COLUMN_CHAMBRE     = "chambre";

    public DatabaseHelper(Context _context) {
        super(_context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase _database) {
        try {
            // Création de la table CodeActivite
            _database.execSQL(
                    "CREATE TABLE " + CODE_ACTIVITE_TABLE_NAME +
                            "(" + CODE_ACTIVITE_COLUMN_CODE + " INTEGER NOT NULL PRIMARY KEY, " +
                            CODE_ACTIVITE_COLUMN_ACTIVITE + " TEXT NOT NULL)"
            );

            // Création de la table Commune
            _database.execSQL(
                    "CREATE TABLE " + COMMUNE_TABLE_NAME +
                            "(" + COMMUNE_COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, " +
                            COMMUNE_COLUMN_CODE_POSTAL + " INTEGER NOT NULL, " +
                            COMMUNE_COLUMN_COMMUNE + " TEXT NOT NULL, " +
                            COMMUNE_COLUMN_SECTEUR + " INTEGER NOT NULL)"
            );

            // Création de la table Gestionnaire
            _database.execSQL(
                    "CREATE TABLE " + GESTIONNAIRE_TABLE_NAME +
                            "(" + GESTIONNAIRE_COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, " +
                            GESTIONNAIRE_COLUMN_PASSWORD + " TEXT NOT NULL, " +
                            GESTIONNAIRE_COLUMN_NOM + " TEXT NOT NULL, " +
                            GESTIONNAIRE_COLUMN_PRENOM + " TEXT NOT NULL, " +
                            GESTIONNAIRE_COLUMN_CHAMBRE + " TEXT NOT NULL)"
            );

            // Création de la table Message
            _database.execSQL(
                    "CREATE TABLE " + MESSAGE_TABLE_NAME +
                            "(" + MESSAGE_COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, " +
                            MESSAGE_COLUMN_ID_AUTEUR + " INTEGER NOT NULL, " +
                            MESSAGE_COLUMN_CHAMBRE + " TEXT NOT NULL, " +
                            MESSAGE_COLUMN_DATE + " TEXT NOT NULL, " +
                            MESSAGE_COLUMN_TEXTE + " TEXT NOT NULL, " +
                            "FOREIGN KEY(" + MESSAGE_COLUMN_ID_AUTEUR + ") REFERENCES " +
                            UTILISATEUR_TABLE_NAME + "(" + UTILISATEUR_COLUMN_ID + "))"
            );

            // Création de la table Societe
            _database.execSQL(
                    "CREATE TABLE " + SOCIETE_TABLE_NAME +
                            "(" + SOCIETE_COLUMN_ID + " INTEGER NOT NULL, " +
                            SOCIETE_COLUMN_ID_ACTIVITE + " INTEGER NOT NULL, " +
                            SOCIETE_COLUMN_SECTEUR + " INTEGER NOT NULL, " +
                            SOCIETE_COLUMN_CODE_POSTAL + " INTEGER NOT NULL, " +
                            SOCIETE_COLUMN_TELEPHONE + " INTEGER NOT NULL, " +
                            SOCIETE_COLUMN_MAIL + " TEXT NOT NULL, " +
                            "FOREIGN KEY(" + SOCIETE_COLUMN_ID_ACTIVITE + ") REFERENCES " +
                            CODE_ACTIVITE_TABLE_NAME + "(" + CODE_ACTIVITE_COLUMN_CODE + "))"
            );

            // Création de la table Utilisateur
            _database.execSQL(
                    "CREATE TABLE " + UTILISATEUR_TABLE_NAME +
                            "(" + UTILISATEUR_COLUMN_ID + " INTEGER NOT NULL, " +
                            UTILISATEUR_COLUMN_CLE + " TEXT NOT NULL, " +
                            UTILISATEUR_COLUMN_CODE_ACT + " INTEGER NOT NULL, " +
                            UTILISATEUR_COLUMN_SECTEUR + " INTEGER NOT NULL, " +
                            UTILISATEUR_COLUMN_CODE_POSTAL + " INTEGER NOT NULL, " +
                            UTILISATEUR_COLUMN_TELEPHONE + " TEXT NOT NULL, " +
                            UTILISATEUR_COLUMN_MAIL + " TEXT NOT NULL, " +
                            UTILISATEUR_COLUMN_CHAMBRE + " TEXT NOT NULL, " +
                            "FOREIGN KEY(" + UTILISATEUR_COLUMN_CODE_ACT + ") REFERENCES " +
                            CODE_ACTIVITE_TABLE_NAME + "(" + CODE_ACTIVITE_COLUMN_CODE + "))"
            );
        } catch (SQLException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
        // Supprime les anciennes tables
        try {
            _db.execSQL("DROP TABLE IF EXISTS " + CODE_ACTIVITE_TABLE_NAME);
            _db.execSQL("DROP TABLE IF EXISTS " + SOCIETE_TABLE_NAME);
            _db.execSQL("DROP TABLE IF EXISTS " + UTILISATEUR_TABLE_NAME);
            _db.execSQL("DROP TABLE IF EXISTS " + MESSAGE_TABLE_NAME);
            _db.execSQL("DROP TABLE IF EXISTS " + COMMUNE_TABLE_NAME);
            _db.execSQL("DROP TABLE IF EXISTS " + GESTIONNAIRE_TABLE_NAME);

            onCreate(_db);
        } catch (SQLException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
        onUpgrade(_db, _oldVersion, _newVersion);
    }

    public void open() {
        mDb = this.getWritableDatabase();
    }

    public void beginTransaction() {
        if (mDb == null)
            open();

        mDb.beginTransaction();
    }

    public void cancelTransaction() {
        if (mDb == null)
            return;

        if (mDb.inTransaction())
            mDb.endTransaction();
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
        if (mDb == null)
            open();

        try {
            mDb.execSQL("DELETE FROM " + CODE_ACTIVITE_TABLE_NAME);
            mDb.execSQL("DELETE FROM " + SOCIETE_TABLE_NAME);
            mDb.execSQL("DELETE FROM " + UTILISATEUR_TABLE_NAME);
            mDb.execSQL("DELETE FROM " + MESSAGE_TABLE_NAME);
            mDb.execSQL("DELETE FROM " + COMMUNE_TABLE_NAME);
            mDb.execSQL("DELETE FROM " + GESTIONNAIRE_TABLE_NAME);
            return true;
        } catch (SQLException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);

            return false;
        }
    }
}