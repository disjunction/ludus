<?php
namespace Dicky;

class Parser
{
    protected $_from = '--';
    protected $_to = '--';
    protected $_state = 'scan';
    
    /**
     * @var SimpleTrans
     */
    protected $_trans = array();
    
    public function getLangCode($name)
    {
        switch (strtolower($name)) {
            case 'german':
                return 'de';
            default:
                return substr(strtolower($name), 0, 2);
        }
    }
    
    public function parseSpelling($spelling, $lang)
    {
        $res['spelling'] = self::normalizeSpeelling($spelling);
        $res['sign'] = $lang . SimpleTrans::spellingToSign($res['spelling']);
        $res['rels'] = self::extractRel($res['sign'], $spelling);
        return $res;
    }
    
    public function parseLine($str)
    {
        $r = preg_match('/^(=+)([^=]+)=+\s*$/', $str, $matches);
        if ($r) {
            $titlePrefix = $matches[1];
            $title = trim($matches[2]);
            if (preg_match('/^-(.+)-$/', $title, $matches)) {
                $this->_state = 'dictionary';
                return $titlePrefix . $matches[1] . $titlePrefix;
            } else {
                $this->_state = 'scan';
                return $str;
            }
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
        
        if ($this->_state == 'dictionary') {
            $r = preg_match('/^\*+\s*(.+)$/', $str, $matches);
            if ($r) {
                $parts = explode('--', $matches[1]);
                if (count($parts) != 2) {
                    return $str;
                }
                $o = new SimpleTrans();
                
                $subj = self::parseSpelling($parts[0], $this->_from);
                $obj = self::parseSpelling($parts[1], $this->_to);
                
                $o->subjSign = $subj['sign'];
                $o->subjSpelling = $subj['spelling'];
                $o->subjRels = $subj['rels'];
                $o->objSign = $obj['sign'];
                $o->objSpelling = $obj['spelling'];
                $o->objRels = $obj['rels'];
                $o->direction = $this->_from . $this->_to;
                
                $this->_trans[] = $o;
                
                return $o;
            }
        }
        
        return $str;
    }
    
    public static function normalizeSpeelling($spelling)
    {
        return trim(preg_replace('/{.+}/', '', $spelling));
    }
    
    public function extractRel($sign, $spelling)
    {
        $r = preg_match_all('/{(.+)}/U', $spelling, $matches, PREG_SET_ORDER);
        if (!$r) return false;
    
        $rels = array();
        foreach ($matches as $match) {
            $parts = explode(':', $match[1]);
            $rel = null;
            if (1 == count($parts)) {
                switch ($match[1]) {
                    case 'f':
                        $rel = new Rel($sign, 'l.f', 'l.gender');
                        break;
                    case 'm':
                        $rel = new Rel($sign, 'l.m', 'l.gender');
                        break;
                    case 'n':
                        $rel = new Rel($sign, 'l.n', 'l.gender');
                        break;
                }
            } else {
                switch ($parts[0]) {
                    case 'plr':
                        $rel = new Rel($sign, 'l.' . $match[1], 'l.plural_rule');
                        break;
                    case 'pl':
                        $rel = new Rel($sign, $lang . self::spellingToSign($match[1]), 'l.plural');
                        break;
                }
            }
            $rel && $rels[] = $rel;
        }
        return $rels;
    }
    
    public function transformLine($str)
    {
        $str = $this->parseLine($str);
        
        if ($str instanceof SimpleTrans) {
            return '{{Translation|' . $str->subjSpelling . '|' . $str->objSpelling . '}}';
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
    
    public function parseText($str)
    {
        $lines = explode("\n", $str);
        foreach ($lines as $line) {
            $this->parseLine($line);
        }
    }
    
    public function getTranslations()
    {
        return $this->_trans;
    }
}