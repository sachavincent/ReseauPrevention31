<!-- chargement de la BDD -->
<?php
include("../script/chargerInfos.php");

if (!isset($activite)){
    $requete = $bdd->query('SELECT code, activite FROM codeactivite');
    while($res = $requete->fetch()){
        $activite[] = $res;
    }
}
if (!isset($commune)){
    $requete = $bdd->query('SELECT idCommune, codePostal, commune, secteur FROM commune ORDER BY commune');
    while($res = $requete->fetch()){
        $commune[] = $res;
    }
}

function listeDestinataire($formulaire, $bdd){

  if (isset($formulaire['toutes-activites']) AND isset($formulaire['toutes-communes']) AND isset($formulaire['toutes-zones'])){
      $requeteListeDestinataire=$bdd->query('SELECT idUtilisateur FROM Utilisateur');

  } elseif (isset($formulaire['toutes-activites']) AND isset($formulaire['toutes-communes'])){
      $requeteListeDestinataire=$bdd->prepare('SELECT idUtilisateur FROM Utilisateur WHERE secteur = ? OR secteur = ? OR secteur = ?');
      $requeteListeDestinataire->execute(array($formulaire['secteur1'], $formulaire['secteur2'], $formulaire['secteur3']));

  } elseif(isset($formulaire['toutes-activites']) AND isset($formulaire['toutes-zones'])){
      $requeteListeDestinataire=$bdd->prepare('SELECT idUtilisateur FROM Utilisateur WHERE idCommune = ? OR idCommune = ? OR idCommune = ?');
      $requeteListeDestinataire->execute(array($formulaire['commune1'], $formulaire['commune2'], $formulaire['commune3']));

  } elseif(isset($formulaire['toutes-communes']) AND isset($formulaire['toutes-zones'])){
      $requeteListeDestinataire=$bdd->prepare('SELECT idUtilisateur FROM Utilisateur WHERE codeAct = ? OR codeAct = ? OR codeAct = ?');
      $requeteListeDestinataire->execute(array($formulaire['activite1'], $formulaire['activite2'], $formulaire['activite3']));

  } elseif(isset($formulaire['toutes-activites'])){
      $requeteListeDestinataire = $bdd->prepare(' SELECT idUtilisateur FROM Utilisateur 
                                                  WHERE (idCommune = ? OR idCommune = ? OR idCommune = ?)
                                                  AND (secteur = ? OR secteur = ? OR secteur = ?)');
      $requeteListeDestinataire->execute(array(   $formulaire['commune1'], $formulaire['commune2'], $formulaire['commune3'],
                                                  $formulaire['secteur1'], $formulaire['secteur2'], $formulaire['secteur3']));

  } elseif(isset($formulaire['toutes-communes'])){
      $requeteListeDestinataire = $bdd->prepare(' SELECT idUtilisateur FROM Utilisateur 
                                                  WHERE (codeAct = ? OR codeAct = ? OR codeAct = ?)
                                                  AND (secteur = ? OR secteur = ? OR secteur = ?)');
      $requeteListeDestinataire->execute(array(   $formulaire['activite1'], $formulaire['activite2'], $formulaire['activite3'],
                                                  $formulaire['secteur1'], $formulaire['secteur2'], $formulaire['secteur3']));
  
  } elseif(isset($formulaire['toutes-zones'])){
      $requeteListeDestinataire = $bdd->prepare(' SELECT idUtilisateur FROM Utilisateur 
                                                  WHERE (idCommune = ? OR idCommune = ? OR idCommune = ?)
                                                  AND (codeAct = ? OR codeAct = ? OR codeAct = ?)');
      $requeteListeDestinataire->execute(array(   $formulaire['commune1'], $formulaire['commune2'], $formulaire['commune3'],
                                                  $formulaire['activite1'], $formulaire['activite2'], $formulaire['activite3']));
  } else{
      $requeteListeDestinataire = $bdd->prepare(' SELECT idUtilisateur FROM Utilisateur 
                                                  WHERE (idCommune = ? OR idCommune = ? OR idCommune = ?)
                                                  AND (codeAct = ? OR codeAct = ? OR codeAct = ?)
                                                  AND (secteur = ? OR secteur = ? OR secteur = ?)');
      $requeteListeDestinataire->execute(array(   $formulaire['commune1'], $formulaire['commune2'], $formulaire['commune3'],
                                                  $formulaire['activite1'], $formulaire['activite2'], $formulaire['activite3'],
                                                  $formulaire['secteur1'], $formulaire['secteur2'], $formulaire['secteur3']));   
  }
  return $requeteListeDestinataire;
}

//Envoyer
if (isset($_POST['envoyer'])){
  if (!(isset($_POST['input-objet-annonce']) AND isset($_POST['texte']))){
      $success = false;
  } else {
      $success = true;
  
      //Creation de l'annonce
      $requeteCreationAnnonce = $bdd->prepare('INSERT INTO `annonce`(`objet`, `texte`) VALUES (?,?)');
      $requeteCreationAnnonce->execute(array($_POST['input-objet-annonce'], $_POST['texte']));
  
      //Recuperationde l'id de l'annonce
      $requeteIdAnnonce = $bdd->prepare('SELECT idAnnonce FROM Annonce WHERE objet = ? AND texte = ?');
      $requeteIdAnnonce->execute(array($_POST['input-objet-annonce'], $_POST['texte']));
      $idAnnonce = ($requeteIdAnnonce->fetch()['idAnnonce']);

      $requeteListeDestinataire = listeDestinataire($_POST, $bdd);
  
     //Creation des lignes de destinationMessage
      $nbDest = 0;
      while ($resultat = $requeteListeDestinataire->fetch()){
          $requeteAjoutDest = $bdd->prepare('INSERT INTO `destinationannonce`(`idAnnonce`, `idUtilisateur`) VALUES (?,?)');
          $requeteAjoutDest->execute(array($idAnnonce, $resultat['idUtilisateur']));
          $nbDest++;
      }
      echo 'Message envoye a : ' . $nbDest . ' utilisateurs';
  } 
  unset($_SESSION['nbDestinataire']);
} //NbDestinataire
elseif (isset($_POST['nbDestinataire'])){
  $requeteListeDestinataire = listeDestinataire($_POST, $bdd);
  
  $_SESSION['nbDestinataire'] = count($requeteListeDestinataire->fetchAll());

  $success = true;

  echo $_SESSION['nbDestinataire'];
} else {
  $success = false;
}
?>

<!-- ============================================== -->
<form method="post" action="">
<!-- barre d'actions -->
<div id="barre-actions">
  <input type="button" class="new-annonce" value="+ Annonce" onclick=window.location.href='demandes.php?e=new_annonce'>
  <input type="button" class="new-conseil" value="+ Conseil" onclick=window.location.href='demandes.php?e=new_conseil'>
  <!-- actions de la demande ouverte -->
  <input class="envoyer" name="envoyer" type="submit" value="Envoyer"/>
  <input class="actions" type="button" value="supprimer" onclick=window.location.href='demandes.php?e=prive&m=none' />
</div>

<!-- barre grise a droite (decoratif) -->
<div id="deco-droite"></div>

<!-- ============================================== -->

<!-- affichage menu gauche -->
<?php include("page/navForce.php"); ?>

<!-- ============================================== -->

<!-- pan liste msg -->
<section id="pan-content">
  <!-- zone de saisies du message -->
  <div id="zone-saisie-annonce">
    <p id="titre-info-demande"><U>Informations à compléter pour une nouvelle annonce :</U></p>
    <div class="selection-params">
      <p class="alignement-msg"><b><U>Activités :</U></b>
        <!-- menu deroulant des activites -->
        <select name="activite1" class="select-activites" size="l">
          <option value="--">Choisir une activité</option>
          <?php
          foreach($activite as $act){
            ?>      <option value=<?= $act['code']?>><?= $act['code'] . ', ' . $act['activite']?></option>
          <?php    }
          ?>
        </select>

        <select name="activite2" class="select-activites" size="l">
          <option value="--">Choisir une activité</option>
          <?php
          foreach($activite as $act){
            ?>      <option value=<?= $act['code']?>><?= $act['code'] . ', ' . $act['activite']?></option>
          <?php    }
          ?>
        </select>

        <select name="activite3" class="select-activites" size="l">
          <option value="--">Choisir une activité</option>
          <?php
          foreach($activite as $act){
            ?>      <option value=<?= $act['code']?>><?= $act['code'] . ', ' . $act['activite']?></option>
          <?php    }
          ?>
        </select>
      </p>
    </div>

    <!-- ligne selection toutes activites -->
    <div class="selection-params">Sélectionner toutes les activités :
      <input id="toutes-activites" name="toutes-activites" type="checkbox"/>
    </div>
  
    <!-- menu deroulant des communes -->
    <div class="selection-params">
      <p class="alignement-msg"><b><U>Commune :</U></b>
        <select name ="commune1" class="select-commune" size="l">
          <option value="--">Choisir une commune</option>
          <?php
          foreach($commune as $com){
            ?>      <option value=<?= $com['idCommune']?>><?= $com['codePostal'] . ', ' . $com['commune']?></option>
          <?php    }
          ?>
        </select>
  
        <select name ="commune2" class="select-commune" size="l">
          <option value="--">Choisir une commune</option>
          <?php
          foreach($commune as $com){
            ?>      <option value=<?= $com['idCommune']?>><?= $com['codePostal'] . ', ' . $com['commune']?></option>
          <?php    }
          ?>
        </select>
  
        <select name ="commune3" class="select-commune" size="l">
          <option value="--">Choisir une commune</option>
          <?php
          foreach($commune as $com){
            ?>      <option value=<?= $com['idCommune']?>><?= $com['codePostal'] . ', ' . $com['commune']?></option>
          <?php    }
          ?>
        </select>
      </p>
    </div>
  
    <!-- ligne selection toutes communes -->
    <div class="selection-params">Sélectionner toutes les communes :
      <input id="toutes-communes" name="toutes-communes" type="checkbox"/>
    </div>
  
    <!-- menu deroulant des communes -->
    <div class="selection-params">
      <p class="alignement-msg"><b><U>Zone :</U></b>
        <select name="secteur1" class="select-zone" size="l">
          <option value="--">Choisir un secteur</option>
          <option value=1>Secteur 1</option>
          <option value=2>Secteur 2</option>
          <option value=3>Secteur 3</option>
          <option value=4>Secteur 4</option>
          <option value=5>Secteur 5</option>
          <option value=6>Secteur 6</option>
          <option value=7>Secteur 7</option>
        </select>
  
        <select name="secteur2" class="select-zone" size="l">
          <option value="--">Choisir un secteur</option>
          <option value=1>Secteur 1</option>
          <option value=2>Secteur 2</option>
          <option value=3>Secteur 3</option>
          <option value=4>Secteur 4</option>
          <option value=5>Secteur 5</option>
          <option value=6>Secteur 6</option>
          <option value=7>Secteur 7</option>
        </select>
  
        <select name="secteur3" class="select-zone" size="l">
          <option value="--">Choisir un secteur</option>
          <option value=1>Secteur 1</option>
          <option value=2>Secteur 2</option>
          <option value=3>Secteur 3</option>
          <option value=4>Secteur 4</option>
          <option value=5>Secteur 5</option>
          <option value=6>Secteur 6</option>
          <option value=7>Secteur 7</option>
        </select>
      </p>
    </div>
  
    <!-- ligne selection toutes zones -->
    <div class="selection-params">Sélectionner toutes les zones :
      <input id="toutes-zones" name="toutes-zones" type="checkbox"/>
    </div><br>
  </div>
  
  <div id="objet-new-annonce">
    <b><U>Objet :</U></b>
    <input name='input-objet-annonce'>
    <!-- affichage des destinataires concernés -->
    <div id="total-destinataires">
      Nombre de destinataires concernés : 
      <label name='destinataires' onclick=window.location.href='../script/nombreDestinataireAnnonce.php'>
        <U><input type="submit" value="cliquez ici" name="nbDestinataire"></input></U>
      </label>
    </div>
  </div>

  <!-- zone de redaction du message -->
  <textarea name="texte" id="write-annonce"></textarea>

</form>
</section>
