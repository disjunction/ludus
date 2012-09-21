<?php
class Dicky_Parser_RelExtractor_Common
{
    /**
     * stored here to avoid passing in params all the time
     * @var string
     */
    protected $_curSign;
    
    /**
     * @var Dicky_Rel[]
     */
    protected $_curRels;
    
    protected function _extractSingleTag($tag)
    {
        // gender markers: masculinum, femininum, neutrum
        if (in_array($tag, array('m', 'f', 'n'))) {
            $this->_curRels[] = new Dicky_Rel($this->_curSign, 'l.' . $tag, 'l.gender');
            return;
        }
        // checks markers such as {vi*} = intrasitive, irregular
        if (preg_match('/^v([it])(\*?)$/', $tag, $matches)) {
            $type = 't' === $matches[1]? 'trasitive' : 'intransitive';
            $this->_curRels[] = new Dicky_Rel($this->_curSign, 'l.' . $type, 'l.transitivity');
            $regularity = '*' === $matches[2]? 'irregular' : 'regular';
            $this->_curRels[] = new Dicky_Rel($this->_curSign, 'l.' . $regularity, 'l.regularity');
            return;
        }
        return null;
    }
    
    protected function _extractColonSeparatedTag($parts)
    {
        switch ($parts[0]) {
            // plural rule provided - the displayed form should be looked up in word table
            case 'plr':
                $this->_curRels[] = new Dicky_Rel($this->_curSign, 
                    Dicky_SimpleTrans::spellingToSign('l.' . $parts[1]),
                    'l.plural_rule');
                break;
            // plural is given in spelling form, same language assumed
            case 'pl':
                $this->_curRels[] = new Dicky_Rel($this->_curSign, 
                    substr($this->_curSign, 0, 2) . Dicky_SimpleTrans::spellingToSign($parts[1]),
                    'l.plural');
                break;
            // explicit link to plural sign
            case 'pls':
                $this->_curRels[] = new Dicky_Rel($this->_curSign,
                    Dicky_SimpleTrans::spellingToSign($parts[1]),
                    'l.plural');
                break;
        }
        return null;
    }

    /**
     * @param string $sign
     * @param string $spelling
     * @return Dicky_Rel[]
     */
    public function extract($sign, $spelling)
    {
        $this->_curRels = array();
        $this->_curSign = $sign;
        
        $r = preg_match_all('/{(.+)}/U', $spelling, $matches, PREG_SET_ORDER);
        if (!$r) return array();
    
        $rels = array();
        foreach ($matches as $match) {
            $parts = explode(':', $match[1]);
            1 == count($parts)? $this->_extractSingleTag($match[1]) : $this->_extractColonSeparatedTag($parts);
        }
        return $this->_curRels;
    }
}