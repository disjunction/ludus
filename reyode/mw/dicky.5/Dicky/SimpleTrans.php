<?php
namespace Dicky;

class SimpleTrans
{
    public $direction;
    public $objSign;
    public $objSpelling;
    public $subjSign;
    public $subjSpelling;
    
    // helper props. for processing caching
    
    public $objRels;
    public $subjRels;
    
    protected static $_map = array(
		"а" => "a",
        "б" => "b",
        "в" => "v",
        "г" => "g",
        "д" => "d",
        "е" => "e",
        "ё" => "jo",
        "ж" => "zh",
        "з" => "z",
        "и" => "i",
        "й" => "j",
        "к" => "k",
        "л" => "l",
        "м" => "m",
        "н" => "n",
        "о" => "o",
        "п" => "p",
        "р" => "r",
        "с" => "s",
        "т" => "t",
        "у" => "u",
        "ф" => "f",
        "х" => "h",
        "ц" => "ts",
        "ч" => "ch",
        "ш" => "sh",
        "щ" => "sch",
        "ь" => "",
        "ы" => "y",
        "ъ" => "",
        "э" => "e",
        "ю" => "ju",
        "я" => "ja",

        "ü" => "ue",
        "ö" => "oe",
        "ä" => "ae",
        "ß" => "ss",
        "Ü" => "Ue",
        "Ö" => "Oe",
        "Ä" => "Ae");
    
    public static function spellingToSign($spelling)
    {
        $spelling = trim($spelling);
        $newSpelling = '';
        for ($i = 0; $i < mb_strlen($spelling, 'utf-8'); $i++) {
            $newSpelling .= isset(self::$_map[$key = mb_substr($spelling, $i, 1, 'utf-8')])?
                self::$_map[$key] : $key;
        }
        return $newSpelling;
    }
    
    public function normalize()
    {
        if (null == $this->subjSign) {
            $this->subjSign = substr($this->direction, 0, 2) . self::spellingToSign($this->subjSpelling);
        }
        if (null == $this->objSign) {
            $this->objSign = substr($this->direction, 2) . self::spellingToSign($this->objSpelling);
        }
    }
    
    public function toArray()
    {
        return array($this->direction,
                     $this->subjSign,
                     $this->subjSpelling,
                     $this->objSign,
                     $this->objSpelling);
    }
}