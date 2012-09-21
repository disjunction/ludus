<?php
//namespace Dicky;

require_once 'Dicky/SimpleTransManager.php';
require_once 'Dicky/RelManager.php';
require_once 'Dicky/WordManager.php';
require_once 'Dicky/Parser.php';

class Dicky_Handler
{
    protected $_config;
    
    function firstCallInit(Parser $parser) {
        global $wgOut;
        require_once 'Dicky/ViewHelper/Translation.php';
        $wgOut->addScript('<link rel="stylesheet" type="text/css" href="/extensions/dicky/dicky.css" />');
        $translation = new Dicky_ViewHelper_Translation($this->_getConfig());
        $parser->setHook('translation', array($translation, 'mwHook'));
        return true;
    }
    
    /**
     * @return Dicky_Config
     */
    function _getConfig()
    {
        if (!$this->_config) {
            $config = new Dicky_Config();
            $config->user = 'ludus';
            $config->password = 'ludus';
            $config->dbname = 'ludus_common_dicky';
            
            $this->_config = $config;
        }
        return $this->_config;
    }
    
    function onSave( &$article, &$user, &$text, &$summary,
 $minor, $watchthis, $sectionanchor, &$flags, &$status )
    {

        $parser = new Dicky_Parser();
        $text = $parser->parseText($text);
        $translations = $parser->getTranslations();
        
        $config = $this->_getConfig();
        
        $transManager = new Dicky_SimpleTransManager();
        $transManager->setConfig($config);
        $relManager = new Dicky_RelManager();
        $relManager->setConfig($config);
        
        $pageTitle = $article->mTitle->mTextform;
        $pageSign = Dicky_SimpleTransManager::pageSign($pageTitle);
        
        $relManager->deletePageRels($pageSign);
        
        foreach ($translations as $translation) {
            $transManager->saveSimpleTrans($translation, $pageTitle);
        }
        
        return true;
    }
    
    function beforeRender ($article, &$content, $bla)
    {
        $parser = new Dicky_Parser();
        $content = $parser->transformText($content);
        return true;
    }
    
    /**
     * @return Dicky_Handler
     */
    static function _()
    {
        static $me;
        return (!$me)? ($me = new self()) : $me;
    }
}

$wgHooks['ArticleSave'][] = array(Dicky_Handler::_(), 'onSave');
$wgHooks['ParserBeforeStrip'][] = array(Dicky_Handler::_(), 'beforeRender');
$wgHooks['ParserFirstCallInit'][] = array(Dicky_Handler::_(), 'firstCallInit');
