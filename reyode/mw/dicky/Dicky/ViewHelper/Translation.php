<?php
require_once dirname(__FILE__) . '/Abstract.php';
require_once dirname(__FILE__) . '/Word.php';

class Dicky_ViewHelper_Translation extends Dicky_ViewHelper_Abstract
{
    protected $_wordViewHelper;
    /**
     * @var Dicky_SimpleTransManager
     */
    protected $_stManager;
    
    public function __construct($config = null)
    {
        parent::__construct($config);
        $this->_wordViewHelper = new Dicky_ViewHelper_Word($config);
        $this->_stManager = new Dicky_SimpleTransManager();
        $this->_stManager->setConfig($config);
    }

    function mwHook( $input, array $args, Parser $parser, PPFrame $frame )
    {
        if (!isset($args['subj'])) {
            return '(error: no subj)';
        }
        if (!isset($args['obj'])) {
            return '(error: no obj)';
        }
        
        $simpleTrans = $this->_stManager->getBySubjObj($args['subj'], $args['obj']);
        if (!$simpleTrans) {
            return '(error: translation not found)';
        }
        
        return '<dt>' . 
               $this->_wordViewHelper->word($simpleTrans->makeSubjWord()) .
               '</dt><dd>' .
               $this->_wordViewHelper->word($simpleTrans->makeObjWord()) .
               '</dd>';
               
    }
}