package fr.gendarmerienationale.reseauprevention31.asynctask;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.LOG;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.writeTraceException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.activity.MainActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * Cette classe permet de télécharger des fichiers depuis le serveur
 */
public class FTPDownloader extends AsyncTask<Void, String, Boolean> {
	private final WeakReference<Context> mContext;
	private final WeakReference<Activity> mActivity;
	private final Dialog mDialog;
	private FTPClient mFtpClient;
	private String mMessage;

	private List<File> mFilesToUpdate;

	public FTPDownloader(Context _context, Dialog _dialog) {
		this.mContext = new WeakReference<>(_context);
		this.mActivity = null;
		this.mDialog = _dialog;
	}

	public FTPDownloader(Context _context, Dialog _dialog, List<File> _filesToUpdate) {
		this.mContext = new WeakReference<>(_context);
		this.mActivity = null;
		this.mDialog = _dialog;

		this.mFilesToUpdate = _filesToUpdate;
	}

	public FTPDownloader(Activity _activity, Context _context, AlertDialog _dialog) {
		this.mContext = new WeakReference<>(_context);
		this.mActivity = new WeakReference<>(_activity);
		this.mDialog = _dialog;
	}

	@Override
	protected void onPreExecute() {
		// Change le message
		if (mDialog instanceof AlertDialog)
			((AlertDialog) mDialog).setMessage(mContext.get().getString(R.string.msg_veuillez_patienter));

		if (mActivity != null) {
			if (mDialog instanceof AlertDialog)
				((AlertDialog) mDialog).getButton(android.app.AlertDialog.BUTTON_POSITIVE).setVisibility(View.GONE);
			else if (mDialog instanceof CustomDialog)
				((CustomDialog) mDialog).onInit();
		}
	}

	@Override
	protected Boolean doInBackground(Void... dialog) {
		try {
			// Le context
			Context context = mContext.get();

			if (MainActivity.sDatabaseHelper == null) {
				mMessage = context.getString(R.string.unavailableDB);
				return false;
			}

			// Démarre la transaction SqlLite
			MainActivity.sDatabaseHelper.beginTransaction();

			// Initialise le client FTP
			mFtpClient = new FTPClient();
			// Augmente la taille du buffer pour un download plus rapide
			mFtpClient.setBufferSize(1024000);

			mFtpClient.setControlEncoding("UTF-8");

			// Connexion FTP
			mFtpClient.connect(Config.sFTPAddress);
			mFtpClient.login(Config.sFTPLogin, Config.sFTPPassword);

			// Passage en mode passif
			mFtpClient.enterLocalPassiveMode();

			// Type de fichier binaire
			mFtpClient.setFileType(FTP.BINARY_FILE_TYPE);

			// Change de working directory
			mFtpClient.changeWorkingDirectory(mFtpClient.printWorkingDirectory() + "/PARAMETRES");

			// Liste des fichiers du dossier
			FTPFile[] files = mFtpClient.listFiles();

			// Chemin de sauvegarde des fichiers
			String savePath = context.getFilesDir().getAbsolutePath();

			// Si des fichiers à télécharger ont été spécifiés, alors on ne réinitialise pas
			if (mFilesToUpdate != null && !mFilesToUpdate.isEmpty()) {
				if (mDialog instanceof CustomDialog)
					publishProgress(((CustomDialog) mDialog).onDownloadStart());

				for (File f : mFilesToUpdate) {
					String name = f.getName();
					FTPFile mostRecentFile = findMostRecentFile(files, f.getName());
					if (mostRecentFile == null) continue;

					downloadFile(mostRecentFile.getName(), savePath + File.separator + name);
				}

				// Termine la transaction SqlLite
				MainActivity.sDatabaseHelper.endTransaction();

				// Déconnexion du FTP
				mFtpClient.disconnect();
				return true;
			}


			// Supprime les anciennes données
			boolean purged = MainActivity.sDatabaseHelper.purgeAll();
			if (!purged) {
				mMessage = context.getString(R.string.purge_impossible);
				return false;
			}

			// On supprime la configuration car on la retélécharge juste après
			purged = MainActivity.sDatabaseHelper.purgeConfig();
			if (!purged) {
				mMessage = context.getString(R.string.purge_impossible);
				return false;
			}

			// Télécharge et process le fichier "PARAMCONTROLE"
			String PARAMCONTROLE_savePath = savePath + "/PARAMCONTROLE.csv";
			if (downloadFile(PARAMCONTROLE, PARAMCONTROLE_savePath))
				processPARAMCONTROLE(PARAMCONTROLE_savePath);

			// Télécharge le fichier "PARAMGENERAL"
			String PARAMGENERAL_savePath = savePath + "/PARAMGENERAL.csv";
			if (downloadFile(PARAMGENERAL, PARAMGENERAL_savePath))
				processPARAMGENERAL(PARAMGENERAL_savePath);

			// Télécharge et process le fichier "MAJDISTRIB"
			String MAJDISTRIB_savePath = savePath + "/MAJDISTRIB.csv";
			if (downloadFile(MAJDISTRIB, MAJDISTRIB_savePath))
				processMAJDISTRIB(MAJDISTRIB_savePath);

			// Télécharge le fichier "CORRESP_NUMSERIE_TYPEUSER_SECTEUR"
			String CORRESP_NUMSERIE_TYPEUSER_SECTEUR_savePath = savePath + "/CORRESP_NUMSERIE_TYPEUSER_SECTEUR.csv";
			if (downloadFile(CORRESP_NUMSERIE_TYPEUSER_SECTEUR, CORRESP_NUMSERIE_TYPEUSER_SECTEUR_savePath))
				processCORRESP_NUMSERIE_TYPEUSER_SECTEUR(CORRESP_NUMSERIE_TYPEUSER_SECTEUR_savePath);


			// Change de working directory
			mFtpClient.changeWorkingDirectory(mFtpClient.printWorkingDirectory() + "/LOGO/ANDROID");

			for (FTPFile file : mFtpClient.listFiles()) {
				if (file.getName().matches("^([a-z]{3})\\.*\\.(?:png)$"))
					downloadFile(file.getName(), Tools.getLogosDirectoryPath() + File.separator + file.getName());
			}

			// On récupère les nouvelles valeurs de configuration dans la configuration actuelle
			Config.setConfig();

			// Termine la transaction SqlLite
			MainActivity.sDatabaseHelper.endTransaction();

			// Déconnexion du FTP
			mFtpClient.disconnect();

			return true;
		} catch (Exception e) {
			writeTraceException(e);
			Log.w(LOG, e.getMessage());
			Log.w(LOG, "Downloading failed!");

			MainActivity.sDatabaseHelper.cancelTransaction();

			return false;
		}
	}

