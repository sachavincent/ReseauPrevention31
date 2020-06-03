package fr.gendarmerienationale.reseauprevention31.asynctask;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.IP;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.LOG;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.writeTraceException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.activity.MainActivity;
import fr.gendarmerienationale.reseauprevention31.dialog.ConnectionDialog;
import fr.gendarmerienationale.reseauprevention31.struct.CodeActivite;
import fr.gendarmerienationale.reseauprevention31.struct.Commune;
import fr.gendarmerienationale.reseauprevention31.struct.Secteur;
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
import org.json.JSONException;
import org.json.JSONObject;

public class ConnectionAPICaller extends AsyncTask<Void, Void, Boolean> {

    private final WeakReference<Context> mContext;

    private String mKeyID, mStrRep;

    private final static String URL = "http://" + IP + ":80/connexion";

    private final WeakReference<ConnectionDialog> mDialog;
    private final WeakReference<MainActivity>     mMainActivity;

    private MenuItem mLogoutItem;

    public ConnectionAPICaller(String _keyID, Context _context, MainActivity _mainActivity, MenuItem _logoutItem,
            ConnectionDialog _dialog) {
        mKeyID = _keyID;

        mContext = new WeakReference<>(_context);
        mMainActivity = new WeakReference<>(_mainActivity);
        mDialog = new WeakReference<>(_dialog);

        mLogoutItem = _logoutItem;
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

            // ajout de la clé d'identification

            writer = new PrintWriter(httpConnection.getOutputStream());
            writer.write("cle_identification=" + mKeyID);
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

                    if (res.equals(mContext.get()
                            .getString(R.string.http_success))) { // Connexion réussie, initialisation des données
                        resultat = extractUtilisateur(new JSONObject(response.getString(res)));
                        if (resultat)
                            mStrRep = mContext.get().getString(R.string.connection_successful);
                        else
                            mStrRep = mContext.get().getString(R.string.connection_error);
                    } else if (res.equals(mContext.get()
                            .getString(R.string.http_error))) { // Connexion impossible, lecture du code d'erreur
                        resultat = false;

                        mStrRep = translateCustomError(response.getString(res));
                    } else { // Réponse mal formattée
                        resultat = false;

                        mStrRep = mContext.get().getString(R.string.connection_unsuccessful);
                    }
                }
            } else {
                mStrRep = translateHTTPError(codeRep);
                Log.w(LOG, mStrRep);
                resultat = false;
            }
        } catch (Exception e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);

            resultat = false;
            mStrRep = mContext.get().getString(R.string.connection_unsuccessful);

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

        if (result) { // fonctionne
            mDialog.get().dismiss();

            mLogoutItem.setVisible(true);

            Button buttonConnexion = mMainActivity.get().findViewById(R.id.buttonConnexion);
            buttonConnexion.setEnabled(false);
        } else // erreur
            mDialog.get().switchValiderState();

        DialogsHelper.displayToast(mContext.get(), mStrRep, Toast.LENGTH_LONG);
    }

    private boolean extractUtilisateur(JSONObject response) throws JSONException {
        String res;
        String id = "";
        String cle_utilisateur = "";
        String mail = "";
        String num_telephone = "";
        String num_siret = "";
        String chambre = "";
        String nom = "";
        String prenom = "";
        String nom_societe = "";
        String secteur = "";
        String code_act = "";
        String id_commune = "";
        Iterator<String> keys = response.keys();
        while (keys.hasNext()) {
            res = keys.next();
            String val = response.getString(res);
            switch (res) {
                case "id":
                    id = val;
                    break;
                case "cle_utilisateur":
                    cle_utilisateur = val;
                    break;
                case "mail":
                    mail = val;
                    break;
                case "num_telephone":
                    num_telephone = val;
                    break;
                case "chambre":
                    chambre = val;
                    break;
                case "id_commune":
                    id_commune = val;
                    break;
                case "secteur":
                    secteur = val;
                    break;
                case "code_activite":
                    code_act = val;
                    break;
                case "nom":
                    nom = val;
                    break;
                case "prenom":
                    prenom = val;
                    break;
                case "nom_societe":
                    nom_societe = val;
                    break;
                case "num_siret":
                    num_siret = val;
                    break;
                default:
                    Log.e(LOG, "Unknown field : " + res);
                    break;
            }
        }
        try {
            CodeActivite codeActivite = MainActivity.sDatabaseHelper.getActiviteByCode(code_act);
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setId(Integer.parseInt(id));
            utilisateur.setCle(cle_utilisateur);
            utilisateur.setCodeActivite(codeActivite);
            utilisateur.setMail(mail);
            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);
            utilisateur.setNomSociete(nom_societe);
            utilisateur.setNumeroTelephone(num_telephone);
            utilisateur.setChambre(chambre);
            utilisateur.setNumeroSiret(num_siret);
            utilisateur.setSecteur(Secteur.getSecteur(Integer.parseInt(secteur)));

            Commune commune = MainActivity.sDatabaseHelper.getCommuneById(Integer.parseInt(id_commune));
            utilisateur.setCommune(commune != null ? commune : new Commune(Integer.parseInt(id_commune)));

            return MainActivity.sDatabaseHelper.saveUser(utilisateur);
        } catch (NumberFormatException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);

            return false;
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
                strErr = mContext.get().getString(R.string.err_http_cle_inconnue);
                break;
            default:
                strErr = mContext.get().getString(R.string.connection_unsuccessful);
                break;

        }

        return strErr;
    }
}