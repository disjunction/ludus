<?php
// namespace Dicky;

require_once dirname(__FILE__) . '/Config.php';

class Dicky_Config
{
    public $host = 'localhost';
    public $user;
    public $password;
    public $dbname;
    
    protected $_pdo;
    
    /**
     * @return PDO
     */
    public function getConnection()
    {
        if (!isset($this->_pdo)) {
            $dsn = "mysql:dbname={$this->dbname};host={$this->host}";            
            $this->_pdo = new PDO($dsn, $this->user, $this->password, array(PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES utf8") );
            $this->_pdo->query('SET CHARSET utf8');
            $this->_pdo->query('SET SET CHARACTER SET utf8');
        }
        return $this->_pdo;
    }
}
