package fr.gendarmerienationale.reseauprevention31.asynctask;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.IP;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.LOG;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.getCurrentLocale;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.writeTraceException;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.activity.AccueilActivity;
import fr.gendarmerienationale.reseauprevention31.dialog.UpdateDatabaseDialog;
import fr.gendarmerienationale.reseauprevention31.util.DialogsHelper;
import fr.gendarmerienationale.reseauprevention31.util.Tools;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import org.json.JSONObject;

public class DatabaseDateAPICaller extends AsyncTask<Void, Void, Boolean> {

    private final WeakReference<Context> mContext;

    private String mKeyID, mStrRep;
    private Date mLastConnectionDate;

    private final static String URL = "http://" + IP + ":80/updatebdd";

    public DatabaseDateAPICaller(Context _context, String _keyID, Date _lastConnectionDate) {
        mLastConnectionDate = _lastConnectionDate;

        mKeyID = _keyID;
        mContext = new WeakReference<>(_context);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        String s;
        StringBuilder chaine = new StringBuilder();

        HttpURLConnection httpConnection = null;
        PrintWriter writer = null;
        InputStream stream = null;
        BufferedReader buffer = null;

        boolean resultat;

        try {
            URL url = new URL(URL);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setConnectTimeout(5000);
            httpConnection.setDoOutput(true);
            httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConnection.connect();
            SimpleDateFormat df = new SimpleDateFormat(Tools.DATE_PATTERN, getCurrentLocale());
            long diff = TimeZone.getDefault().getRawOffset() - df.getTimeZone().getRawOffset();
            // Si mLastConnectionDate = null alors 1ere mise à jour, sinon on renvoie epoch depuis la derniere maj
            long lastUpdate = mLastConnectionDate == null ? -1 : mLastConnectionDate.getTime() / 1000;
            Log.d(LOG, "Last update : " + mLastConnectionDate + " => " + lastUpdate);
            writer = new PrintWriter(httpConnection.getOutputStream());
            writer.write("cle_identification=" + mKeyID + "&derniere_mise_a_jour=" + lastUpdate + "&device_id=" +
                    Tools.getDeviceID());
            writer.flush();


            int codeRep = httpConnection.getResponseCode();
            if (codeRep == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
                buffer = new BufferedReader(new InputStreamReader(stream));

                while ((s = buffer.readLine()) != null)
                    chaine.append(s);

                Log.d(LOG, "DatabaseDateAPICaller=" + chaine.toString());


                JSONObject response = new JSONObject(chaine.toString());

                Iterator<String> keys = response.keys();
                if (!keys.hasNext()) {
                    mStrRep = "";

                    resultat = false;
                } else {
                    String res = keys.next();

                    if (res.equals(mContext.get()
                            .getString(R.string.http_success))) { // Connexion réussie, initialisation des données
                        resultat = Boolean.parseBoolean(response.getString(res));
                    } else if (res.equals(mContext.get()
                            .getString(R.string.http_error))) { // Connexion impossible, lecture du code d'erreur
                        resultat = false;

                        mStrRep = translateCustomError(response.getString(res));
                    } else // Réponse mal formattée
                        resultat = false;
                }
            } else {
                mStrRep = translateHTTPError(codeRep);

                resultat = false;
            }
        } catch (Exception e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);

            resultat = false;
        } finally {
            try {
                if (httpConnection != null)
                    httpConnection.disconnect();

                if (writer != null)
                    writer.close();

                if (buffer != null)
                    buffer.close();

                if (stream != null)
                    stream.close();
            } catch (IOException e) {
                Log.w(LOG, e.getMessage());
                writeTraceException(e);
            }
        }

        return resultat;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Handler handler = new Handler();
        Log.d(LOG, "DatabaseDateAPICaller result=" + result);
        if (result) { // La base de données est à jour
            mContext.get().startActivity(new Intent(mContext.get(), AccueilActivity.class));

            handler.postDelayed(() -> {
                DialogsHelper.displayToast(mContext.get(), "Base de données à jour", Toast.LENGTH_SHORT);
            }, 1200);
        } else {
            if (mStrRep == null) { // La base de données doit être mise à jour
                handler.postDelayed(() -> {
                    new UpdateDatabaseDialog(mContext.get()).show();
                }, 1200);
            } else { // Erreur lors de la requête
                DialogsHelper.displayToast(mContext.get(), mStrRep, Toast.LENGTH_LONG);
            }
        }
    }

    private String translateHTTPError(int _code) {
        String strErr = mContext.get().getString(R.string.err_http_connect) + " - " + _code;

        switch (_code) {
            case 401:
                strErr = mContext.get().getString(R.string.err_http_auth_requise);
                break;
            case 403:
                strErr = mContext.get().getString(R.string.err_http_acces_interdit);
                break;
            case 404:
                strErr = mContext.get().getString(R.string.err_http_page_non_trouvee);
                break;
            case 500:
                strErr = mContext.get().getString(R.string.err_http_err_serveur_interne);
                break;
        }

        return strErr;
    }

    private String translateCustomError(String _code) {
        String strErr;

        switch (_code) {
            case "100":
                strErr = mContext.get().getString(R.string.err_http_informations_manquantes);
                break;
            case "101":
                strErr = mContext.get().getString(R.string.err_http_cle_inconnue);
                break;
            case "102":
                strErr = mContext.get().getString(R.string.err_http_err_bdd);
                break;
            case "103":
                strErr = mContext.get().getString(R.string.err_http_err_date);
                break;
            default:
                strErr = mContext.get().getString(R.string.connection_unsuccessful) + ", code " + _code;
                break;

        }

        return strErr;
    }
}