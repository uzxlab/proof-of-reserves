package com.uzx;

import com.alibaba.fastjson.JSONObject;
import com.uzx.model.MerkleProof;
import com.uzx.util.CollectionUtils;
import com.uzx.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Verify whether the account assets are included in the Merkle tree published by UZX
 *
 * @author UZX
 */
public class MerkleValidator {
    /**
     * merkel_tree_bg.json
     **/
    private static final String MERKLE_TREE_UZX_FILE_PATH = "merkel_tree_uzx.json";

    public static void main(String[] args) {
        System.out.println("Merkel tree path validation start");
        // 获取"merkel_tree_uzx.json"文件内容
        String merkleJsonFile = getMerkleJsonFile();
        if (StringUtils.isBlank(merkleJsonFile)) {
            System.out.println("Merkel tree path validation failed, invalid merkle proof file");
            return;
        }

        // 获得默克尔树证明对象
        MerkleProof merkleProof = JSONObject.parseObject(merkleJsonFile, MerkleProof.class);

        // 默克尔树参数验证
        if(validate(merkleProof)){
            System.out.println("Consistent with the Merkle tree root hash. The verification succeeds");
        }else {
            System.out.println("Inconsistent with the Merkle tree root hash. The verification fails");
        }
    }

    /**
     * validate
     * @param merkleProof
     * @return
     * @author UZX
     * @date 2024-06-20
     */
    private static boolean validate(MerkleProof merkleProof){

        // self节点不能为空 并且 path节点也不能为空
        if(merkleProof.getSelf() == null || CollectionUtils.isEmpty(merkleProof.getPath())){
            return false;
        }

        // 验证self数据一致性
        if(!merkleProof.getSelf().validateSelf()){
            return false;
        }

        // 验证path参数验证
        if(!merkleProof.getPath().get(0).validatePath()){
            return false;
        }

        if(merkleProof.getPath().get(0).getRole().intValue() == merkleProof.getSelf().getRole().intValue()){
            return false;
        }

        return merkleProof.validate();
    }


    /**
     * get merkel_tree_uzx.json content
     *
     * @author UZX
     * @date 2024-06-20
     */
    private static String getMerkleJsonFile() {
        StringBuilder builder = new StringBuilder();
        try {
            Files.readAllLines(Path.of(MERKLE_TREE_UZX_FILE_PATH), StandardCharsets.UTF_8).forEach(builder::append);
            return builder.toString();
        } catch (IOException e) {
            throw new RuntimeException(MERKLE_TREE_UZX_FILE_PATH + " file does not exist");
        }
    }
}


