<?php
session_start();
?>

<!DOCTYPE html>
<html>
<head>
    <title>Profil</title>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="../style/style.css" />
    <link rel="icon" type="image/png" href="../images/icon.png" />
    <script src="https://kit.fontawesome.com/161f845565.js" crossorigin="anonymous"></script>
</head>  
<body>
    <header>
        <title>Profil</title>
    </header>

    <span class="switch-mode">🌓</span>

    <!-- affichage menu gauche -->
    <?php include("page/navChambre.php"); ?>
    <!-- ============================================== -->  
    <!-- page du profil -->
    <section id="pan-profil">
        <div class="block-titre">
            MON PROFIL
        </div>
        
        <!-- ========== PROFIL ========== -->  

        <form action="../script/modifier_profil.php?return=none" method="post"  enctype="multipart/form-data">
            <!-- conteneur image de profil avec nom prenom -->
            <div id="pan-photo">
                <div class="img-profil">
                    <?php
                    if (file_exists('../images/' . $_SESSION['id'] . 'c.jpeg')){
                    echo  '<img src="../images/'. $_SESSION["id"]. 'c.jpeg">';
                    } elseif (file_exists('../images/' . $_SESSION['id'] . 'c.jpg')){
                    echo  '<img src="../images/'. $_SESSION["id"]. 'c.jpg">';
                    } elseif (file_exists('../images/' . $_SESSION['id'] . 'c.png')){
                    echo  '<img src="../images/'. $_SESSION["id"]. 'c.png">';
                    } else {
                    echo  '<img src="../images/profil/user.jpg">';
                    } ?>
                </div>
                <!-- affichage nom / prenom -->
                <h1 class="nom"> <?php echo $_SESSION['nom']." ".$_SESSION['prenom']; ?></h1>
            </div>
            <!-- conteneur avec les infos modifiables -->
            <div class="zone-infos">
            <!-- nom -->
            <p>Nom</p>
            <input class="input-infos" name="nom" value=<?= $_SESSION['nom']; ?> />
            <!-- prenom -->
            <p>Prénom</p>
            <input class="input-infos" name="prenom" value=<?= $_SESSION['prenom']; ?> />
            <!-- mail -->
            <p>Adresse Mail</p>
            <input class="input-infos" name="mail" type="email" value=<?= $_SESSION['mail']; ?> />
            <!-- modifier photo profil -->
            <p>Photo</p>
            <input type="file" name="photoProfil" id='photoProfil' accept='image/jpeg'>
            <!-- ligne d'erreur si la modification échoue -->
            <?php 
                /* recuperation de l'URL pour les modifications de profil */
                switch ($_GET['return']) {
                    case 'none' :
                        default;
                    break;
                    case 'success' :
                        echo '<p id="success">Modifications effectuées.</p>';
                    break;
                    case 'error' :
                        echo '<p id="error">Erreur lors de la modification du profil.</p>';
                    break;
                } ?>
            </div>
            <!-- mdp actuel -->
            <div class="zone-mdp">
            <p>Mot de passe actuel<i class="fas fa-exclamation-circle" title="Champ obligatoire"></i></p>
            <!-- <img class="required-input" src="../images/required.png" title="champ obligatoire" /> -->
            <input class="input-mdp" name="mdp" type="password" placeholder="Entrer le mot de passe actuel" required/>
            <!-- nouveau mdp -->
            <p>Nouveau mot de passe</p>
            <input class="input-mdp" name="nouveauMdp" type="password" placeholder="Saisissez ici un nouveau mot de passe"/>
            <!-- confirmation mdp -->
            <p>Confirmation</p>
            <input class="input-mdp" name="confirmationMdp" type="password" placeholder="Confirmez ici le nouveau mot de passe"/>

            <!-- bouton valider -->
            <input class="btn-profil" type="submit" value="Valider" />
            </div>

        </form>
    </section>
    <script src="../script/dark-mode.js"></script>
</body>
</html>