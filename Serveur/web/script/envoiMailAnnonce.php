<?php
require '../../vendor/autoload.php';
use \Mailjet\Resources;
$mj = new \Mailjet\Client('0904172806ab77b1da0f835836cbadc3','6c72c75e671030418bdcdc09004fee86',true,['version' => 'v3.1']);
foreach($listeDestinataire as $dest){
    $body = [
        'Messages' => [
            [
                'From' => [
                    'Email' => "reseauprevention31@gmail.com",
                    'Name' => "Prevention31"
                ],
                'To' => [
                    [
                        'Email' => $dest['mail'],
                        'Name' => $dest['nomUtilisateur'] . $dest['prenomUtilisateur']
                    ]
                ],
                'Subject' => $_POST['input-objet-annonce'],
                'TextPart' => $_POST['texte'],
                'HTMLPart' => $_POST['texte']
            ]
        ]
    ];
    $response = $mj->post(Resources::$Email, ['body' => $body]);
    //$response->success() && var_dump($response->getData());    
}
?>
