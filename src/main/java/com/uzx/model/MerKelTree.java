package com.uzx.model;

import com.uzx.constants.TreeNodeRoleConstants;
import com.uzx.util.MerkelTreeUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默克尔树
 * @author UZX
 * @date 2024-06-20
 */
public class MerKelTree {

    /**
     * build merkelTree root node
     * @param path
     * @param self
     * @return {@link TreeNode }
     * @author UZX
     * @date 2024-06-20
     */
    public TreeNode buildMerkelTreeRoot(List<TreeNode> path, TreeNode self) {
        if (path.size() <= 1) {
            throw new IllegalArgumentException("Must be at least two leafs to construct a Merkle tree");
        }

        TreeNode parent = createParentTreeNode(path.get(0), self);

        for (int i = 1; i < path.size() - 1; i++) {
            self = parent;
            parent = createParentTreeNode(path.get(i), self);
        }

        return parent;
    }

    /**
     * createParentTreeNode
     * @param friend
     * @param self
     * @return {@link TreeNode }
     * @author UZX
     * @date 2024-06-20
     */
    TreeNode createParentTreeNode(TreeNode friend, TreeNode self) {
        TreeNode parent;
        if (TreeNodeRoleConstants.LEFT_NODE.equals(friend.getRole())) {
            // friend 是左节点
            parent = constructInternalNode(friend, self);
        }else {
            // friend 是右节点或者空节点
            parent = constructInternalNode(self, friend);
        }

        return parent;
    }

    /**
     * constructInternalNode
     * @param left
     * @param right
     * @return
     */
    private TreeNode constructInternalNode(TreeNode left, TreeNode right) {
        TreeNode parent = new TreeNode();

        if (right == null) {
            right = createEmptyTreeNode(left);
        }
        parent.mergeAsset(left);
        parent.mergeAsset(right);

        // 左节点hash+右节点hash+parent资产+左节点层级
        parent.setLevel(left.getLevel() - 1);
        parent.setMerkelLeaf(MerkelTreeUtils.createMerkelParentLeaf(left, right, parent));
        return parent;
    }

    /**
     * clearAssetsMap
     * @param right
     * @return {@link java.util.Map<java.lang.String,java.math.BigDecimal> }
     * @author UZX
     * @date 2024-06-20
     */
    private static Map<String, BigDecimal> clearAssetsMap(TreeNode right) {
        Map<String, BigDecimal> assetsMap = right.getBalances();
        Map<String, BigDecimal> result = new HashMap<>();
        assetsMap.keySet().forEach(coinName -> result.put(coinName, BigDecimal.ZERO));
        return result;
    }

    /**
     * createEmptyTreeNode
     * @param source
     * @return {@link TreeNode }
     * @author UZX
     * @date 2024-06-20
     */
    private TreeNode createEmptyTreeNode(TreeNode source){
        TreeNode target = new TreeNode();

        target.setAuditId(source.getAuditId());
        target.setNonce(source.getNonce());
        target.setMerkelLeaf(source.getMerkelLeaf());
        target.setLevel(source.getLevel());
        target.setRole(source.getRole());
        target.setEncryptUid(source.getEncryptUid());
        target.setRole(TreeNodeRoleConstants.EMPTY_NODE);
        target.setBalances(clearAssetsMap(target));

        return target;
    }

}
