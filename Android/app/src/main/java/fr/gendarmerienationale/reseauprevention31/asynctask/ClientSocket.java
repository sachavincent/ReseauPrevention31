package fr.gendarmerienationale.reseauprevention31.asynctask;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.LOG;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.writeTraceException;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Cette classe permet d'écouter le serveur sur un thread en permanence
 * Les messages envoyés par le serveur sans requête du client sont reçus ici
 */
public class ClientSocket extends AsyncTask<Void, Void, Void> {

    public final static String SERVER_IP = "";
    public final static int    PORT      = 1111;

    private Socket mSocket;

    @Override
    protected void onPreExecute() {
        try {
            this.mSocket = new Socket(SERVER_IP, PORT);
        } catch (IOException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(mSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                decodeLine(line);
            }

        } catch (IOException e) {
            Log.w(LOG, e.getMessage());
            writeTraceException(e);
        } finally {
            try {
                this.mSocket.close();

                if (bufferedReader != null)
                    bufferedReader.close();

                if (inputStreamReader != null)
                    inputStreamReader.close();
            } catch (IOException e2) {
                Log.w(LOG, e2.getMessage());
                writeTraceException(e2);
            }
        }

        return null;
    }

    private void decodeLine(String line) {
        switch (line) {

        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {

    }

    @Override
    protected void onPostExecute(Void aVoid) {

    }
}
