package fr.gendarmerienationale.reseauprevention31.asynctask;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.IP;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.LOG;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.writeTraceException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.activity.MainActivity;
import fr.gendarmerienationale.reseauprevention31.dialog.UpdateDatabaseDialog;
import fr.gendarmerienationale.reseauprevention31.util.Tools;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;


public class FTPDownloader extends AsyncTask<Void, String, Boolean> {

    private final WeakReference<Context> mContext;
    private final UpdateDatabaseDialog   mDialog;
    private       FTPClient              mFtpClient;
    private       String                 mStrRep;

    public final static String FTP_PASSWORD = "root";

    public FTPDownloader(Context _context, UpdateDatabaseDialog _dialog) {
        this.mContext = new WeakReference<>(_context);
        this.mDialog = _dialog;
    }

    @Override
    protected void onPreExecute() {
        // Change le message
        mDialog.onInit();
    }

    @Override
    protected Boolean doInBackground(Void... dialog) {
        try {
            // Initialise le client FTP
            mFtpClient = new FTPClient();

            // Augmente la taille du buffer pour un download plus rapide
            mFtpClient.setBufferSize(1024000);

            mFtpClient.setControlEncoding("UTF-8");

            // Connexion FTP
            mFtpClient.connect(IP);
            mFtpClient.login(Tools.getDeviceID() + MainActivity.sDatabaseHelper.getUserKey(), FTP_PASSWORD);

            // Passage en mode passif
            mFtpClient.enterLocalPassiveMode();

            // Type de fichier binaire
            mFtpClient.setFileType(FTP.BINARY_FILE_TYPE);

            publishProgress(mDialog.onDownloadStart());

            for (FTPFile f : mFtpClient.listFiles()) {
                downloadFile(f.getName(), Tools.getDatabaseFolder() + File.separator + f.getName());
            }

            // Déconnexion du FTP
            mFtpClient.disconnect();

            return true;
        } catch (Exception e) {
            writeTraceException(e);
            Log.w(LOG, e.getMessage());
            Log.w(LOG, "Downloading failed!");
            MainActivity.sDatabaseHelper.cancelTransaction();

            mStrRep = mContext.get().getResources().getString(R.string.connection_error);

            return false;
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        mDialog.setText(values[0]);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        // Ferme le dialog et affiche le message d'information
        if (result)
            mDialog.onSuccess();
        else
            mDialog.onError(mStrRep);
    }

    /**
     * Télécharge un fichier depuis le FTP
     */
    private boolean downloadFile(String ftpPath, String localPath) throws Exception {
        Log.d(LOG, "Downloading file: " + ftpPath + " . . .");
        FileOutputStream desFileStream = new FileOutputStream(localPath);
        boolean status = mFtpClient.retrieveFile(ftpPath, desFileStream);
        desFileStream.close();

        return status;
    }

}
