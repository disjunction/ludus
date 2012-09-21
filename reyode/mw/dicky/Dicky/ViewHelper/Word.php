<?php
class Dicky_ViewHelper_Word extends Dicky_ViewHelper_Abstract
{
    /**
    * @var Dicky_RelManager
    */
    protected $_relManager;
    /**
     * @var Dicky_SimpleTransManager
     */
    protected $_stManager;
    /**
     * @var Dicky_WordManager
     */
    protected $_wordManager;
    protected static $_ignoreTypes = array(
        'r.belong' => 1,
        'l.gender' => 1,
    );
    public function __construct($config = null)
    {
        parent::__construct($config);
        $this->_relManager = new Dicky_RelManager();
        $this->_relManager->setConfig($config);
        $this->_stManager = new Dicky_SimpleTransManager();
        $this->_stManager->setConfig($config);
        $this->_wordManager = new Dicky_WordManager();
        $this->_wordManager->setConfig($config);
    }
    
    /**
     * @param Dicky_Word $word
     */
    public function word(Dicky_Word $word)
    {
        $relset = $this->_relManager->getRelSetBySubjSign($word->sign);
        $tail = '';
        $spellingClasses = 'spelling';
        
        if ($gender = $relset->getByTypeSign('l.gender')) {
            $spellingClasses .= ' gender' . substr($gender->objSign, 2);
        }
        
        foreach ($relset as $rel) {
            if (!isset(self::$_ignoreTypes[$rel->typeSign])) {
                $relWord = $this->_wordManager->getBySign($rel->objSign) and
                    $tail .= ' ' . $relWord->spelling;
            }
        }
        
        $prefix = '<span class="' . $spellingClasses . '">';
        return $prefix . $word->spelling . '</span> ' . $tail;
    }
}