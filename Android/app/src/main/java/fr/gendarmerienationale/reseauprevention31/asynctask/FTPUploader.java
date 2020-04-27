package fr.gendarmerienationale.reseauprevention31.asynctask;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.LOG;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.getPath;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.writeTraceException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.activity.MainActivity;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Cette classe permet d'envoyer des fichiers sur le serveur
 * TODO: Utile ?
 */
public class FTPUploader extends AsyncTask<Void, String, Boolean> {

    private final WeakReference<Context> mContext;
    private final SuiviTransfertDialog   mDialog;
    private       FTPClient              mFtpClient;
    private       String                 mMessage;
    private       List<File>             files;


    private List<String> mIdsInv;
    private List<String> mIdsRam;
    private List<String> mIdsTrf;
    private List<String> mIdsQue;

    public FTPUploader(Context _context, SuiviTransfertDialog _dialog, List<File> _files) {
        this.mContext = new WeakReference<>(_context);
        this.mDialog = _dialog;
        this.files = _files;
    }


    @Override
    protected void onPreExecute() {
        // Change le message
        mDialog.setMessage(mContext.get().getString(R.string.connecting));
    }

    @Override
    protected Boolean doInBackground(Void... dialog) {
        // Le context
        Context context = mContext.get();

        if (MainActivity.sDatabaseHelper == null) {
            mMessage = context.getString(R.string.unavailableDB);
            return false;
        }

        Config.Connexion connexion = checkNetworkStatus(context);
        if (connexion == null || !Arrays.asList(Config.sAuthorizedNetworkConnections).contains(connexion)) {
            mMessage = context.getString(R.string.impossibleWirelessTrf);
            return false;
        }

        publishProgress(context.getString(R.string.loadingTrf));

        mIdsInv = new ArrayList<>();
        mIdsRam = new ArrayList<>();
        mIdsTrf = new ArrayList<>();
        mIdsQue = new ArrayList<>();

        // Démarre la transaction SqlLite
        MainActivity.sDatabaseHelper.beginTransaction();

        // Initialise le client FTP
        mFtpClient = new FTPClient();

        mFtpClient.setControlEncoding("UTF-8");
        try {
            // Connexion FTP
            mFtpClient.connect(Config.sFTPAddress);
            mFtpClient.login(Config.sFTPLogin, Config.sFTPPassword);

            // Passage en mode passif
            mFtpClient.enterLocalPassiveMode();

            // Type de fichier binaire
            mFtpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Change de working directory
            mFtpClient
                    .changeWorkingDirectory(mFtpClient.printWorkingDirectory() + File.separator + "FICHIER_TERMINAUX");

            for (File f : files) {

                String name = f.getName();
                String[] parts = name.split("_");

                if (parts.length != 3)
                    continue;

                String idDistributeur = parts[0].trim();
                String num = parts[1];

                if (name.endsWith(".INV"))
                    mIdsInv.add(num + idDistributeur);
                else if (name.endsWith(".RAM") || name.endsWith(".RAC"))
                    mIdsRam.add(num + idDistributeur);
                else if (name.endsWith(".TRF"))
                    mIdsTrf.add(num + idDistributeur);
                else if (name.endsWith(".QUE"))
                    mIdsQue.add("QUEST_" + idDistributeur + "_" + num);

                FileInputStream fis = new FileInputStream(getPath(f));
                boolean done = mFtpClient.storeFile(f.getName(), fis);
                if (!done)
                    throw new IllegalStateException("Enregistrement vers le FTP impossible");

                deleteFile(f);
            }

            // Termine la transaction SqlLite
            MainActivity.sDatabaseHelper.endTransaction();

            // Déconnexion du FTP
            mFtpClient.disconnect();

            return true;
        } catch (Exception e) {
            writeTraceException(e);
            Log.w(LOG, e.getMessage());
            Log.w(LOG, "Uploading failed!");

            MainActivity.sDatabaseHelper.cancelTransaction();

            return false;
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        mDialog.setMessage(values[0]);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        // Ferme le dialog et affiche le message d'information

        // On supprime les fichiers du dossier SEND, s'il y a des fichiers dans ce répertoire, il y a eu une erreur
        mDialog.deleteFilesInSendDirectory();

        if (result) {
            mDialog.dismiss();

            if (result) {
                if (new File(getSendDirectoryPath()).listFiles().length == 0) {
                    // le transfert a réussi, car tous les fichiers ont été supprimés

                    // suppression des fichiers qui ont été transférés
                    if (mDialog.deleteFiles()) {
                        // On met à jour la base de données quand on sait que tout le reste est correct
                        boolean done = MainActivity.sDatabaseHelper.updateItems(mIdsInv, mIdsRam, mIdsTrf, mIdsQue);
                        if (done) {
                            Toast.makeText(mContext.get(), R.string.trf_termine, Toast.LENGTH_LONG).show();
                            mDialog.returnToMainScreen();
                        } else
                            Toast.makeText(mContext.get(), R.string.mise_a_jour_interrompue, Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(mContext.get(), R.string.suppr_trf_interrompue, Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(mContext.get(), R.string.trf_interrompu, Toast.LENGTH_LONG).show();

                for (File f : new File(getSendDirectoryPath()).listFiles())
                    Log.w(LOG, f.getName());
            }
        } else {
            mDialog.setWindowError();
            mDialog.setMessage(mMessage);
        }
    }
}
