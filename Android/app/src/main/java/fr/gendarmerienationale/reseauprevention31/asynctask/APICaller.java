package fr.gendarmerienationale.reseauprevention31.asynctask;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.LOG;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.writeTraceException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.dialog.RequestConnectionDialog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class APICaller extends AsyncTask<Void, Void, Boolean> {

    private final WeakReference<Context> mContext;

    private String mKeyID, mStrRep;

    private final static String URL = "http://176.158.18.92:25003";

    private final WeakReference<RequestConnectionDialog> mDialog;

    public APICaller(String _keyID, Context _context, RequestConnectionDialog _dialog) {
        mKeyID = _keyID;

        mContext = new WeakReference<>(_context);
        mDialog = new WeakReference<>(_dialog);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        String s;
        StringBuilder chaine = new StringBuilder();

        HttpURLConnection httpConnection = null;
        PrintWriter writer = null;
        InputStream stream = null;
        BufferedReader buffer = null;
        try {
            URL url = new URL(URL);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setDoOutput(true);
            httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConnection.connect();

            // ajout de la clé d'identification

            writer  = new PrintWriter(httpConnection.getOutputStream());
            writer.write("cle_identification=" + mKeyID);
            writer.flush();



            int codeRep = httpConnection.getResponseCode();
            if (codeRep == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
                buffer = new BufferedReader(new InputStreamReader(stream));

                while ((s = buffer.readLine()) != null)
                    chaine.append(s);
            } else {
                mStrRep = translateErrHttp(mContext.get(), codeRep);
                Log.w(LOG, mStrRep);

                return false;
            }

            return true;
        } catch (Exception e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
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

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        if (result && mDialog.get() != null) {
            mDialog.get().dismiss();

            Toast.makeText(mContext.get(), mContext.get().getString(R.string.connection_successful), Toast.LENGTH_LONG)
                    .show();
        } else {
            Toast.makeText(mContext.get(),
                    mStrRep != null ? mStrRep : mContext.get().getString(R.string.connection_unsuccessful),
                    Toast.LENGTH_SHORT).show();

            mDialog.get().switchValiderState();
        }
    }

    private static String translateErrHttp(Context _ctx, int _code) {
        String strErr = _ctx.getString(R.string.err_http_connect) + " - " + _code;

        switch (_code) {
            case 401:
                strErr = _ctx.getString(R.string.err_http_auth_requise);
                break;
            case 403:
                strErr = _ctx.getString(R.string.err_http_acces_interdit);
                break;
            case 404:
                strErr = _ctx.getString(R.string.err_http_page_non_trouvee);
                break;
            case 500:
                strErr = _ctx.getString(R.string.err_http_err_serveur_interne);
                break;
        }

        return strErr;
    }
}