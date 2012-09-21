<?php
namespace Dicky;

require_once __DIR__ . '/Config.php';
require_once __DIR__ . '/Rel.php';

class RelManager
{
    /**
    * @var Config
    */
    protected $_config;
    
    public function setConfig($config)
    {
        $this->_config = $config;
    }
    
    public function deletePageRels($titleSign)
    {
        $pdo = $this->_config->getConnection();
        
        $title = $pdo->quote($titleSign);
        
        $sql = "select r.*,count(*) as pageCount from Rel r 
        	    inner join Rel r2 on r.subjSign=r2.subjSign 
                where r.typeSign='r.belong' and  
                      r.objSign=$title and
                      r2.typeSign='r.belong'
                group by r2.subjSign, r2.typeSign";
        
        foreach ($pdo->query($sql) as $row) {
            $sql = "delete from Rel where subjSign=" . $pdo->quote($row['subjSign']);
            if ($row['pageCount'] > 1) {
                $sql .= " and typeSign='r.belong'";
            }
            $pdo->query($sql);
        }
    }
    
    public function saveRel(Rel $rel)
    {
        $pdo = $this->_config->getConnection();
        $sql = 'replace into Rel(subjSign, objSign, typeSign, value) values(?, ?, ?, ?)';
        $statement = $pdo->prepare($sql);
        $result = $statement->execute($rel->toArray());
    }
    
    public function saveRels($rels)
    {
        $pdo = $this->_config->getConnection();
        foreach ($rels as $rel) {
            $this->saveRel($rel);
        }
    }
}