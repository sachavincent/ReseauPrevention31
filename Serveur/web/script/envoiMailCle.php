<?php
$cle = $infoUtilisateur['cle'];
$requete = $bdd->prepare('SELECT nomUtilisateur, prenomUtilisateur, mail FROM Utilisateur WHERE cle = ?');
$requete->execute(array($cle));
$infoUtilisateur = $requete->fetch();
/* 
require '../../vendor/autoload.php';
use \Mailjet\Resources;
$mj = new \Mailjet\Client('0904172806ab77b1da0f835836cbadc3','6c72c75e671030418bdcdc09004fee86',true,['version' => 'v3.1']);
$body = [
    'Messages' => [
        [
            'From' => [
                'Email' => "reseauprevention31@gmail.com",
                'Name' => "Prevention31"
            ],
            'To' => [
                [
                    'Email' => $infoUtilisateur['mail'],
                    'Name' => $infoUtilisateur['nomUtilisateur'] . $infoUtilisateur['prenomUtilisateur']
                ]
            ],
            'Subject' => "Validation de votre inscription",
            'TextPart' => "Bonjour, suite à votre inscription sur l'application prevention31 voici votre clé de connexion unique:" . $cle
                            . ' Pour vous connecter sur l\'application : - Copiez la cle - Rendez-vous sur l\'application - Selectionnez connexion puis collez la clé - Enfin validez, vous voila maintenant sur votre espace' 
                            . 'L\'équipe RéseauPrévention31',
            'HTMLPart' => "<p> Bonjour,<br><br> suite à votre inscription sur l'application prevention31 voici votre clé de connexion unique: </p> <p><h2>" . $cle ."</h2></p>"
                            . '<p> Pour vous connecter sur l\'application :<br> - Copiez la cle<br> - Rendez-vous sur l\'application<br> - Selectionnez connexion puis collez la clé<br> - Enfin validez, vous voila maintenant sur votre espace</p>'
                            . '<br><p>L\'équipe RéseauPrévention31</p>'
        ]
    ]
];
$response = $mj->post(Resources::$Email, ['body' => $body]); */
//$response->success() && var_dump($response->getData());

shell_exec ("echo 'Bonjour,\n\nsuite à votre inscription sur l`application prevention31 voici votre clé de connexion unique: \n\n" . $cle 
	. "\n\nPour vous connecter sur l`application :\n	- Copiez la cle\n	- Rendez-vous sur l`application\n	- Selectionnez connexion puis collez la clé\n	- Enfin validez, vous voilà maintenant sur votre espace\n\nL`équipe RéseauPrévention31' | mail -s 'Validation de votre inscription' " . $infoUtilisateur['mail']);
?>
