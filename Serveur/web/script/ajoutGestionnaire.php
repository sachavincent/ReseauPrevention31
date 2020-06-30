<?php session_start(); ?>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <link rel="stylesheet" href="../style/style.css" />
        <link rel="icon" type="image/png" href="../images/icon.png" />
    </head>

    <body>
        <!-- header -->
        <header>
            <title>Page d'administration</title>
        </header>

        <input class="btn-admin" type="button" value="retour" title="Cliquez ici pour revenir à l'écran des chambres"
        onclick="window.location.href='../connexion/choix-chambre.php'" >

        <!-- conteneur principal -->
        <section id="page-identification">
        
        <?php include 'connexionBDD.php';
        // panel verifier connexion administrateur
        if (!(isset($_POST['mdpAdmin']) OR isset($_SESSION['mdpAdmin']))) { ?>   

            <form action="ajoutGestionnaire.php" method="post">
            <div id="pan-connexion-admin">
                <h1>Connexion Administrateur</h1>
                <input type="password" name="mdpAdmin" placeholder="Entrer le mot de passe" required>
                <?php if (isset($_GET['p']) AND $_GET['p'] == 'mdpInco') {
                    echo '<p id="error">Le mot de passe est incorrect.<p>';
                } ?>
                <input class="button-id" type="submit" value="Valider">
            </div>
            </form>
        <?php 
        }
        else {
            $_SESSION['mdpAdmin'] = isset($_POST['mdpAdmin']) ? $_POST['mdpAdmin'] : $_SESSION['mdpAdmin'];
            $requeteMdpAdmin = $bdd->prepare('SELECT mdp FROM Administrateur WHERE `mdp` = PASSWORD(?)');
            $requeteMdpAdmin->execute(array($_SESSION['mdpAdmin']));
            $mdpHach = ($requeteMdpAdmin->fetch())['mdp'];

            if (empty($mdpHach)){
                session_destroy();
                header('Location: ajoutGestionnaire.php?p=mdpInco');
                exit();
            } else {
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
                } ?>

            <form action="ajoutGestionnaire.php" method="post">
            <!-- conteneur ajout gestionnaire -->
            <div id="pan-ajout-gestionnaire">
                <h1>CRÉATION<br> D'UN GESTIONNAIRE</h1>

                <select name="select-chambre" required>
                    <option value="">Choisir un gestionnaire</option>
                    <option value="CMA">CMA</option>
                    <option value="CCI">CCI</option>
                    <option value="CA">CA</option>
                    <option value="G">Gendarmerie</option>
                    <option value="P">Police</option>
                </select>
                
                <!-- champs de saisies -->
                <input class="input-new-gestionnaire" type="text" name="id" placeholder="Entrer l'identifiant" required>
                <input class="input-new-gestionnaire" type="password" name="mdp" placeholder="Entrer le mot de passe" required>
                <input class="input-new-gestionnaire" type="password" name="mdp-confirm" placeholder="Confirmation du mot de passe" required>
                <input class="input-new-gestionnaire" type="text" name="nom" placeholder="Entrer le nom" required>
                <input class="input-new-gestionnaire" type="text" name="prenom" placeholder="Entrer le prénom" required>
                <input class="input-new-gestionnaire" type="email" name="mail" placeholder="Entrer l'adresse email" required>
                <!-- affichage erreur si erreur -->
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
                <!-- btn valider -->
                <input class="button-id" type="submit" value="Valider">
            </div>
            </form>
<?php   }
    } 
?>
        </section>
    </body>
</html>
