package com.alioth4j.yamltrie;

import java.util.HashMap;
import java.util.Map;

class TrieNode {

    Map<String, TrieNode> children = new HashMap<>();

    Object value = null;

    TrieNode() {
    }

}
