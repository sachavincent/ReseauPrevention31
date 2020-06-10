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

<!-- ============================================== -->

<!-- pan liste msg -->
<section id="pan-content">

  <div id="barre-envoi">
    <input class="btn-new-annonce-conseil" type="submit" value="Envoyer" />
    <input class="btn-new-annonce-conseil" type="button" value="supprimer" onclick="window.location.href='demandes.php?e=prive&m=none'" />
  </div>

  <!-- zone de saisies du message -->
  <section id="zone-saisie-annonce">
    <img src="../images/carte.png" alt="Carte" id="carteHG">
    <fieldset>
      <legend>Informations à compléter pour une nouvelle annonce :</legend>
      <div class="champs-annonce"><span id="align-activite">Activités :</span>
        <!-- menu deroulant des activites -->
        <input type="text" class="rechercheActivite" name='activite1' placeholder='Saisir une activité'/>
        <input type="text" class="rechercheActivite" name='activite2' placeholder='Saisir une activité'/>
        <input type="text" class="rechercheActivite" name='activite3' placeholder='Saisir une activité'/>
      </div>

      <!-- ligne selection toutes activites -->
      <div class="champs-annonce">Sélectionner toutes les activités :
        <input class="select-all" name="toutes-activites" type="checkbox"/>
      </div>

      <!-- menu deroulant des communes -->
      <div class="champs-annonce"><span id="align-commune">Commune :</span>
        <input type="text" class="rechercheCommune" name='commune1' placeholder='Saisir une commune'/>
        <input type="text" class="rechercheCommune" name='commune2' placeholder='Saisir une commune'/>
        <input type="text" class="rechercheCommune" name='commune3' placeholder='Saisir une commune'/>
      </div>

      <!-- ligne selection toutes communes -->
      <div class="champs-annonce">Sélectionner toutes les communes :
        <input class="select-all" name="toutes-communes" type="checkbox"/>
      </div>

      <!-- menu deroulant des communes -->
      <div class="champs-annonce"><span id="align-zone">Zone :</span>
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
      </div>

      <!-- ligne selection toutes zones -->
      <div class="champs-annonce">Sélectionner toutes les zones :
        <input class="select-all" name="toutes-zones" type="checkbox"/>
      </div>

      <!-- checkbox envoi mail -->
      <div class="champs-annonce"><b>Envoyer l'annonce par mail :</b>
          <input name="mail" type="checkbox"/>
      </div>
    </fieldset>
  </section>

  <div id="objet-new-annonce">
    <fieldset>
      <legend>Objet :</legend>
      <input name='input-objet-annonce' required>
    </fieldset>
  </div>

  <!-- zone de redaction du message -->
  <textarea name="texte" id="write-annonce" required></textarea>

</form>
<!-- Traitement des recherches commune/activite -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
$('.rechercheCommune').autocomplete({
  source : '../script/rechercheCommune.php',
  autoFocus: true,
  minLength: 0
});

$('.rechercheActivite').autocomplete({
  source : '../script/rechercheActivite.php',
  autoFocus: true,
  minLength: 0
});
</script>
</section>
