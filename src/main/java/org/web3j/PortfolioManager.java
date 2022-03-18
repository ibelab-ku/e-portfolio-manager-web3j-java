package org.web3j;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class PortfolioManager extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610dd8806100206000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c80637602cda9146100675780638ae50d8c14610090578063bf58eab9146100a3578063d6a71974146100b8578063ec77f830146100cb578063fa3118d5146100dc575b600080fd5b61007a610075366004610a5e565b610102565b6040516100879190610c3a565b60405180910390f35b61007a61009e366004610a5e565b6101b5565b6100b66100b1366004610a99565b6101e3565b005b61007a6100c6366004610bda565b610442565b600154604051908152602001610087565b6100ef6100ea366004610a5e565b6104ee565b6040516100879796959493929190610c97565b60606000826040516101149190610c1e565b9081526020016040518091039020600701805461013090610d51565b80601f016020809104026020016040519081016040528092919081815260200182805461015c90610d51565b80156101a95780601f1061017e576101008083540402835291602001916101a9565b820191906000526020600020905b81548152906001019060200180831161018c57829003601f168201915b50505050509050919050565b60606000826040516101c79190610c1e565b9081526020016040518091039020600801805461013090610d51565b8860008a6040516101f49190610c1e565b9081526020016040518091039020600001908051906020019061021892919061093e565b508760008a60405161022a9190610c1e565b9081526020016040518091039020600101908051906020019061024e92919061093e565b508660008a6040516102609190610c1e565b9081526020016040518091039020600201908051906020019061028492919061093e565b508560008a6040516102969190610c1e565b908152602001604051809103902060030190805190602001906102ba92919061093e565b508460008a6040516102cc9190610c1e565b908152602001604051809103902060040190805190602001906102f092919061093e565b508360008a6040516103029190610c1e565b9081526020016040518091039020600501908051906020019061032692919061093e565b508260008a6040516103389190610c1e565b9081526020016040518091039020600601819055508160008a60405161035e9190610c1e565b9081526020016040518091039020600701908051906020019061038292919061093e565b508060008a6040516103949190610c1e565b908152602001604051809103902060080190805190602001906103b892919061093e565b5060018054808201825560009190915289516103fb917fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf6019060208c019061093e565b507f940c345badda4e20e0698f32204fb8deb81328c15b449fbca415fb536c17e3fa89878760405161042f93929190610c54565b60405180910390a1505050505050505050565b6001818154811061045257600080fd5b90600052602060002001600091509050805461046d90610d51565b80601f016020809104026020016040519081016040528092919081815260200182805461049990610d51565b80156104e65780601f106104bb576101008083540402835291602001916104e6565b820191906000526020600020905b8154815290600101906020018083116104c957829003601f168201915b505050505081565b606080606080606080600080886040516105089190610c1e565b90815260405190819003602001812090600090610526908b90610c1e565b908152602001604051809103902060010160008a6040516105479190610c1e565b908152602001604051809103902060020160008b6040516105689190610c1e565b908152602001604051809103902060030160008c6040516105899190610c1e565b908152602001604051809103902060040160008d6040516105aa9190610c1e565b908152602001604051809103902060070160008e6040516105cb9190610c1e565b9081526020016040518091039020600601548680546105e990610d51565b80601f016020809104026020016040519081016040528092919081815260200182805461061590610d51565b80156106625780601f1061063757610100808354040283529160200191610662565b820191906000526020600020905b81548152906001019060200180831161064557829003601f168201915b5050505050965085805461067590610d51565b80601f01602080910402602001604051908101604052809291908181526020018280546106a190610d51565b80156106ee5780601f106106c3576101008083540402835291602001916106ee565b820191906000526020600020905b8154815290600101906020018083116106d157829003601f168201915b5050505050955084805461070190610d51565b80601f016020809104026020016040519081016040528092919081815260200182805461072d90610d51565b801561077a5780601f1061074f5761010080835404028352916020019161077a565b820191906000526020600020905b81548152906001019060200180831161075d57829003601f168201915b5050505050945083805461078d90610d51565b80601f01602080910402602001604051908101604052809291908181526020018280546107b990610d51565b80156108065780601f106107db57610100808354040283529160200191610806565b820191906000526020600020905b8154815290600101906020018083116107e957829003601f168201915b5050505050935082805461081990610d51565b80601f016020809104026020016040519081016040528092919081815260200182805461084590610d51565b80156108925780601f1061086757610100808354040283529160200191610892565b820191906000526020600020905b81548152906001019060200180831161087557829003601f168201915b505050505092508180546108a590610d51565b80601f01602080910402602001604051908101604052809291908181526020018280546108d190610d51565b801561091e5780601f106108f35761010080835404028352916020019161091e565b820191906000526020600020905b81548152906001019060200180831161090157829003601f168201915b505050505091509650965096509650965096509650919395979092949650565b82805461094a90610d51565b90600052602060002090601f01602090048101928261096c57600085556109b2565b82601f1061098557805160ff19168380011785556109b2565b828001600101855582156109b2579182015b828111156109b2578251825591602001919060010190610997565b506109be9291506109c2565b5090565b5b808211156109be57600081556001016109c3565b600082601f8301126109e7578081fd5b813567ffffffffffffffff80821115610a0257610a02610d8c565b604051601f8301601f19908116603f01168101908282118183101715610a2a57610a2a610d8c565b81604052838152866020858801011115610a42578485fd5b8360208701602083013792830160200193909352509392505050565b600060208284031215610a6f578081fd5b813567ffffffffffffffff811115610a85578182fd5b610a91848285016109d7565b949350505050565b60008060008060008060008060006101208a8c031215610ab7578485fd5b893567ffffffffffffffff80821115610ace578687fd5b610ada8d838e016109d7565b9a5060208c0135915080821115610aef578687fd5b610afb8d838e016109d7565b995060408c0135915080821115610b10578687fd5b610b1c8d838e016109d7565b985060608c0135915080821115610b31578687fd5b610b3d8d838e016109d7565b975060808c0135915080821115610b52578687fd5b610b5e8d838e016109d7565b965060a08c0135915080821115610b73578586fd5b610b7f8d838e016109d7565b955060c08c0135945060e08c0135915080821115610b9b578384fd5b610ba78d838e016109d7565b93506101008c0135915080821115610bbd578283fd5b50610bca8c828d016109d7565b9150509295985092959850929598565b600060208284031215610beb578081fd5b5035919050565b60008151808452610c0a816020860160208601610d21565b601f01601f19169290920160200192915050565b60008251610c30818460208701610d21565b9190910192915050565b602081526000610c4d6020830184610bf2565b9392505050565b606081526000610c676060830186610bf2565b8281036020840152610c798186610bf2565b90508281036040840152610c8d8185610bf2565b9695505050505050565b60e081526000610caa60e083018a610bf2565b8281036020840152610cbc818a610bf2565b90508281036040840152610cd08189610bf2565b90508281036060840152610ce48188610bf2565b90508281036080840152610cf88187610bf2565b905082810360a0840152610d0c8186610bf2565b9150508260c083015298975050505050505050565b60005b83811015610d3c578181015183820152602001610d24565b83811115610d4b576000848401525b50505050565b600181811c90821680610d6557607f821691505b60208210811415610d8657634e487b7160e01b600052602260045260246000fd5b50919050565b634e487b7160e01b600052604160045260246000fdfea2646970667358221220e963defe7d9de7f93d6cdfab596ba5f205a3fd1e05e8edc56fc1983e834156b264736f6c63430008040033";

    public static final String FUNC_PORTFOLIOLIST = "PortfolioList";

    public static final String FUNC_COUNTPORTFOLIO = "countPortfolio";

    public static final String FUNC_GETPORTFOLIOINFO = "getPortfolioInfo";

    public static final String FUNC_GETPORTFOLIOSTATUS = "getPortfolioStatus";

    public static final String FUNC_GETPORTFOLIOURL = "getPortfolioURL";

    public static final String FUNC_NEWPORTFOLIO = "newPortfolio";

    public static final Event NEWPORTFOLIOCREATED_EVENT = new Event("newPortfolioCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event PF_CERTIFICATEISSUED_EVENT = new Event("pf_CertificateIssued", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected PortfolioManager(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PortfolioManager(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PortfolioManager(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PortfolioManager(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<NewPortfolioCreatedEventResponse> getNewPortfolioCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWPORTFOLIOCREATED_EVENT, transactionReceipt);
        ArrayList<NewPortfolioCreatedEventResponse> responses = new ArrayList<NewPortfolioCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewPortfolioCreatedEventResponse typedResponse = new NewPortfolioCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._pf_no = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._reg_name = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._professor_name = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewPortfolioCreatedEventResponse> newPortfolioCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, NewPortfolioCreatedEventResponse>() {
            @Override
            public NewPortfolioCreatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWPORTFOLIOCREATED_EVENT, log);
                NewPortfolioCreatedEventResponse typedResponse = new NewPortfolioCreatedEventResponse();
                typedResponse.log = log;
                typedResponse._pf_no = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._reg_name = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._professor_name = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NewPortfolioCreatedEventResponse> newPortfolioCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWPORTFOLIOCREATED_EVENT));
        return newPortfolioCreatedEventFlowable(filter);
    }

    public List<Pf_CertificateIssuedEventResponse> getPf_CertificateIssuedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PF_CERTIFICATEISSUED_EVENT, transactionReceipt);
        ArrayList<Pf_CertificateIssuedEventResponse> responses = new ArrayList<Pf_CertificateIssuedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Pf_CertificateIssuedEventResponse typedResponse = new Pf_CertificateIssuedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._cert_no = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._pf_no = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._reg_name = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._professor_name = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse._score = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<Pf_CertificateIssuedEventResponse> pf_CertificateIssuedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, Pf_CertificateIssuedEventResponse>() {
            @Override
            public Pf_CertificateIssuedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PF_CERTIFICATEISSUED_EVENT, log);
                Pf_CertificateIssuedEventResponse typedResponse = new Pf_CertificateIssuedEventResponse();
                typedResponse.log = log;
                typedResponse._cert_no = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._pf_no = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._reg_name = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._professor_name = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse._score = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<Pf_CertificateIssuedEventResponse> pf_CertificateIssuedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PF_CERTIFICATEISSUED_EVENT));
        return pf_CertificateIssuedEventFlowable(filter);
    }

    public RemoteFunctionCall<String> PortfolioList(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PORTFOLIOLIST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> countPortfolio() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_COUNTPORTFOLIO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple7<String, String, String, String, String, String, BigInteger>> getPortfolioInfo(String _pf_no) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETPORTFOLIOINFO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_pf_no)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple7<String, String, String, String, String, String, BigInteger>>(function,
                new Callable<Tuple7<String, String, String, String, String, String, BigInteger>>() {
                    @Override
                    public Tuple7<String, String, String, String, String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<String, String, String, String, String, String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> getPortfolioStatus(String _pf_no) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETPORTFOLIOSTATUS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_pf_no)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getPortfolioURL(String _pf_no) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETPORTFOLIOURL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_pf_no)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> newPortfolio(String _pf_no, String _pf_name, String _reg_dtime, String _reg_name, String _professor_name, String _professor_pk, String _score, String _pf_status, String _pf_url) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_NEWPORTFOLIO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_pf_no), 
                new org.web3j.abi.datatypes.Utf8String(_pf_name), 
                new org.web3j.abi.datatypes.Utf8String(_reg_dtime), 
                new org.web3j.abi.datatypes.Utf8String(_reg_name), 
                new org.web3j.abi.datatypes.Utf8String(_professor_name), 
                new org.web3j.abi.datatypes.Utf8String(_professor_pk), 
                new org.web3j.abi.datatypes.generated.Uint256(Long.parseLong(_score)),
                new org.web3j.abi.datatypes.Utf8String(_pf_status), 
                new org.web3j.abi.datatypes.Utf8String(_pf_url)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static PortfolioManager load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PortfolioManager(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PortfolioManager load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PortfolioManager(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PortfolioManager load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PortfolioManager(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PortfolioManager load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PortfolioManager(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PortfolioManager> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PortfolioManager.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PortfolioManager> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PortfolioManager.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<PortfolioManager> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PortfolioManager.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PortfolioManager> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PortfolioManager.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class NewPortfolioCreatedEventResponse extends BaseEventResponse {
        public String _pf_no;

        public String _reg_name;

        public String _professor_name;
    }

    public static class Pf_CertificateIssuedEventResponse extends BaseEventResponse {
        public String _cert_no;

        public String _pf_no;

        public String _reg_name;

        public String _professor_name;

        public BigInteger _score;
    }
}
