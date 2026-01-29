package com.alioth4j.yamltrie;

import java.util.List;
import java.util.Optional;

public class Trie {

    private final TrieNode root = new TrieNode();

    public void insert(List<String> path, Object value) {
        TrieNode cur = root;
        for (String key : path) {
            cur = cur.children.computeIfAbsent(key, k -> new TrieNode());
        }
        cur.value = value;
    }

    public Optional<Object> search(List<String> path) {
        return search(root, path);
    }

    private Optional<Object> search(TrieNode node, List<String> path) {
        if (path.isEmpty()) {
            return Optional.ofNullable(node.value);
        }
        Optional<Object> result = Optional.empty();
        String key = path.get(0);
        List<String> nextPath = path.subList(1, path.size());
        if (key.matches("\\d+")) {
            // handle list
            int i = 0;
            while (true) {
                TrieNode nodeI = node.children.get(String.valueOf(i));
                if (nodeI == null) {
                    break;
                }
                result = search(nodeI, nextPath);
                if (result.isPresent()) {
                    return result;
                }
                i++;
            }
        } else {
            TrieNode nextNode = node.children.get(key);
            if (nextNode == null) {
                return Optional.empty();
            }
            result = search(nextNode, nextPath);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName())
          .append("{")
          .append(System.lineSeparator());
        dfsBuildString(root, sb, 0);
        sb.append("}").append(System.lineSeparator());
        return sb.toString();
    }

    private static final String INDENT = "  ";

    private void dfsBuildString(TrieNode node, StringBuilder sb, int depth) {
        if (node.value != null) {
            sb.append(INDENT.repeat(depth))
              .append("[value] ")
              .append(node.value)
              .append(System.lineSeparator());
        }

        node.children.keySet().stream()
                .sorted()
                .forEach(key -> {
                    sb.append(INDENT.repeat(depth))
                      .append(key)
                      .append(System.lineSeparator());
                    dfsBuildString(node.children.get(key), sb, depth + 1);
                });
    }

}
