package fr.gendarmerienationale.reseauprevention31.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import fr.gendarmerienationale.reseauprevention31.R;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Cette classe permet d'envoyer des requêtes au serveur et de
 * gérer sa réponse
 */
public class APICaller extends AsyncTask<Void, Void, Boolean> {

	private final WeakReference<Context> mContext;

	private String mUser, mPassword, mIdDevice, mVersionApp, mVersionDev, mModelDev, mToken, mStrRep;


	private final WeakReference<RequestConnectionDialog> mDialog;

	public APICaller(String _user, String _password, String _idDevice, String _versionApp, String _versionDev, String _modelDev, Context _context, RequestConnectionDialog _dialog) {
		mUser = _user;
		mPassword = _password;
		mIdDevice = _idDevice;
		mVersionApp = _versionApp;
		mVersionDev = _versionDev;
		mModelDev = _modelDev;

		mContext = new WeakReference<>(_context);
		mDialog = new WeakReference<>(_dialog);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}


	@Override
	protected Boolean doInBackground(Void... params) {
		String s;
		StringBuilder chaine = new StringBuilder();

		String urlParameters = mContext.get().getString(R.string.web_http_post_ident).replace(" ", "&");
		urlParameters = urlParameters.replace("XXX", mUser);
		urlParameters = urlParameters.replace("YYY", mPassword);
		urlParameters = urlParameters.replace("ZZZ", mIdDevice);
		urlParameters = urlParameters.replace("AAA", mVersionApp);
		urlParameters = urlParameters.replace("BBB", mVersionDev);
		urlParameters = urlParameters.replace("CCC", mModelDev);

		try {
			URL url = new URL("https://api.tourgest.net/v3/auth");
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			httpConnection.setRequestMethod("POST");
			httpConnection.setDoOutput(true);
			httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpConnection.connect();

			// ajout des paramètres
			OutputStreamWriter writer = new OutputStreamWriter(httpConnection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();

			int codeRep = httpConnection.getResponseCode();
			if (codeRep == HttpURLConnection.HTTP_OK) {
				InputStream stream = httpConnection.getInputStream();
				BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
				while ((s = buffer.readLine()) != null)
					chaine.append(s);

				Log.d(LOG, chaine.toString());
				JSonIdent ident = extractTourGestWebIdent(mContext.get(), chaine.toString());
				if (ident == null) return false;

				if (ident.getError() == null)
					mToken = ident.getToken();
				else {
					mStrRep = translateErrIdent(ident.getError());

					return false;
				}
			} else {
				mStrRep = translateErrHttp(mContext.get(), codeRep);
				Log.w(LOG, mStrRep);

				return false;
			}
			writer.close();

			if (httpConnection != null)
				httpConnection.disconnect();

			return true;
		} catch (Exception e) {
			Log.w(LOG, e.getMessage());
			writeTraceException(e);
		}

		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);

		if (mToken != null && !mToken.isEmpty() && result && mDialog.get() != null) {
			mDialog.get().dismiss();

			Toast.makeText(mContext.get(), mContext.get().getString(R.string.connection_successful), Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(mContext.get(), mStrRep != null ? mStrRep : mContext.get().getString(R.string.connection_unsuccessful), Toast.LENGTH_SHORT).show();

			mDialog.get().switchValiderState();
		}
	}

	private String translateErrIdent(String _err) {
		String strErr = _err;

		switch (_err) {
			case "100":
				strErr = mContext.get().getString(R.string.err_http_serveur_err);
				break;
			case "101":
				strErr = mContext.get().getString(R.string.err_http_username_password_invalid);
				break;
			case "102":
				strErr = mContext.get().getString(R.string.err_http_licence_utilisee);
				break;
			case "103":
				strErr = mContext.get().getString(R.string.err_http_compte_expire);
				break;
			case "104":
				strErr = mContext.get().getString(R.string.err_http_app_invalid);
				break;
		}

		return strErr;
	}

	private static JSonIdent extractTourGestWebIdent(Context _context, String _strReponse) {
		JSonIdent ident = new JSonIdent();

		try {
			JSONObject reader = new JSONObject(_strReponse);
			Iterator<String> keys = reader.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				if (key.equals(_context.getString(R.string.json_error))) {
					ident.setError(reader.getString(key));
				} else if (key.equals(_context.getString(R.string.json_ident_token))) {
					ident.setToken(reader.getString(key));
				} else if (key.equals(_context.getString(R.string.json_ident_releveur_identity))) {
					ident.setReleveurIdentitiy(reader.getString(key));
				} else if (key.equals(_context.getString(R.string.json_ident_regie_name))) {
					ident.setRegieName(reader.getString(key));
				} else if (key.equals(_context.getString(R.string.json_ident_expiration))) {
					ident.setExpirationDate(reader.getString(key));
				} else {
					Log.d(LOG, "Key=" + key);
				}
			}
		} catch (JSONException e) {
			Log.w(LOG, e.getMessage());
			writeTraceException(e);
			return null;
		}

		return ident;
	}

	private static String translateErrHttp(Context _ctx, int _code) {
		String strErr = _ctx.getString(R.string.err_http_connect) + " - " + String.valueOf(_code);

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