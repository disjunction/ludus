<?php
// namespace Dicky;

require_once dirname(__FILE__) . '/Parser/RelExtractor/Common.php';
require_once dirname(__FILE__) . '/Parser/RelExtractor/German.php';

class Dicky_Parser
{
    protected $_from = '--';
    protected $_to = '--';
    
    /**
     * possible states:
     * * scan
     * * dictionary
     */
    protected $_state = 'scan';
    
    /**
     * @var Dicky_SimpleTrans
     */
    protected $_trans = array();
    
    /**
     * @var Dicky_Parser_RelExctractor_Common
     */
    protected $_extractorCommon;

    /**
     * multiton of extensions of Common RelExtractor for other languages
     * 
     * the strings will be replaced by the corresponding extractor on request
     * @var Dicky_Parser_RelExctractor_Common[]
     */
    protected $_extractors = array('de' => 'German');
    
    
    public function __construct()
    {
        $this->_extractorCommon = new Dicky_Parser_RelExtractor_Common();
    }

    /**
     * @param string $lang
     * @return Dicky_Parser_RelExtractor_Common
     */
    protected function _getRelExtractor($lang)
    {
        //phpinfo();
        //die();
        if (isset($this->_extractors[$lang])) {
            if (!is_object($this->_extractors[$lang])) {
                require_once dirname(__FILE__) . '/Parser/RelExtractor/' . $this->_extractors[$lang] . '.php';
                $className = 'Dicky_Parser_RelExtractor_' . $this->_extractors[$lang];
                $this->_extractors[$lang] = new $className;
            }
            return $this->_extractors[$lang];
        } else {
            return $this->_extractorCommon;
        }
    } 
    
    public function getLangCode($name)
    {
        switch (strtolower($name)) {
            case 'german':
                return 'de';
            default:
                return substr(strtolower($name), 0, 2);
        }
    }
    
    public function parseSpelling($spelling, $lang, $sign = null)
    {
        $res['spelling'] = self::normalizeSpeelling($spelling);
        $res['sign'] = $sign? $sign : $lang . Dicky_SimpleTrans::spellingToSign($res['spelling']);
        $res['rels'] = self::extractRel($res['sign'], $spelling);
        return $res;
    }
    
    /**
     * @param unknown_type $str
     * @return string|Dicky_SimpleTrans
     */
    public function parseLine(&$source, $transform = true)
    {
        $str = $source;
        $r = preg_match('/^(=+)([^=]+)=+\s*$/', $str, $matches);
        if ($r) {
            $titlePrefix = $matches[1];
            $title = trim($matches[2]);
            if (preg_match('/^-(.+)-$/', $title, $matches)) {
                $this->_state = 'dictionary';
                $str = $titlePrefix . $matches[1] . $titlePrefix . "\n<dl>";
            } else {
                $this->_state = 'scan';
            }
            return $str;
        }

        $r = preg_match_all('/\[\[Translate (from|to)::(.+)\]\]/U', $str, $matches);
        if ($r) {
            for ($i = 0; $i<count($matches[0]); $i++) {
                if ('from' == $matches[1][$i]) {
                    $this->_from = $this->getLangCode($matches[2][$i]);
                }
                if ('to' == $matches[1][$i]) {
                    $this->_to = $this->getLangCode($matches[2][$i]);
                }
            }
            return $str;
        }
        
        if ('dictionary' == $this->_state) {
            
            $subjSign = $objSign = null;
            
            // try parse explicit signs after '#' char
            if (false !== ($posHash = strpos($str, '#'))) {
                $macro = substr($str, $posHash);
                if (preg_match('/([^\s]+)\s*->\s*([^\s]+)/', $str, $matches)) {
                    $subjSign = $matches[1];
                    $objSign = $matches[2];
                }
                $str = substr($str, 0, $posHash);
            }
            
            $r = preg_match('/^\*+\s*(.+)$/', $str, $matches);
            if ($r) {
                $parts = explode('--', $matches[1]);
                if (count($parts) != 2) {
                    return $str;
                }
                $o = new Dicky_SimpleTrans();
                
                $subj = self::parseSpelling($parts[0], $this->_from, $subjSign);
                $obj = self::parseSpelling($parts[1], $this->_to, $objSign);
                
                $o->subjSign = $subjSign? $subjSign : $subj['sign'];
                $o->subjSpelling = $subj['spelling'];
                $o->subjRels = $subj['rels'];
                $o->objSign = $objSign? $objSign : $obj['sign'];
                $o->objSpelling = $obj['spelling'];
                $o->objRels = $obj['rels'];
                $o->direction = $this->_from . $this->_to;
                
                $this->_trans[] = $o;
                
                // if text is transformable, then add sign hints after '#'
                $transform and $source = trim($str) . '  # ' . $o->subjSign . '->' . $o->objSign;
                
                return $o;
            }
        }
        
        return $str;
    }
    
    /**
     * removes {f}, {m}, {vi} etc. from spelling
     * @param string $spelling
     * @return string
     */
    public static function normalizeSpeelling($spelling)
    {
        return trim(preg_replace('/{.+}/', '', $spelling));
    }
    
    public function extractRel($sign, $spelling)
    {
        $lang = substr($sign, 0, 2);
        return $this->_getRelExtractor($lang)->extract($sign, $spelling);
    }
    
    public function transformLine($str)
    {
        $str = $this->parseLine($str);
        
        if ($str instanceof Dicky_SimpleTrans) {
            return '<translation subj="' . $str->subjSign. '" obj="' . $str->objSign . '" />';
        }
        
        return $str;
    }
    
    public function transformText($str)
    {
        if (isset($_GET['action']) && 'edit' == $_GET['action']) {
            return $str;
        }
        
        $lines = explode("\n", $str);
        $text = '';
        foreach ($lines as $line) {
            $text .= $this->transformLine($line) . "\n";
        }
        
        return $text;
    }
    
    public function parseText(&$str, $transform = true)
    {
        
        $newText = '';
        $lines = explode("\n", $str);
        foreach ($lines as $line) {
            $this->parseLine($line, $transform);
            $newText .= $line . "\n";
        }
        
        return $newText;
    }
    
    public function getTranslations()
    {
        return $this->_trans;
    }
}
