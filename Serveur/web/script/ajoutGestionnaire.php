<?php
    include 'connexionBDD.php';
    if (isset($_POST['select-chambre']) AND isset($_POST['mdp-confirm']) AND isset($_POST['id']) AND isset($_POST['mdp']) AND isset($_POST['nom']) AND isset($_POST['prenom']) AND isset($_POST['select-chambre']) AND isset($_POST['mail'])){
        // chambre ou force
        if ($_POST['mdp'] == $_POST['mdp-confirm']){

            $type = ($_POST['select-chambre'] == 'G' OR $_POST['select-chambre'] == 'P') ? 'FORCE' : 'CHAMBRE';
            switch($type){
                case 'FORCE':
                    $requeteListeId = $bdd->query('SELECT idForce FROM `ForceDeLOrdre`');
                    $requeteAjout = $bdd->prepare('INSERT INTO `ForceDeLOrdre`(`idForce`, `mdpForce`, `nomForce`, `prenomForce`, `force`, `mail`) VALUES (?,?,?,?,?,?)');
                break;
                case 'CHAMBRE':
                    $requeteListeId = $bdd->query('SELECT idGestionnaire FROM `Gestionnaire`');
                    $requeteAjout = $bdd->prepare('INSERT INTO `Gestionnaire`(`idGestionnaire`, `mdpGestionnaire`, `nomGestionnaire`, `prenomGestionnaire`, `chambre`, `mail`) VALUES (?,?,?,?,?,?)');
                break;
                default: exit();
            }
            $listeId = $requeteListeId->fetchAll();

            foreach($listeId as $id){
                if ($id[0] == $_POST[id]){
                    header('Location: ajoutGestionnaire.php?etat=idInco');
                    exit();
                }
            }
            $requeteAjout->execute(array($_POST['id'], password_hash($_POST['mdp'], PASSWORD_DEFAULT), $_POST['nom'], $_POST['prenom'], $_POST['select-chambre'] , $_POST['mail']));
            header('location: ajoutGestionnaire.php?etat=success');
            exit();
        } else {
            header('Location: ajoutGestionnaire.php?etat=mdpInco');
            exit();
        }
        header('Location: ajoutGestionnaire.php?etat=failed');
        exit();
    }
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="../style.css" />
    <title>Ajout d'un gestionnaire</title>
</head>
<body>
    <header>
      <title>Identification</title>
    </header>
    <form action="ajoutGestionnaire.php" method="post">
        <!-- conteneur principal -->
        <section id="panel-new-gestionnaire">
            <section id="creation-gestionnaire">
            <h3>CRÉATION<br> D'UN GESTIONNAIRE</h3><br>
                <select name="select-chambre" required>
                    <option value="">Choisir un gestionnaire</option>
                    <option value="CMA">CMA</option>
                    <option value="CCI">CCI</option>
                    <option value="CA">CA</option>
                    <option value="G">Gendarmerie</option>
                    <option value="P">Police</option>
                </select><br>
                
                <div class="identifiants"><b>Identifiant</b></div>
                <input class="input-new-gestionnaire" type="text" name="id" placeholder="Entrer l'identifiant" required><br>
                
                <div class="identifiants"><b>Mot de passe</b></div>
                <input class="input-new-gestionnaire" type="password" name="mdp" placeholder="Entrer le mot de passe" required><br>
                
                <div class="identifiants"><b>Confirmation mot de passe</b></div>
                <input class="input-new-gestionnaire" type="password" name="mdp-confirm" placeholder="Confirmation du mot de passe" required><br>
                
                <div class="identifiants"><b>Nom</b></div>
                <input class="input-new-gestionnaire" type="text" name="nom" placeholder="Entrer le nom" required><br>
                
                <div class="identifiants"><b>Prénom</b></div>
                <input class="input-new-gestionnaire" type="text" name="prenom" placeholder="Entrer le prénom" required><br>

                <div class="identifiants"><b>Adresse Mail</b></div>
                <input class="input-new-gestionnaire" type="email" name="mail" placeholder="Entrer l'adresse email" required><br>

                <input class="bouton-id" type="submit"><br>
                
                <?php
                    if (isset($_GET['etat'])){
                        switch($_GET['etat']){
                            case 'success':
                                echo '<p id="ajout-success">Utilisateur ajouté<p>';
                            break;
                            case 'failed':
                                echo '<p id="ajout-failed">Erreur de l\'ajout de l\'utilisateur<p>';
                            break;
                            case 'mdpInco':
                                echo '<p id="ajout-mdp-different">Les mots de passe ne correspondent pas !<p>';
                            break;                       
                            case 'idInco':
                                echo '<p id="ajout-id-existant">Cet identifiant existe déjà !<p>';
                            break;
                        }
                    }
                ?>
            </section>
        </section>
    </form>
</body>
</html>