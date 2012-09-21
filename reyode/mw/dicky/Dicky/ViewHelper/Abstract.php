<?php
class Dicky_ViewHelper_Abstract
{
    /**
     * @var Dicky_Config
     */
    protected $_config;
    public function __construct($config = null)
    {
        $this->setConfig($config);
    }
    public function setConfig(Dicky_Config $config)
    {
        $this->_config = $config;
    }
}