<?php
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Slim\Factory\AppFactory;

require __DIR__ . '/vendor/autoload.php';

$app = AppFactory::create();

$app->post('/inscription', function (Request $request, Response $response) {
    include './appli/script/connexionBdd.php';
    include './appli/script/inscription.php';
    return $response;
});

$app->post('/connexion', function (Request $request, Response $response) {
    include './appli/script/connexionBdd.php';
    include './appli/script/connexionUtilisateur.php';
    return $response;
});

$app->post('/updatebdd', function (Request $request, Response $response) {
    include './appli/script/connexionBdd.php';
    include './appli/script/updateBdd.php';
    return $response;
});

$app->post('/test', function (Request $request, Response $response) {
    include './appli/script/connexionBdd.php';
    include './appli/script/test.php';
    return $response;
});

$app->post('/', function (Request $request, Response $response) {
    include './web/connexion/choix-chambre.php';
    return $response;
});

$app->run();

?>