package com.alioth4j.yamltrie;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YamlTrieLoader {

    public YamlTrieLoader() {
    }

    public Trie load(InputStream yamlInputStream) {
        Yaml yaml = new Yaml();
        Object data = yaml.load(yamlInputStream);
        Trie trie = new Trie();
        traverse(trie, data, new ArrayList<>());
        return trie;
    }

    private void traverse(Trie trie, Object data, List<String> path) {
        if (data instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) data;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                String key = String.valueOf(entry.getKey());
                path.add(key);
                traverse(trie, entry.getValue(), path);
                path.remove(path.size() - 1);
            }
        } else if (data instanceof List) {
            List<?> list = (List<?>) data;
            for (int i = 0; i < list.size(); i++) {
                path.add(String.valueOf(i));
                traverse(trie, list.get(i), path);
                path.remove(path.size() - 1);
            }
        } else {
            trie.insert(path, data);
        }
    }

}
