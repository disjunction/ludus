<?php
// namespace Dicky;

class Dicky_Rel
{
    public $subSign;
    public $objSign;
    public $typeSign;
    public $value;
    
    public function __construct($subj = null, $obj = null, $type = null, $value = 1)
    {
        $this->subSign = $subj;
        $this->objSign = $obj;
        $this->typeSign = $type;
        $this->value = $value;
    }
    
    public function toArray()
    {
        return array($this->subSign, $this->objSign, $this->typeSign, $this->value);
    }
}
