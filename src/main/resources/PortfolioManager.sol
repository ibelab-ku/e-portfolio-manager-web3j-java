
// SPDX-License-Identifier: GPL-3.0
pragma solidity ^0.8.4;

/*
*@Title :  e-Portfolio Manager Smart Contract 
*   	   Developed by Merlec Mpyana M. 
* 		   Email: mlecjm(a)korea.ac.kr 
*		   Intelligent Blockchain Engineering Lab, Korea University
*/

contract PortfolioManager{
    
    event newPortfolioCreated(string _pf_no, string _reg_name, string _professor_name);
    event pf_CertificateIssued(string _cert_no, string _pf_no, string _reg_name, 
                               string _professor_name, uint256 _score);
    
    struct Portfolio{
        string pf_no;
        string pf_name;
        string reg_dtime;
        string reg_name;
        string professor_name;
        string professor_pk;
        uint256 score;
        string pf_status;
        string pf_url;
        
    }
    
    mapping(string => Portfolio) Portfolios;
    string[] public PortfolioList;
    

    function newPortfolio(
        string memory _pf_no,
        string memory _pf_name,
        string memory _reg_dtime,
        string memory _reg_name,
        string memory _professor_name,
        string memory _professor_pk,
        uint256 _score,
        string memory _pf_status,
        string memory _pf_url) 
        public{
            Portfolios[_pf_no].pf_no = _pf_no;
            Portfolios[_pf_no].pf_name = _pf_name;
            Portfolios[_pf_no].reg_dtime = _reg_dtime;
            Portfolios[_pf_no].reg_name = _reg_name;
            Portfolios[_pf_no].professor_name = _professor_name;
            Portfolios[_pf_no].professor_pk = _professor_pk;
            Portfolios[_pf_no].score = _score;
            Portfolios[_pf_no].pf_status = _pf_status;
            Portfolios[_pf_no].pf_url = _pf_url;
        
        PortfolioList.push(_pf_no);
        
        emit newPortfolioCreated(_pf_no, _reg_name, _professor_name);
    }
    
    function countPortfolio() view public returns(uint){
        return PortfolioList.length;
    }
    
    
    function getPortfolioInfo(string memory _pf_no)
        view
        public
        returns(string memory, string memory, string memory, string memory, string memory, string memory, uint256)
        {
            return(Portfolios[_pf_no].pf_no,
            Portfolios[_pf_no].pf_name,
            Portfolios[_pf_no].reg_dtime,
            Portfolios[_pf_no].reg_name,
            Portfolios[_pf_no].professor_name,
            Portfolios[_pf_no].pf_status,
            Portfolios[_pf_no].score );
    }
        
   
    function getPortfolioURL(string memory _pf_no) view public 
        returns(string memory){ return(Portfolios[_pf_no].pf_url);
    }
    
    
    function getPortfolioStatus(string memory _pf_no) view public 
        returns(string memory){ return(Portfolios[_pf_no].pf_status);
    }

}
