<?php
require_once dirname(__FILE__) . '/Config.php';
require_once dirname(__FILE__) . '/Rel.php';

class Dicky_AbstractManager
{
    /**
     * @var Dicky_Config
     */
    protected $_config;

    public function setConfig(Dicky_Config $config)
    {
        $this->_config = $config;
    }
    
    /**
     * @return PDO
     */
    protected function _getCon()
    {
        return $this->_config->getConnection();
    }
    
    protected function _getOneRow($sql, $params = array())
    {
        $statement = $this->_getCon()->prepare($sql);
        $statement->execute($params);
        return $statement->fetch(PDO::FETCH_ASSOC);
    }
}