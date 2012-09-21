<?php
namespace Dicky;

class Rel
{
    public $subSign;
    public $objSign;
    public $typeSign;
    public $value = 1;
    
    public function __construct($subj = null, $obj = null, $type = null)
    {
        $this->subSign = $subj;
        $this->objSign = $obj;
        $this->typeSign = $type;
    }
    
    public function toArray()
    {
        return array($this->subSign, $this->objSign, $this->typeSign, $this->value);
    }
}