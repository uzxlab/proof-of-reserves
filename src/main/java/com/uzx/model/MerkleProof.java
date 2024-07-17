package com.uzx.model;

import java.util.List;
/**
 * MerkleProof
 * @author UZX
 * @date 2024-06-20
 */
public class MerkleProof {
    private List<TreeNode> path;
    private TreeNode self;

    public List<TreeNode> getPath() {
        return path;
    }

    public TreeNode getSelf() {
        return self;
    }

    public void setPath(List<TreeNode> path) {
        this.path = path;
    }

    public void setSelf(TreeNode self) {
        this.self = self;
    }

    /**
     * validate
     * A new root is being introduced through the path and self provided by the user. Compare with the root in the path
     * @return {@link boolean }
     * @author UZX
     * @date 2024-06-20
     */
    public boolean validate() {
        TreeNode newRoot = new MerKelTree().buildMerkelTreeRoot(path, self);
        TreeNode oldRoot = path.get(path.size() - 1);

        System.out.printf("Generator Root BTC balance : %s ,merkel_tree_uzx Root BTC balance in file: %s%n", newRoot.getBalances().get("BTC"), oldRoot.getBalances().get("BTC"));
        System.out.printf("Generator Root ETH balance : %s ,merkel_tree_uzx Root ETH balance in file: %s%n", newRoot.getBalances().get("ETH"), oldRoot.getBalances().get("ETH"));
        System.out.printf("Generator Root USDT balance : %s ,merkel_tree_uzx Root USDT balance in file: %s%n", newRoot.getBalances().get("USDT"), oldRoot.getBalances().get("USDT"));
        System.out.printf("Generator Root MerkelLeaf : %s ,merkel_tree_uzx Root MerkelLeaf in file: %s%n", newRoot.getMerkelLeaf(), oldRoot.getMerkelLeaf());

        if (newRoot.getMerkelLeaf().equals(oldRoot.getMerkelLeaf()) && newRoot.validateEqualsBalances(oldRoot) && newRoot.getLevel().equals(oldRoot.getLevel())) {
            return true;
        }
        return false;
    }

}
