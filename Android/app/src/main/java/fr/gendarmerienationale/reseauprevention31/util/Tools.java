package fr.gendarmerienationale.reseauprevention31.util;

import static fr.gendarmerienationale.reseauprevention31.application.App.getContext;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import fr.gendarmerienationale.reseauprevention31.activity.MainActivity;
import fr.gendarmerienationale.reseauprevention31.struct.Annonce;
import fr.gendarmerienationale.reseauprevention31.struct.CodeActivite;
import fr.gendarmerienationale.reseauprevention31.struct.Commune;
import fr.gendarmerienationale.reseauprevention31.struct.Conseil;
import fr.gendarmerienationale.reseauprevention31.struct.Secteur;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.PatternSyntaxException;
import pub.devrel.easypermissions.EasyPermissions;

public class Tools {

    public final static String LOG = "ReseauPrevention31";

    private final static String MAIN_FOLDER     = "ReseauPrevention31";
    private final static String DATABASE_FOLDER = MAIN_FOLDER + File.separator + "Database";
    private static final String TRACE_FILE      = "ReseauPrevention31.txt";

    private final static String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private final static char DELIMITER = '\0';

    private static int width;

    public static int getScreenWidth(Activity _activity) {
        Point size = new Point();
        _activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }


    public static int getWidth() {
        return width;
    }

    public static void setWidth(int _width) {
        Tools.width = _width;
    }

    /**
     * Volume music à fond
     */
    public static void setMaxVolume(Context _context) {
        try {
            AudioManager mAudioManager = (AudioManager) _context.getSystemService(Context.AUDIO_SERVICE);
            if (Build.VERSION.SDK_INT >= 21 && mAudioManager.isVolumeFixed())
                return;
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                    mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche le clavier virtuel sur une view
     */
    public static void showKeyboardOnView(View v, Context _context) {
        v.requestFocus();
        InputMethodManager imm = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * Permet de ferme le clavier virtuel
     */
    public static void hideKeyboardFromView(View v, Context _context) {
        v.clearFocus();
        InputMethodManager imm = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    /**
     * Permet de récupérer le Locale de l'utilisateur
     */
    public static Locale getCurrentLocale() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return getContext().getResources().getConfiguration().getLocales().get(0);
        else
            return getContext().getResources().getConfiguration().locale;

    }

    /**
     * Permet de récupérer la date formattée à partir d'un String
     */
    public static Date getDateFromDatabase(String _date) {
        if (_date == null || _date.equals(""))
            return null;

        try {
            return new SimpleDateFormat(DATE_PATTERN, getCurrentLocale()).parse(_date);
        } catch (ParseException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }
        return null;
    }

    /**
     * Permet de comparer deux dates
     */
    public static boolean compareDates(String _date1, String _date2) {
        if (_date1 == null || _date1.equals("") || _date2 == null || _date2.equals(""))
            return false;

        return _date1.equals(_date2);
    }

    /**
     * Permet de comparer la date donnée avec la date d'aujourd'hui
     */
    public static boolean isDateToday(String _date) {
        return compareDates(_date, getStringDate(Calendar.getInstance().getTime()));
    }

    /**
     * Permet de récupérer la date formatée pour une insertion à partir d'une date
     */
    public static String getDateFromDatabase(Date _date) {
        if (_date == null)
            return "";
        return new SimpleDateFormat(DATE_PATTERN, getCurrentLocale()).format(_date);
    }

    /**
     * Permet de récupérer la date actuelle formatée pour une insertion
     */
    public static String getCurrentDateForInsert() {
        return new SimpleDateFormat(DATE_PATTERN, getCurrentLocale()).format(Calendar.getInstance().getTime());
    }

    /**
     * Permet de récupérer la date formattée pour une insertion
     */
    public static String getDateForInsert(Date _date) {
        return new SimpleDateFormat(DATE_PATTERN, getCurrentLocale()).format(_date);
    }

    /**
     * Permet de récupérer la date formattée à partir d'une date
     */
    public static String getStringDate(Date _date) {
        if (_date == null)
            return "";
        return new SimpleDateFormat("dd/MM/yyyy", getCurrentLocale()).format(_date);
    }

    /**
     * Permet de supprimer un dossier
     */
    public static void deleteDirectory(File _directory) {
        File[] files = _directory.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory())
                    deleteDirectory(f);
                else
                    f.delete();
            }
        }
        _directory.delete();
    }

