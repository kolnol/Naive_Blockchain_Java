package blockchain;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class BlockchainManager {
    private List<Block> blockchain = new ArrayList<>();

    public BlockchainManager() {
        this.blockchain.add(getGenesisBlock());
        System.out.println("Creating Manager with genesis block.");
    }

    public String calculateHashForBlock(Block block) {
        return calculateHash(block.getIndex(),
                block.getPrevHash(),
                block.getTimestamp(),
                block.getData());
    }

    public String calculateHash(Long index, String prevHash, Date timeStamp, Data data) {
        String toHash = index+prevHash+timeStamp.toString()+data.toString();
        return Hashing.sha256()
                .hashString(toHash, StandardCharsets.UTF_8)
                .toString();
    }

    public Block generateNextBlock(Data blockData) {
        Block prevBlock = getLatestBlock();//TODO
        Long nextIndex = prevBlock.getIndex() + 1;
        Date nextTimestamp = new Date();
        String nextHash = calculateHash(nextIndex,
                prevBlock.getHash(),
                nextTimestamp,
                blockData);

        return new Block(nextIndex,
                prevBlock.getHash(),
                nextTimestamp,
                blockData,
                nextHash);
    }

    public Block getGenesisBlock() {
        return new Block(
                0L,
                "0",
                new Date(),
                new Data("Genesis src.main.java.Block"),
                null
        );
    }

    public Block getLatestBlock() {
        return this.blockchain.get(blockchain.size()-1);
    }

    public boolean isValidBlock(Block prevBlock, Block newBlock) {
        if (prevBlock.getIndex() + 1 != newBlock.getIndex()) {
            System.err.println("Invalid Index");
            return false;
        } else if (!prevBlock.getHash().equals(newBlock.getPrevHash())) {
            System.err.println("Invalid previous Hash");
            return false;
        } else if (calculateHashForBlock(newBlock).equals(newBlock.getHash())) {
            System.err.println("Invalid new Hash");
            return false;
        }

        return true;
    }

    public boolean isValidChain(ArrayList<Block> chain) {
        for(int i = 0; i < chain.size()-1; i++) {
            if (!isValidBlock(chain.get(i), chain.get(i+1))) {
                return false;
            }
        }

        return true;
    }

    public void replaceChain(ArrayList<Block> newBlocks) {
        if(isValidChain(newBlocks) && newBlocks.size() > blockchain.size()) {
            System.out.println("Received blockchain is valid. Replacing current blockchain with received blockchain");
            this.blockchain = newBlocks;
            //broadcast();
        } else {
            System.err.println("Received blockchain is invalid.");
        }
    }

    public List<Block> getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(List<Block> blockchain) {
        this.blockchain = blockchain;
    }

    public void addBlock(Block block) {
        this.blockchain.add(block);
    }
}
