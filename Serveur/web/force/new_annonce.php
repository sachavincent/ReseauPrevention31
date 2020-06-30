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

<!-- ========== NEW ANNONCE =========== -->

<form method="POST" action="../script/creationAnnonce.php">

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
        <input type="text" class="rechercheActivite" name='activite1' placeholder='Saisir une activité' onblur="afficheDest()"/>
        <input type="text" class="rechercheActivite" name='activite2' placeholder='Saisir une activité' onblur="afficheDest()"/>
        <input type="text" class="rechercheActivite" name='activite3' placeholder='Saisir une activité' onblur="afficheDest()"/>
    </div>

    <!-- ligne selection toutes activites -->
    <div class="champs-annonce">Sélectionner toutes les activités :
        <input class="select-all" name="toutes-activites" type="checkbox" onclick="afficheDest()"/>
    </div>

    <!-- menu deroulant des communes -->
    <div class="champs-annonce"><span id="align-commune">Commune :</span>
        <input type="text" class="rechercheCommune" name='commune1' placeholder='Saisir une commune' onblur="afficheDest()"/>
        <input type="text" class="rechercheCommune" name='commune2' placeholder='Saisir une commune' onblur="afficheDest()"/>
        <input type="text" class="rechercheCommune" name='commune3' placeholder='Saisir une commune' onblur="afficheDest()"/>
    </div>

    <!-- ligne selection toutes communes -->
    <div class="champs-annonce">Sélectionner toutes les communes :
        <input class="select-all" name="toutes-communes" type="checkbox" onclick="afficheDest()"/>
    </div>

    <!-- menu deroulant des communes -->
    <div class="champs-annonce"><span id="align-zone">Zone :</span>
        <select name="secteur1" class="select-zone" size="l" onblur="afficheDest()">
            <option value="0">Choisir un secteur</option>
            <option value=1>Secteur 1</option>
            <option value=2>Secteur 2</option>
            <option value=3>Secteur 3</option>
            <option value=4>Secteur 4</option>
            <option value=5>Secteur 5</option>
            <option value=6>Secteur 6</option>
            <option value=7>Secteur 7</option>
        </select>

        <select name="secteur2" class="select-zone" size="l" onblur="afficheDest()">
            <option value="0">Choisir un secteur</option>
            <option value=1>Secteur 1</option>
            <option value=2>Secteur 2</option>
            <option value=3>Secteur 3</option>
            <option value=4>Secteur 4</option>
            <option value=5>Secteur 5</option>
            <option value=6>Secteur 6</option>
            <option value=7>Secteur 7</option>
        </select>

        <select name="secteur3" class="select-zone" size="l" onblur="afficheDest()">
            <option value="0">Choisir un secteur</option>
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
        <input class="select-all" name="toutes-zones" type="checkbox" onclick="afficheDest()"/>
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
    <p id="dest-concernes">Nombre de destinataires concernés : 0</p>
    </fieldset>
</div>


<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/d3js/5.15.1/d3.min.js"></script>

<script type="text/javascript">
    // affichage destinataires concernés (nb for test)
    function afficheDest() {
        var dest = document.getElementById('dest-concernes');
        
        var info = new Object();
        info['toutes-zones'] = document.getElementsByName('toutes-zones')[0].checked;
        info['toutes-activites'] = document.getElementsByName('toutes-activites')[0].checked;
        info['toutes-communes'] = document.getElementsByName('toutes-communes')[0].checked;
        
        info['commune1'] = document.getElementsByName('commune1')[0].value;
        info['commune2'] = document.getElementsByName('commune2')[0].value;
        info['commune3'] = document.getElementsByName('commune3')[0].value;
        
        info['activite1'] = document.getElementsByName('activite1')[0].value;
        info['activite2'] = document.getElementsByName('activite2')[0].value;
        info['activite3'] = document.getElementsByName('activite3')[0].value;
    
        info['secteur1'] = document.getElementsByName('secteur1')[0].selectedIndex;
        info['secteur2'] = document.getElementsByName('secteur2')[0].selectedIndex;
        info['secteur3'] = document.getElementsByName('secteur3')[0].selectedIndex;

        console.log(info)

        console.log("check all")
        console.log(info['toutes-zones'], info['toutes-activites'], info['toutes-communes'])
        // console.log("commune")
        // console.log(info['commune1'], info['commune2'], info['commune3'])
        // console.log("activite")
        // console.log(info['activite1'], info['activite2'], info['activite3'])
        // console.log("secteur")
        // console.log(info['secteur1'], info['secteur2'], info['secteur3'])

        let request = 
        $.ajax({
            type: "POST",
            url: '../script/calculerDestinatairesAnnonce.php',
            data: info,
            dataType: 'json',
            asynch: false,
            timeout: 3000,
            success: function(data){
            var nbDest = 0;
            for (i in data) {
                nbDest++;
            }
            dest.textContent = "Nombre de destinataires concernés : " + nbDest;
            
            },
            error : function(data){
            dest.textContent = "Nombre de destinataires concernés : ko " ;
            console.log(data)
            
            }
        });

    }
</script>

<!-- zone de redaction du message -->
<textarea name="texte" id="write-annonce" required></textarea>

</form>
<!-- Traitement des recherches commune/activite -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
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
