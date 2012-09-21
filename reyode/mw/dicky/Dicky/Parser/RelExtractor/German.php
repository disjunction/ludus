<?php
class Dicky_Parser_RelExtractor_German extends Dicky_Parser_RelExtractor_Common
{
    protected function _extractSingleTag($tag)
    {
        parent::_extractSingleTag($tag);
        if ('s' == $tag) {
            $this->_curRels[] = new Dicky_Rel($this->_curSign, 'desein', 'l.conjugation');
        }
        return null;
    }
}