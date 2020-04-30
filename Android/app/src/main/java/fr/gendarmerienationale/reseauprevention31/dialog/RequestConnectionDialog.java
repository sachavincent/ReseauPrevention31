package fr.gendarmerienationale.reseauprevention31.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import fr.gendarmerienationale.reseauprevention31.BuildConfig;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.asynctask.APICaller;
import fr.gendarmerienationale.reseauprevention31.util.Tools;

public class RequestConnectionDialog extends Dialog {

    private Activity mActivity;
    private Context  mContext;

    public RequestConnectionDialog(@NonNull Context _context, @NonNull Activity _activity) {
        super(_context);

        this.mContext = _context;
        this.mActivity = _activity;
    }

    @Override
    protected void onStart() {
        super.onStart();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getWindow().setLayout((int) (Tools.getWidth() * .9), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View dialogView = inflater.inflate(R.layout.dialog_request_connection, null);
        setContentView(dialogView);

        // Event quand on appuie sur "Valider"
        Button btnValider = findViewById(R.id.btnValiderConnection);
        btnValider.setOnClickListener(dialog -> valider());
    }

    /**
     * Permet de valider la réponse de l'utilisateur
     */
    private void valider() {
        TextInputEditText keyField = findViewById(R.id.keyFieldConnection);
        if (keyField.getText() == null || keyField.getText().toString().isEmpty())
            return;

        switchValiderState();

        //TODO: Call API with request
        new APICaller(keyField.getText().toString(), mContext, this).execute();
    }

    public void switchValiderState() {
        Button button = findViewById(R.id.btnValiderConnection);
        button.setEnabled(!button.isEnabled());
    }
}
