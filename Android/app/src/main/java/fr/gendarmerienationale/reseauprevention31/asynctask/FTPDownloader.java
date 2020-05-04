package fr.gendarmerienationale.reseauprevention31.asynctask;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.LOG;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.writeTraceException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import fr.gendarmerienationale.reseauprevention31.activity.MainActivity;
import fr.gendarmerienationale.reseauprevention31.dialog.UpdateDatabaseDialog;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.List;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;


public class FTPDownloader extends AsyncTask<Void, String, Boolean> {

    private final WeakReference<Context> mContext;
    private final UpdateDatabaseDialog   mDialog;
    private       FTPClient              mFtpClient;
    private       String                 mStrRep;

    private List<File> mFilesToUpdate;

    public FTPDownloader(Context _context, UpdateDatabaseDialog _dialog, List<File> _filesToUpdate) {
        this.mContext = new WeakReference<>(_context);
        this.mDialog = _dialog;

        this.mFilesToUpdate = _filesToUpdate;
    }

    @Override
    protected void onPreExecute() {
        // Change le message
        mDialog.onInit();
    }

    @Override
    protected Boolean doInBackground(Void... dialog) {
        try {
            // Le context
            Context context = mContext.get();

            // Démarre la transaction SqlLite
            MainActivity.sDatabaseHelper.beginTransaction();

            // Initialise le client FTP
            mFtpClient = new FTPClient();

            // Augmente la taille du buffer pour un download plus rapide
            mFtpClient.setBufferSize(1024000);

            mFtpClient.setControlEncoding("UTF-8");

            // Connexion FTP
//            mFtpClient.connect(Config.sFTPAddress);
//            mFtpClient.login(Config.sFTPLogin, Config.sFTPPassword);

            // Passage en mode passif
            mFtpClient.enterLocalPassiveMode();

            // Type de fichier binaire
            mFtpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Change de working directory
            mFtpClient.changeWorkingDirectory(mFtpClient.printWorkingDirectory() + "/PARAMETRES");

            // Chemin de sauvegarde des fichiers
            String savePath = context.getFilesDir().getAbsolutePath();

            publishProgress(mDialog.onDownloadStart());

            for (File f : mFilesToUpdate) {
                downloadFile(f.getName(), savePath + File.separator + f.getName());
            }

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
        FileOutputStream desFileStream = new FileOutputStream(localPath);
        boolean status = mFtpClient.retrieveFile(ftpPath, desFileStream);
        desFileStream.close();

        return status;
    }

}
