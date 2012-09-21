<?php

require_once dirname(__FILE__) . '/Config.php';
require_once dirname(__FILE__) . '/Rel.php';

class Dicky_RelSet implements Iterator
{
    protected $_raw;
    public function __construct($raw = null)
    {
        $this->_raw = $raw;
    }
    
    public function getByTypeSign($typeSign)
    {
        foreach ($this->_raw as $row) {
            if ($row['typeSign'] == $typeSign) {
                return $this->_makeRel($row);
            }
        }
        return null;
    }
    
    protected function _makeRel($row = null)
    {
        if (null === $this->_raw) {
            return null;
        }
        return new Dicky_Rel($row['subjSign'], $row['objSign'], $row['typeSign']);
    }
    
    /**
     * @return Dicky_Rel
     */
    public function current()
    {
        return $this->_makeRel(current($this->_raw));
    }
    public function key ()
    {
        return key($this->_raw);
    }
    public function next()
    {
        next($this->_raw);
    }
    public function rewind()
    {
        reset($this->_raw);
    }
    public function valid()
    {
        return is_array($this->_raw) && current($this->_raw) !== false;
    }
}