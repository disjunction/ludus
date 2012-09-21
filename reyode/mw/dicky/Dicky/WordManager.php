<?php
require_once dirname(__FILE__) . '/AbstractManager.php';
require_once dirname(__FILE__) . '/Word.php';

class Dicky_WordManager extends Dicky_AbstractManager
{
    protected $_cacheBySign = array();

    /**
     * @param string $sign
     * @return Dicky_Word|null
     */
    public function getBySign($sign)
    {
        if (!isset($this->_cacheBySign[$sign])) {
            $sql = "select * from Word where sign=? limit 1";
            $result = $this->_getOneRow($sql, array($sign));
            $this->_cacheBySign[$sign] = $result? Dicky_Word::fromArray($result) : null;
        }
        return $this->_cacheBySign[$sign];
    }
}