<?php
// namespace Dicky;

require_once dirname(__FILE__) . '/RelSet.php';
require_once dirname(__FILE__) . '/Rel.php';
require_once dirname(__FILE__) . '/AbstractManager.php';

class Dicky_RelManager extends Dicky_AbstractManager
{   
    public function deletePageRels($titleSign)
    {
        $pdo = $this->_getCon();
        
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
    
    public function saveRel(Dicky_Rel $rel)
    {
        $sql = 'replace into Rel(subjSign, objSign, typeSign, value) values(?, ?, ?, ?)';
        $statement = $this->_getCon()->prepare($sql);
        $result = $statement->execute($rel->toArray());
    }
    
    public function saveRels($rels)
    {
        $pdo = $this->_config->getConnection();
        foreach ($rels as $rel) {
            $this->saveRel($rel);
        }
    }
    
    /**
     * @param string $subjSign
     * @return Dicky_RelSet
     */
    public function getRelSetBySubjSign($subjSign)
    {
        $statement = $this->_getCon()->prepare("select * from Rel where subjSign=?");
        $statement->execute(array($subjSign));
        return new Dicky_RelSet($statement->fetchAll(PDO::FETCH_ASSOC));
    }
}
