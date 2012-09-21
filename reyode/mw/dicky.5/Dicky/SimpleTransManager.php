<?php
namespace Dicky;

require_once __DIR__ . '/Config.php';
require_once __DIR__ . '/SimpleTrans.php';
require_once __DIR__ . '/RelManager.php';

class SimpleTransManager
{
    /**
     * @var Config
     */
    protected $_config;
    
    public function setConfig($config)
    {
       $this->_config = $config; 
    }
    
    public static function pageSign($title)
    {
        return 'a.' . SimpleTrans::spellingToSign($title);;
    }
    
    public function cleanRelsForPage(SimpleTrans $o, $title)
    {
        $pageSign = self::pageSign($title);
    }
    
    public function saveSimpleTrans(SimpleTrans $o, $title = null)
    {
        $o->normalize();
        
        $pdo = $this->_config->getConnection();
        $pdo->beginTransaction();
        
        $sql = 'replace into SimpleTrans(direction, subjSign, subjSpelling, objSign, objSpelling) values(?, ?, ?, ?, ?)';
        $statement = $pdo->prepare($sql);
        $result = $statement->execute($o->toArray());
        
        if ($title) {
            $pageSign = self::pageSign($title);
            $o->objRels[] = new Rel($o->objSign, $pageSign, 'r.belong');
            $o->subjRels[] = new Rel($o->subjSign, $pageSign, 'r.belong');
        }
        
        if ($o->objRels || $o->subjRels) {
            $relManager = new RelManager();
            $relManager->setConfig($this->_config);
            
            $o->subjRels && $relManager->saveRels($o->subjRels);
            $o->objRels && $relManager->saveRels($o->objRels);
        }
        
        $pdo->commit();
    }
}