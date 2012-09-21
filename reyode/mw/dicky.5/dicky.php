<?php
namespace Dicky;

require_once 'Dicky/SimpleTransManager.php';
require_once 'Dicky/RelManager.php';
require_once 'Dicky/Parser.php';

class Handler
{
    function onSave( &$article, &$user, &$text, &$summary,
 $minor, $watchthis, $sectionanchor, &$flags, &$status )
    {

        $parser = new Parser();
        $parser->parseText($text);
        $translations = $parser->getTranslations();
        
        $config = new Config();
        $config->user = 'ludus';
        $config->password = 'ludus';
        $config->dbname = 'ludus_common_dicky';
        
        $transManager = new SimpleTransManager();
        $transManager->setConfig($config);
        $relManager = new RelManager();
        $relManager->setConfig($config);
        
        $pageTitle = $article->mTitle->mTextform;
        $pageSign = SimpleTransManager::pageSign($pageTitle);
        
        $relManager->deletePageRels($pageSign);
        
        foreach ($translations as $translation) {
            $transManager->saveSimpleTrans($translation, $pageTitle);
        }
        
        return true;
    }
    
    function beforeRender ($article, &$content, $bla)
    {
        $parser = new Parser();
        $content = $parser->transformText($content);
        return true;
    }
}

$wgHooks['ArticleSave'][] = array(new Handler(), 'onSave');
$wgHooks['ParserBeforeStrip'][] = array(new Handler(), 'beforeRender');
