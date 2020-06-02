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
import fr.gendarmerienationale.reseauprevention31.struct.Message.Emetteur;
import fr.gendarmerienationale.reseauprevention31.util.Tools;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int    DATABASE_VERSION = 1;
    private static final String DATABASE_NAME    = "reseauprevention31.mDb";

    private SQLiteDatabase mDb;

    private static final String CODE_ACTIVITE_TABLE_NAME      = "CodeActivite";
    private static final String CODE_ACTIVITE_COLUMN_CODE     = "code";
    private static final String CODE_ACTIVITE_COLUMN_ACTIVITE = "activite";


    private static final String CONSEIL_TABLE_NAME   = "Conseil";
    private static final String CONSEIL_COLUMN_ID    = "id_conseil";
    private static final String CONSEIL_COLUMN_OBJET = "objet";
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

    private static final String MESSAGE_TABLE_NAME      = "Message";
    private static final String MESSAGE_COLUMN_ID       = "id_message";
    private static final String MESSAGE_COLUMN_ID_FIL   = "id_fil_message";
    private static final String MESSAGE_COLUMN_DATE     = "date";
    private static final String MESSAGE_COLUMN_TEXTE    = "texte";
    private static final String MESSAGE_COLUMN_SEEN     = "vu";
    private static final String MESSAGE_COLUMN_EMETTEUR = "emetteur";

    private static final String FIL_TABLE_NAME                = "Fil";
    private static final String FIL_COLUMN_OBJET              = "objet";
    private static final String FIL_COLUMN_ID                 = "id_fil";
    private static final String FIL_COLUMN_ID_USER            = "id_utilisateur";
    private static final String FIL_COLUMN_ID_DERNIER_MESSAGE = "id_dernier_message";

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
                            FIL_COLUMN_OBJET + " TEXT NOT NULL, " +
                            FIL_COLUMN_ID_DERNIER_MESSAGE + " INTEGER, " +
                            "FOREIGN KEY(" + FIL_COLUMN_ID_DERNIER_MESSAGE + ") REFERENCES " +
                            MESSAGE_TABLE_NAME + "(" + MESSAGE_COLUMN_ID + "), " +
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
                            MESSAGE_COLUMN_EMETTEUR + " TEXT NOT NULL, " +
                            "FOREIGN KEY(" + MESSAGE_COLUMN_ID_FIL + ") REFERENCES " +
                            FIL_TABLE_NAME + "(" + FIL_COLUMN_ID + "))"
            );

            // Création de la table Utilisateur
            _database.execSQL(
                    "CREATE TABLE " + UTILISATEUR_TABLE_NAME +
                            "(" + UTILISATEUR_COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, " +
                            UTILISATEUR_COLUMN_CLE + " TEXT NOT NULL, " +
                            UTILISATEUR_COLUMN_CODE_ACT + " INTEGER, " +
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
                            CONSEIL_COLUMN_OBJET + " TEXT NOT NULL, " +
                            CONSEIL_COLUMN_TEXTE + " TEXT NOT NULL)"
            );

            // Création de la table Config
            _database.execSQL(
                    "CREATE TABLE " + CONFIG_TABLE_NAME +
                            "(" + CONFIG_LOCK + " CHAR(1) NOT NULL DEFAULT 'X', " +
                            CONFIG_COLUMN_LAST_DB_UPDATE + " TEXT, " +
                            "CONSTRAINT PK_CONFIG PRIMARY KEY (" + CONFIG_LOCK + "), " +
                            "CONSTRAINT CK_CONFIG_LOCKED CHECK (" + CONFIG_LOCK + "='X'))"
            );

            // Trigger fils de discussions
            String sql = "CREATE TRIGGER last_message_trigger " +
                    "AFTER INSERT ON " + MESSAGE_TABLE_NAME + " FOR EACH ROW BEGIN " +
                    "UPDATE " + FIL_TABLE_NAME +
                    " SET " + FIL_COLUMN_ID_DERNIER_MESSAGE + " = " + "new." + MESSAGE_COLUMN_ID + " WHERE " +
                    "new." + MESSAGE_COLUMN_ID_FIL + " = " + FIL_COLUMN_ID + "; " +
                    "END;";

            Log.d(LOG, sql);
            _database.execSQL(sql);

            ContentValues contentValues = new ContentValues();
            contentValues.putNull(CONFIG_COLUMN_LAST_DB_UPDATE);
            _database.insertOrThrow(CONFIG_TABLE_NAME, "", contentValues);
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
            mDb.execSQL("DELETE FROM " + COMMUNE_TABLE_NAME);

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
            if (code_act == null)
                throw new IllegalArgumentException("Code activité null");

            String[] projection = {CODE_ACTIVITE_COLUMN_CODE, CODE_ACTIVITE_COLUMN_ACTIVITE};
            cursor = mDb.query(CODE_ACTIVITE_TABLE_NAME, projection, CODE_ACTIVITE_COLUMN_CODE + " = ?",
                    new String[]{code_act}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) { // Il y a bien une activité avec ce code
                codeActivite = new CodeActivite();
                codeActivite.setCode(cursor.getInt(0));
                codeActivite.setActivite(cursor.getString(1));
            }
        } catch (IllegalArgumentException | SQLiteException e) {
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
            if (utilisateur == null)
                throw new IllegalArgumentException("Utilisateur null");

            Log.d(LOG, "saving user: " + utilisateur.toString());
            ContentValues values = new ContentValues();
            values.put(UTILISATEUR_COLUMN_ID, utilisateur.getId());
            values.put(UTILISATEUR_COLUMN_CLE, utilisateur.getCle());
            values.put(UTILISATEUR_COLUMN_CHAMBRE,
                    utilisateur.getChambre() == null ? null : utilisateur.getChambre().toString());
            values.put(UTILISATEUR_COLUMN_CODE_ACT,
                    utilisateur.getCodeActivite() == null ? null : utilisateur.getCodeActivite().toString());
            values.put(UTILISATEUR_COLUMN_PRENOM, utilisateur.getPrenom());
            values.put(UTILISATEUR_COLUMN_NOM, utilisateur.getNom());
            values.put(UTILISATEUR_COLUMN_NUM_SIRET, utilisateur.getNumeroSiret());
            values.put(UTILISATEUR_COLUMN_TELEPHONE, utilisateur.getNumeroTelephone());
            values.put(UTILISATEUR_COLUMN_SECTEUR,
                    utilisateur.getSecteur() == null ? null : utilisateur.getSecteur().getNum());
            values.put(UTILISATEUR_COLUMN_MAIL, utilisateur.getMail());
            if (utilisateur.getCommune() != null)
                values.put(UTILISATEUR_COLUMN_ID_COMMUNE, utilisateur.getCommune().getId());
            values.put(UTILISATEUR_COLUMN_NOM_SOCIETE, utilisateur.getNomSociete());

            return mDb.insertOrThrow(UTILISATEUR_TABLE_NAME, "", values) != -1;
        } catch (SQLiteException | IllegalArgumentException e) {
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
     * null si aucune màj n'a jamais été faite
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
        if (!purgeAllForUpdate()) {
            Log.w(LOG, "Purge for update failed!");
            return false;
        }

        try {
            beginTransaction();

            File[] files = databaseFolder.listFiles();

            // Les fils de discussion doivent être insérés après les messages
            File[] finalList = new File[files.length];
            File filFile = null;
            int i = 0;
            for (File f : files) {
                if (f.getName().equalsIgnoreCase("messageprive.csv")) {
                    filFile = f;
                    continue;
                }

                finalList[i++] = f;
            }

            if (filFile != null)
                finalList[i] = filFile;

            // Parcours de tous les fichiers
            // Chaque fichier est transféré dans la base de données
            // Si l'insertion des données se passe sans problème, les fichiers sont supprimés
            for (File dbFile : finalList) {
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
                    case "messageprive.csv":
                        if (extractMessages(dbFile))
                            deleteFile(dbFile);
                        break;
                    case "fildediscussion.csv":
                        if (extractFils(dbFile))
                            deleteFile(dbFile);
                        break;
                    case "annonce.csv":
                        if (extractAnnonces(dbFile))
                            deleteFile(dbFile);
                        break;
                    default:
                        Log.d(LOG, "File not supported: " + dbFile.getName());
                        break;
                }
            }

            // Mise à jour de la date de dernière mise à jour de la base de données
            ContentValues values = new ContentValues();
            values.put(CONFIG_COLUMN_LAST_DB_UPDATE, Tools.getCurrentDateForInsert());

            res = mDb.update(CONFIG_TABLE_NAME, values, null, null) == 1;
            if (res)
                Log.d(LOG, "Database successfully updated!");
            else
                Log.d(LOG, "Database didn't update!");
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
    public Utilisateur getUser() {
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

                if (cursor.getColumnCount() == 12) {
                    Commune commune = new Commune();
                    commune.setId(cursor.getInt(8));
                    utilisateur.setCommune(commune);
                }
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
                FIL_COLUMN_OBJET,
                FIL_COLUMN_ID_USER,
                FIL_COLUMN_ID_DERNIER_MESSAGE
        };
        try (Cursor cursor = mDb.query(FIL_TABLE_NAME,
                columns, FIL_COLUMN_ID + " = ?", new String[]{String.valueOf(_idFil)}, null, null, null)) {

            if (cursor.moveToNext()) {
                fil = new FilDeDiscussion();

                fil.setId(cursor.getInt(0));
                fil.setObjet(cursor.getString(1));
                fil.setUtilisateur(getUser());
                fil.setDernierMessage(getMessageById(cursor.getInt(3), fil));
            }
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return fil;
    }

    /**
     * Permet de récupérer le message par son id
     *
     * @param _idMessage l'id du message
     * @param _fil le fil du message
     * @return le message
     */
    public Message getMessageById(int _idMessage, FilDeDiscussion _fil) {
        if (mDb == null)
            open();

        Message message = null;
        String[] columns = new String[]{
                MESSAGE_COLUMN_ID,
                MESSAGE_COLUMN_DATE,
                MESSAGE_COLUMN_TEXTE,
                MESSAGE_COLUMN_SEEN,
                MESSAGE_COLUMN_EMETTEUR
        };
        try (Cursor cursor = mDb.query(MESSAGE_TABLE_NAME,
                columns, MESSAGE_COLUMN_ID + " = ?", new String[]{String.valueOf(_idMessage)}, null, null, null)) {

            if (cursor.moveToNext()) {
                message = new Message();

                message.setId(cursor.getInt(0));
                message.setDate(getDateFromDatabase(cursor.getString(1)));
                message.setTexte(cursor.getString(2));
                message.setVu(cursor.getInt(3) == 1);
                message.setEmetteur(Emetteur.getEmetteur(cursor.getString(4)));
                _fil.setDernierMessage(message);
                message.setFil(_fil);
            }
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return message;
    }


    /**
     * Permet d'insérer une commune dans la base de données
     */
    public boolean insertCommune(Commune _commune) {
        if (mDb == null)
            open();

        try {
            if (_commune == null)
                throw new IllegalArgumentException("Commune null");

            ContentValues values = new ContentValues();
            values.put(COMMUNE_COLUMN_ID, _commune.getId());
            values.put(COMMUNE_COLUMN_NOM, _commune.getNom());
            values.put(COMMUNE_COLUMN_CODE_POSTAL, _commune.getCodePostal());
            values.put(COMMUNE_COLUMN_SECTEUR, _commune.getSecteur().getNum());
            return mDb.insertOrThrow(COMMUNE_TABLE_NAME, "", values) != -1;
        } catch (IllegalArgumentException | SQLiteException e) {
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
            if (_codeActivite == null)
                throw new IllegalArgumentException("CodeActivite null");

            ContentValues values = new ContentValues();
            values.put(CODE_ACTIVITE_COLUMN_CODE, _codeActivite.getCode());
            values.put(CODE_ACTIVITE_COLUMN_ACTIVITE, _codeActivite.getActivite());
            return mDb.insertOrThrow(CODE_ACTIVITE_TABLE_NAME, "", values) != -1;
        } catch (IllegalArgumentException | SQLiteException e) {
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
            if (_conseil == null)
                throw new IllegalArgumentException("Conseil null");

            ContentValues values = new ContentValues();
            values.put(CONSEIL_COLUMN_ID, _conseil.getId());
            values.put(CONSEIL_COLUMN_OBJET, _conseil.getObjet());
            values.put(CONSEIL_COLUMN_TEXTE, _conseil.getTexte());
            return mDb.insertOrThrow(CONSEIL_TABLE_NAME, "", values) != -1;
        } catch (IllegalArgumentException | SQLiteException e) {
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
            if (_annonce == null)
                throw new IllegalArgumentException("Annonce null");

            ContentValues values = new ContentValues();
            values.put(ANNONCE_COLUMN_ID, _annonce.getId());
            values.put(ANNONCE_COLUMN_TEXTE, _annonce.getTexte());
            values.put(ANNONCE_COLUMN_DATE, getDateForInsert(_annonce.getDate()));
            return mDb.insertOrThrow(ANNONCE_TABLE_NAME, "", values) != -1;
        } catch (IllegalArgumentException | SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return false;
    }


    /**
     * Permet d'insérer un Message privé dans la base de données
     */
    public boolean insertMessage(Message _message) {
        if (mDb == null)
            open();

        try {
            if (_message == null)
                throw new IllegalArgumentException("Message null");

            ContentValues values = new ContentValues();
            values.put(MESSAGE_COLUMN_ID, _message.getId());
            values.put(MESSAGE_COLUMN_ID_FIL, _message.getFil().getId());
            values.put(MESSAGE_COLUMN_TEXTE, _message.getTexte());
            values.put(MESSAGE_COLUMN_DATE, Tools.getDateForInsert(_message.getDate()));
            values.put(MESSAGE_COLUMN_SEEN, _message.isVu() ? 1 : 0);
            values.put(MESSAGE_COLUMN_EMETTEUR, _message.getEmetteur().toString());

            return mDb.insertOrThrow(MESSAGE_TABLE_NAME, "", values) != -1;
        } catch (IllegalArgumentException | SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return false;
    }


    /**
     * Permet d'insérer un Fil de discussion dans la base de données
     */
    public boolean insertFil(FilDeDiscussion _fil) {
        if (mDb == null)
            open();

        try {
            if (_fil == null)
                throw new IllegalArgumentException("Fil null");

            ContentValues values = new ContentValues();
            values.put(FIL_COLUMN_ID, _fil.getId());
            values.put(FIL_COLUMN_OBJET, _fil.getObjet());
            values.put(FIL_COLUMN_ID_USER, _fil.getUtilisateur().getId());
            if (_fil.getDernierMessage() != null)
                values.put(FIL_COLUMN_ID_DERNIER_MESSAGE, _fil.getDernierMessage().getId());
            return mDb.insertOrThrow(FIL_TABLE_NAME, "", values) != -1;
        } catch (IllegalArgumentException | SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return false;
    }


    /**
     * Permet de récupérer les conseils
     *
     * @return les conseils
     */
    public List<Conseil> getConseils() {
        if (mDb == null)
            open();

        List<Conseil> conseils = new ArrayList<>();

        String[] columns = new String[]{
                CONSEIL_COLUMN_ID,
                CONSEIL_COLUMN_OBJET,
                CONSEIL_COLUMN_TEXTE
        };
        try (Cursor cursor = mDb.query(CONSEIL_TABLE_NAME,
                columns, null, new String[0], null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Conseil conseil = new Conseil();

                    conseil.setId(cursor.getInt(0));
                    conseil.setObjet(cursor.getString(1));
                    conseil.setTexte(cursor.getString(2));

                    conseils.add(conseil);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return conseils;
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
                columns, ANNONCE_COLUMN_SEEN + " = ?", new String[]{"1"}, null, null, ANNONCE_COLUMN_DATE + " DESC")) {

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
                columns, ANNONCE_COLUMN_SEEN + " = ?", new String[]{"0"}, null, null, ANNONCE_COLUMN_DATE + " DESC")) {

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
                MESSAGE_COLUMN_SEEN,
                MESSAGE_COLUMN_EMETTEUR
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
                    message.setEmetteur(Emetteur.getEmetteur(cursor.getString(5)));
                    messages.add(message);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return messages;
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
                FIL_COLUMN_ID_USER,
                FIL_COLUMN_OBJET,
                FIL_COLUMN_ID_DERNIER_MESSAGE
        };
        try (Cursor cursor = mDb.query(FIL_TABLE_NAME, columns, "", new String[0], null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    FilDeDiscussion filDeDiscussion = new FilDeDiscussion();
                    filDeDiscussion.setId(cursor.getInt(0));
                    filDeDiscussion.setUtilisateur(getUser());
                    filDeDiscussion.setObjet(cursor.getString(2));
                    filDeDiscussion.setDernierMessage(getMessageById(cursor.getInt(3), filDeDiscussion));
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

    /**
     * Permet de récupérer la liste des messages appartenant au fil de discussion
     *
     * @param _idFil l'id du fil de discussion
     * @return la liste des messages
     */
    public List<Message> getMessagesOfFil(int _idFil) {
        if (mDb == null)
            open();

        List<Message> messages = new ArrayList<>();
        if (_idFil == 0)
            return messages;

        String[] columns = new String[]{
                MESSAGE_COLUMN_ID,
                MESSAGE_COLUMN_ID_FIL,
                MESSAGE_COLUMN_TEXTE,
                MESSAGE_COLUMN_DATE,
                MESSAGE_COLUMN_SEEN,
                MESSAGE_COLUMN_EMETTEUR
        };
        try (Cursor cursor = mDb.query(MESSAGE_TABLE_NAME, columns, MESSAGE_COLUMN_ID_FIL + " = ?",
                new String[]{String.valueOf(_idFil)}, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Message message = new Message();
                    message.setId(cursor.getInt(0));
                    message.setFil(getFilById(cursor.getInt(1)));
                    message.setTexte(cursor.getString(2));
                    message.setDate(Tools.getDateFromDatabase(cursor.getString(3)));
                    message.setVu(cursor.getInt(4) == 1);
                    message.setEmetteur(Emetteur.getEmetteur(cursor.getString(5)));
                    //TODO Gérer le vu

                    messages.add(message);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }

        return messages;
    }
}