	@Override
	protected void onProgressUpdate(String... values) {
		if (mDialog instanceof AlertDialog)
			((AlertDialog) mDialog).setMessage(values[0]);
//		else if (mDialog instanceof CustomDialog && values[0].equals("Start"))
//			((CustomDialog) mDialog).setMessage(values[0]);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// Ferme le dialog et affiche le message d'information

		if (mDialog instanceof AlertDialog) {
			if (mMessage == null) {
				if (result)
					Toast.makeText(mContext.get(), R.string.msg_download_success, Toast.LENGTH_LONG).show();
				else if (Config.sIsDataInit)
					Toast.makeText(mContext.get(), R.string.msg_download_error, Toast.LENGTH_LONG).show();
			} else if (Config.sIsDataInit || result)
				Toast.makeText(mContext.get(), mMessage, Toast.LENGTH_LONG).show();

			if (!result && !Config.sIsDataInit && mActivity != null && mContext.get() != null) {
				// L'initialisation a échoué, on réessaye
				Button button = ((AlertDialog) mDialog).getButton(android.app.AlertDialog.BUTTON_POSITIVE);
				button.setCompoundDrawables(mContext.get().getResources().getDrawable(R.drawable.ic_action_retry), null, null, null);
				button.setOnClickListener(v -> {
					final AlertDialog dialog = new AlertDialog.Builder(mActivity.get())
							.setCancelable(false)
							.setTitle(R.string.msg_init_data)
							.setPositiveButton(mActivity.get().getString(R.string.retry), null)
							.setMessage("")
							.create();

					dialog.setOnDismissListener(dialog1 -> {
						if (Config.sIsDataInit)
							new RequestConnectionDialog(mContext.get(), mActivity.get()).show();
					});

					dialog.setOnShowListener(_dialog -> {
						// Télécharge les données depuis le FTP
						new FTPDownloader(mActivity.get(), mContext.get(), dialog).execute();
					});

					dialog.show();

					mDialog.dismiss();
				});
				button.setVisibility(View.VISIBLE);

				mDialog.setTitle(mContext.get().getString(R.string.erreur));
				((AlertDialog) mDialog).setIcon(R.drawable.ic_action_error);
				if (mMessage != null)
					((AlertDialog) mDialog).setMessage(mMessage);
				else
					((AlertDialog) mDialog).setMessage(mContext.get().getString(R.string.msg_download_error));

			} else {
				if (result) {
					Config.sIsDataInit = true;
					MainActivity.sDatabaseHelper.setDataInit();

					if (mActivity != null && mActivity.get() instanceof MainActivity) {
						// Le téléchargement est terminé, on peut activer les boutons
						((MainActivity) mActivity.get()).setListeners();
					}
				}

				mDialog.dismiss();
			}
		} else if (mDialog instanceof CustomDialog) {
			CustomDialog dialog = (CustomDialog) mDialog;
			if (result)
				dialog.onSuccess();
			else
				dialog.onError(mMessage);
		}
	}

	/**
	 * Télécharge un fichier depuis le FTP
	 */
	private boolean downloadFile(String ftpPath, String localPath) throws Exception {
		FileOutputStream desFileStream = new FileOutputStream(localPath);
		boolean status = mFtpClient.retrieveFile(ftpPath, desFileStream);
		desFileStream.close();

		return status;
	}
}
