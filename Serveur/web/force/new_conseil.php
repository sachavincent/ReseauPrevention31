<!-- chargement de la BDD -->
<?php include("../script/chargerInfos.php");?>

<!-- ============================================== -->

<form method="post" action="../script/creationConseil.php">

<!-- pan liste msg -->
<section id="pan-content">

  <div id="barre-envoi">
    <input class="btn-new-annonce-conseil" type="submit" value="Envoyer" />
    <input class="btn-new-annonce-conseil" type="button" value="supprimer" onclick="window.location.href='demandes.php?e=prive&m=none'" />
  </div>

  <div id="objet-new-conseil">
    <fieldset>
      <legend>Objet :</legend>
      <input name='input-objet-conseil' required>
      <p class="user-concernes">à destination des utilisateurs de l'application mobile.</p>
    </fieldset>
  </div>

  <textarea name="texte" id="write-conseil" required></textarea>
</section>

</form>
