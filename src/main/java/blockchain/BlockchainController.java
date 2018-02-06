package blockchain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BlockchainController {

    @Autowired
    BlockchainManager blockchainManager;

    @RequestMapping(path = "/blockchain", method = RequestMethod.GET)
    public List<Block> getBlockchain() {
        return blockchainManager.getBlockchain();
    }

    @RequestMapping(path = "/blockchain", method = RequestMethod.POST)
    public List<Block> addNewBlock(Data data) {
        Block newBlock = blockchainManager.generateNextBlock(data);
        blockchainManager.addBlock(newBlock);
        System.out.println("New block added: " + newBlock.toString());
        //broadcast();
        return blockchainManager.getBlockchain();
    }

}
