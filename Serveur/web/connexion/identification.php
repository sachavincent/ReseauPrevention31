<?php
session_start();
/* récupération de l'URL pour obtenir le choix de l'user */
switch ($_GET['chambre']) {
    case 'CCI':
        $_SESSION['chambre'] = 'CCI';
        $_SESSION['logoChambre'] = '../images/cci.png';
    break;
    case 'CA':
        $_SESSION['chambre'] = 'CA';
        $_SESSION['logoChambre'] = '../images/ca.png';
    break;
    case 'CMA':
        $_SESSION['chambre'] = 'CMA';
        $_SESSION['logoChambre'] = '../images/cma.png';
    break;
    case 'G':
        $_SESSION['chambre'] = 'G';
        $_SESSION['logoChambre'] = '../images/gendarmerie.png';
    break;
    case 'P':
        $_SESSION['chambre'] = 'P';
        $_SESSION['logoChambre'] = '../images/police.png';
    break;
    default:
        die;
}
?>

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
        <title>Identification</title>
    </header>

    <!-- conteneur principal -->
    <section id="page-identification">
    <!-- conteneur identification -->
    <div id="pan-identification">
        <form action=<?= "verifierConnexion.php?chambre=" . $_SESSION['chambre'];?>  method="post">
        <!-- affichage des elements de connexion -->
        <div id="logo-choisi"><img src=<?= $_SESSION['logoChambre'];?> ></div>
        
        <!-- zone de saisie identifiant -->
        <input name='id' type="login" placeholder="Entrer le nom d'utilisateur" required>

        <!-- zone de saisie mdp -->
        <input name='mdp' type="password" placeholder="Entrer le mot de passe" required>

        <!-- ligne d'erreur si la connexion échoue -->
        <?php if ($_SESSION['connexion']) { echo '<p id="login-failed">Identifiant ou mot de passe incorrect.</p>'; }?>
        <!-- boutons retour / valider -->
        <div class="buttons-id">
            <input onclick="window.location.href='choix-chambre.php'"; class="button-id" type="button" value="retour" >
            <input class="button-id" type="submit" value="valider" >
        </div>
        </form>
    </div>
    </section>
</body>
</html>