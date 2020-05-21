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
    $requete = $bdd->query('SELECT codePostal, commune, secteur FROM commune ORDER BY commune');
    while($res = $requete->fetch()){
        $commune[] = $res;
    }
}
?>

<!-- ============================================== -->

<!-- barre d'actions -->
<div id="barre-actions">
  <input type="button" class="new-annonce" value="+ Annonce" onclick=window.location.href='demandes.php?e=new_annonce'>
  <input type="button" class="new-conseil" value="+ Conseil" onclick=window.location.href='demandes.php?e=new_conseil'>
  <!-- actions de la demande ouverte -->
  <input class="actions" type="button" value="Envoyer"/>
  <input class="actions" type="button" value="supprimer"/>
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
    <div class="selection-params">
      <p class="alignement-msg">Activités :
        <!-- menu deroulant des activites -->
        <select class="select-activites" size="l">
          <option value="--">Choisir une activité</option>
          <?php
          foreach($activite as $act){
            ?>      <option><?= $act['code'] . ', ' . $act['activite']?></option>
          <?php    }
          ?>
        </select>

        <select class="select-activites" size="l">
          <option value="--">Choisir une activité</option>
          <?php
          foreach($activite as $act){
            ?>      <option><?= $act['code'] . ', ' . $act['activite']?></option>
          <?php    }
          ?>
        </select>

        <select class="select-activites" size="l">
          <option value="--">Choisir une activité</option>
          <?php
          foreach($activite as $act){
            ?>      <option><?= $act['code'] . ', ' . $act['activite']?></option>
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
      <p class="alignement-msg">Commune :
        <select class="select-commune" size="l">
          <option value="--">Choisir une commune</option>
          <?php
          foreach($commune as $com){
            ?>      <option><?= $com['codePostal'] . ', ' . $com['commune']?></option>
          <?php    }
          ?>
        </select>

        <select class="select-commune" size="l">
          <option value="--">Choisir une commune</option>
          <?php
          foreach($commune as $com){
            ?>      <option><?= $com['codePostal'] . ', ' . $com['commune']?></option>
          <?php    }
          ?>
        </select>

        <select class="select-commune" size="l">
          <option value="--">Choisir une commune</option>
          <?php
          foreach($commune as $com){
            ?>      <option><?= $com['codePostal'] . ', ' . $com['commune']?></option>
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
      <p class="alignement-msg">Zone :
        <select class="select-zone" size="l">
          <option value="--">Choisir un secteur</option>
          <option>Secteur 1</option>
          <option>Secteur 2</option>
          <option>Secteur 3</option>
          <option>Secteur 4</option>
          <option>Secteur 5</option>
          <option>Secteur 6</option>
          <option>Secteur 7</option>
        </select>

        <select class="select-zone" size="l">
          <option value="--">Choisir un secteur</option>
          <option>Secteur 1</option>
          <option>Secteur 2</option>
          <option>Secteur 3</option>
          <option>Secteur 4</option>
          <option>Secteur 5</option>
          <option>Secteur 6</option>
          <option>Secteur 7</option>
        </select>

        <select class="select-zone" size="l">
          <option value="--">Choisir un secteur</option>
          <option>Secteur 1</option>
          <option>Secteur 2</option>
          <option>Secteur 3</option>
          <option>Secteur 4</option>
          <option>Secteur 5</option>
          <option>Secteur 6</option>
          <option>Secteur 7</option>
        </select>
      </p>
    </div>

    <!-- ligne selection toutes zones -->
    <div class="selection-params">Sélectionner toutes les zones :
      <input id="toutes-zones" name="toutes-zones" type="checkbox"/>
    </div><br>

    <!-- ligne selection envoi par mail -->
    <div class="selection-params">Type d'envoi :
      <input id="envoi-mail" name="type-envoi" type="checkbox" value="mail"><label for="envoi-mail">Mail</label>
      <input id="envoi-notif" name="type-envoi" type="checkbox" value="notification"><label for="envoi-notif">Notification</label>
    </div><br>

    <!-- affichage des destinataires concernés -->
    <div id="total-destinataires">Nombre de destinataire concernés : 121</div>
  </div>

  <!-- zone de redaction du message -->
  <textarea id="write-msg"></textarea>

</section>
