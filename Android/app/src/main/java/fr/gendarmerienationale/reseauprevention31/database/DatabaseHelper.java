package fr.gendarmerienationale.reseauprevention31.database;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.NonNull;
import fr.gendarmerienationale.reseauprevention31.struct.*;
import fr.gendarmerienationale.reseauprevention31.util.Tools;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int    DATABASE_VERSION = 1;
    private static final String DATABASE_NAME    = "reseauprevention31.mDb";

    private SQLiteDatabase mDb;

    private static final String CODE_ACTIVITE_TABLE_NAME      = "CodeActivite";
    private static final String CODE_ACTIVITE_COLUMN_CODE     = "code";
    private static final String CODE_ACTIVITE_COLUMN_ACTIVITE = "activite";


    private static final String CONSEIL_TABLE_NAME   = "Conseil";
    private static final String CONSEIL_COLUMN_ID    = "id_conseil";
    private static final String CONSEIL_COLUMN_TEXTE = "texte";


    private static final String COMMUNE_TABLE_NAME         = "Commune";
    private static final String COMMUNE_COLUMN_ID          = "id_commune";
    private static final String COMMUNE_COLUMN_CODE_POSTAL = "code_postal";
    private static final String COMMUNE_COLUMN_NOM         = "nom";
    private static final String COMMUNE_COLUMN_SECTEUR     = "secteur";


    private static final String GESTIONNAIRE_TABLE_NAME      = "Gestionnaire";
    private static final String GESTIONNAIRE_COLUMN_ID       = "id_gestionnaire";
    private static final String GESTIONNAIRE_COLUMN_PASSWORD = "mdp_gestionnaire";
    private static final String GESTIONNAIRE_COLUMN_NOM      = "nom_gestionnaire";
    private static final String GESTIONNAIRE_COLUMN_PRENOM   = "prenom_gestionnaire";
    private static final String GESTIONNAIRE_COLUMN_CHAMBRE  = "chambre";

    private static final String ANNONCE_TABLE_NAME   = "Annonce";
    private static final String ANNONCE_COLUMN_ID    = "id_annonce";
    private static final String ANNONCE_COLUMN_DATE  = "date";
    private static final String ANNONCE_COLUMN_TEXTE = "texte";
    private static final String ANNONCE_COLUMN_SEEN  = "vu";

    private static final String MESSAGE_TABLE_NAME    = "Message";
    private static final String MESSAGE_COLUMN_ID     = "id_message";
    private static final String MESSAGE_COLUMN_ID_FIL = "id_fil_message";
    private static final String MESSAGE_COLUMN_DATE   = "date";
    private static final String MESSAGE_COLUMN_TEXTE  = "texte";
    private static final String MESSAGE_COLUMN_SEEN   = "vu";

    private static final String FIL_TABLE_NAME     = "Fil";
    private static final String FIL_COLUMN_ID      = "id_fil";
    private static final String FIL_COLUMN_ID_USER = "id_utilisateur";

    private static final String UTILISATEUR_TABLE_NAME         = "Utilisateur";
    private static final String UTILISATEUR_COLUMN_ID          = "id_utilisateur";
    private static final String UTILISATEUR_COLUMN_CLE         = "cle";
    private static final String UTILISATEUR_COLUMN_CODE_ACT    = "code_act";
    private static final String UTILISATEUR_COLUMN_NOM_SOCIETE = "nom_societe";
    private static final String UTILISATEUR_COLUMN_SECTEUR     = "secteur";
    private static final String UTILISATEUR_COLUMN_NOM         = "nom";
    private static final String UTILISATEUR_COLUMN_PRENOM      = "prenom";
    private static final String UTILISATEUR_COLUMN_NUM_SIRET   = "num_siret";
    private static final String UTILISATEUR_COLUMN_TELEPHONE   = "telephone";
    private static final String UTILISATEUR_COLUMN_MAIL        = "mail";
    private static final String UTILISATEUR_COLUMN_CHAMBRE     = "chambre";
    private static final String UTILISATEUR_COLUMN_ID_COMMUNE  = "id_commune";

    private static final String CONFIG_TABLE_NAME            = "Config";
    private static final String CONFIG_LOCK                  = "lock";
    private static final String CONFIG_COLUMN_LAST_DB_UPDATE = "last_db_update";

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
                            COMMUNE_COLUMN_NOM + " TEXT NOT NULL, " +
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

            // Création de la table Annonce
            _database.execSQL(
                    "CREATE TABLE " + ANNONCE_TABLE_NAME +
                            "(" + ANNONCE_COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, " +
                            ANNONCE_COLUMN_DATE + " TEXT NOT NULL, " +
                            ANNONCE_COLUMN_SEEN + " INTEGER NOT NULL DEFAULT 0, " +
                            ANNONCE_COLUMN_TEXTE + " TEXT NOT NULL)"
            );

            // Création de la table Fil
            _database.execSQL(
                    "CREATE TABLE " + FIL_TABLE_NAME +
                            "(" + FIL_COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, " +
                            FIL_COLUMN_ID_USER + " INTEGER NOT NULL, " +
                            "FOREIGN KEY(" + FIL_COLUMN_ID_USER + ") REFERENCES " +
                            UTILISATEUR_TABLE_NAME + "(" + UTILISATEUR_COLUMN_ID + "))"
            );

            // Création de la table Message
            _database.execSQL(
                    "CREATE TABLE " + MESSAGE_TABLE_NAME +
                            "(" + MESSAGE_COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, " +
                            MESSAGE_COLUMN_ID_FIL + " INTEGER NOT NULL, " +
                            MESSAGE_COLUMN_DATE + " TEXT NOT NULL, " +
                            MESSAGE_COLUMN_SEEN + " INTEGER NOT NULL DEFAULT 0, " +
                            MESSAGE_COLUMN_TEXTE + " TEXT NOT NULL, " +
                            "FOREIGN KEY(" + MESSAGE_COLUMN_ID + ") REFERENCES " +
                            FIL_TABLE_NAME + "(" + FIL_COLUMN_ID + "))"
            );

            // Création de la table Utilisateur
            _database.execSQL(
                    "CREATE TABLE " + UTILISATEUR_TABLE_NAME +
                            "(" + UTILISATEUR_COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, " +
                            UTILISATEUR_COLUMN_CLE + " TEXT NOT NULL, " +
                            UTILISATEUR_COLUMN_CODE_ACT + " INTEGER NOT NULL, " +
                            UTILISATEUR_COLUMN_SECTEUR + " INTEGER NOT NULL, " +
                            UTILISATEUR_COLUMN_NUM_SIRET + " INTEGER NOT NULL, " +
                            UTILISATEUR_COLUMN_PRENOM + " TEXT NOT NULL, " +
                            UTILISATEUR_COLUMN_NOM + " TEXT NOT NULL, " +
                            UTILISATEUR_COLUMN_TELEPHONE + " TEXT NOT NULL, " +
                            UTILISATEUR_COLUMN_NOM_SOCIETE + " TEXT NOT NULL, " +
                            UTILISATEUR_COLUMN_MAIL + " TEXT NOT NULL, " +
                            UTILISATEUR_COLUMN_CHAMBRE + " TEXT NOT NULL, " +
                            UTILISATEUR_COLUMN_ID_COMMUNE + " INTEGER, " +
                            "FOREIGN KEY(" + UTILISATEUR_COLUMN_CODE_ACT + ") REFERENCES " +
                            CODE_ACTIVITE_TABLE_NAME + "(" + CODE_ACTIVITE_COLUMN_CODE + "), " +
                            "FOREIGN KEY(" + UTILISATEUR_COLUMN_ID_COMMUNE + ") REFERENCES " +
                            COMMUNE_TABLE_NAME + "(" + COMMUNE_COLUMN_ID + "))"
            );

            // Création de la table Conseil
            _database.execSQL(
                    "CREATE TABLE " + CONSEIL_TABLE_NAME +
                            "(" + CONSEIL_COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, " +
                            CONSEIL_COLUMN_TEXTE + " TEXT NOT NULL)"
            );

            // Création de la table Config
            _database.execSQL(
                    "CREATE TABLE " + CONFIG_TABLE_NAME +
                            "(" + CONFIG_LOCK + " CHAR(1) NOT NULL DEFAULT 'X', " +
                            CONFIG_COLUMN_LAST_DB_UPDATE + " TEXT NOT NULL DEFAULT \"\", " +
                            "CONSTRAINT PK_CONFIG PRIMARY KEY (" + CONFIG_LOCK + "), " +
                            "CONSTRAINT CK_CONFIG_LOCKED CHECK (" + CONFIG_LOCK + "='X'))"
            );

            //TODO Temp
            ContentValues contentValues = new ContentValues();
            contentValues.put(CODE_ACTIVITE_COLUMN_CODE, 11);
            contentValues.put(CODE_ACTIVITE_COLUMN_ACTIVITE, "Culture");
            _database.insertOrThrow(CODE_ACTIVITE_TABLE_NAME, "", contentValues);

            contentValues = new ContentValues();
            contentValues.put(CODE_ACTIVITE_COLUMN_CODE, 22);
            contentValues.put(CODE_ACTIVITE_COLUMN_ACTIVITE, "Culture 2");
            _database.insertOrThrow(CODE_ACTIVITE_TABLE_NAME, "", contentValues);

            contentValues = new ContentValues();
            contentValues.put(ANNONCE_COLUMN_ID, 1);
            contentValues.put(ANNONCE_COLUMN_TEXTE,
                    "Contenu de l'annonce numéro 1, ce message est destiné aux particuliers et adresse les problèmes dans le secteur de Toulouse et de ses alentours. En particulier regardant la crise sanitaire actuelle et les mesures de déconfinement mises en place par le gouvernement à partir du 11 mai.");
            contentValues.put(ANNONCE_COLUMN_DATE, getCurrentDateForInsert());
            _database.insertOrThrow(ANNONCE_TABLE_NAME, "", contentValues);

            contentValues = new ContentValues();
            contentValues.put(ANNONCE_COLUMN_ID, 2);
            contentValues.put(ANNONCE_COLUMN_TEXTE,
                    "Contenu de l'annonce numéro 2, ce message est destiné aux particuliers et adresse les problèmes dans le secteur de Toulouse et de ses alentours. En particulier regardant la crise sanitaire actuelle et les mesures de déconfinement mises en place par le gouvernement à partir du 11 mai.");
            contentValues.put(ANNONCE_COLUMN_DATE, getCurrentDateForInsert());
            _database.insertOrThrow(ANNONCE_TABLE_NAME, "", contentValues);
            Log.d(LOG, "done");
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
            _db.execSQL("DROP TABLE IF EXISTS " + FIL_TABLE_NAME);
            _db.execSQL("DROP TABLE IF EXISTS " + MESSAGE_TABLE_NAME);
            _db.execSQL("DROP TABLE IF EXISTS " + UTILISATEUR_TABLE_NAME);
            _db.execSQL("DROP TABLE IF EXISTS " + ANNONCE_TABLE_NAME);
            _db.execSQL("DROP TABLE IF EXISTS " + COMMUNE_TABLE_NAME);
            _db.execSQL("DROP TABLE IF EXISTS " + GESTIONNAIRE_TABLE_NAME);
            _db.execSQL("DROP TABLE IF EXISTS " + CONSEIL_TABLE_NAME);

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
            mDb.execSQL("DELETE FROM " + FIL_TABLE_NAME);
            mDb.execSQL("DELETE FROM " + MESSAGE_TABLE_NAME);
            mDb.execSQL("DELETE FROM " + UTILISATEUR_TABLE_NAME);
            mDb.execSQL("DELETE FROM " + ANNONCE_TABLE_NAME);
            mDb.execSQL("DELETE FROM " + COMMUNE_TABLE_NAME);
            mDb.execSQL("DELETE FROM " + GESTIONNAIRE_TABLE_NAME);
            mDb.execSQL("DELETE FROM " + CONSEIL_TABLE_NAME);
        } catch (SQLException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);

            return false;
        }

        return true;
    }


    /**
     * Purge la base de données pour la mise à jour
     */
    private boolean purgeAllForUpdate() {
        if (mDb == null)
            open();

        boolean res = true;
        try {
            beginTransaction();

            // La table Utilisateur est supprimée temporairement car Commune est utilisée dans cette table
            Utilisateur utilisateur = getUser();

            mDb.execSQL("DELETE FROM " + CODE_ACTIVITE_TABLE_NAME);
            mDb.execSQL("DELETE FROM " + FIL_TABLE_NAME);
            mDb.execSQL("DELETE FROM " + MESSAGE_TABLE_NAME);
            mDb.execSQL("DELETE FROM " + UTILISATEUR_TABLE_NAME);
            mDb.execSQL("DELETE FROM " + ANNONCE_TABLE_NAME);
            mDb.execSQL("DELETE FROM " + CONSEIL_TABLE_NAME);

            saveUser(utilisateur);
        } catch (SQLException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);

            res = false;
        } finally {
            endTransaction();
        }

        return res;
    }

    /**
     * Permet de récupérer l'activité correspondante à son code dans la base de données
     *
     * @param code_act le code de l'activité
     * @return l'activité
     */
    public CodeActivite getActiviteByCode(String code_act) {
        if (mDb == null)
            open();

        CodeActivite codeActivite = null;

        Cursor cursor = null;
        try {
            String[] projection = {CODE_ACTIVITE_COLUMN_CODE, CODE_ACTIVITE_COLUMN_ACTIVITE};
            cursor = mDb.query(CODE_ACTIVITE_TABLE_NAME, projection, CODE_ACTIVITE_COLUMN_CODE + " = ?",
                    new String[]{code_act}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) { // Il y a bien une activité avec ce code
                codeActivite = new CodeActivite();
                codeActivite.setCode(cursor.getInt(0));
                codeActivite.setActivite(cursor.getString(1));
            }
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return codeActivite;
    }

    /**
     * Permet d'enregistrer un utilisateur dans la base de données
     * Seul l'utilisateur courant est enregistré ici et est supprimé lorsqu'il est déconnecté
     *
     * @param utilisateur l'utilisateur à enregister
     * @return true si l'enregistrement s'est bien passé
     */
    public boolean saveUser(Utilisateur utilisateur) {
        if (mDb == null)
            open();

        try {
            ContentValues values = new ContentValues();
            values.put(UTILISATEUR_COLUMN_ID, utilisateur.getId());
            values.put(UTILISATEUR_COLUMN_CLE, utilisateur.getCle());
            values.put(UTILISATEUR_COLUMN_CHAMBRE, utilisateur.getChambre().toString());
            values.put(UTILISATEUR_COLUMN_CODE_ACT, utilisateur.getCodeActivite().toString());
            values.put(UTILISATEUR_COLUMN_PRENOM, utilisateur.getPrenom());
            values.put(UTILISATEUR_COLUMN_NOM, utilisateur.getNom());
            values.put(UTILISATEUR_COLUMN_NUM_SIRET, utilisateur.getNumeroSiret());
            values.put(UTILISATEUR_COLUMN_TELEPHONE, utilisateur.getNumeroTelephone());
            values.put(UTILISATEUR_COLUMN_SECTEUR, utilisateur.getSecteur().toString());
            values.put(UTILISATEUR_COLUMN_MAIL, utilisateur.getMail());
            if (utilisateur.getCommune() != null)
                values.put(UTILISATEUR_COLUMN_ID_COMMUNE, utilisateur.getCommune().getId());
            values.put(UTILISATEUR_COLUMN_NOM_SOCIETE, utilisateur.getNomSociete());

            return mDb.insertOrThrow(UTILISATEUR_TABLE_NAME, "", values) != -1;
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return false;
    }

    /**
     * Permet de supprimer l'utilisateur de la base de données aka le déconnecter
     *
     * @return true si la suppression a fonctionné
     */
    public boolean deleteUser() {
        if (mDb == null)
            open();

        try {
            return mDb.delete(UTILISATEUR_TABLE_NAME, "", new String[0]) == 1;
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return false;
    }

    /**
     * Permet de savoir si l'utilisateur est connecté
     *
     * @return true si l'utilisateur est connecté
     */
    public boolean isUserConnected() {
        if (mDb == null)
            open();

        boolean res = false;

        try (Cursor cursor = mDb.rawQuery("SELECT * FROM " + UTILISATEUR_TABLE_NAME, new String[0])) {
            res = cursor.getCount() == 1;
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return res;
    }

    /**
     * Permet de récupérer la liste des codes APE
     *
     * @return la liste des codes APE
     */
    public List<CodeActivite> getCodesAPE() {
        if (mDb == null)
            open();

        List<CodeActivite> codesAPE = new ArrayList<>();
        try (Cursor cursor = mDb
                .query(CODE_ACTIVITE_TABLE_NAME, new String[]{CODE_ACTIVITE_COLUMN_CODE, CODE_ACTIVITE_COLUMN_ACTIVITE},
                        "", new String[0], null, null,
                        null)) {

            while (cursor.moveToNext()) {
                CodeActivite codeActivite = new CodeActivite();

                codeActivite.setCode(cursor.getInt(0));
                codeActivite.setActivite(cursor.getString(1));

                codesAPE.add(codeActivite);
            }
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return codesAPE;
    }

    /**
     * Permet de récupérer la liste des communes
     *
     * @return la liste des communes
     */
    public List<Commune> getCommunes() {
        if (mDb == null)
            open();

        List<Commune> communes = new ArrayList<>();

        try (Cursor cursor = mDb.query(COMMUNE_TABLE_NAME,
                new String[]{COMMUNE_COLUMN_ID, COMMUNE_COLUMN_NOM, COMMUNE_COLUMN_CODE_POSTAL,
                        COMMUNE_COLUMN_SECTEUR}, "", new String[0], null, null, null)) {

            while (cursor.moveToNext()) {
                Commune commune = new Commune();

                commune.setId(cursor.getInt(0));
                commune.setNom(cursor.getString(1));
                commune.setCodePostal(cursor.getInt(2));
                commune.setSecteur(Secteur.getSecteur(cursor.getInt(3)));

                communes.add(commune);
            }
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return communes;
    }

    /**
     * Permet de savoir si la base de données est à jour
     */
    public Date getLastDatabaseUpdateDate() {
        if (mDb == null)
            open();

        Date date = null;
        try (Cursor cursor = mDb.query(CONFIG_TABLE_NAME,
                new String[]{CONFIG_COLUMN_LAST_DB_UPDATE}, "", new String[0], null, null, null)) {

            if (cursor.moveToNext())
                date = getDateFromDatabase(cursor.getString(0));
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return date;
    }

    /**
     * Permet de récupérer la clé d'identification de l'utilisateur s'il est connecté
     *
     * @return la clé d'identification de l'utilisateur
     */
    public String getUserKey() {
        if (mDb == null)
            open();

        if (!isUserConnected())
            return null;

        String res = null;
        try (Cursor cursor = mDb.query(UTILISATEUR_TABLE_NAME,
                new String[]{UTILISATEUR_COLUMN_CLE}, "", new String[0], null, null, null)) {

            if (cursor.moveToNext())
                res = cursor.getString(0);
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return res;
    }

    /**
     * Permet de mettre à jour la base de données
     */
    public boolean updateDatabase() {
        if (mDb == null)
            open();

        File databaseFolder = new File(Tools.getDatabaseFolder());
        boolean res = false;


        // Les tables qui doivent être remplacés sont vidées
        if (!purgeAllForUpdate())
            return false;

        try {
            beginTransaction();

            // Parcours de tous les fichiers
            // Chaque fichier est transféré dans la base de données
            // Si l'insertion des données se passe sans problème, les fichiers sont supprimés
            for (File dbFile : databaseFolder.listFiles()) {
                switch (dbFile.getName().toLowerCase()) {
                    case "commune.csv":
                        if (extractCommunes(dbFile))
                            deleteFile(dbFile);
                        break;
                    case "codeactivite.csv":
                        if (extractCodeActivites(dbFile))
                            deleteFile(dbFile);
                        break;
                    case "conseil.csv":
                        if (extractConseils(dbFile))
                            deleteFile(dbFile);
                        break;
                    case "message.csv":
                        if (extractAnnonces(dbFile))
                            deleteFile(dbFile);
                        break;
                }
            }

            // Mise à jour de la date de dernière mise à jour de la base de données
            ContentValues values = new ContentValues();
            values.put(CONFIG_COLUMN_LAST_DB_UPDATE, Tools.getCurrentDateForInsert());

            res = mDb.update(CONFIG_TABLE_NAME, values, "", new String[0]) == 1;
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        } finally {
            endTransaction();
        }

        return res;
    }


    /**
     * Permet de récupérer l'utilisateur s'il est connecté
     *
     * @return l'utilisateur
     */
    private Utilisateur getUser() {
        if (mDb == null)
            open();

        if (!isUserConnected())
            return null;

        Utilisateur utilisateur = null;
        String[] columns = new String[]{
                UTILISATEUR_COLUMN_ID,
                UTILISATEUR_COLUMN_PRENOM,
                UTILISATEUR_COLUMN_NOM,
                UTILISATEUR_COLUMN_CODE_ACT,
                UTILISATEUR_COLUMN_NUM_SIRET,
                UTILISATEUR_COLUMN_SECTEUR,
                UTILISATEUR_COLUMN_NOM_SOCIETE,
                UTILISATEUR_COLUMN_CLE,
                UTILISATEUR_COLUMN_ID_COMMUNE,
                UTILISATEUR_COLUMN_CHAMBRE,
                UTILISATEUR_COLUMN_TELEPHONE,
                UTILISATEUR_COLUMN_MAIL
        };

        try (Cursor cursor = mDb.query(UTILISATEUR_TABLE_NAME,
                columns, "", new String[0], null, null, null)) {

            if (cursor.moveToNext()) {
                utilisateur = new Utilisateur();

                utilisateur.setId(cursor.getInt(0));
                utilisateur.setPrenom(cursor.getString(1));
                utilisateur.setNom(cursor.getString(2));
                utilisateur.setCodeActivite(getActiviteByCode(cursor.getString(3)));
                utilisateur.setNumeroSiret(cursor.getString(4));
                utilisateur.setSecteur(Secteur.getSecteur(cursor.getInt(5)));
                utilisateur.setNomSociete(cursor.getString(6));
                utilisateur.setCle(cursor.getString(7));

                Commune commune = new Commune();
                commune.setId(cursor.getInt(8));
                utilisateur.setCommune(commune);

                utilisateur.setChambre(cursor.getString(9));
                utilisateur.setNumeroTelephone(cursor.getString(10));
                utilisateur.setMail(cursor.getString(11));
            }
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return utilisateur;
    }

    /**
     * Permet de récupérer la commune par son id
     *
     * @param _idCommune l'id de la commune
     * @return la commune
     */
    public Commune getCommuneById(int _idCommune) {
        if (mDb == null)
            open();

        Commune commune = null;
        String[] columns = new String[]{
                COMMUNE_COLUMN_ID,
                COMMUNE_COLUMN_NOM,
                COMMUNE_COLUMN_SECTEUR,
                COMMUNE_COLUMN_CODE_POSTAL
        };
        try (Cursor cursor = mDb.query(COMMUNE_TABLE_NAME,
                columns, COMMUNE_COLUMN_ID + " = ?", new String[]{String.valueOf(_idCommune)}, null, null, null)) {

            if (cursor.moveToNext()) {
                commune = new Commune();

                commune.setId(cursor.getInt(0));
                commune.setNom(cursor.getString(1));
                commune.setSecteur(Secteur.getSecteur(cursor.getInt(2)));
                commune.setCodePostal(cursor.getInt(3));
            }
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return commune;
    }


    /**
     * Permet de récupérer le fil de discussion par son id
     *
     * @param _idFil l'id du fil de discussion
     * @return le fil de discussion
     */
    public FilDeDiscussion getFilById(int _idFil) {
        if (mDb == null)
            open();

        FilDeDiscussion fil = null;
        String[] columns = new String[]{
                FIL_COLUMN_ID,
                FIL_COLUMN_ID_USER
        };
        try (Cursor cursor = mDb.query(FIL_TABLE_NAME,
                columns, FIL_COLUMN_ID + " = ?", new String[]{String.valueOf(_idFil)}, null, null, null)) {

            if (cursor.moveToNext()) {
                fil = new FilDeDiscussion();

                fil.setId(cursor.getInt(0));
                fil.setUtilisateur(getUser());
            }
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return fil;
    }


    /**
     * Permet d'insérer une commune dans la base de données
     */
    public boolean insertCommune(Commune _commune) {
        if (mDb == null)
            open();

        try {
            ContentValues values = new ContentValues();
            values.put(COMMUNE_COLUMN_ID, _commune.getId());
            values.put(COMMUNE_COLUMN_NOM, _commune.getNom());
            values.put(COMMUNE_COLUMN_CODE_POSTAL, _commune.getCodePostal());
            values.put(COMMUNE_COLUMN_SECTEUR, _commune.getSecteur().getNum());
            return mDb.insertOrThrow(COMMUNE_TABLE_NAME, "", values) != -1;
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return false;
    }

    /**
     * Permet d'insérer un CodeActivite dans la base de données
     */
    public boolean insertCodeActivite(CodeActivite _codeActivite) {
        if (mDb == null)
            open();

        try {
            ContentValues values = new ContentValues();
            values.put(CODE_ACTIVITE_COLUMN_CODE, _codeActivite.getCode());
            values.put(CODE_ACTIVITE_COLUMN_ACTIVITE, _codeActivite.getActivite());
            return mDb.insertOrThrow(CODE_ACTIVITE_TABLE_NAME, "", values) != -1;
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return false;
    }

    /**
     * Permet d'insérer un Conseil dans la base de données
     */
    public boolean insertConseil(Conseil _conseil) {
        if (mDb == null)
            open();

        try {
            ContentValues values = new ContentValues();
            values.put(CONSEIL_COLUMN_ID, _conseil.getId());
            values.put(CONSEIL_COLUMN_TEXTE, _conseil.getTexte());
            return mDb.insertOrThrow(CONSEIL_TABLE_NAME, "", values) != -1;
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return false;
    }

    /**
     * Permet d'insérer une Annonce dans la base de données
     */
    public boolean insertAnnonce(Annonce _annonce) {
        if (mDb == null)
            open();

        try {
            ContentValues values = new ContentValues();
            values.put(ANNONCE_COLUMN_ID, _annonce.getId());
            values.put(ANNONCE_COLUMN_TEXTE, _annonce.getTexte());
            values.put(ANNONCE_COLUMN_DATE, getDateForInsert(_annonce.getDate()));
            return mDb.insertOrThrow(ANNONCE_TABLE_NAME, "", values) != -1;
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return false;
    }

    /**
     * Permet de récupérer la liste des anciennes annonces
     *
     * @return la liste des anciennes annonces
     */
    public List<Annonce> getOldAnnouncements() {
        if (mDb == null)
            open();

        List<Annonce> annonces = new ArrayList<>();
        String[] columns = new String[]{
                ANNONCE_COLUMN_ID,
                ANNONCE_COLUMN_TEXTE,
                ANNONCE_COLUMN_DATE
        };
        try (Cursor cursor = mDb.query(ANNONCE_TABLE_NAME,
                columns, ANNONCE_COLUMN_SEEN + " = ?", new String[]{"1"}, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Annonce annonce = new Annonce();

                    annonce.setId(cursor.getInt(0));
                    annonce.setTexte(cursor.getString(1));

                    annonce.setDate(Tools.getDateFromDatabase(cursor.getString(2)));
                    annonce.setVue(true);

                    annonces.add(annonce);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return annonces;
    }

    /**
     * Permet de récupérer la liste des nouvelles annonces
     *
     * @return la liste des nouvelles annonces
     */
    public List<Annonce> getNewAnnouncements() {
        if (mDb == null)
            open();

        List<Annonce> annonces = new ArrayList<>();
        String[] columns = new String[]{
                ANNONCE_COLUMN_ID,
                ANNONCE_COLUMN_TEXTE,
                ANNONCE_COLUMN_DATE
        };
        try (Cursor cursor = mDb.query(ANNONCE_TABLE_NAME,
                columns, ANNONCE_COLUMN_SEEN + " = ?", new String[]{"0"}, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Annonce annonce = new Annonce();

                    annonce.setId(cursor.getInt(0));
                    annonce.setTexte(cursor.getString(1));

                    annonce.setDate(Tools.getDateFromDatabase(cursor.getString(2)));
                    annonce.setVue(false);

                    annonces.add(annonce);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return annonces;
    }

    /**
     * Permet de mettre l'état d'un message à "Vu"
     *
     * @param _message le message
     * @return true si la base de données a été mise à jour
     */
    public boolean markMessageAsSeen(@NonNull Message _message) {
        if (mDb == null)
            open();

        boolean res = false;

        try {
            ContentValues values = new ContentValues();
            values.put(MESSAGE_COLUMN_SEEN, 1);

            res = mDb.update(MESSAGE_TABLE_NAME, values, MESSAGE_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(_message.getId())}) == 1;

            if (res)
                _message.setVu(true);
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return res;
    }

    /**
     * Permet de mettre l'état d'une annonce à "Vue"
     *
     * @param _annonce l'annonce
     * @return true si la base de données a été mise à jour
     */
    public boolean markAnnouncementAsSeen(@NonNull Annonce _annonce) {
        if (mDb == null)
            open();

        boolean res = false;

        try {
            ContentValues values = new ContentValues();
            values.put(ANNONCE_COLUMN_SEEN, 1);

            res = mDb.update(ANNONCE_TABLE_NAME, values, ANNONCE_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(_annonce.getId())}) == 1;

            if (res)
                _annonce.setVue(true);
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return res;
    }

    /**
     * Permet de récupérer tous les messages privés
     *
     * @return la liste des messages
     */
    public List<Message> getAllMessages() {
        if (mDb == null)
            open();

        List<Message> messages = new ArrayList<>();
        String[] columns = new String[]{
                MESSAGE_COLUMN_ID,
                MESSAGE_COLUMN_ID_FIL,
                MESSAGE_COLUMN_TEXTE,
                MESSAGE_COLUMN_DATE,
                MESSAGE_COLUMN_SEEN
        };
        try (Cursor cursor = mDb.query(MESSAGE_TABLE_NAME, columns, "", new String[0], null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Message message = new Message();
                    message.setId(cursor.getInt(0));
                    message.setFil(getFilById(cursor.getInt(1)));
                    message.setTexte(cursor.getString(2));
                    message.setDate(Tools.getDateFromDatabase(cursor.getString(3)));
                    message.setVu(cursor.getInt(4) == 1);

                    messages.add(message);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return messages;
    }

    private String randomNonsense =
            "orem ipsum dolor sit amet, consectetur adipiscing elit. Etiam et condimentum nibh, a fringilla odio. Integer nec aliquam elit. Aliquam erat volutpat. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Quisque leo augue, malesuada vel sem id, convallis bibendum ante. Aliquam et quam urna. Quisque cursus ullamcorper tristique. Suspendisse eu nibh in massa lacinia sodales. Suspendisse ac velit nisl.\n" +
                    "\n" +
                    "In dictum interdum sapien, eu imperdiet lorem venenatis varius. Duis volutpat facilisis magna, ut porttitor tortor consectetur sit amet. Sed sit amet sapien sed elit cursus viverra at at dui. Proin convallis condimentum quam sit amet molestie. Donec sit amet semper ligula. Integer et libero placerat, porta ipsum quis, elementum tellus. In eget dignissim nisl. In dignissim, arcu id blandit elementum, mi lectus bibendum ex, ac finibus elit diam ut mauris. Pellentesque mauris nisl, pulvinar et lectus sed, pulvinar vulputate urna.\n" +
                    "\n" +
                    "Morbi ligula nisl, euismod ut viverra vitae, tristique eget felis. Praesent volutpat, erat in sollicitudin finibus, urna turpis hendrerit lectus, eu laoreet nunc nunc venenatis mi. Phasellus suscipit mollis nulla, mattis feugiat ex consectetur id. Sed scelerisque turpis ex, sed tempor sem varius sit amet. Proin ac pulvinar nisl, eu mollis ipsum. Nunc ac eleifend dui. Curabitur auctor pellentesque interdum. Quisque ut mi erat.\n" +
                    "\n" +
                    "Maecenas sed ex sapien. Nam placerat urna nec euismod tempor. Nunc eget nisl scelerisque, congue mi vel, molestie mauris. Nulla mollis fringilla ligula quis rutrum. Mauris a lorem arcu. Vivamus sit amet commodo arcu. Mauris nec condimentum ante, eget varius urna. Donec pharetra tortor dictum libero varius, sed fermentum nisi vestibulum. Praesent mi nisl, varius ut bibendum id, viverra ac odio. Aenean eget justo at dui interdum aliquam id sit amet ipsum.\n" +
                    "\n" +
                    "Etiam fermentum hendrerit tristique. Vivamus a velit urna. Mauris eget enim lorem. Phasellus justo erat, interdum sed sodales vel, scelerisque at velit. Quisque maximus ornare viverra. Morbi eu pellentesque quam. Quisque felis eros, consectetur eget blandit nec, dignissim ut mi. Mauris urna purus, feugiat ut quam nec, pharetra pharetra dolor. Vivamus nec ornare neque, ac venenatis enim. Ut urna ipsum, sagittis at tristique nec, malesuada eu quam.\n" +
                    "\n" +
                    "Cras finibus facilisis mauris, interdum placerat lectus sagittis non. Ut consequat ipsum elementum felis mollis malesuada. Suspendisse vestibulum tempor mauris quis rhoncus. Duis ligula tortor, tincidunt nec eleifend id, finibus sed metus. Vestibulum non lectus in sapien faucibus porta. Pellentesque pretium, mi faucibus finibus interdum, neque orci consectetur mi, ut rhoncus elit risus sed eros. Pellentesque tempus enim vitae laoreet varius. Cras dignissim euismod congue. Duis sed est nisl. Ut fermentum et nunc ac pretium. Phasellus ligula neque, cursus sed ligula vel, tincidunt facilisis diam. Duis tincidunt venenatis ullamcorper. Sed mollis quam ornare urna fermentum, in vulputate ante finibus. Proin maximus dictum urna eget porta.\n" +
                    "\n" +
                    "Integer quis lobortis ex. Sed nec enim est. Vestibulum ut aliquet nisi. Sed finibus faucibus felis, quis pellentesque quam tincidunt at. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dapibus ut sem et vehicula. In est purus, scelerisque non auctor sed, ornare a diam. Orci varius.";

    //TODO TEMP à remplacer par un vrai insert
    public boolean insertRandomMessage() {
        if (mDb == null)
            open();

        try {
            Random random = new Random();
            int fil = random.nextInt();
            ContentValues values = new ContentValues();
            values.put(FIL_COLUMN_ID, fil);
            values.put(FIL_COLUMN_ID_USER, Objects.requireNonNull(getUser()).getId());
            mDb.insertOrThrow(FIL_TABLE_NAME, "", values);

            values = new ContentValues();
            values.put(MESSAGE_COLUMN_ID, random.nextInt());
            values.put(MESSAGE_COLUMN_TEXTE, randomNonsense.substring(0, random.nextInt(randomNonsense.length() - 1)));
            values.put(MESSAGE_COLUMN_DATE, getCurrentDateForInsert());
            values.put(MESSAGE_COLUMN_ID_FIL, fil);
            return mDb.insertOrThrow(MESSAGE_TABLE_NAME, "", values) != -1;
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return false;
    }

    /**
     * Permet de récupérer tous les fils de discussion
     *
     * @return la liste des fils de discussion
     */
    public List<FilDeDiscussion> getAllFilDeDiscussions() {
        if (mDb == null)
            open();

        List<FilDeDiscussion> filsDeDiscussions = new ArrayList<>();
        String[] columns = new String[]{
                FIL_COLUMN_ID,
                FIL_COLUMN_ID_USER
        };
        try (Cursor cursor = mDb.query(FIL_TABLE_NAME, columns, "", new String[0], null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    FilDeDiscussion filDeDiscussion = new FilDeDiscussion();
                    filDeDiscussion.setId(cursor.getInt(0));
                    filDeDiscussion.setUtilisateur(getUser());
                    //TODO vérifier utilisateur = récepteur
                    filsDeDiscussions.add(filDeDiscussion);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return filsDeDiscussions;
    }
}