<?php
class Dicky_Word
{
    public $sign;
    public $spelling;
    
    public function __construct($sign, $spelling)
    {
        $this->sign = $sign;
        $this->spelling = $spelling;
    }
    
    /**
     * @param assoc $row
     * @return Dicky_Word
     */
    public static function fromArray($row)
    {
        return new Dicky_Word($row['sign'], $row['spelling']);
    }
}