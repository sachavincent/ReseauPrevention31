package fr.gendarmerienationale.reseauprevention31.asynctask;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.LOG;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.writeTraceException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.activity.MainActivity;
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
import java.util.Date;

public class DatabaseDateAPICaller extends AsyncTask<Void, Void, Boolean> {

    private final WeakReference<Context> mContext;

    private String mKeyID, mStrRep;
    private Date mLastConnectionDate;

    private final static String URL = "http://192.168.42.26:80";

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

            writer = new PrintWriter(httpConnection.getOutputStream());
            writer.write("cle_identification=" + mKeyID + " last_connection=" + mLastConnectionDate);
            writer.flush();


            int codeRep = httpConnection.getResponseCode();
            if (codeRep == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
                buffer = new BufferedReader(new InputStreamReader(stream));

                while ((s = buffer.readLine()) != null)
                    chaine.append(s);

                Log.d(LOG, chaine.toString());

                if (chaine.toString().equalsIgnoreCase("true") ||
                        chaine.toString().equalsIgnoreCase("false")) { // Réponse correxte
                    resultat = Boolean.parseBoolean(chaine.toString());
                } else { // Erreur
                    mStrRep = translateCustomError(chaine.toString());

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

        if (result) { // La base de données est à jour
            DialogsHelper.displayToast(mContext.get(), "Base de données à jour", Toast.LENGTH_SHORT);
        } else {
            if (mStrRep == null) { // La base de données doit être mise à jour
                new UpdateDatabaseDialog(mContext.get()).show();
                //TODO: Mise à jour de la base de données
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
        String strErr = "";

        switch (_code) {
            default:
//                strErr = mContext.get().getString(R.string.connection_unsuccessful);
                break;

        }

        return strErr;
    }
}