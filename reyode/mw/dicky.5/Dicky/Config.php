<?php
namespace Dicky;

class Config
{
    public $host = 'localhost';
    public $user;
    public $password;
    public $dbname;
    
    protected $_pdo;
    
    /**
     * @return \PDO
     */
    public function getConnection()
    {
        if (!isset($this->_pdo)) {
            $dsn = "mysql:dbname={$this->dbname};host={$this->host}";            
            $this->_pdo = new \PDO($dsn, $this->user, $this->password);
            $this->_pdo->query('SET CHARSET utf8');
        }
        return $this->_pdo;
    }
}