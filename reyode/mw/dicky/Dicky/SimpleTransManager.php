<?php
// namespace Dicky;

require_once dirname(__FILE__) . '/AbstractManager.php';
require_once dirname(__FILE__) . '/SimpleTrans.php';
require_once dirname(__FILE__) . '/RelManager.php';

class Dicky_SimpleTransManager extends Dicky_AbstractManager
{
    /**
     * @var Dicky_RelManager
     */
    protected $_relManager;
    
    public static function pageSign($title)
    {
        return 'a.' . Dicky_SimpleTrans::spellingToSign($title);;
    }
    
    public function cleanRelsForPage(Dicky_SimpleTrans $o, $title)
    {
        $pageSign = self::pageSign($title);
    }
    
    /**
     * @return Dicky_RelManager
     */
    protected function _getRelManager()
    {
        if (!isset($this->_relManager)) {
            $this->_relManager = new Dicky_RelManager();
            $this->_relManager->setConfig($this->_config);
        }
        return $this->_relManager;
    }

    
    public function saveSimpleTrans(Dicky_SimpleTrans $o, $title = null)
    {
        $o->normalize();
        
        $pdo = $this->_config->getConnection();
        $pdo->beginTransaction();
        
        $sql = 'replace into SimpleTrans(direction, subjSign, subjSpelling, objSign, objSpelling) values(?, ?, ?, ?, ?)';
        $statement = $pdo->prepare($sql);
        $statement->execute($o->toArray());
        $statement->execute($o->getMirror()->toArray());
        
        if ($title) {
            $pageSign = self::pageSign($title);
            $o->objRels[] = new Dicky_Rel($o->objSign, $pageSign, 'r.belong');
            $o->subjRels[] = new Dicky_Rel($o->subjSign, $pageSign, 'r.belong');
        }
        
        if ($o->objRels || $o->subjRels) {
            $relManager = $this->_getRelManager();
            
            $o->subjRels && $relManager->saveRels($o->subjRels);
            $o->objRels && $relManager->saveRels($o->objRels);
        }
        
        $pdo->commit();
    }
    
    /**
     * @param string $field
     * @param string $value
     * @return Dicky_SimpleTrans|null
     */
    public function getByField($field, $value)
    {
        $sql = "select * from SimpleTrans where $field=? limit 1";
        $result = $this->_getOneRow($sql, array($value));
        return $result? Dicky_SimpleTrans::fromArray($result) : null;
    }
    
    /**
    * @param string $field
    * @param string $value
    * @return Dicky_SimpleTrans|null
    */
    public function getBySubjObj($subjSign, $objSign)
    {
        $sql = "select * from SimpleTrans where subjSign=? and objSign=? limit 1";
        $result = $this->_getOneRow($sql, array($subjSign, $objSign));
        return $result? Dicky_SimpleTrans::fromArray($result) : null;
    }
}
