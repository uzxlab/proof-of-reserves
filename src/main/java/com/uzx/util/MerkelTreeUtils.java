package com.uzx.util;

import com.alibaba.fastjson.JSONObject;
import com.uzx.constants.MerkelTreeConstants;
import com.uzx.model.TreeNode;

/**
 * MerkelTreeUtils
 * @author UZX
 * @date 2024-06-20
 */
public class MerkelTreeUtils {

    /**
     * 生成节点hash
     * @param treeNode
     */
    public static String createMerkelNodeLeaf(TreeNode treeNode) {
        StringBuffer merkelHash = new StringBuffer();
        merkelHash.append(treeNode.getEncryptUid())
                .append(MerkelTreeConstants.COMMA)
                .append(treeNode.getNonce())
                .append(MerkelTreeConstants.COMMA)
                .append(JSONObject.toJSONString(treeNode.getBalances()));
        return EncryptionUtils.sha256(merkelHash.toString()).substring(0,16);
    }

    /**
     * createMerkelParentLeaf
     * @param left
     * @param right
     * @param parent
     * @return {@link String }
     * @author UZX
     * @date 2024-06-20
     */
    public static String createMerkelParentLeaf(TreeNode left, TreeNode right, TreeNode parent) {
        String hashId = left.getMerkelLeaf() +
                right.getMerkelLeaf() +
                MerkelTreeConstants.COMMA +
                JSONObject.toJSONString(parent.getBalances()) +
                MerkelTreeConstants.COMMA +
                (parent.getLevel());
        return EncryptionUtils.sha256(hashId).substring(0, 16);
    }
}
