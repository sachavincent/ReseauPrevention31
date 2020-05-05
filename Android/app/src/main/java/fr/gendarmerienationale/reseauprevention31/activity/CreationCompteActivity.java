package fr.gendarmerienationale.reseauprevention31.activity;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.LOG;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.writeTraceException;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.asynctask.RegisterAPICaller;
import fr.gendarmerienationale.reseauprevention31.struct.Chambre;
import fr.gendarmerienationale.reseauprevention31.struct.CodeActivite;
import fr.gendarmerienationale.reseauprevention31.struct.Commune;
import fr.gendarmerienationale.reseauprevention31.struct.Secteur;
import fr.gendarmerienationale.reseauprevention31.struct.Utilisateur;
import fr.gendarmerienationale.reseauprevention31.util.DialogsHelper;
import fr.gendarmerienationale.reseauprevention31.util.Tools;
import java.util.ArrayList;
import java.util.List;

public class CreationCompteActivity extends AppCompatActivity {

    private CodeActivite mSelectedCode;
    private Commune      mSelectedCommune;
    private Secteur      mSelectedSecteur;

    private Chambre mSelectedChambre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_creation_compte);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setSubtitle(R.string.creer_un_compte);

        initListeners();
    }

    private void initListeners() {
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
                mSelectedCommune = null;
                mSelectedSecteur = Secteur.getSecteur(i + 1);
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


        final AppCompatImageView chambreCCI = findViewById(R.id.chambreCCI_CreationCompte);
        final AppCompatImageView chambreCA = findViewById(R.id.chambreCA_CreationCompte);
        final AppCompatImageView chambreCMA = findViewById(R.id.chambreCMA_CreationCompte);


        chambreCCI.setOnClickListener(v -> {
            if (mSelectedChambre != null)
                if (mSelectedChambre == Chambre.CCI) {
                    mSelectedChambre = null;
                    chambreCCI.setBackgroundDrawable(null);
                } else {
                    chambreCA.setBackgroundDrawable(null);
                    chambreCMA.setBackgroundDrawable(null);
                    chambreCCI.setBackgroundResource(R.drawable.border_image);
                    mSelectedChambre = Chambre.CCI;
                }
        });

        chambreCA.setOnClickListener(v -> {
            if (mSelectedChambre == Chambre.CA) {
                mSelectedChambre = null;
                chambreCA.setBackgroundDrawable(null);
            } else {
                chambreCA.setBackgroundResource(R.drawable.border_image);
                chambreCMA.setBackgroundDrawable(null);
                chambreCCI.setBackgroundDrawable(null);
                mSelectedChambre = Chambre.CA;
            }
        });

        chambreCMA.setOnClickListener(v -> {
            if (mSelectedChambre == Chambre.CMA) {
                mSelectedChambre = null;
                chambreCMA.setBackgroundDrawable(null);
            } else {
                chambreCA.setBackgroundDrawable(null);
                chambreCMA.setBackgroundResource(R.drawable.border_image);
                chambreCCI.setBackgroundDrawable(null);
                mSelectedChambre = Chambre.CMA;
            }
        });

        final EditText numSiretField = findViewById(R.id.numSiretField);
        final EditText prenomField = findViewById(R.id.prenomField);
        final EditText nomField = findViewById(R.id.nomField);
        final EditText nomSocieteField = findViewById(R.id.nomSocieteField);
        final EditText numTelephoneField = findViewById(R.id.numTelField);
        final EditText mailField = findViewById(R.id.mailField);

        // Le bouton valider
        final Button boutonValider = findViewById(R.id.btnValiderCreationCompte);
        boutonValider.setOnClickListener(v -> {
            String numSiret = null;
            String prenom = null;
            String nom = null;
            String nomSociete = null;
            String numTelephone = null;
            String mail = null;

            boolean allFieldsFilled = true;
            if (isEditTextFilled(numSiretField) && Tools.isNumSiretCorrect(numSiretField.getText().toString()))
                numSiret = numSiretField.getText().toString();
            else {
                allFieldsFilled = false;
                numSiretField.setError(getResources().getString(R.string.num_siret_incorrect));
            }

            if (isEditTextFilled(prenomField))
                prenom = prenomField.getText().toString();
            else {
                allFieldsFilled = false;
                prenomField.setError(getResources().getString(R.string.renseigner_prenom));
            }

            if (isEditTextFilled(nomField))
                nom = nomField.getText().toString();
            else {
                allFieldsFilled = false;
                nomField.setError(getResources().getString(R.string.renseigner_nom));
            }

            if (isEditTextFilled(nomSocieteField))
                nomSociete = nomSocieteField.getText().toString();
            else {
                allFieldsFilled = false;
                nomSocieteField.setError(getResources().getString(R.string.renseigner_nom_societe));
            }

            if (mSelectedSecteur == null) {
                allFieldsFilled = false;
                localisationField.setError(getResources().getString(R.string.renseigner_localisation));
            }

            if (mSelectedCode == null) {
                allFieldsFilled = false;
                codeAPEField.setError(getResources().getString(R.string.renseigner_code_ape));
            }

            if (isEditTextFilled(numTelephoneField)) {
                numTelephone = numTelephoneField.getText().toString();
                if (numTelephone.length() != 10) {
                    // Format du numéro incorrect
                    numTelephone = null;
                    allFieldsFilled = false;
                    numTelephoneField.setError(getResources().getString(R.string.num_telephone_incorrect));
                }
            } else {
                allFieldsFilled = false;
                numTelephoneField.setError(getResources().getString(R.string.renseigner_num_telephone));
            }

            if (isEditTextFilled(mailField)) {
                mail = mailField.getText().toString();
                if (!mail.toUpperCase().matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$")) {
                    // Format du mail incorrect
                    mail = null;
                    allFieldsFilled = false;
                    mailField.setError(getResources().getString(R.string.mail_incorrect));
                }
            } else {
                allFieldsFilled = false;
                mailField.setError(getResources().getString(R.string.renseigner_mail));
            }

            if (mSelectedChambre == null) { // Aucune chambre sélectionnée
                // Bordure en rouge
                chambreCA.setBackgroundResource(R.drawable.red_border_image);
                chambreCCI.setBackgroundResource(R.drawable.red_border_image);
                chambreCMA.setBackgroundResource(R.drawable.red_border_image);
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    // Suppression des bordures 0.8s plus tard
                    // Sauf si sélection
                    if (mSelectedChambre != Chambre.CMA)
                        chambreCMA.setBackgroundDrawable(null);
                    if (mSelectedChambre != Chambre.CA)
                        chambreCA.setBackgroundDrawable(null);
                    if (mSelectedChambre != Chambre.CCI)
                        chambreCCI.setBackgroundDrawable(null);
                }, 800);
            }

            if (allFieldsFilled) { // Tout est rempli
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setSecteur(mSelectedSecteur);
                utilisateur.setNumeroTelephone(numTelephone);
                utilisateur.setMail(mail);
                utilisateur.setNumeroSiret(numSiret);
                utilisateur.setNom(nom);
                utilisateur.setPrenom(prenom);
                utilisateur.setNomSociete(nomSociete);
                utilisateur.setCodeActivite(mSelectedCode);
                utilisateur.setCommune(mSelectedCommune);
                utilisateur.setChambre(mSelectedChambre);

                new RegisterAPICaller(CreationCompteActivity.this, utilisateur).execute();
            }
        });
    }

    private boolean isEditTextFilled(EditText _editText) {
        return _editText.getText() != null && !_editText.getText().toString().isEmpty();
    }

    private boolean checkCodeAPE(List<CodeActivite> _listeAPE, String _selectedCodeAPE) {
        mSelectedCode = null;

        int selectedCode;

        if (_selectedCodeAPE.isEmpty()) {
            // Empêche la fermeture du clavier
            return true;
        }

        try {
            selectedCode = Integer.parseInt(_selectedCodeAPE);
        } catch (NumberFormatException e) {
            writeTraceException(e);
            Log.w(LOG, e.getMessage());

            // Le code n'a pas le bon format
            DialogsHelper.displayError(this, getString(R.string.code_ape_incorrect));

            // Empêche la fermeture du clavier
            return true;
        }

        // Vérifie si la valeur existe
        for (CodeActivite codeActivite : _listeAPE)
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

    private boolean checkLocalisation(List<Commune> _listeCommunes, String _selectedLocalisation) {
        mSelectedCommune = null;
        mSelectedSecteur = null;

        if (_selectedLocalisation.isEmpty()) {
            // Empêche la fermeture du clavier
            return true;
        }

        String regex1 = "^" + _selectedLocalisation + " \\(31[0-9]{3}\\) - \\bZone\\b [1-7]$";
        String regex2 = "^[a-zA-Z]{2,} \\(" + _selectedLocalisation + "\\) - \\bZone\\b [1-7]$";

        // Vérifie si la valeur existe
        if (_selectedLocalisation.matches("^\\bZone\\b [1-7]$")) { // Zone sélectionnée
            mSelectedSecteur = Secteur.getSecteur(Integer.parseInt(_selectedLocalisation.split(" ")[1]));
        } else {
            String regex = "^[a-zA-Z]{2,} \\(31[0-9]{3}\\) - \\bZone\\b [1-7]$";
            if (regex1.matches(regex) || regex2.matches(regex) || _selectedLocalisation.matches(regex)) {
                // Commune sélectionnée ou nom impossible

                for (Commune commune : _listeCommunes) {
                    if (commune.toString().equals(_selectedLocalisation) ||
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

    @Override
    public boolean navigateUpTo(Intent _upIntent) {
        return super.navigateUpTo(_upIntent);
    }
}