    /**
     * Permet d'écrire dans un fichier
     */
    public static void writeToFile(String _file, String _message) {
        try {
            if (!EasyPermissions.hasPermissions(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE))
                throw new PermissionDeniedException("Vous n'avez pas la permission d'accéder au stockage");

            File directory = new File(getDirectoryPath());
            if (!directory.exists())
                directory.mkdirs();

            File file = new File(getPath(_file));
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    Log.w(LOG, e.getMessage());
                    Log.w(LOG, "Exception : " + _file);
                    Log.w(LOG, "Exception : " + _message);
                }
            }

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file, true),
                    StandardCharsets.UTF_8);
            BufferedWriter bfWriter = new BufferedWriter(outputStreamWriter);
            bfWriter.write(_message);
            bfWriter.write("\r\n");

            bfWriter.close();
            outputStreamWriter.close();
        } catch (IOException | SecurityException | PermissionDeniedException e) {
            Log.w(LOG, e.getMessage());
            Log.w(LOG, "Exception : " + _file);
            Log.w(LOG, "Exception : " + _message);
            writeTraceException(e);
        }
    }

    /**
     * Permet de récupérer le chemin complet du fichier dans le dossier de l'application
     */
    public static String getPath(String _fileName) {
        return getDirectoryPath() + File.separator + _fileName;
    }

    /**
     * Permet de récupérer le chemin complet du fichier
     * dans le dossier de l'application avec ses sous dossiers
     */
    public static String getPath(File _file) {
        try {
            String postFolderPath = _file.getPath().split(MAIN_FOLDER.replace("+", "\\+"))[1];

            return getDirectoryPath() + postFolderPath;
        } catch (PatternSyntaxException | IndexOutOfBoundsException e) {
            writeTraceException(e);
            Log.w(LOG, e.getMessage());

            return getPath(_file.getName());
        }
    }

    /**
     * Permet de récupérer le chemin complet du dossier de l'application
     */
    public static String getDirectoryPath() {
        String path = Environment.getExternalStorageDirectory() + File.separator + MAIN_FOLDER;
        File directory = new File(path);
        if (!directory.exists())
            directory.mkdir();

        return path;
    }

    /**
     * Permet de récupérer le chemin complet du dossier Database de l'application
     */
    public static String getDatabaseFolder() {
        String path = Environment.getExternalStorageDirectory() + File.separator + DATABASE_FOLDER;
        File directory = new File(path);
        if (!directory.exists())
            directory.mkdirs();

        return path;
    }

    /**
     * Permet de changer le chemin du fichier source vers le fichier destination à partir des noms des fichiers
     */
    public static boolean moveFile(String _sourceFileName, String _destFileName) {
        return moveFile(new File(getPath(_sourceFileName)), new File(getPath(_destFileName)));
    }

    /**
     * Permet de changer le chemin du fichier source vers le fichier destination
     */
    public static boolean moveFile(File _sourceFile, File _destFile) {
        try {
            if (!EasyPermissions.hasPermissions(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE))
                throw new PermissionDeniedException("Vous n'avez pas la permission d'accéder au stockage");

            if (_destFile.exists())
                _destFile.delete();

            return _sourceFile.renameTo(_destFile);
        } catch (SecurityException | PermissionDeniedException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }
        return false;
    }

    /**
     * Permet de supprimer le fichier avec le nom donné
     */
    public static boolean deleteFile(String _fileName) {
        return deleteFile(new File(getPath(_fileName)));
    }

    /**
     * Permet de supprimer le fichier donné
     */
    public static boolean deleteFile(File _file) {
        try {
            if (!EasyPermissions.hasPermissions(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE))
                throw new PermissionDeniedException("Vous n'avez pas la permission d'accéder au stockage");

            if (_file.exists())
                return _file.delete();
        } catch (SecurityException | PermissionDeniedException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }
        return false;
    }

    /**
     * Permet de copier le contenu du fichierA et dans le fichierB à partir de leurs noms
     */
    public static boolean copyFileContent(String _source, String _dest, boolean _append, boolean _deleteSource) {
        return copyFileContent(new File(getPath(_source)), new File(getPath(_dest)), _append, _deleteSource);
    }

    /**
     * Permet de copier le contenu du fichierA et dans le fichierB
     */
    public static boolean copyFileContent(File _source, File _dest, boolean _append, boolean _deleteSource) {
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bfWriter = null;

        InputStreamReader inputStreamReader = null;
        BufferedReader brReader = null;
        try {
            if (!EasyPermissions.hasPermissions(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE))
                throw new PermissionDeniedException("Vous n'avez pas la permission d'accéder au stockage");

            File directory = new File(getDirectoryPath());
            if (!directory.exists())
                throw new IOException("Dossier de l'application inexistant");

            if (!_source.exists())
                throw new IllegalArgumentException("Fichier source inexistant");

            if (!_dest.exists())
                _dest.createNewFile();

            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(_dest, _append), StandardCharsets.UTF_8);
            bfWriter = new BufferedWriter(outputStreamWriter);

            inputStreamReader = new InputStreamReader(new FileInputStream(_source), StandardCharsets.UTF_8);
            brReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = brReader.readLine()) != null) {
                bfWriter.write(line);
                bfWriter.write("\r\n");
            }

            if (_deleteSource)
                return _source.delete();

            return true;
        } catch (IOException | SecurityException | PermissionDeniedException | IllegalArgumentException e) {
            Log.w(LOG, e.getMessage());
            Log.w(LOG, "Exception : Source : " + _source);
            Log.w(LOG, "Exception : Destination : " + _dest);
            writeTraceException(e);
        } finally {
            try {
                if (bfWriter != null)
                    bfWriter.close();
                if (outputStreamWriter != null)
                    outputStreamWriter.close();
                if (brReader != null)
                    brReader.close();
                if (inputStreamReader != null)
                    inputStreamReader.close();
            } catch (IOException e) {
                Log.w(LOG, e.getMessage());
                writeTraceException(e);
            }
        }
        return false;
    }

    /**
     * Permet d'écrire le message dans la trace de l'application
     */
    public static void writeTrace(String _message) {
        File file;
        try {
            if (!EasyPermissions.hasPermissions(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE))
                throw new PermissionDeniedException("Vous n'avez pas la permission d'accéder au stockage");

            file = new File(getPath(TRACE_FILE));
            if (!file.exists())
                file.createNewFile();
        } catch (IOException | PermissionDeniedException e) {
            Log.w(LOG, e.getMessage());
            Log.w(LOG, "Exception : " + _message);

            return;
        }

        String time = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", getCurrentLocale())
                .format(Calendar.getInstance().getTime());
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file, true),
                    StandardCharsets.UTF_8);
            BufferedWriter bfWriter = new BufferedWriter(outputStreamWriter);
            bfWriter.write(time + " => " + _message);
            bfWriter.write("\r\n");
            bfWriter.write("\r\n");
            bfWriter.close();
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.w(LOG, e.getMessage());
            Log.w(LOG, "Exception : " + _message);
        }
    }

    /**
     * Permet d'écrire l'exception dans la trace de l'application
     */
    public static void writeTraceException(Exception _exception) {
        File file;
        try {
            if (!EasyPermissions.hasPermissions(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE))
                throw new PermissionDeniedException("Vous n'avez pas la permission d'accéder au stockage");

            file = new File(getPath(TRACE_FILE));
            if (!file.exists())
                file.createNewFile();
        } catch (IOException | PermissionDeniedException e) {
            Log.w(LOG, e.getMessage());
            Log.w(LOG, "Exception : " + _exception.getMessage());

            return;
        }

        String time = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", getCurrentLocale())
                .format(Calendar.getInstance().getTime());
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file, true),
                    StandardCharsets.UTF_8);
            BufferedWriter bfWriter = new BufferedWriter(outputStreamWriter);
            bfWriter.write(time + " => (" + getExceptionLine(_exception));
            bfWriter.write("\r\n");
            bfWriter.write(_exception.getMessage());
            bfWriter.write("\r\n");
            bfWriter.write("\r\n");
            bfWriter.close();
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.w(LOG, e.getMessage());
            Log.w(LOG, "Exception : " + _exception.getMessage());
        }
    }

    /**
     * Permet de récupérer les lignes des erreurs d'une exception
     */
    public static String getExceptionLine(Exception _exception) {
        StringBuilder res = new StringBuilder();
        for (StackTraceElement s : _exception.getStackTrace())
            res.append(s.getFileName()).append(", line=").append(s.getLineNumber()).append("; ");

        return res.toString();
    }

    /**
     * Permet de récupérer la taille du fichier
     */
    public static double getFileWeight(String _fileName) {
        try {
            if (!EasyPermissions.hasPermissions(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE))
                throw new PermissionDeniedException("Vous n'avez pas la permission d'accéder au stockage");

            File file = new File(getPath(_fileName));
            if (!file.exists())
                throw new IllegalArgumentException("Fichier = " + _fileName + " inexistant, taille introuvable.");

            return file.length();
        } catch (IllegalArgumentException | PermissionDeniedException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }
        return -1;
    }

    /**
     * Permet de récupérer le numéro de série du téléphone
     * https://stackoverflow.com/questions/11029294/android-how-to-programmatically-access-the-device-serial-number-shown-in-the-av/52606366#52606366
     */
    @SuppressLint("HardwareIds")
    public static String getDeviceID() {
        return Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * Permet de récupérer le nom du device
     * https://stackoverflow.com/questions/1995439/get-android-phone-model-programmatically
     */
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer))
            return capitalize(model);

        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str))
            return str;

        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c))
                capitalizeNext = true;

            phrase.append(c);
        }

        return phrase.toString();
    }

    /**
     * Permet de vérifier que le numéro Siret est correct
     *
     * @param _numSiret le num Siret
     * @return true si le numéro Siret est correct
     */
    public static boolean isNumSiretCorrect(String _numSiret) {
        if (_numSiret.length() != 14)
            return false;

        try {
            Long.parseLong(_numSiret);
        } catch (NumberFormatException e) {
            return false;
        }

        int resultat = 0;
        for (int i = 0; i < 14; i++) {
            int val = Character.getNumericValue(_numSiret.charAt(i));
            int res;

            if (i % 2 == 0) // Emplacement pair
                res = val * 2;
            else
                res = val;

            if (res > 9)
                res = sumDigits(res);

            resultat += res;
        }

        return resultat % 10 == 0;
    }

    /**
     * Permet d'additionner les chiffres d'un nombre jusqu'à que celui ci soit un chiffre
     *
     * @param n le nombre
     * @return le chiffre
     */
    public static int sumDigits(int n) {
        return (1 + ((n - 1) % 9));
    }


    /**
     * Permet d'extraire les CodeActivités du fichier donné et de les insérer dans la base de données
     *
     * @param _dbFile le fichier donné contenant les données des CodeActivités
     */
    public static boolean extractCodeActivites(File _dbFile) {
        FileReader reader = null;
        BufferedReader bufferedReader = null;

        boolean res = true;
        try {
            reader = new FileReader(_dbFile);
            bufferedReader = new BufferedReader(reader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(String.valueOf(DELIMITER));

                CodeActivite codeActivite = new CodeActivite();
                codeActivite.setCode(Integer.parseInt(values[0]));
                codeActivite.setActivite(values[1]);

                boolean done = MainActivity.sDatabaseHelper.insertCodeActivite(codeActivite);
                if (!done) {
                    res = false;

                    Log.w(LOG, "Insertion went wrong for CodeActivite: " + codeActivite.toString());
                }
            }

        } catch (IOException | NumberFormatException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);

            res = false;
        } finally {
            try {
                if (reader != null)
                    reader.close();
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                Log.w(LOG, e.getMessage());
                writeTraceException(e);
            }
        }

        return res;
    }

    /**
     * Permet d'extraire les communes du fichier donné et de les insérer dans la base de données
     *
     * @param _dbFile le fichier donné contenant les données des communes
     */
    public static boolean extractCommunes(File _dbFile) {
        FileReader reader = null;
        BufferedReader bufferedReader = null;

        boolean res = true;
        try {
            reader = new FileReader(_dbFile);
            bufferedReader = new BufferedReader(reader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(String.valueOf(DELIMITER));

                Commune commune = new Commune();
                commune.setId(Integer.parseInt(values[0]));
                commune.setCodePostal(Integer.parseInt(values[1]));
                commune.setNom(values[2]);
                commune.setSecteur(Secteur.getSecteur(Integer.parseInt(values[3])));

                boolean done = MainActivity.sDatabaseHelper.insertCommune(commune);
                if (!done) {
                    res = false;

                    Log.w(LOG,
                            "Insertion went wrong for Commune: " + commune.toString() + ", with id " + commune.getId());
                }
            }

        } catch (IOException | NumberFormatException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);

            res = false;
        } finally {
            try {
                if (reader != null)
                    reader.close();
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                Log.w(LOG, e.getMessage());
                writeTraceException(e);
            }
        }

        return res;
    }

    /**
     * Permet d'extraire les conseils du fichier donné et de les insérer dans la base de données
     *
     * @param _dbFile le fichier donné contenant les données des conseils
     */
    public static boolean extractConseils(File _dbFile) {
        FileReader reader = null;
        BufferedReader bufferedReader = null;

        boolean res = true;
        try {
            reader = new FileReader(_dbFile);
            bufferedReader = new BufferedReader(reader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(String.valueOf(DELIMITER));

                Conseil conseil = new Conseil();
                conseil.setId(Integer.parseInt(values[0]));
                conseil.setTexte(values[1]);

                boolean done = MainActivity.sDatabaseHelper.insertConseil(conseil);
                if (!done) {
                    res = false;

                    Log.w(LOG, "Insertion went wrong for Conseil: " + conseil.toString());
                }
            }

        } catch (IOException | NumberFormatException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);

            res = false;
        } finally {
            try {
                if (reader != null)
                    reader.close();
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                Log.w(LOG, e.getMessage());
                writeTraceException(e);
            }
        }

        return res;
    }

    /**
     * Permet d'extraire les annonces du fichier donné et de les insérer dans la base de données
     *
     * @param _dbFile le fichier donné contenant les données des annonces
     */
    public static boolean extractAnnonces(File _dbFile) {
        FileReader reader = null;
        BufferedReader bufferedReader = null;

        boolean res = true;
        try {
            reader = new FileReader(_dbFile);
            bufferedReader = new BufferedReader(reader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(String.valueOf(DELIMITER));

                Annonce annonce = new Annonce();
                annonce.setId(Integer.parseInt(values[0]));
                annonce.setDate(getDateFromDatabase(values[1]));
                annonce.setTexte(values[2]);

                boolean done = MainActivity.sDatabaseHelper.insertAnnonce(annonce);
                if (!done) {
                    res = false;

                    Log.w(LOG, "Insertion went wrong for Annonce: " + annonce.toString());
                }
            }

        } catch (IOException | NumberFormatException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);

            res = false;
        } finally {
            try {
                if (reader != null)
                    reader.close();
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                Log.w(LOG, e.getMessage());
                writeTraceException(e);
            }
        }

        return res;
    }
}
