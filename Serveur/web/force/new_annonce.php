<!-- chargement de la BDD -->
<?php
include("../script/chargerInfos.php");

if (!isset($activite)){
    $requete   = $bdd->query('SELECT code, activite FROM CodeActivite');
    while($res = $requete->fetch()){
        $activite[] = $res;
    }
}
if (!isset($commune)){
    $requete   = $bdd->query('SELECT idCommune, codePostal, commune, secteur FROM Commune ORDER BY commune');
    while($res = $requete->fetch()){
        $commune[] = $res;
    }
}
?>

<!-- ============================================== -->
<form method="post" action="../script/creationAnnonce.php">
<!-- barre d'actions -->
<div id="barre-actions">
  <input type="button" class="new-annonce" value="+ Annonce" onclick="window.location.href='demandes.php?e=new_annonce'" />
  <input type="button" class="new-conseil" value="+ Conseil" onclick="window.location.href='demandes.php?e=new_conseil'" />
  <!-- actions de la demande ouverte -->
  <input class="envoyer" type="submit" value="Envoyer" />
  <input class="actions" type="button" value="supprimer" onclick="window.location.href='demandes.php?e=prive&m=none'" />
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
  <section id="zone-saisie-annonce">
    <fieldset>
      <legend>Informations à compléter pour une nouvelle annonce :</legend>
      <div class="selection-params">
        <p class="alignement-msg"><b>Activités :</b>
          <!-- menu deroulant des activites -->
          <select name="activite1" class="select-activites" size="l">
            <option value="--">Choisir une activité</option>
            <?php foreach($activite as $act) { ?>
                <option value=<?= $act['code']?>><?= $act['code'] . ', ' . $act['activite'] ?></option>
            <?php } ?>
          </select>
  
          <select name="activite2" class="select-activites" size="l">
            <option value="--">Choisir une activité</option>
            <?php foreach($activite as $act) { ?>
                <option value=<?= $act['code']?>><?= $act['code'] . ', ' . $act['activite'] ?></option>
            <?php } ?>
          </select>
  
          <select name="activite3" class="select-activites" size="l">
            <option value="--">Choisir une activité</option>
            <?php foreach($activite as $act) { ?>
                <option value=<?= $act['code']?>><?= $act['code'] . ', ' . $act['activite'] ?></option>
            <?php } ?>
          </select>
        </p>
      </div>
  
      <!-- ligne selection toutes activites -->
      <div class="selection-params">Sélectionner toutes les activités :
        <input id="toutes-activites" name="toutes-activites" type="checkbox"/>
      </div>
    
      <!-- menu deroulant des communes -->
      <div class="selection-params">
        <p class="alignement-msg"><b>Commune :</b>
          <select name ="commune1" class="select-commune" size="l">
            <option value="--">Choisir une commune</option>
            <?php foreach($commune as $com) { ?>      
                <option value=<?= $com['idCommune']?>><?= $com['codePostal'] . ', ' . $com['commune'] ?></option>
            <?php } ?>
          </select>
    
          <select name ="commune2" class="select-commune" size="l">
            <option value="--">Choisir une commune</option>
            <?php foreach($commune as $com) { ?>      
                <option value=<?= $com['idCommune']?>><?= $com['codePostal'] . ', ' . $com['commune'] ?></option>
            <?php } ?>
          </select>
    
          <select name ="commune3" class="select-commune" size="l">
            <option value="--">Choisir une commune</option>
            <?php foreach($commune as $com) { ?>      
                <option value=<?= $com['idCommune']?>><?= $com['codePostal'] . ', ' . $com['commune'] ?></option>
            <?php } ?>
          </select>
        </p>
      </div>
    
      <!-- ligne selection toutes communes -->
      <div class="selection-params">Sélectionner toutes les communes :
        <input id="toutes-communes" name="toutes-communes" type="checkbox"/>
      </div>
    
      <!-- menu deroulant des communes -->
      <div class="selection-params">
        <p class="alignement-msg"><b>Zone :</b>
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
  
      <!-- checkbox envoi mail -->
      <div class="selection-params"><b>Envoyer l'annonce par mail :</b>
          <input id="mail" name="mail" type="checkbox"/>
      </div><br>
    </fieldset>

    <fieldset>
      <legend>Objet :</legend>
      <input name='input-objet-annonce' required>
    </fieldset>
  </section>

  <!-- zone de redaction du message -->
  <textarea name="texte" id="write-annonce" required></textarea>

</form>
</section>
