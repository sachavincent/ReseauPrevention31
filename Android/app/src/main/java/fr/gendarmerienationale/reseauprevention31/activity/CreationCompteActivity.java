package fr.gendarmerienationale.reseauprevention31.activity;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.LOG;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.writeTraceException;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.struct.CodeActivite;
import fr.gendarmerienationale.reseauprevention31.struct.Commune;
import fr.gendarmerienationale.reseauprevention31.struct.Secteur;
import fr.gendarmerienationale.reseauprevention31.util.DialogsHelper;
import fr.gendarmerienationale.reseauprevention31.util.Tools;
import java.util.ArrayList;
import java.util.List;

public class CreationCompteActivity extends AppCompatActivity {

    private CodeActivite mSelectedCode;
    private Commune      mSelectedCommune;
    private Secteur      mSelectedSecteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_creation_compte);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setSubtitle(R.string.creer_un_compte);

        final List<CodeActivite> listeAPE = MainActivity.sDatabaseHelper.getCodesAPE();
        final List<String> listeLocalisations = new ArrayList<>();
        List<Commune> listeCommunes = MainActivity.sDatabaseHelper.getCommunes();
        for (Secteur secteur : Secteur.values())
            listeLocalisations.add(secteur.toString());
        for (Commune commune : listeCommunes)
            listeLocalisations.add(commune.toString());

        for (String c : listeLocalisations)
            Log.d(LOG, c);

        // Adapters pour les AutoCompleteTextView
        ArrayAdapter<CodeActivite> adapterCodeAPE = new ArrayAdapter<>(this, R.layout.dropdown_item_distrib, listeAPE);
        ArrayAdapter<String> adapterCommune = new ArrayAdapter<>(this, R.layout.dropdown_item_distrib,
                listeLocalisations);

        // les autoCompleteTextView
        AutoCompleteTextView codeAPEField = findViewById(R.id.codeAPEField);
        codeAPEField.setAdapter(adapterCodeAPE);
        codeAPEField.setThreshold(1);

        AutoCompleteTextView localisationField = findViewById(R.id.localisationField);
        localisationField.setAdapter(adapterCommune);
        localisationField.setThreshold(1);


        // Quand un item est sélectionné
        codeAPEField.setOnItemClickListener((adapterView, view, i, l) -> {
            // Masque le clavier
            Tools.hideKeyboardFromView(codeAPEField, CreationCompteActivity.this);

            mSelectedCode = listeAPE.get(i);
            Log.d(LOG, "Code sélectionné : " + mSelectedCode);
        });


        // Quand un item est sélectionné
        localisationField.setOnItemClickListener((adapterView, view, i, l) -> {
            // Masque le clavier
            Tools.hideKeyboardFromView(localisationField, CreationCompteActivity.this);

            if (listeCommunes.size() > i) { // Commune sélectionnée
                mSelectedCommune = listeCommunes.get(i);
                mSelectedSecteur = mSelectedCommune.getSecteur();
            } else { // Secteur sélectionné
                mSelectedSecteur = Secteur.getSecteur(i - listeCommunes.size());
            }

            Log.d(LOG, "Commune sélectionnée : " + mSelectedCommune);
            Log.d(LOG, "Secteur sélectionné : " + mSelectedSecteur);
        });

        // Quand on appuie sur la touche valider/entrer du clavier
        codeAPEField.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String selectedCodeAPE = v.getText().toString();

                return checkCodeAPE(listeAPE, selectedCodeAPE);
            }

            // Empêche la fermeture du clavier
            return true;
        });
        // Quand on appuie sur la touche valider/entrer du clavier
        localisationField.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String selectedLocalisation = v.getText().toString();

                return checkLocalisation(listeCommunes, selectedLocalisation);
            }

            // Empêche la fermeture du clavier
            return true;
        });
    }

    private boolean checkCodeAPE(List<CodeActivite> listeAPE, String selectedCodeAPE) {
        mSelectedCode = null;

        int selectedCode;

        if (selectedCodeAPE.isEmpty()) {
            // Empêche la fermeture du clavier
            return true;
        }

        try {
            selectedCode = Integer.parseInt(selectedCodeAPE);
        } catch (NumberFormatException e) {
            writeTraceException(e);
            Log.w(LOG, e.getMessage());

            // Le code n'a pas le bon format
            DialogsHelper.displayError(this, getString(R.string.code_ape_incorrect));

            // Empêche la fermeture du clavier
            return true;
        }

        // Vérifie si la valeur existe
        for (CodeActivite codeActivite : listeAPE)
            if (codeActivite.getCode() == selectedCode)
                mSelectedCode = codeActivite;

        if (mSelectedCode == null) { // Le code n'existe pas
            DialogsHelper.displayError(this, getString(R.string.code_ape_inexistant));
            // Empêche la fermeture du clavier
            return true;
        }

        Log.d(LOG, "Code sélectionné : " + mSelectedCode);

        // Code correct, on ferme le clavier
        return false;
    }

    private boolean checkLocalisation(List<Commune> listeCommunes, String selectedLocalisation) {
        mSelectedCommune = null;
        mSelectedSecteur = null;

        if (selectedLocalisation.isEmpty()) {
            // Empêche la fermeture du clavier
            return true;
        }

        String regex1 = "^" + selectedLocalisation + " \\(31[0-9]{3}\\) - \\bZone\\b [1-7]$";
        String regex2 = "^[a-zA-Z]{2,} \\(" + selectedLocalisation + "\\) - \\bZone\\b [1-7]$";

        // Vérifie si la valeur existe
        if (selectedLocalisation.matches("^\\bZone\\b [1-7]$")) { // Zone sélectionnée
            mSelectedSecteur = Secteur.getSecteur(Integer.parseInt(selectedLocalisation.split(" ")[1]));
        } else {
            String regex = "^[a-zA-Z]{2,} \\(31[0-9]{3}\\) - \\bZone\\b [1-7]$";
            if (regex1.matches(regex) || regex2.matches(regex) || selectedLocalisation.matches(regex)) {
                // Commune sélectionnée ou nom impossible

                for (Commune commune : listeCommunes) {
                    if (commune.toString().equals(selectedLocalisation) ||
                            commune.toString().matches(regex1) ||
                            commune.toString().matches(regex2)) { // Commune trouvée
                        if (mSelectedCommune != null) { // Plusieurs communes trouvées avec les infos données
                            DialogsHelper.displayError(this, getString(R.string.localisation_imprecise));

                            // Empêche la fermeture du clavier
                            return true;
                        }

                        mSelectedCommune = commune;
                        mSelectedSecteur = commune.getSecteur();
                    }
                }
            }
        }

        if (mSelectedCommune == null) { // Le code n'existe pas
            DialogsHelper.displayError(this, getString(R.string.localisation_inexistante));
            // Empêche la fermeture du clavier
            return true;
        }

        Log.d(LOG, "Commune sélectionnée : " + mSelectedCommune);
        Log.d(LOG, "Secteur sélectionné : " + mSelectedSecteur);

        // Code correct, on ferme le clavier
        return false;
    }

    public CodeActivite getSelectedCode() {
        return this.mSelectedCode;
    }

    public Commune getSelectedCommune() {
        return this.mSelectedCommune;
    }

    public Secteur getSelectedSecteur() {
        return this.mSelectedSecteur;
    }

    @Override
    public boolean navigateUpTo(Intent upIntent) {
        return super.navigateUpTo(upIntent);
    }
}

