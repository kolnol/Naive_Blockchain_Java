package blockchain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity addNewBlock(@RequestBody Data data) {
        Block newBlock = blockchainManager.generateNextBlock(data);
        if (blockchainManager.addBlock(newBlock)) {
            System.out.println("New block added: " + newBlock.toString());
            //broadcast();
            return ResponseEntity.ok(blockchainManager.getBlockchain());
        } else {
            return new ResponseEntity<>("Not valid Block."
                    , HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
