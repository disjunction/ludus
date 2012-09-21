<?php
namespace Dicky;

require_once 'Dicky/SimpleTransManager.php';
require_once 'Dicky/Parser.php';

$config = new Config();
$config->user = 'ludus';
$config->password = 'ludus';
$config->dbname = 'ludus_common_dicky';

//$manager = new SimpleTransManager();
//$processor->setConfig($config);
//$processor->process('hello world');

$parser = new Parser();
$a = $parser->transformText("askdfjh
[[Translate from::German]] -> [[Translate to::Russian]]
== Hello World ==
adfsklasdfj
=== - dic 1 - === 
* Größe -- размер
=== no ===
* ok -- nook
* G -- kasjdfl
");
