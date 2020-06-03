package fr.gendarmerienationale.reseauprevention31.asynctask;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.IP;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.LOG;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.writeTraceException;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.struct.Utilisateur;
import fr.gendarmerienationale.reseauprevention31.util.DialogsHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import org.json.JSONObject;

public class RegisterAPICaller extends AsyncTask<Void, Void, Boolean> {

    private final WeakReference<Activity> mActivity;

    private String      mStrRep;
    private Utilisateur mUtilisateur;

    private final static String URL = "http://" + IP + ":80/inscription";

    public RegisterAPICaller(Activity _activity, Utilisateur _utilisateur) {
        mUtilisateur = _utilisateur;

        mActivity = new WeakReference<>(_activity);
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
            writer.write(constructRequest());
            writer.flush();


            int codeRep = httpConnection.getResponseCode();
            if (codeRep == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
                buffer = new BufferedReader(new InputStreamReader(stream));

                while ((s = buffer.readLine()) != null)
                    chaine.append(s);

                Log.d(LOG, chaine.toString());


                JSONObject response = new JSONObject(chaine.toString());

                Iterator<String> keys = response.keys();
                if (!keys.hasNext()) {
                    mStrRep = "";

                    resultat = false;
                } else {
                    String res = keys.next();

                    if (res.equals(mActivity.get()
                            .getString(R.string.http_success))) { // Connexion réussie, initialisation des données
                        resultat = Boolean.parseBoolean(response.getString(res));
                    } else if (res.equals(mActivity.get()
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

    /**
     * Permet de construire la requête avec les données de l'utilisateur
     *
     * @return la requête construite
     */
    private String constructRequest() {
        return "prenom=" +
                mUtilisateur.getPrenom() +
                "&nom=" +
                mUtilisateur.getNom() +
                "&chambre=" +
                mUtilisateur.getChambre() +
                "&code_activite=" +
                mUtilisateur.getCodeActivite().getCode() +
                "&mail=" +
                mUtilisateur.getMail() +
                "&num_telephone=" +
                mUtilisateur.getNumeroTelephone() +
                "&nom_societe=" +
                mUtilisateur.getNomSociete() +
                "&num_siret=" +
                mUtilisateur.getNumeroSiret() +
                (mUtilisateur.getCommune() != null ? "&id_commune=" + mUtilisateur.getCommune().getId() : "") +
                "&secteur=" +
                mUtilisateur.getSecteur().getNum();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        if (result) { // Fenêtre de confirmation d'envoi de mail
            new AlertDialog.Builder(mActivity.get())
                    .setTitle(mActivity.get().getString(R.string.inscription_reussie))
                    .setMessage(mActivity
                            .get().getString(R.string.envoi_cle, mUtilisateur.getMail(), mUtilisateur.getChambre().toString()))
                    .setPositiveButton(R.string.ok, (dialog, which) -> {
                        mActivity.get().onBackPressed();
                        dialog.dismiss();
                    })
                    .setIcon(R.drawable.mail)
                    .setCancelable(false)
                    .show();
        } else {
            DialogsHelper.displayToast(mActivity.get(), mStrRep, Toast.LENGTH_LONG);
        }
    }

    private String translateHTTPError(int _code) {
        String strErr = mActivity.get().getString(R.string.err_http_connect) + " - " + _code;

        switch (_code) {
            case 401:
                strErr = mActivity.get().getString(R.string.err_http_auth_requise);
                break;
            case 403:
                strErr = mActivity.get().getString(R.string.err_http_acces_interdit);
                break;
            case 404:
                strErr = mActivity.get().getString(R.string.err_http_page_non_trouvee);
                break;
            case 500:
                strErr = mActivity.get().getString(R.string.err_http_err_serveur_interne);
                break;
        }

        return strErr;
    }

    private String translateCustomError(String _code) {
        String strErr;

        switch (_code) {
            case "100":
                strErr = mActivity.get().getString(R.string.err_http_informations_manquantes);
                break;
            case "103":
                strErr = mActivity.get().getString(R.string.err_http_err_date);
                break;
            case "104":
                strErr = mActivity.get().getString(R.string.err_http_err_register_spam);
                break;
            default:
                strErr = mActivity.get().getString(R.string.connection_unsuccessful) + ", code " + _code;
                break;

        }

        return strErr;
    }
}