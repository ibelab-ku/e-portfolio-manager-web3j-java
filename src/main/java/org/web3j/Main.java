package org.web3j;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

public class Main {

    private static final String CONTRACT_ADDRESS = "0x2be55e8ae2afc15d9be961f2fe22dda1667c85e2"; 

    private static final String PRIVATE_KEY ="92ae49a67318afb5661d6169cbe872cbc459c660fa09b80dcf2bc8ab522a68d7";  

    private static final BigInteger GAS_PRICE = BigInteger.valueOf(0);

    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);
	
    public static void main(String[] args) throws Exception{

        //Web3j client initialization
        System.out.println("Connecting to the Ethereum node ... ");
        Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:7545"));  
        System.out.println("Successfully connected to the Ethereum client node!");

        // Credential used for accessing to the portfolio contract and creating new portfolio
        Credentials credentials = Credentials.create(PRIVATE_KEY);

        // web3_clientVersion returns the current client version.
        Web3ClientVersion clientVersion = web3.web3ClientVersion().send();

        // eth_blockNumber returns the number of most recent block.
        EthBlockNumber blockNumber = web3.ethBlockNumber().send();

       // Printout the client version, last block number and gas price
        System.out.println("================= Client Node Details ===================");
        System.out.println("Client version: " + clientVersion.getWeb3ClientVersion());
        System.out.println("Block number: " + blockNumber.getBlockNumber());

        try {
        // Create a new portfolio record
        PortfolioManager createPortfolio  = PortfolioManager.load(CONTRACT_ADDRESS, web3, credentials, GAS_PRICE, GAS_LIMIT);
        TransactionReceipt transactionReceipt = createPortfolio.newPortfolio(
                "02",
                "Portfolio Management",
                "2021-08-11 20:04:20",
                "Mpyana Merlec",
                "Hoh Peter In",
                "5293dd66030618da2d529db061ee06be24ddeb344262048bbb3588ec408f9d5c",
                "98",
                "Completed",
                "https://github.com/ibelab-ku/e-portfolio-manager-web3j-javar").send();

        System.out.println("================= Transaction Details ===================");
        String  txHash = transactionReceipt.getTransactionHash();
        System.out.println("Transaction hash: " + txHash);
        System.out.println("Tx Block Number: " + transactionReceipt.getBlockNumber());
        System.out.println("=========================================================");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
