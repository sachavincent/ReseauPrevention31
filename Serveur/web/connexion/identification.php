<?php 
session_start();
/* récupération de l'URL pour obtenir le choix de l'user */
switch ($_GET['chambre']){
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
    <link rel="stylesheet" href="../style.css" />
  </head>

  <body>
    <!-- header -->
    <header>
        <title>Identification</title>
    </header>

    <!-- conteneur principal -->
    <section id="page-identification">
      <!-- conteneur identification -->
      <section id="panel-identification">
        <h2>IDENTIFICATION</h2>

        <form action=<?php echo "verifierConnexion.php?chambre=" . $_SESSION['chambre'];?>  method="post">
        <!-- affichage des elements de connexion -->
        <div id="logo-choisi"><img src=<?php echo $_SESSION['logoChambre'];?> width="170px" height="170px"></div>
        
        <div class="identifiants"><b>Nom d'utilisateur</b></div>
        <!-- zone de saisie identifiant -->
        <input id="login-username" name='id' type="login" placeholder="Entrer le nom d'utilisateur" required>

        <div class="identifiants"><b>Mot de passe</b></div>
        <!-- zone de saisie mdp -->
        <input id="login-password" name='mdp' type="password" placeholder="Entrer le mot de passe" required>

        <br>
        <!-- boutons retour / valider -->
        <input onclick=window.location.href='choix-chambre.php'; class="bouton-id" type="button" value="retour" >
        <input class="bouton-id" type="submit" value="valider" >

        <!-- ligne d'erreur si la connexion échoue -->
        <?php if ($_SESSION['connexion']) { echo '<p id="login-failed">Identifiant ou mot de passe incorrect.</p>'; }?>

        </form>
      </section>
    </section>

  </body>
</html>